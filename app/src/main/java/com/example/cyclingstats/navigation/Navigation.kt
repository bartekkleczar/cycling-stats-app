package com.example.cyclingstats.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.example.cyclingstats.screens.*
import com.example.cyclingstats.viewModel.TrainingViewModel

@Composable
fun Navigation(trainingViewModel: TrainingViewModel, context: Context) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Overview.route) {
        //Overview
        composable(route = Screen.Overview.route) {
            OverviewScreen(navController = navController, trainingViewModel = trainingViewModel, context = context)
        }
        //Trainings
        composable(route = Screen.Trainings.route) {
            Trainings(navController = navController, trainingViewModel, context)
        }
        //Add Training
        composable(route = Screen.AddTraining.route) {
            AddTraining(navController = navController, trainingViewModel)
        }
        //Settings
        composable(route = Screen.Settings.route) {
            Settings(navController = navController, trainingViewModel = trainingViewModel)
        }
        //TrainingView
        composable(
            route = Screen.TrainingView.route + "/{index}",
            arguments = listOf(
                navArgument("index") {
                    type = NavType.IntType
                    defaultValue = -1
                    nullable = false
                }
            )
        ) { entry ->
            TrainingView(navController, trainingViewModel,
                index = entry.arguments?.getInt("index"),
            )
        }
        //Edit Training
        composable(
            route = Screen.EditTraining.route + "/{index}",
            arguments = listOf(
                navArgument("index") {
                    type = NavType.IntType
                    defaultValue = -1
                    nullable = false
                }
            )
        ) { entry ->
            EditTraining(navController, trainingViewModel,
                index = entry.arguments?.getInt("index"),
            )
        }
    }
}