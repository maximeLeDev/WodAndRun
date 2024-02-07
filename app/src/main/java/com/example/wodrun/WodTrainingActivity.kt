package com.example.wodrun

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class WodTrainingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wod_training)

        val screenTitle = findViewById<TitleView>(R.id.screenTitle)
        screenTitle.changeImageLeft(R.drawable.crossfitlogo)
        screenTitle.changeImageRigth(R.drawable.crossfitlogo)
        screenTitle.changeText(R.string.wod)
    }

}