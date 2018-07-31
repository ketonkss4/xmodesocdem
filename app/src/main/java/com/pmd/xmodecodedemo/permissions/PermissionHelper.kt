package com.pmd.xmodecodedemo.permissions

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat

/**
 * Abstracts the permissions boilerplate code away in a neat little API
 */
class PermissionHelper(private val activity: Activity) {

    interface PermissionRequestListener {
        fun onUserDenial()
        fun onNeedsPermission()
        fun onPermanentDenial()
        fun onPermissionGranted()
    }

    /**
     * Requests Location permission settings and notifies PermissionRequestListener of results
     */
    fun requestLocationTrackingPermission(listener: PermissionRequestListener) {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                            Manifest.permission.ACCESS_FINE_LOCATION)) {
                listener.onUserDenial()
            } else {
                if (isFirstTimeRequestingPermission(
                                activity.applicationContext,
                                Manifest.permission.ACCESS_FINE_LOCATION
                        )) {

                    onFirstTimeRequestingPermission(
                            activity.applicationContext,
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            false
                    )

                    listener.onNeedsPermission()
                }else{
                    //Permission disabled by device policy or user denied permanently
                    listener.onPermanentDenial()
                }
            }
        } else {
            listener.onPermissionGranted()
            //permission already obtained
        }
    }
}