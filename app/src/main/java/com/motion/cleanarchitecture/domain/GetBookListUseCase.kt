package com.motion.cleanarchitecture.domain

import androidx.lifecycle.LiveData

class GetBookListUseCase(private val repository: BookListRepository) {

    fun getBookList():LiveData<List<BookItem>>{
        return repository.getBookList()
    }
}