package com.motion.cleanarchitecture.data

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [BookItemDbModel::class], version = 1, exportSchema = false)
abstract class AppDataBase: RoomDatabase() {

    abstract fun booLIstDao():BookListDao

    companion object{

        private var INSTANCE:AppDataBase?= null
        private var LOG = Any()
        private const val DB_NAME = "db_name"

        fun getInstance(application: Application):AppDataBase{
            INSTANCE?.let {
                return it
            }
            synchronized(LOG){
                INSTANCE?.let {
                    return it
                }
                val db = Room.databaseBuilder(application,AppDataBase::class.java, DB_NAME)
                    .allowMainThreadQueries().build()

                INSTANCE = db
                return db
            }

        }
    }
}