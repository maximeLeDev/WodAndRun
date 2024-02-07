package com.example.wodrun

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class RunTrainingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_run_training)

        val screenTitle = findViewById<TitleView>(R.id.screenTitle)
        screenTitle.changeImageLeft(R.drawable.runlogo)
        screenTitle.changeImageRigth(R.drawable.runlogo)
        screenTitle.changeText(R.string.run)
    }
}