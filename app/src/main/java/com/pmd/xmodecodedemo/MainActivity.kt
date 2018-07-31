package com.pmd.xmodecodedemo

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private val presenter: ActivityPresenter = ActivityPresenter()

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val textView = findViewById<TextView>(R.id.id_text_view)
        textView.setOnTouchListener { view, motionEvent ->
            presenter.handleTextViewOnTouchEvent(motionEvent, view)
        }
        textView.setOnClickListener { presenter.handleTextViewClickEvent(this) }
    }

}
