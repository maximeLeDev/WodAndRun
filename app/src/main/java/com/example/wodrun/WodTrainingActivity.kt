package com.example.wodrun

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.room.Room
import com.example.myapplication.SQL.PRDao
import com.example.wodrun.SQL.AppDatabase
import com.example.wodrun.model.Exo
import com.example.wodrun.service.ExosListAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class WodTrainingActivity : AppCompatActivity() {

    lateinit var prDao : PRDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wod_training)

        val screenTitle = findViewById<TitleView>(R.id.screenTitle)
        screenTitle.changeImageLeft(R.drawable.crossfitlogo)
        screenTitle.changeImageRigth(R.drawable.crossfitlogo)
        screenTitle.changeText(R.string.wod)

        val db = Room.databaseBuilder(
            this,
            AppDatabase::class.java, "database-name"
        ).build()
        prDao = db.prDao()

        executeCall()
    }

    private var exosList: List<Exo>? = null
    private fun executeCall() {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val response = ApiClient.apiService.getExos()
                if (response.isSuccessful && response.body() != null) {
                    exosList = response.body()
                    updateListView()
                } else {
                    Toast.makeText(
                        this@WodTrainingActivity,
                        "Error Occurred: ${response.message()}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } catch (e: Exception) {
                Toast.makeText(
                    this@WodTrainingActivity,
                    "Error Occurred: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
    private fun updateListView() {
        // Vérifier si exosList est non null
        Log.d("ExosList", exosList.toString())
        if (exosList != null) {
            // Créer un adaptateur pour la ListView avec les données de exosList
            val adapter = ExosListAdapter(this@WodTrainingActivity, exosList!!)

            // Trouver la ListView dans le layout de l'activité
            val listView: ListView = findViewById(R.id.listeExos)

            // Définir l'adaptateur sur la ListView
            listView.adapter = adapter
        } else {
            // Gérer le cas où exosList est null (par exemple, si les données n'ont pas encore été récupérées)
            Toast.makeText(
                this@WodTrainingActivity,
                "Data was not available now",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}