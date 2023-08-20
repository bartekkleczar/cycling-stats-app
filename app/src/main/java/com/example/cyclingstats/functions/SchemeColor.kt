package com.example.cyclingstats.functions

import androidx.compose.ui.graphics.Color
import com.example.cyclingstats.ui.theme.DarkFou
import com.example.cyclingstats.ui.theme.DarkPrim
import com.example.cyclingstats.ui.theme.DarkSec
import com.example.cyclingstats.ui.theme.DarkTer
import com.example.cyclingstats.ui.theme.LightFou
import com.example.cyclingstats.ui.theme.LightPrim
import com.example.cyclingstats.ui.theme.LightSec
import com.example.cyclingstats.ui.theme.LightTer

fun schemeColor(color: String, isSystemInDarkTheme: Boolean): Color {
    return when(color){
        "prim" -> {
            if(isSystemInDarkTheme) DarkPrim else LightPrim
        }
        "sec" -> {
            if(isSystemInDarkTheme) DarkSec else LightSec
        }
        "ter" -> {
            if(isSystemInDarkTheme) DarkTer else LightTer
        }
        "fou" -> {
            if(isSystemInDarkTheme) DarkFou else LightFou
        }
        "black" -> {
            if(isSystemInDarkTheme) Color.White else Color.Black
        }
        else -> Color.Transparent
    }
}