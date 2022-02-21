package com.motion.cleanarchitecture.domain

import androidx.lifecycle.LiveData

interface BookListRepository {

  suspend  fun  addBookItem(bookItem: BookItem)
  suspend  fun  deleteBookItem(bookItem: BookItem)
  suspend  fun  deleteBookList()
  suspend  fun  editBookItem(bookItem: BookItem)
  suspend  fun  getBookItem(bookId: Int):BookItem
    fun getBookList():LiveData<List<BookItem>>
}