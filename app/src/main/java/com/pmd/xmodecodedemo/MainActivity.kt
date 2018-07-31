package com.pmd.xmodecodedemo

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.pmd.xmodecodedemo.permissions.PermissionHelper
import com.pmd.xmodecodedemo.presenter.ActivityPresenter
import com.pmd.xmodecodedemo.presenter.ActivityPresenter.Companion.LOCATION_ACCESS_REQ_CODE
import com.pmd.xmodecodedemo.presenter.requestRecurringLocationTrackingTask


class MainActivity : AppCompatActivity(), PermissionHelper.PermissionRequestListener {
    private val presenter: ActivityPresenter = ActivityPresenter()

    /**
     * In this method the main functions of the activity are setup and initiated.
     * The textview is treated with  touchListener and clickLister combo to ensure it
     * only responds to the right touch and the permission for Location Tracking is Obtained
     */
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val textView = findViewById<TextView>(R.id.id_text_view)
        textView.setOnTouchListener { view, motionEvent ->
            presenter.handleTextViewOnTouchEvent(motionEvent, view)
        }
        textView.setOnClickListener { presenter.handleTextViewClickEvent(this) }
        val permissionHelper = PermissionHelper(this)
        permissionHelper.requestLocationTrackingPermission(this)
    }


    override fun onUserDenial() {
        //show user rationale for permission and prompt re-request
    }

    /**
     * Uses system prompt to obtain user permission
     */
    override fun onNeedsPermission() {
        ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_ACCESS_REQ_CODE)
    }

    /**
     * Notifies user that permission is disabled or otherwise denied
     */
    override fun onPermanentDenial() {
        //notify user of permanent denial
        Toast.makeText(applicationContext, "Permission is disabled by policy or settings", Toast.LENGTH_LONG).show()
    }

    /**
     * Requests start of Location Tracking task upon receiving User Permission
     */
    override fun onPermissionGranted() {
        requestRecurringLocationTrackingTask(this)
    }

    /**
     * Handles result of user permission request
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == LOCATION_ACCESS_REQ_CODE) {
            if (grantResults.size < 0) {
                Log.v("Permission Result: ", "Permission result canceled")
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                onPermissionGranted()
            } else {
                //permission denied warn user
                onUserDenial()
            }
        }
    }
}
