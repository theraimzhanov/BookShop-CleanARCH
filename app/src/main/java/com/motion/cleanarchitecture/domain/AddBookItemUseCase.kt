package com.motion.cleanarchitecture.domain

class AddBookItemUseCase(private val repository: BookListRepository) {

    fun addBookItem(bookItem: BookItem) {
repository.addBookItem(bookItem)
    }
}