package com.example.cyclingstats.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.example.cyclingstats.Screens.*

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Overview.route) {
        //Overview
        composable(route = Screen.Overview.route) {
            OverviewScreen(navController = navController)
        }
        //Trainings
        composable(route = Screen.Trainings.route) {
            Trainings(navController = navController)
        }
        //Add Training
        composable(route = Screen.AddTraining.route) {
            AddTraining(navController = navController)
        }
        //Settings
        composable(route = Screen.Settings.route) {
            Settings(navController = navController)
        }
        //Edit Training
        composable(
            route = Screen.EditTraining.route + "/{index}",
            arguments = listOf(
                navArgument("index") {
                    type = NavType.IntType
                    defaultValue = "-1"
                    nullable = false
                }
            )
        ) { entry ->
            EditTraining(navController, index = entry.arguments?.getInt("name"))
        }
    }
}