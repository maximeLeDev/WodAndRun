package com.example.wodrun

import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView

class MenuEntryView(context : Context, attrs: AttributeSet) : FrameLayout(context, attrs) {

    lateinit var destination : Class<*>

    init {
      inflate(context, R.layout.view_menu_entry, this)

        this.setOnClickListener{
            val intent = Intent(context, destination)
            context.startActivity(intent)
        }
        this.setOnTouchListener { v: View, m: MotionEvent ->
            // Perform tasks here
            if (m.action == MotionEvent.ACTION_DOWN) {
                v.alpha = 0.5f
                true
            } else if (m.action == MotionEvent.ACTION_UP) {
                v.alpha = 1.0f
                true
            }
            false
        }
    }
    fun changeImage(resId: Int) {
        findViewById<ImageView>(R.id.imageMenuEntry).setImageResource(resId)
    }
    fun changeText(resId: Int) {
        findViewById<TextView>(R.id.textMenuEntry).setText(resId)
    }
}