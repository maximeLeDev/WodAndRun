package com.example.wodrun

import android.content.Context
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.example.wodrun.model.Exo

fun showDialog(context: Context, exoName: String?, exo: Exo?, callback: (Exo, String) -> Unit) {
    val inputText = EditText(context)
    AlertDialog.Builder(context)
        .setTitle(exoName)
        .setView(inputText)
        .setPositiveButton("OK"){ dialog, _ ->
            //take inputText val in newPR
            val newPR = inputText.text.toString()
            callback(exo!!,newPR)
            //Close alertDialog
            dialog.dismiss()
        }
        .setNegativeButton("Not OK"){ dialog, _ ->
            //Close alertDialog without instruction
            dialog.dismiss()
        }
        .create()
        .show()
}