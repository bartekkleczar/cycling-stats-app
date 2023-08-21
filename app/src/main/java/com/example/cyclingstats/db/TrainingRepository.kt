package com.example.cyclingstats.db

import com.example.cyclingstats.MainActivity
import kotlinx.coroutines.flow.first

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

    suspend fun getTotalDistance(): Float {
        val trainingsList = trainings.first()
        return String.format("%.2f" ,trainingsList.sumOf { (it.distance.toDouble()) }).toFloat()
    }

    suspend fun getLongestTime(): String {
        val trainings = trainings.first()

        if (trainings.isEmpty()) {
            return "0:00 h"
        }

        val timesInMinutes = trainings.map { training ->
            val (hours, minutes) = training.trainingTime.split("-").map { it.toInt() }
            hours * 60 + minutes
        }

        val longestTimeInMinutes = timesInMinutes.maxOrNull() ?: 0

        val hours = longestTimeInMinutes / 60
        val minutes = longestTimeInMinutes % 60
        return "$hours:${String.format("%02d", minutes)} h"
    }

    suspend fun getHighestSpeed(): String {
        val trainings = trainings.first()

        if (trainings.isEmpty()) {
            return "0 km/h"
        }

        val speeds = trainings.map { training ->
            training.maxSpeed
        }

        val highestSpeed = speeds.maxOrNull() ?: 0

        return "$highestSpeed km/h"
    }

    suspend fun getLongestDistance(): String {
        val trainings = trainings.first()

        if (trainings.isEmpty()) {
            return "0 km"
        }

        val distances = trainings.map { training ->
            training.distance
        }

        val longestDistance = distances.maxOrNull() ?: 0f

        return "${String.format("%.2f",longestDistance)} km"
    }
    
    suspend fun getTrainingByIndex(index: Int): Training {
        val trainings = trainings.first()

        if (trainings.isEmpty()) {
            MainActivity.defaultTraining()
        }

        var out = MainActivity.defaultTraining(-2)

        trainings.map { training ->
            if(training.index == index) {
                out = training
            }
        }
        return out
    }
}