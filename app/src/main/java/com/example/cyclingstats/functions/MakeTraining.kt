package com.example.cyclingstats.functions

import com.example.cyclingstats.db.Training

fun makeTraining(
    index: Int?,
    trainingDate: String?,
    trainingTime: String?,
    wholeTime: String?,
    distance: String?,
    averageSpeed: String?,
    maxSpeed: String?,
    averagePulse: Int?,
    maxPulse: Int?,
    calories: Int?,
    averagePace: String?,
    maxPace: String?,
    startTime: String?,
    finishTime: String?
): Training {
    return Training(
        index ?: -1,
        trainingDate ?: "Err",
        trainingTime ?: "Err",
        wholeTime ?: "Err",
        formatFloat(distance),
        formatFloat(averageSpeed),
        formatFloat(maxSpeed),
        averagePulse ?: 0,
        maxPulse ?: 0,
        calories ?: 0,
        averagePace ?: "Err",
        maxPace ?: "Err",
        startTime ?: "Err",
        finishTime ?: "Err",
    )
}