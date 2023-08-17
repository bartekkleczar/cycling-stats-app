package com.example.cyclingstats.navigation

sealed class Screen(val route: String){
    object Overview: Screen("overview")
    object Trainings: Screen("trainings")
    object AddTraining: Screen("add_training")
    object EditTraining: Screen("edit_training")
    object Settings: Screen("settings")

    fun withArgs(vararg args: String): String{
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}