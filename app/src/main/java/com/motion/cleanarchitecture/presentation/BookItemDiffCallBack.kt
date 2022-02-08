package com.motion.cleanarchitecture.presentation

import androidx.recyclerview.widget.DiffUtil
import com.motion.cleanarchitecture.domain.BookItem

class BookItemDiffCallBack: DiffUtil.ItemCallback<BookItem>() {
    override fun areItemsTheSame(oldItem: BookItem, newItem: BookItem): Boolean {
      return  oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: BookItem, newItem: BookItem): Boolean {
       return oldItem == newItem
    }
}