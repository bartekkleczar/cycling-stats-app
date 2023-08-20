package com.example.cyclingstats.composables

import android.widget.Space
import androidx.annotation.MainThread
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsBike
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cyclingstats.MainActivity
import com.example.cyclingstats.db.Training
import com.example.cyclingstats.functions.*
import com.example.cyclingstats.navigation.Screen
import com.example.cyclingstats.ui.theme.LightFou

@Composable
fun TrainingListItem(item: Training, isSystemInDarkTheme: Boolean, navController: NavController,) {
    Row(
        Modifier
            .height(120.dp)
            .fillMaxWidth()
            .background(color = schemeColor("prim", isSystemInDarkTheme))
            .clickable {
                navController.navigate(Screen.TrainingView.withArgs(item.index.toString()))
            },
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Cycling",
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                color = schemeColor("black", isSystemInDarkTheme)
            )
            Icon(
                imageVector = Icons.Default.DirectionsBike,
                contentDescription = "Cycling",
                modifier = Modifier
                    .size(65.dp),
                tint = schemeColor("sec", isSystemInDarkTheme)
            )

        }
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = timeFormat(item.wholeTime),
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                textAlign = TextAlign.Center,
                color = schemeColor("black", isSystemInDarkTheme),
                modifier = Modifier.fillMaxWidth(0.5f)
            )
            Text(
                text = "${timeFormat(item.startTime)} - ${timeFormat(item.finishTime)}",
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                textAlign = TextAlign.Center,
                color = schemeColor("black", isSystemInDarkTheme),
                modifier = Modifier.fillMaxWidth(0.5f)
            )
            Text(
                text = "${item.distance} km | ${item.calories} cal",
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                textAlign = TextAlign.Center,
                color = schemeColor("black", isSystemInDarkTheme),
                modifier = Modifier.fillMaxWidth(0.5f)
            )
        }
    }
}


/*@Preview
@Composable
fun TrainingListItemPreview() {
    TrainingListItem(
        Training(
            index = 1,
            trainingDate = "2023-08-19",
            trainingTime = "2-05",
            wholeTime = "2-30",
            distance = 40.5f,
            averageSpeed = 17f,
            maxSpeed = 55.7f,
            averagePulse = 145,
            maxPulse = 203,
            calories = 1300,
            averagePace = "2-50",
            maxPace = "1-05",
            startTime = "9-00",
            finishTime = "11-30",
        ),
        false
    )
}*/