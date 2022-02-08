package com.motion.cleanarchitecture.domain

import androidx.lifecycle.LiveData

interface BookListRepository {

    fun addBookItem(bookItem: BookItem)
    fun deleteBookItem(bookItem: BookItem)
    fun deleteBookList()
    fun editBookItem(bookItem: BookItem)
    fun getBookItem(bookId: Int):BookItem
    fun getBookList():LiveData<List<BookItem>>
}