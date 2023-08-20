package com.example.cyclingstats.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TrainingDao {
    @Insert
    suspend fun insertTraining(training: Training)

    @Update
    suspend fun updateTraining(training: Training)

    @Delete
    suspend fun deleteTraining(training: Training)

    @Query("DELETE FROM training_data_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM training_data_table")
    fun getAllTrainings(): Flow<List<Training>>
}