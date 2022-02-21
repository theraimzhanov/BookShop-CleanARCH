package com.motion.cleanarchitecture.presentation

import android.app.Application
import androidx.lifecycle.*
import com.motion.cleanarchitecture.data.BookListRepositoryImpl
import com.motion.cleanarchitecture.domain.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class BookListViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = BookListRepositoryImpl(application)


    private val getBookListUseCase = GetBookListUseCase(repository)
    private val editBookItemUseCase = EditBookItemUseCase(repository)
    private val deleteBookItemUseCase = DeleteBookItemUseCase(repository)
    private val deleteBookListUseCase = DeleteBookListUseCase(repository)

     val getBookList = getBookListUseCase.getBookList()

    fun changeState(bookItem: BookItem) {
        viewModelScope.launch {
            val newElement = bookItem.copy(state = !bookItem.state)
            editBookItemUseCase.editBookItem(newElement)
        }
    }

    fun deleteBookItem(bookItem: BookItem) {
        viewModelScope.launch {
            deleteBookItemUseCase.deleteBookItem(bookItem)
        }
    }

    fun deleteBookList(){
        viewModelScope.launch {
            deleteBookListUseCase.deleteBookList()
        }
    }
}