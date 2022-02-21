package com.motion.cleanarchitecture.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.motion.cleanarchitecture.domain.BookItem

@Entity(tableName = "book_list")
data class BookItemDbModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    val name: String,
    val author: String,
    val price: Int,
    val state: Boolean

)
