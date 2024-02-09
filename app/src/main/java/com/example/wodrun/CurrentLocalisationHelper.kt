package com.example.wodrun

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority

//Pas de check si bien la position car appeler seulement si position est active
@SuppressLint("MissingPermission")
fun getCurrentLocalisation(context: Context, callback: (latitude: Double, longitude: Double) -> Unit) {
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
        location?.let {
            // Récupérer les coordonnées de la dernière position connue
            val latitude = it.latitude
            val longitude = it.longitude
            callback(latitude, longitude)
            Log.d("lastLocation", "Latitude: $latitude, Longitude: $longitude") }
    }
    val locationRequest = LocationRequest.create();
    locationRequest.priority = Priority.PRIORITY_HIGH_ACCURACY
    locationRequest.interval = 5000
    locationRequest.smallestDisplacement=20f
    val locationCallback = object : LocationCallback(){
        override fun onLocationResult(locationResult: LocationResult) {
            for(location in locationResult.locations){
                //Toast.makeText(context, "Nouvelle position connue : ${location?.latitude}, ${location?.longitude}", Toast.LENGTH_SHORT).show()
                Log.d("onLocationResult", "Nouvelle position connue : ${location?.latitude}, ${location?.longitude}") }
            }
        }
    fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
}