package com.datotoda.carshop.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.datotoda.carshop.model.Car


@Database(entities = [Car::class], version = 1, exportSchema = false)
abstract class CarDatabase: RoomDatabase() {
    abstract fun useDao(): CarDao
    companion object {
        @Volatile
        private var INSTANCE: CarDatabase? = null

        fun getDatabase(context: Context): CarDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null){
                return tempInstance
            }

            synchronized(this){
                val instance = Room
                    .databaseBuilder(context.applicationContext, CarDatabase::class.java, "car_database")
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}