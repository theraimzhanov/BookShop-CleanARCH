package com.motion.cleanarchitecture.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.motion.cleanarchitecture.domain.BookItem
import com.motion.cleanarchitecture.domain.BookListRepository
import java.lang.RuntimeException
import kotlin.random.Random

object BookListRepositoryImpl : BookListRepository {

    private val bookListLD =MutableLiveData<List<BookItem>>()
    private val bookList = sortedSetOf<BookItem>({ e1, e2 -> e1.id.compareTo(e2.id) })
    private var autogenerateId = 0

    init {
for (i in 0 until 20){
val element = BookItem("Book $i","Author $i",i*50)
    addBookItem(element)
}
    }

    override fun addBookItem(bookItem: BookItem) {
        if (bookItem.id == BookItem.START_ID) {
            bookItem.id = autogenerateId++
        }
        bookList.add(bookItem)
        updateBookList()
    }

    override fun deleteBookItem(bookItem: BookItem) {
        bookList.remove(bookItem)
        updateBookList()
    }

    override fun deleteBookList() {
        bookList.removeAll(bookList)
        updateBookList()
    }

    override fun editBookItem(bookItem: BookItem) {
        val oldElement = getBookItem(bookItem.id)
        bookList.remove(oldElement)
        addBookItem(bookItem)
    }

    override fun getBookItem(bookId: Int): BookItem {
         return bookList.find {
it.id == bookId
         } ?: throw RuntimeException("Элемент $bookId  не найден ")
    }

    override fun getBookList(): LiveData<List<BookItem>> {
 return bookListLD
    }
    private fun updateBookList(){
bookListLD.value =bookList.toList()
    }
}