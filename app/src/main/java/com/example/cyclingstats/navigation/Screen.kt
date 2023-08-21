package com.example.cyclingstats.navigation

sealed class Screen(val route: String){
    object Overview: Screen("overview")
    object Trainings: Screen("trainings")
    object AddTraining: Screen("add_training")
    object EditTraining: Screen("edit_training")
    object Settings: Screen("settings")
    object TrainingView: Screen("training_view")

    fun withArgs(vararg args: Any): String{
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}