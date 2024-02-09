package com.example.wodrun

import GlobalVariables
import android.app.AlertDialog
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
import org.osmdroid.config.Configuration
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.MapEventsOverlay
import org.osmdroid.views.overlay.Marker

class RunTrainingActivity : AppCompatActivity() {
    private lateinit var mapView: MapView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_run_training)

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

        listenToNewMarker()
    }

    private fun listenToNewMarker(){
        val eventsOverlay = MapEventsOverlay(object : MapEventsReceiver {
            override fun singleTapConfirmedHelper(p: GeoPoint?): Boolean{
                return false
            }

            override fun longPressHelper(p: GeoPoint?): Boolean {
                showMarkerDialog(p)
                return true
            }
        })
        mapView.overlays.add(eventsOverlay)
    }

    private fun showMarkerDialog(p: GeoPoint?) {
        val inputTextDescription = EditText(this)
        val alertDialog = AlertDialog.Builder(this)
            .setTitle("Ajouter un marqueur")
            .setView(inputTextDescription)
            .setPositiveButton("Ajouter") { dialog, _ ->
                val title = "Run the ${GlobalVariables.date}"
                val description = inputTextDescription.text.toString()
                addMarker(p, title, description)
                dialog.dismiss()
            }
            .setNegativeButton("Annuler") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
        alertDialog.show()
    }

    private fun addMarker(geoPoint: GeoPoint?, title: String, description: String) {
        val startMarker = Marker(mapView)
        startMarker.title = title
        startMarker.snippet = description
        startMarker.position = geoPoint
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        mapView.overlays.add(startMarker)
        mapView.invalidate()
        vibratePhone()
    }

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
    /*private fun listenToNewMarker(){
        val eventsOverlay = MapEventsOverlay(object : MapEventsReceiver {
            override fun singleTapConfirmedHelper(p: GeoPoint?): Boolean{
                return false
            }

            override fun longPressHelper(p: GeoPoint?): Boolean {
                val startMarker = Marker(mapView)
                startMarker.title = "NOUVEAU POINT"
                startMarker.position = p
                startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                mapView.overlays.add(startMarker)
                mapView.invalidate()
                vibratePhone()
                return true
            }
        })
        mapView.overlays.add(eventsOverlay)
    }

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


    }*/
}

/*
* val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH) + 1 // Les mois commencent à 0
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    val date = "$year-$month-$day"
* */