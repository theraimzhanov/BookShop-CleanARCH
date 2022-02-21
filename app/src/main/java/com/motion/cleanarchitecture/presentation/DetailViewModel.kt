package com.motion.cleanarchitecture.presentation

import android.app.Application
import androidx.lifecycle.*
import com.motion.cleanarchitecture.data.BookListRepositoryImpl
import com.motion.cleanarchitecture.domain.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.stream.Stream


class DetailViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = BookListRepositoryImpl(application)

    private val getBookItemUseCase = GetBookItemUseCase(repository)
    private val addBookItemUseCase = AddBookItemUseCase(repository)
    private val editBookItemUseCase = EditBookItemUseCase(repository)

    private val _errorInputAuthor = MutableLiveData<Boolean>()
    val errorInputAuthor: LiveData<Boolean>
        get() = _errorInputAuthor

    private val _errorInputBook = MutableLiveData<Boolean>()
    val errorInputBook: LiveData<Boolean>
        get() = _errorInputBook

    private val _errorInputPrice = MutableLiveData<Boolean>()
    val errorInputPrice: LiveData<Boolean>
        get() = _errorInputPrice

    private val _bookItem = MutableLiveData<BookItem>()
    val bookItem: LiveData<BookItem>
        get() = _bookItem

    private val _closeScreen = MutableLiveData<Unit>()
    val closeScreen: LiveData<Unit>
        get() = _closeScreen

    fun getBookItem(bookId: Int) {
        viewModelScope.launch {
            val item = getBookItemUseCase.getBookItem(bookId)
            _bookItem.postValue(item)
        }

    }

    fun addBookItem(author: String?, name: String?, price: String?) {
        val authorOfBook = parseAuthor(author)
        val nameOfBook = parseName(name)
        val priceOfBook = parsePrice(price)
        val validate = validateInput(nameOfBook, authorOfBook, priceOfBook)
        if (validate) {
            viewModelScope.launch {
                val bookItem = BookItem(nameOfBook, authorOfBook, priceOfBook, true)
                addBookItemUseCase.addBookItem(bookItem)
                finishScreen()
            }

        }
    }

    fun editBookItem(author: String?, name: String?, price: String?) {
        val authorOfBook = parseAuthor(author)
        val nameOfBook = parseName(name)
        val priceOfBook = parsePrice(price)
        val validate = validateInput(nameOfBook, authorOfBook, priceOfBook)
        if (validate) {
            _bookItem.value?.let {
                viewModelScope.launch {
                    val item = it.copy(name = nameOfBook, author = authorOfBook, price = priceOfBook)
                    editBookItemUseCase.editBookItem(item)
                    finishScreen()
                }

            }
        }
    }

    fun resetErrorName() {
        _errorInputBook.value = false
    }

    fun resetErrorAuthor() {
        _errorInputAuthor.value = false
    }

    fun resetErrorPrice() {
        _errorInputPrice.value = false
    }

    private fun finishScreen() {
        _closeScreen.value = Unit
    }

    private fun parseAuthor(author: String?): String {
        return author?.trim() ?: ""
    }

    private fun parseName(name: String?): String {
        return name?.trim() ?: ""
    }

    private fun parsePrice(price: String?): Int {
        return try {
            price?.trim()?.toInt() ?: 0
        } catch (e: Exception) {
            0
        }
    }

    private fun validateInput(name: String, author: String, price: Int): Boolean {
        var result = true
        if (name.isBlank()) {
            _errorInputBook.value = true
            result = false
        }
        if (author.isBlank()) {
            _errorInputAuthor.value = true
            result = false
        }
        if (price <= 0) {
            _errorInputPrice.value = true
            result = false
        }
        return result
    }
}