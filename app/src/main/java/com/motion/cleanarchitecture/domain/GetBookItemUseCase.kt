package com.motion.cleanarchitecture.domain

class GetBookItemUseCase(private val repository: BookListRepository) {

  suspend  fun getBookItem(bookId:Int):BookItem{
      return repository.getBookItem(bookId)
    }
}