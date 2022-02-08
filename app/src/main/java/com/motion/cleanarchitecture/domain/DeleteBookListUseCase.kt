package com.motion.cleanarchitecture.domain

class DeleteBookListUseCase(private val repository: BookListRepository) {

    fun deleteBookList(){
   repository.deleteBookList()
    }
}