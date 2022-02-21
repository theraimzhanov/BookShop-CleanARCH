package com.motion.cleanarchitecture.domain

class DeleteBookListUseCase(private val repository: BookListRepository) {

   suspend fun deleteBookList(){
   repository.deleteBookList()
    }
}