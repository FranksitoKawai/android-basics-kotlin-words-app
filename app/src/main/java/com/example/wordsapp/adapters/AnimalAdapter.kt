package com.example.wordsapp.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wordsapp.LetterAdapter
import com.example.wordsapp.R
import com.example.wordsapp.lists.Animals
import com.example.wordsapp.objects.Animal
import com.squareup.picasso.Picasso


class AnimalAdapter(private val idTipoAnimal: String, context: Context)
    : RecyclerView.Adapter<AnimalAdapter.AnimalViewHolder>()  {
    private val filteredList: List<Animal>

    val SEARCH_PREFIX = "https://www.google.com/search?q="

    init {
        filteredList = Animals().AnimalsList.filter { animal -> animal.idTipoAnimal == Integer.parseInt(idTipoAnimal) }
    }

    class AnimalViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val Imagen = view.findViewById<ImageView>(R.id.imageView)
        val Nombre = view.findViewById<TextView>(R.id.nombre)
        val Descripcion = view.findViewById<TextView>(R.id.descripcion)
        val ButtonVerGoogle = view.findViewById<Button>(R.id.buttonVerGoogle)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
        val layout = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_content, parent, false)

        // Setup custom accessibility delegate to set the text read
        layout.accessibilityDelegate = LetterAdapter
        return AnimalAdapter.AnimalViewHolder(layout)
    }

    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
        val item = filteredList[position]
        // Needed to call startActivity
        val context = holder.view.context
        // Set the text of the WordViewHolder
        holder.Nombre.text = item.nombre
        holder.Descripcion.text  = item.descripcion
        Picasso.get().load(item.imagen).into(holder.Imagen)
        // Assigns a [OnClickListener] to the button contained in the [ViewHolder]
        holder.ButtonVerGoogle.setOnClickListener {
            val queryUrl: Uri = Uri.parse("${SEARCH_PREFIX}${item.nombre}")
            val intent = Intent(Intent.ACTION_VIEW, queryUrl)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return filteredList.size
    }

}