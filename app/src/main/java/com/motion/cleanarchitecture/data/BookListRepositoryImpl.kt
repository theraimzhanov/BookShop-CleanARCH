package com.motion.cleanarchitecture.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import com.motion.cleanarchitecture.domain.BookItem
import com.motion.cleanarchitecture.domain.BookListRepository

class BookListRepositoryImpl(
    application: Application
) : BookListRepository {

    private val bookListDao = AppDataBase.getInstance(application).booLIstDao()
   private val mapper = BookListMapper()


    override suspend fun addBookItem(bookItem: BookItem) {
      bookListDao.addBookItem(mapper.mapEntityToDbModel(bookItem))
    }

    override suspend fun deleteBookItem(bookItem: BookItem) {
        bookListDao.deleteBookItem(bookItem.id)
    }

    override suspend fun deleteBookList() {
        bookListDao.deleteBookList()
    }

    override suspend fun editBookItem(bookItem: BookItem) {
        bookListDao.addBookItem(mapper.mapEntityToDbModel(bookItem))
    }

    override suspend fun getBookItem(bookId: Int): BookItem {
     val item = bookListDao.getShopItem(bookId)
        return mapper.mapDbModelToEntity(item)
    }

    override fun getBookList(): LiveData<List<BookItem>> = Transformations.map(bookListDao.getShopList()){
        mapper.mapListDbModelToListEntity(it)
    }

}