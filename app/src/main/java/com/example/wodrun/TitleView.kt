package com.example.wodrun

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView

class TitleView(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {
    init {
        inflate(context, R.layout.view_title, this)
    }
    fun changeImageLeft(resId: Int) {
        findViewById<ImageView>(R.id.imageTitleLeft).setImageResource(resId)
    }
    fun changeImageRigth(resId: Int) {
        findViewById<ImageView>(R.id.imageTitleRight).setImageResource(resId)
    }
    fun changeText(resId: Int) {
        findViewById<TextView>(R.id.textViewTitle).setText(resId)
    }
}