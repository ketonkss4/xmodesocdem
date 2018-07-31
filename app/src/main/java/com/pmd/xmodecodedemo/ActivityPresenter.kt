package com.pmd.xmodecodedemo

import android.content.Context
import android.view.MotionEvent
import android.view.View
import android.widget.Toast


class ActivityPresenter {


    fun handleTextViewOnTouchEvent(motionEvent: MotionEvent, view: View): Boolean {
        //if touch is on first half of view, touch event will be passed on
        return isTouchOnFirstHalfOfView(motionEvent.x, view.width)
    }

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
}