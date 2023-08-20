package com.example.cyclingstats.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Training::class], version = 1)
abstract class TrainingDatabase: RoomDatabase() {
    abstract val trainingDao: TrainingDao

    companion object{
        @Volatile
        private var INSTANCE: TrainingDatabase? = null
        fun getInstance(context: Context): TrainingDatabase{
            synchronized(this){
                var instance = INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    TrainingDatabase::class.java,
                    "training_data_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}