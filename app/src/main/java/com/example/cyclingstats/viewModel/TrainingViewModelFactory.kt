package com.example.cyclingstats.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cyclingstats.db.TrainingRepository

class TrainingViewModelFactory(private val repository: TrainingRepository): ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>):T {
        if(modelClass.isAssignableFrom(TrainingViewModel::class.java)) {
            return TrainingViewModel(repository) as T
        }
        throw  IllegalArgumentException("Unknown ViewMOdel Class")
    }
}