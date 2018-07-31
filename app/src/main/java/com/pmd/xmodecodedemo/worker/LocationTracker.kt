package com.pmd.xmodecodedemo.worker

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.google.android.gms.location.LocationServices

class LocationTracker : BroadcastReceiver() {


    /**
     * This method queries the last known location of the user when triggered
     * by a service. Uses the fusedLocationProviderClient to provide accurate location
     * info using both network and GPS. It also will simply return the last known location
     * if those components are not able to be used
     */
    @SuppressLint("MissingPermission")
    override fun onReceive(context: Context, intent: Intent) {
        //Update location
        Log.v("Location Tracker ", "Update Attempt")

        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
        fusedLocationProviderClient.lastLocation.addOnCompleteListener { task ->
            val result = task.result
            if (task.isSuccessful && result != null) {
                Toast.makeText(context, " You are at ${result.longitude}, ${result.latitude} !", Toast.LENGTH_LONG).show()
                //TODO To persist the location data we receive here we can use any of the following ways
                //TODO Save to FILE, Save to Preferences, Save to SQLite Database
                //TODO Due to the simple nature of this data I would elect to simply save it to Preferences
                //TODO as a key value pair of Time_Stamp and Location Coordinates. Preferences is perfect for
                //TODO this simple data storage
            } else if (!task.isSuccessful) {
                Log.v("Location Tracker ", "Error occurred")
            }
        }

    }
}