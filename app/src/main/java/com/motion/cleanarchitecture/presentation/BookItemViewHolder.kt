package com.motion.cleanarchitecture.presentation

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.motion.cleanarchitecture.R

class BookItemViewHolder( val view: View) : RecyclerView.ViewHolder(view) {
    val textViewAuthor = view.findViewById<TextView>(R.id.authorOfBook)
    val textViewNameOFBook = view.findViewById<TextView>(R.id.nameOfBook)
    val textViewPrice = view.findViewById<TextView>(R.id.priceOfBook)
}