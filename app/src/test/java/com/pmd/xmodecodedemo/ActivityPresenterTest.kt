package com.pmd.xmodecodedemo

import android.view.MotionEvent
import android.view.View
import junit.framework.Assert
import org.junit.Test
import org.mockito.Mockito

class ActivityPresenterTest {

    val activityPresenter = ActivityPresenter()
    @Test
    fun returnFalseWhenPointerCoordinateIsLessThanViewWidth() {
        val motionEvent = Mockito.mock(MotionEvent::class.java)
        val pointerCoordinate = 10f
        Mockito.`when`(motionEvent.x).thenReturn(pointerCoordinate)

        val view = Mockito.mock(View::class.java)
        val viewWidth = 20
        Mockito.`when`(view.width).thenReturn(viewWidth)

        Assert.assertFalse(activityPresenter.handleTextViewOnTouchEvent(motionEvent, view))
    }
}