package com.motion.cleanarchitecture.data

import androidx.lifecycle.LiveData
import com.motion.cleanarchitecture.domain.BookItem

class BookListMapper {

    fun mapEntityToDbModel(bookItem: BookItem):BookItemDbModel = BookItemDbModel(
        id = bookItem.id,
        name = bookItem.name,
        author = bookItem.author,
        price = bookItem.price,
        state = bookItem.state
    )

    fun mapDbModelToEntity(bookItemDbModel: BookItemDbModel):BookItem = BookItem(
        id = bookItemDbModel.id,
        name = bookItemDbModel.name,
        author = bookItemDbModel.author,
        price = bookItemDbModel.price,
        state = bookItemDbModel.state
    )

    fun mapListDbModelToListEntity(list: List<BookItemDbModel>) = list.map {
        mapDbModelToEntity(it)
    }
}