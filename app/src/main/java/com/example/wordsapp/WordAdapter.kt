/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.wordsapp

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import android.graphics.BitmapFactory
import com.squareup.picasso.Picasso
import java.net.URL


/**
 * Adapter for the [RecyclerView] in [DetailActivity].
 */
class WordAdapter(private val letterId: String, context: Context) :
    RecyclerView.Adapter<WordAdapter.WordViewHolder>() {

    private val filteredWords: List<FrutaClass>

    init {
        // Retrieve the list of words from res/values/arrays.xml
        val words = arrayListOf<FrutaClass>(
            FrutaClass("Manzana", "La manzana es el fruto comestible de la especie Malus domestica, el manzano común. Es una fruta pomácea de forma redonda y sabor muy dulce, dependiendo de la variedad. Los manzanos se cultivan en todo el mundo y son las especies más utilizadas del género Malus", "VITAMINA C, VITAMINA A , POTASIO, HIERRO, MAGNESIO, FLAVANOIDES, CALCIO", "https://www.ecured.cu/images/d/d0/Manzana.jpeg"),
            FrutaClass("Pera", "La pera es el fruto del peral (Pyrus communis, de la familia de las Rosáceas). La pera es una fruta jugosa, carnosa, de diferente tamaño, sabor y color (del verde al amarillo, al rojo, hasta el color castaña), según las variedades de pera cultivadas. ... La flor del peral es blanca y aparece en primavera", "vitaminas C y A","https://estaticos.muyinteresante.es/media/cache/1000x_thumb/uploads/images/gallery/57026dd35bafe848678b456b/peras3.jpg"),
            FrutaClass("Banana", "El plátano es una fruta tropical procedente de la planta herbácea que recibe el mismo nombre o banano, perteneciente a la familia de las musáceas. Tiene forma alargada o ligeramente curvada, de 100-200 g de peso. La piel es gruesa, de color amarillo y fácil de pelar, y la pulpa es blanca o amarillenta y carnosa.", "vitaminas A, C, B1, B2, B6, B9 -ácido fólico- y E. Por otra parte, en cuanto a los minerales, se encuentran el potasio, magnesio, hierro, selenio, zinc y calcio.","https://esferasalud.com/wp-content/uploads/2017/02/platanos_mexicanos.jpg"),
            FrutaClass("Uvas", "La uva es un fruta que crece en racimos apretados. Su pulpa es blanca o púrpura y de sabor dulce. ... Su pulpa es blanca o púrpura y de sabor dulce. Se consume como fruta fresca o zumo, aunque su utilidad principal es la obtención de vinos.", "vitamina C, vitamina A, vitamina K, carotenos, vitaminas del complejo B, como piridoxina, riboflavina y tiamina","https://www.cuerpomente.com/medio/2019/07/03/hojas-de-parra_e6c86810_846x846.jpg"),
            FrutaClass("Melon", "El melón puede ser redondo o alargado, de corteza amarilla, verde o combinada según la variedad. La pulpa es aromática, jugosa y dulce, resultando una fruta ideal para calmar la sed. ... El melón es una fruta globosa, redonda o alargada, de 20 a 30cm de largo y hasta 2kg de peso.", "vitaminas A, B, C y E, ácido fólico, fibra, además de minerales como calcio, hierro y potasio","https://saboryestilo.com.mx/wp-content/uploads/2019/06/beneficios-del-melon-1.jpg"),

        )

        filteredWords = words
            // Returns items in a collection if the conditional clause is true,
            // in this case if an item starts with the given letter,
            // ignoring UPPERCASE or lowercase.
            .filter { it.nombre.contentEquals(letterId) }
            // Returns a collection that it has shuffled in place
            .shuffled()
            // Returns the first n items as a [List]
            .take(5)
    }

    class WordViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val TextDescription = view.findViewById<TextView>(R.id.descripcion)
        val TextName = view.findViewById<TextView>(R.id.nombre_seleccionado)
        val TextAtributos = view.findViewById<TextView>(R.id.vitaminas)
        val ImageViewUrl = view.findViewById<ImageView>(R.id.imageView)
    }

    override fun getItemCount(): Int = filteredWords.size

    /**
     * Creates new views with R.layout.item_view as its template
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val layout = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_content, parent, false)

        // Setup custom accessibility delegate to set the text read
        layout.accessibilityDelegate = Accessibility

        return WordViewHolder(layout)
    }

    /**
     * Replaces the content of an existing view with new data
     */
    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {

        val item = filteredWords[position]
        // Needed to call startActivity
        val context = holder.view.context

        // Set the text of the WordViewHolder
        holder.TextName.text = item.nombre
        holder.TextDescription.text = item.descripcion
        holder.TextAtributos.text = item.virtudes

        Picasso.get().load(item.image).into(holder.ImageViewUrl)

        // Assigns a [OnClickListener] to the button contained in the [ViewHolder]
        //holder.button.setOnClickListener {
        //    val queryUrl: Uri = Uri.parse("${WordListFragment.SEARCH_PREFIX}${item}")
        //    val intent = Intent(Intent.ACTION_VIEW, queryUrl)
        //    context.startActivity(intent)
        //}
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
            val customString = host?.context?.getString(R.string.look_up_word)
            val customClick =
                AccessibilityNodeInfo.AccessibilityAction(
                    AccessibilityNodeInfo.ACTION_CLICK,
                    customString
                )
            info?.addAction(customClick)
        }
    }
}