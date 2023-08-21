package com.example.cyclingstats

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.example.cyclingstats.db.*
import com.example.cyclingstats.functions.formatFloat
import com.example.cyclingstats.functions.makeTraining
import com.example.cyclingstats.navigation.Navigation
import com.example.cyclingstats.viewModel.*

class MainActivity : ComponentActivity() {
    private lateinit var trainingViewModel: TrainingViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dao = TrainingDatabase.getInstance(application).trainingDao
        val repository = TrainingRepository(dao)
        val factory = TrainingViewModelFactory(repository)
        trainingViewModel = ViewModelProvider(this, factory)[TrainingViewModel::class.java]

        setContent {
            Navigation(trainingViewModel, this)
        }
    }


    companion object {
        fun defaultTraining(index: Int = -1): Training{
            return makeTraining(
                index = index,
                trainingDate = "Err",
                trainingTime = "Err",
                wholeTime = "Err",
                distance = "0",
                averageSpeed = "0",
                maxSpeed = "0",
                averagePulse = 0,
                maxPulse = 0,
                calories = 0,
                averagePace = "Err",
                maxPace = "Err",
                startTime = "Err",
                finishTime = "Err",
            )
        }

        const val totalDistance = "TOTAL_DISTANCE"
        const val longestTime = "LONGEST_TIME"
    }
}