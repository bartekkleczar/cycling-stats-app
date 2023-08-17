package com.example.cyclingstats.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = DarkPrim,
    secondary = DarkSec,
    tertiary = DarkTer,

    inversePrimary = DarkPrim,
    inverseOnSurface = DarkPrim,
    inverseSurface = DarkPrim,

    // Other default colors to override
    background = DarkPrim,
    //surface = DarkPrim,
    onPrimary = DarkPrim,
    onSecondary = DarkSec,
    onTertiary = DarkTer,
    onBackground = DarkPrim,
    //onSurface = DarkPrim,
)

private val LightColorScheme = lightColorScheme(
    primary = LightPrim,
    secondary = LightSec,
    tertiary = LightTer,

    primaryContainer = LightPrim,

    inversePrimary = LightPrim,
    inverseOnSurface = LightPrim,
    inverseSurface = LightPrim,

    // Other default colors to override
    background = LightPrim,
    surface = LightSec,
    onPrimary = LightPrim,
    onSecondary = Color.Black,
    onTertiary = Color.Black,
    onBackground = LightPrim,
    //onSurface = LightPrim,

)

@Composable
fun CyclingStatsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

fun schemeColor(color: String, isSystemInDarkTheme: Boolean): Color{
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