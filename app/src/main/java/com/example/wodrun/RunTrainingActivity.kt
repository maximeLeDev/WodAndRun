package com.example.wodrun

import GlobalVariables
import android.app.AlertDialog
import android.content.ClipDescription
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import com.example.myapplication.SQL.runPointDao
import com.example.wodrun.SQLRun.runPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.osmdroid.config.Configuration
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.ItemizedIconOverlay
import org.osmdroid.views.overlay.MapEventsOverlay
import org.osmdroid.views.overlay.Marker

class RunTrainingActivity : AppCompatActivity() {
    private lateinit var mapView: MapView
    private lateinit var runPointDao: runPointDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_run_training)

        runPointDao = MainActivity.db.runPointDao()

        val screenTitle = findViewById<TitleView>(R.id.screenTitle)
        screenTitle.changeImageLeft(R.drawable.runlogo)
        screenTitle.changeImageRigth(R.drawable.runlogo)
        screenTitle.changeText(R.string.run)

        /*Initialisation de la MAP*/
        mapView = findViewById<MapView>(R.id.runMap)
        mapView.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE)
        mapView.setMultiTouchControls(true)

        Configuration.getInstance().setUserAgentValue(getPackageName())
        val mapController = mapView.controller
        /*Zoom de la map*/
        mapController.setZoom(15.0)
        /*Centre de la map lors du chargement de la vue*/
        getCurrentLocalisation(this) { latitude, longitude ->
            // Utilisez les coordonnées de latitude et de longitude ici
            mapController.setCenter(GeoPoint(latitude, longitude))
        }
        //mapController.setCenter(GeoPoint(46.65835963858322, 0.3604307768018405))
        /*Bouton pour current location du user*/
        val currentLocButton = findViewById<Button>(R.id.button)
        currentLocButton.setOnClickListener{
            getCurrentLocalisation(this) { latitude, longitude ->
                // Utilisez les coordonnées de latitude et de longitude ici
                mapController.setCenter(GeoPoint(latitude, longitude))
            }
        }
        //Pour affcher toutes les courses
        displayMarker()
        //Pour ecouter les nouvelles courses
        listenToNewMarker()
    }

    private fun listenToNewMarker(){
        val eventsOverlay = MapEventsOverlay(object : MapEventsReceiver {
            override fun singleTapConfirmedHelper(p: GeoPoint?): Boolean{
                return false
            }
            //Affiche une boite de dialogue quand longPress
            override fun longPressHelper(p: GeoPoint?): Boolean {
                //Affiche la boite de dialogue
                showMarkerDialog(p)
                return true
            }
        })
        mapView.overlays.add(eventsOverlay)
    }

    private fun showMarkerDialog(p: GeoPoint?) {
        //initialise la boite de dialogue avec un layout
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_marker, null)
        val kmEditText = dialogView.findViewById<EditText>(R.id.kmEditText)
        val timeEditText = dialogView.findViewById<EditText>(R.id.timeEditText)

        val alertDialog = AlertDialog.Builder(this)
            .setTitle("Add parcours")
            .setView(dialogView)
            .setPositiveButton("Add") { dialog, _ ->
                val title = "Date : ${GlobalVariables.date}"
                val description = "Kilometers : ${kmEditText.text.toString()} " +
                        "Time : ${timeEditText.text.toString()}"
                addMarker(p, title, description, "showMarkerDialog")
                dialog.dismiss()
            }
            .setNegativeButton("Return") { dialog, _ ->
                dialog.dismiss()
            }
            .create()

        alertDialog.show()
    }

    //Ajoute un point sur la carte + enregistrement en base avec ROOM
    private fun addMarker(geoPoint: GeoPoint?, title: String, description: String, callingFunction: String, subDescription: String = "toto") {
        val markerRun = Marker(mapView)
        val timestamp = System.currentTimeMillis()
        markerRun.title = title
        markerRun.snippet = description
        markerRun.position = geoPoint
        //si nouveau marker pas en base alors execute subDescription est timestamp sinon subdescription egale subdescription ecrit en base
        //permet de faire la suppression d'un marker en base
        when(callingFunction){
            "showMarkerDialog" -> markerRun.subDescription = timestamp.toString()
            else -> markerRun.subDescription = subDescription
        }
        markerRun.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        mapView.overlays.add(markerRun)
        mapView.invalidate()
        /*Ajout en base*/
        GlobalScope.launch(Dispatchers.Main) {
            //si nouveau marker pas en base alors execute sinon rien
            when(callingFunction){
                "showMarkerDialog" -> runPointDao.insertAll(
                    runPoint(0, geoPoint!!.longitude, geoPoint.latitude, title, description, timestamp.toString())
                )

            }
            markerRun.setOnMarkerClickListener { marker, mapView ->
                // Afficher une boîte de dialogue de confirmation pour la suppression du marqueur
                AlertDialog.Builder(this@RunTrainingActivity)
                    .setTitle("Information")
                    .setMessage("$title - $description")
                    .setPositiveButton("Delete") { dialog, which ->
                        // Supprimer le marqueur de la carte
                        mapView.overlays.remove(marker)
                        GlobalScope.launch(Dispatchers.Main) {
                            runPointDao.delete(runPointDao.getMarkBySubDescription(marker.subDescription)!!)
                        }
                        mapView.invalidate()
                        dialog.dismiss()
                        // Supprimer le marqueur de la base de données ou effectuer d'autres opérations nécessaires
                    }
                    .setNegativeButton("Return"){ dialog, _ ->
                        //Close alertDialog without instruction
                        dialog.dismiss()
                    }
                    .show()
                true // Indique que le clic sur le marqueur a été géré
            }
        }
        vibratePhone()
    }

    //Affiche tout les points en base ROOM sur la carte
    private fun displayMarker(){
        GlobalScope.launch(Dispatchers.Main) {
            //recuperation de toute les courses
            val runs = runPointDao.getAllRun()
            withContext(Dispatchers.Main){
                //Pour éviter de répéter le code, j'aurais pu utiliser la méthode addmarker ici. Je regrette de ne pas l'avoir fait...
                //addMarker(GeoPoint(run.latitudeRunPoint,run.longitudeRunPoint), run.titleRunPoint, run.descriptionRunPoint)
                //C'est ainsi que je l'aurais fait
                //je l'ai fait au final
                for(run in runs){
                    /*val markerRun = Marker(mapView)
                    markerRun.title = run.titleRunPoint
                    markerRun.snippet = run.descriptionRunPoint
                    markerRun.position = GeoPoint(run.latitudeRunPoint,run.longitudeRunPoint)
                    markerRun.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                    mapView.overlays.add(markerRun)*/
                    addMarker(GeoPoint(run.latitudeRunPoint,run.longitudeRunPoint), run.titleRunPoint, run.descriptionRunPoint, "displayMarker", run.subDescriptionRunPoint)
                }
                mapView.invalidate()
            }
        }

    }

    //vibration de l'appareil
    private fun vibratePhone(){
        val vibration = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
            val vibratorManager =
                getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator
        }else{
            @Suppress("DEPRECATION")
            getSystemService(VIBRATOR_SERVICE) as Vibrator
        }
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            vibration.vibrate(VibrationEffect.createOneShot(150, VibrationEffect.DEFAULT_AMPLITUDE))
        }else{
            @Suppress("DEPRECATION")
            vibration.vibrate(150)
        }


    }
}