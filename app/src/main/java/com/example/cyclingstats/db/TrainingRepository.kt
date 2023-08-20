package com.example.cyclingstats.db

import kotlinx.coroutines.flow.map

class TrainingRepository(private val dao: TrainingDao) {
    val trainings = dao.getAllTrainings()

    suspend fun insert(training: Training){
        return dao.insertTraining(training)
    }

    suspend fun update(training: Training){
        return dao.updateTraining(training)
    }

    suspend fun delete(training: Training){
        return dao.deleteTraining(training)
    }

    suspend fun deleteAll(){
        return dao.deleteAll()
    }
}