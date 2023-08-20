package com.example.cyclingstats.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.cyclingstats.functions.makeTraining
import com.example.cyclingstats.navigation.Screen
import com.example.cyclingstats.viewModel.TrainingViewModel

@Composable
fun EditTraining(
    navController: NavController,
    trainingViewModel: TrainingViewModel,
    index: Int?,
    trainingDate: String?,
    trainingTime: String?,
    wholeTime: String?,
    distance: Float?,
    averageSpeed: Float?,
    maxSpeed: Float?,
    averagePulse: Int?,
    maxPulse: Int?,
    calories: Int?,
    averagePace: String?,
    maxPace: String?,
    startTime: String?,
    finishTime: String?
){
    if(index == -1 || index == null) navController.navigate(Screen.AddTraining.route)
    var training = makeTraining(index, trainingDate, trainingTime, wholeTime, distance.toString(), averageSpeed.toString(), maxSpeed.toString(), averagePulse, maxPulse, calories, averagePace, maxPace, startTime, finishTime)

}