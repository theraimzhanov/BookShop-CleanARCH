package com.motion.cleanarchitecture.presentation

import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.motion.cleanarchitecture.R
import com.motion.cleanarchitecture.domain.BookItem
import java.lang.RuntimeException

class BookAdapter : ListAdapter<BookItem, BookItemViewHolder>(BookItemDiffCallBack()) {

var onBookItemClickListener:((BookItem)->Unit)? = null
var onBookItemLongClickListener:((BookItem)->Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookItemViewHolder {
val layout =when(viewType){
VIEW_TYPE_STATE_ENABLED->R.layout.state_enabled
VIEW_TYPE_STATE_DISABLED->R.layout.state_disabled
    else->throw RuntimeException("Ошибка в макете")
}
val view = LayoutInflater.from(parent.context).inflate(layout,parent,false)
        return BookItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.view.setOnClickListener {
        onBookItemClickListener?.invoke(item)
        }
        holder.view.setOnLongClickListener {
            onBookItemLongClickListener?.invoke(item)
            true
        }
        holder.textViewNameOFBook.text = item.name
        holder.textViewPrice.text = item.price.toString()
        holder.textViewAuthor.text = item.author
    }

    override fun getItemViewType(position: Int): Int {
        val element = getItem(position)
        return if (element.state) {
            VIEW_TYPE_STATE_ENABLED
        } else {
            VIEW_TYPE_STATE_DISABLED
        }
    }

    companion object {
        const val VIEW_TYPE_STATE_ENABLED = 10
        const val VIEW_TYPE_STATE_DISABLED = 11
        const val MAX_POOL_SIZE = 25
    }
}