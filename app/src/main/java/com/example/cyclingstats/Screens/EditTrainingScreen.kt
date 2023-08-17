package com.example.cyclingstats.Screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.cyclingstats.navigation.Screen

@Composable
fun EditTraining(navController: NavController, index: Int?){
    if(index == -1 || index == null) navController.navigate(Screen.AddTraining.route)

}