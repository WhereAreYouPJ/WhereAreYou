package com.whereareyounow.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.naver.maps.geometry.LatLng
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationUtil @Inject constructor(
    private val context: Context
) {
    fun getCurrentLocation(callback: suspend (LatLng) -> Unit) {
        val request = LocationRequest.Builder(500)
            .setMaxUpdates(2)
            .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
            .build()

        val client = LocationServices.getFusedLocationProviderClient(context)
        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.locations.lastOrNull()?.let {
                    Log.e("location", "${it.latitude}, ${it.longitude}")
//                    latLng.latitude = it.latitude
//                    latLng.longitude = it.longitude
                    CoroutineScope(Dispatchers.Default).launch {
                        callback(LatLng(it.latitude, it.longitude))
                    }
                }
                client.removeLocationUpdates(this)
            }
        }
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        client.requestLocationUpdates(
            request,
            locationCallback,
            Looper.getMainLooper()
        )
    }
}

data class Coordinate(
    var latitude: Double,
    var longitude: Double
)