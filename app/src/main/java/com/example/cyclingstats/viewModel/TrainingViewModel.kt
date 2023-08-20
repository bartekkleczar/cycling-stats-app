package com.example.cyclingstats.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cyclingstats.MainActivity
import com.example.cyclingstats.MyPreferences
import com.example.cyclingstats.db.Training
import com.example.cyclingstats.db.TrainingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class TrainingViewModel(private val repository: TrainingRepository): ViewModel() {
    val trainings = repository.trainings

    fun insert(training: Training) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(training)
        }
    }

    fun delete(training: Training) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(training)
        }
    }

    fun update(training: Training) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.update(training)
        }
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }
    }
}