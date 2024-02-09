package com.example.wodrun

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*MAP + GPS*/
        if(checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager. PERMISSION_GRANTED ||
            checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            //on a la permission
            getCurrentLocalisation(this) { latitude, longitude ->
                // Utilisez les coordonnées de latitude et de longitude ici
                Log.d("LocationCallback", "Latitude: $latitude, Longitude: $longitude")
            }
        }else{
            //on a pas la permission
            if(shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)||
                shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                //Afficher importance
                //redemander permission a la fin du dialogue
                requestPermission()
            }else{
                //Demander Permission
                requestPermission()
            }
        }
        /*FIN MAP + GPS*/

        val appTitle = findViewById<TitleView>(R.id.appTitle)
        appTitle.changeImageLeft(R.drawable.crossfitlogo)
        appTitle.changeImageRigth(R.drawable.runlogo)
        appTitle.changeText(R.string.app_name)

        val menuEntryRun = findViewById<MenuEntryView>(R.id.menuEntryRun)
        menuEntryRun.destination = RunTrainingActivity::class.java;
        menuEntryRun.changeImage(R.drawable.pisteathletisme)
        menuEntryRun.changeText(R.string.run)

        val menuEntryCrossfit = findViewById<MenuEntryView>(R.id.menuEntryCrossfit)
        menuEntryCrossfit.destination = WodTrainingActivity::class.java;
        menuEntryCrossfit.changeImage(R.drawable.sallecrossfit)
        menuEntryCrossfit.changeText(R.string.wod)
    }
    fun requestPermission(){
        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                    //Toast.makeText(this, "FINE OK", Toast.LENGTH_SHORT).show()
                    getCurrentLocalisation(this) { latitude, longitude ->
                        // Utilisez les coordonnées de latitude et de longitude ici
                        Log.d("LocationCallback", "Latitude: $latitude, Longitude: $longitude")
                    }
                }
                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    //Toast.makeText(this, "COARSE OK", Toast.LENGTH_SHORT).show()
                    getCurrentLocalisation(this) { latitude, longitude ->
                        // Utilisez les coordonnées de latitude et de longitude ici
                        Log.d("LocationCallback", "Latitude: $latitude, Longitude: $longitude")
                    }
                }
                else -> {
                    Toast.makeText(this, "LOCATION REFUSED, APPLICATION MAY DECREASE HER PERFORMANCE", Toast.LENGTH_SHORT).show()
                }
            }
        }
        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }
}