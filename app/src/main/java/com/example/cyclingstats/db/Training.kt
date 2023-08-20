package com.example.cyclingstats.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "training_data_table")
data class Training(
    @PrimaryKey(autoGenerate = true)
    val index: Int,

    @ColumnInfo(name = "training_date")
    val trainingDate: String, // YYYY-MM-DD

    @ColumnInfo(name = "training_time")
    val trainingTime: String, // hours-minutes

    @ColumnInfo(name = "training_whole_time")
    val wholeTime: String, // hours-minutes

    @ColumnInfo(name = "training_distance")
    val distance: Float, // km

    @ColumnInfo(name = "training_average_speed")
    val averageSpeed: Float, // km/h

    @ColumnInfo(name = "training_max_speed")
    val maxSpeed: Float, // km/h

    @ColumnInfo(name = "training_average_pulse")
    val averagePulse: Int, // beats/min

    @ColumnInfo(name = "training_max_pulse")
    val maxPulse: Int, // beats/min

    @ColumnInfo(name = "training_calories")
    val calories: Int,

    @ColumnInfo(name = "training_average_pace")
    val averagePace: String, // minutes,secs

    @ColumnInfo(name = "training_max_pace")
    val maxPace: String, // minutes,secs

    @ColumnInfo(name = "training_start_time")
    val startTime: String, //hour:minute

    @ColumnInfo(name = "training_finish_time")
    val finishTime: String //hour:minute
)
