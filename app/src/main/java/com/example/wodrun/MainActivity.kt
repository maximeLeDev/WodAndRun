package com.example.wodrun

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val appTitle = findViewById<TitleView>(R.id.appTitle)
        appTitle.changeImageLeft(R.drawable.crossfitlogo)
        appTitle.changeImageRigth(R.drawable.runlogo)
        appTitle.changeText(R.string.app_name)

        val menuEntryRun = findViewById<MenuEntryView>(R.id.menuEntryRun)
        menuEntryRun.destination = RunTrainingActivity::class.java;
        menuEntryRun.changeImage(R.drawable.pisteathletisme)
        menuEntryRun.changeText(R.string.run)

        val menuEntryCrossfit = findViewById<MenuEntryView>(R.id.menuEntryCrossfit)
        menuEntryCrossfit.destination = WodTrainingActivity::class.java;
        menuEntryCrossfit.changeImage(R.drawable.sallecrossfit)
        menuEntryCrossfit.changeText(R.string.wod)
    }
}