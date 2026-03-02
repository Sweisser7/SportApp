package com.example.sportapp.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Looper
import com.google.android.gms.location.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class LocationManager(
    private val context: Context,
    private val fusedLocationProviderClient: FusedLocationProviderClient
) {

    @SuppressLint("MissingPermission") // Permissions werden im ViewModel/UI geprüft
    fun getLocationUpdates(): Flow<Location> = callbackFlow {

        // 1. Konfiguration: Wie oft und wie genau?
        val locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY, 2000L // Intervall: 2 Sekunden
        ).apply {
            setMinUpdateDistanceMeters(1f) // Nur Updates bei min. 1 Meter Bewegung
        }.build()

        // 2. Der Callback, der die Daten in den Flow schickt
        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                super.onLocationResult(result)
                result.lastLocation?.let { location ->
                    trySend(location) // Schickt die Location in den Stream
                }
            }
        }

        // 3. Registrierung beim FusedLocationProvider
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )

        // 4. Cleanup: Wenn der Flow nicht mehr beobachtet wird (z.B. Stop gedrückt)
        awaitClose {
            fusedLocationProviderClient.removeLocationUpdates(locationCallback)
        }
    }
}