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
                    type = NavType.StringType
                    defaultValue = "-1"
                    nullable = false
                }
            )
        ) { entry ->
            TrainingView(navController, trainingViewModel,
                index = entry.arguments?.getString("index"),
            )
        }
        //Edit Training
        composable(
            route = Screen.EditTraining.route + "/{index}",
            arguments = listOf(
                navArgument("index") {
                    type = NavType.IntType
                    defaultValue = "-1"
                    nullable = false
                },
                navArgument("trainingDate") {
                    type = NavType.IntType
                    defaultValue = "Err"
                    nullable = false
                },
                navArgument("trainingTime") {
                    type = NavType.IntType
                    defaultValue = "-1"
                    nullable = false
                },
                navArgument("averagePace") {
                    type = NavType.StringType
                    defaultValue = "Err"
                    nullable = false
                },
                navArgument("maxPace") {
                    type = NavType.StringType
                    defaultValue = "Err"
                    nullable = false
                },
                navArgument("averagePulse") {
                    type = NavType.IntType
                    defaultValue = -1
                    nullable = false
                },
                navArgument("maxPulse") {
                    type = NavType.IntType
                    defaultValue = -1
                    nullable = false
                },
                navArgument("averageSpeed") {
                    type = NavType.FloatType
                    defaultValue = -1f
                    nullable = false
                },
                navArgument("maxSpeed") {
                    type = NavType.FloatType
                    defaultValue = -1f
                    nullable = false
                },
                navArgument("calories") {
                    type = NavType.IntType
                    defaultValue = -1
                    nullable = false
                },
                navArgument("distance") {
                    type = NavType.FloatType
                    defaultValue = -1f
                    nullable = false
                },
                navArgument("finishTime") {
                    type = NavType.StringType
                    defaultValue = "Err"
                    nullable = false
                },
                navArgument("startTime") {
                    type = NavType.StringType
                    defaultValue = "Err"
                    nullable = false
                },
                navArgument("wholeTime") {
                    type = NavType.IntType
                    defaultValue = -1
                    nullable = false
                },

            )
        ) { entry ->
            EditTraining(navController, trainingViewModel,
                index = entry.arguments?.getInt("index"),
                trainingDate = entry.arguments?.getString("trainingDate"),
                trainingTime = entry.arguments?.getString("trainingTime"),
                averagePace = entry.arguments?.getString("averagePace"),
                maxPace = entry.arguments?.getString("maxPace"),
                averagePulse = entry.arguments?.getInt("averagePulse"),
                maxPulse = entry.arguments?.getInt("maxPulse"),
                averageSpeed = entry.arguments?.getFloat("averageSpeed"),
                maxSpeed = entry.arguments?.getFloat("maxSpeed"),
                calories = entry.arguments?.getInt("calories"),
                distance = entry.arguments?.getFloat("distance"),
                finishTime = entry.arguments?.getString("finishTime"),
                startTime = entry.arguments?.getString("startTime"),
                wholeTime = entry.arguments?.getString("wholeTime"),
            )
        }
    }
}