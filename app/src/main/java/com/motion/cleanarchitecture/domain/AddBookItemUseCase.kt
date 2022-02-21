package com.motion.cleanarchitecture.domain

class AddBookItemUseCase(private val repository: BookListRepository) {

   suspend fun addBookItem(bookItem: BookItem) {
repository.addBookItem(bookItem)
    }
}