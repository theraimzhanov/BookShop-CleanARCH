package com.motion.cleanarchitecture.domain

class EditBookItemUseCase(private val repository: BookListRepository) {

fun editBookItem(bookItem: BookItem){
repository.editBookItem(bookItem)
}
}