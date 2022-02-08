package com.motion.cleanarchitecture.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.motion.cleanarchitecture.data.BookListRepositoryImpl
import com.motion.cleanarchitecture.domain.*

class BookListViewModel : ViewModel() {

    private val repository = BookListRepositoryImpl

    private val getBookListUseCase = GetBookListUseCase(repository)
    private val editBookItemUseCase = EditBookItemUseCase(repository)
    private val deleteBookItemUseCase = DeleteBookItemUseCase(repository)
    private val deleteBookListUseCase = DeleteBookListUseCase(repository)

     val getBookList = getBookListUseCase.getBookList()



    fun changeState(bookItem: BookItem) {
        val newElement = bookItem.copy(state = !bookItem.state)
        editBookItemUseCase.editBookItem(newElement)
    }

    fun deleteBookItem(bookItem: BookItem) {
        deleteBookItemUseCase.deleteBookItem(bookItem)
    }

    fun deleteBookList(){
        deleteBookListUseCase.deleteBookList()
    }
}