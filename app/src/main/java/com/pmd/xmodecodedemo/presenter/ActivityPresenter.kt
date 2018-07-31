package com.pmd.xmodecodedemo.presenter

import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import com.pmd.xmodecodedemo.BuildConfig
import com.pmd.xmodecodedemo.worker.LocationTracker


class ActivityPresenter : BroadcastReceiver() {

    companion object {
        const val LOCATION_ACCESS_REQ_CODE = 0xD
    }

    /**
     *  Handles touch event on the text view from the activity, if user selected the first
     *  half of the text view containing "hello" the event will propagate to the click action
     */
    fun handleTextViewOnTouchEvent(motionEvent: MotionEvent, view: View): Boolean {
        //if touch is on first half of view, touch event will be passed on
        return isTouchOnFirstHalfOfView(motionEvent.x, view.width)
    }

    /**
     * Displays a toast upon receiving click event
     */
    fun handleTextViewClickEvent(context: Context) {
        Toast.makeText(context.applicationContext, "Hello!", Toast.LENGTH_SHORT).show()
    }


    /**
     * Returns true if touch event is made on the first half of the view
     */
    private fun isTouchOnFirstHalfOfView(pointerX: Float, width: Int): Boolean {
        val halfScreenWidth = width / 2
        return pointerX > halfScreenWidth
    }

    /**
     * Calls requestRecurringLocationTrackingTask upon receiving BOOT_COMPLETED service
     * receives LOCKED_BOOT_COMPLETED intent in order to start service before device unlock
     */
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "android.intent.action.BOOT_COMPLETED" ||
                intent.action == "android.intent.action.LOCKED_BOOT_COMPLETED") {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                requestRecurringLocationTrackingTask(context)
            }
        }
    }

}

/**
 * Creates Alarm Service for LocationTracker to periodically report location based on the
 * Build Variant
 * is static to enable being called from the BOOT_COMPLETED service
 */
fun requestRecurringLocationTrackingTask(context: Context) {
    val locTrackerIntent = Intent(context, LocationTracker::class.java)
    val pendingIntent = PendingIntent.getBroadcast(context, 0, locTrackerIntent, 0)
    val alarmManager: AlarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val interval = (BuildConfig.TIME_INTERVAL).toLong()
    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent)
}
