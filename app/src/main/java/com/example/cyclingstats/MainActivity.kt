package com.example.cyclingstats

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.example.cyclingstats.db.*
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
        const val totalDistance = "TOTAL_DISTANCE"
        const val longestTime = "LONGEST_TIME"
    }
}