package com.example.wordsapp.adapters

import android.os.Build
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.wordsapp.LetterAdapter
import com.example.wordsapp.LetterListFragmentDirections
import com.example.wordsapp.R
import com.example.wordsapp.lists.TiposAnimal
import com.squareup.picasso.Picasso

class TipoAnimalAdapter: RecyclerView.Adapter<TipoAnimalAdapter.TipoAnimalViewHolder>()  {


    class TipoAnimalViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val Imagen = view.findViewById<ImageButton>(R.id.imageView)
        val Nombre = view.findViewById<TextView>(R.id.nombre)
        val Layout = view.findViewById<LinearLayout>(R.id.layout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TipoAnimalViewHolder {
        val layout = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_content_preview, parent, false)

        // Setup custom accessibility delegate to set the text read
        layout.accessibilityDelegate = LetterAdapter
        return TipoAnimalAdapter.TipoAnimalViewHolder(layout)
    }

    override fun onBindViewHolder(holder: TipoAnimalViewHolder, position: Int) {
        val tipoItem = TiposAnimal().TipoAnimalsList[position]
        holder.Nombre.text = tipoItem.nombre
        Picasso.get().load(tipoItem.image).into(holder.Imagen)

        holder.Imagen.setOnClickListener {
            val action = LetterListFragmentDirections.actionLetterListFragmentToWordListFragment(letter = tipoItem.id.toString())
            // Navigate using that action
            holder.view.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return TiposAnimal().TipoAnimalsList.size
    }

    // Setup custom accessibility delegate to set the text read with
    // an accessibility service
    companion object Accessibility : View.AccessibilityDelegate() {
        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        override fun onInitializeAccessibilityNodeInfo(
            host: View?,
            info: AccessibilityNodeInfo?
        ) {
            super.onInitializeAccessibilityNodeInfo(host, info)
            // With `null` as the second argument to [AccessibilityAction], the
            // accessibility service announces "double tap to activate".
            // If a custom string is provided,
            // it announces "double tap to <custom string>".
            val customString = host?.context?.getString(R.string.look_up_words)
            val customClick =
                AccessibilityNodeInfo.AccessibilityAction(
                    AccessibilityNodeInfo.ACTION_CLICK,
                    customString
                )
            info?.addAction(customClick)
        }
    }
}