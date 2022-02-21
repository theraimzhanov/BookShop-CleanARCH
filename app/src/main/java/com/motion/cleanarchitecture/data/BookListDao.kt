package com.motion.cleanarchitecture.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface BookListDao {

    @Query("SELECT * FROM book_list")
    fun getShopList(): LiveData<List<BookItemDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addBookItem(bookItemDbModel: BookItemDbModel)

    @Query("DELETE FROM book_list WHERE id =:booItemId ")
    fun deleteBookItem(booItemId:Int)

    @Query("SELECT * FROM book_list WHERE id =:booItemId LIMIT 1")
    fun getShopItem(booItemId: Int): BookItemDbModel

    @Query("DELETE FROM book_list")
    fun deleteBookList()
}