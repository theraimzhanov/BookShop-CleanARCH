package com.motion.cleanarchitecture.domain

data class BookItem(
    val name: String,
    val author:String,
    val price: Int,
    val state: Boolean = true,
    var id: Int = START_ID
){
    companion object{
        const val START_ID = 0
    }
}