package com.example.wodrun.service

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import com.example.wodrun.R
import com.example.wodrun.model.Exo

import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ExosListAdapter(context: Context, private val exos: List<Exo>) : ArrayAdapter<Exo>(context, R.layout.item_exo, exos) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val listItemView = convertView ?: inflater.inflate(R.layout.item_exo, parent, false)

        // Récupérer l'élément Exo à cette position
        val currentExo = exos[position]

        // Mettre à jour les vues dans le layout de l'élément de liste avec les données de l'objet Exo
        val itemNameTextView = listItemView.findViewById<TextView>(R.id.exoName)
        itemNameTextView.text = currentExo.name
        val itemTypeTextView = listItemView.findViewById<TextView>(R.id.exoType)
        itemTypeTextView.text = currentExo.type
        val itemImageView = listItemView.findViewById<ImageView>(R.id.exoImage)
        Picasso.get()
            .load(currentExo.image)
            .into(itemImageView)
        val itemVideoLinkImageView = listItemView.findViewById<ImageView>(R.id.exoYoutubeLink)
        itemVideoLinkImageView.setOnClickListener {
            val url = currentExo.video // Votre lien externe
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            context.startActivity(intent)
        }

        return listItemView
    }
}