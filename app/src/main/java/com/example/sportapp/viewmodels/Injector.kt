package com.example.sportapp.viewmodels

import android.content.Context
import com.example.sportapp.storage.database
import com.example.sportapp.storage.repository
import com.google.android.gms.location.LocationServices
import com.example.sportapp.location.LocationManager

object Injector {

    private fun getRepository(context: Context): repository {
        return repository.returnInstance((database.getDatabase(context.applicationContext).dao()))
    }

    private fun getLocationManager(context: Context): LocationManager {
        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context.applicationContext)
        return LocationManager(fusedLocationProviderClient)
    }

    fun provideModelFactory(context: Context): Factory {
        val repository = getRepository(context)
        val locationManager = getLocationManager(context)

        return Factory(
            repository = repository,
            locationManager = locationManager
        )
    }
}