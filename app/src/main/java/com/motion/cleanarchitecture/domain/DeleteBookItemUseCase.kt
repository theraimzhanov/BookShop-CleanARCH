package com.motion.cleanarchitecture.domain

class DeleteBookItemUseCase(private val repository: BookListRepository) {

    fun deleteBookItem(bookItem: BookItem){
      repository.deleteBookItem(bookItem)
    }
}