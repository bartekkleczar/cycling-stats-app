package com.example.cyclingstats.screens

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.DirectionsBike
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.DirectionsBike
import androidx.compose.material.icons.outlined.DirectionsRun
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.LocalFireDepartment
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material.icons.outlined.Route
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.ShutterSpeed
import androidx.compose.material.icons.outlined.Speed
import androidx.compose.material.icons.outlined.Stop
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.cyclingstats.functions.*
import com.example.cyclingstats.navigation.NavigationItem
import com.example.cyclingstats.navigation.Screen
import com.example.cyclingstats.ui.theme.CyclingStatsTheme
import com.example.cyclingstats.viewModel.TrainingViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTraining(navController: NavController, trainingViewModel: TrainingViewModel) {
    CyclingStatsTheme {
        val items = listOf(
            NavigationItem(
                title = "Overview",
                selectedIcon = Icons.Filled.BarChart,
                unselectedIcon = Icons.Outlined.BarChart,
                route = Screen.Overview.route
            ),
            NavigationItem(
                title = "Trainings",
                selectedIcon = Icons.Filled.DirectionsBike,
                unselectedIcon = Icons.Outlined.DirectionsBike,
                route = Screen.Trainings.route,
                badgeCount = trainingViewModel.trainings.collectAsStateWithLifecycle(initialValue = emptyList()).value.size
            ),
            NavigationItem(
                title = "Settings",
                selectedIcon = Icons.Filled.Settings,
                unselectedIcon = Icons.Outlined.Settings,
                route = Screen.Settings.route
            ),
            NavigationItem(
                title = "New Training",
                selectedIcon = Icons.Filled.Add,
                unselectedIcon = Icons.Outlined.Add,
                route = Screen.AddTraining.route
            )
        )
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
            val scope = rememberCoroutineScope()
            var selectedItemIndex by rememberSaveable {
                mutableStateOf(3)
            }
            ModalNavigationDrawer(
                drawerContent = {
                    ModalDrawerSheet {
                        Spacer(modifier = Modifier.height(16.dp))
                        items.forEachIndexed { index, item ->
                            NavigationDrawerItem(
                                colors = NavigationDrawerItemDefaults.colors(
                                    selectedContainerColor = schemeColor(
                                        "prim",
                                        isSystemInDarkTheme()
                                    )
                                ),
                                label = {
                                    Text(
                                        text = item.title,
                                        color = schemeColor("black", isSystemInDarkTheme())
                                    )
                                },
                                selected = index == selectedItemIndex,
                                onClick = {
                                    navController.navigate(item.route)
                                    selectedItemIndex = index
                                    scope.launch {
                                        drawerState.close()
                                    }
                                },
                                icon = {
                                    Icon(
                                        imageVector = if (index == selectedItemIndex) {
                                            item.selectedIcon
                                        } else item.unselectedIcon,
                                        contentDescription = item.title
                                    )
                                },
                                badge = {
                                    item.badgeCount?.let {
                                        Text(text = item.badgeCount.toString())
                                    }
                                },
                                modifier = Modifier
                                    .padding(NavigationDrawerItemDefaults.ItemPadding)
                            )
                        }
                    }
                },
                drawerState = drawerState
            ) {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        CenterAlignedTopAppBar(
                            title = {
                                Text(
                                    text = "New Training",
                                    fontSize = 25.sp,
                                    fontWeight = FontWeight.Bold,
                                )
                            },
                            navigationIcon = {
                                IconButton(onClick = {
                                    scope.launch {
                                        drawerState.open()
                                    }
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Menu,
                                        contentDescription = "Menu"
                                    )
                                }
                            }
                        )
                    },
                ) { values ->

                    Column {
                        var trainingDate by remember {
                            mutableStateOf("")
                        }

                        var trainingTime by remember {
                            mutableStateOf("")
                        }

                        var wholeTime by remember {
                            mutableStateOf("")
                        }

                        var startTime by remember {
                            mutableStateOf("")
                        }

                        var finishTime by remember {
                            mutableStateOf("")
                        }

                        var distance by remember {
                            mutableStateOf("")
                        }

                        var avgSpeed by remember {
                            mutableStateOf("")
                        }

                        var maxSpeed by remember {
                            mutableStateOf("")
                        }

                        var averagePulse by remember {
                            mutableStateOf("")
                        }

                        var maxPulse by remember {
                            mutableStateOf("")
                        }

                        var calories by remember {
                            mutableStateOf("")
                        }

                        var avgPace by remember {
                            mutableStateOf("")
                        }

                        var maxPace by remember {
                            mutableStateOf("")
                        }

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(0.9f)
                                .padding(horizontal = 16.dp)
                                .verticalScroll(rememberScrollState())
                        ) {
                            Spacer(modifier = Modifier.height(85.dp))
                            OutlinedTextField(
                                modifier = Modifier
                                    .padding(bottom = 10.dp)
                                    .fillMaxWidth(0.8f),
                                value = trainingDate,
                                onValueChange = {
                                    trainingDate = it
                                },
                                placeholder = {
                                    Text(text = "YYYY-MM-DD")
                                },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Outlined.CalendarMonth,
                                        contentDescription = "Calendar"
                                    )
                                },
                                label = {
                                    Text(text = "Enter training date")
                                },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color.Black,
                                    unfocusedBorderColor = Color.Black,
                                    focusedLabelColor = Color.Black
                                )
                            )
                            OutlinedTextField(
                                modifier = Modifier
                                    .padding(bottom = 10.dp)
                                    .fillMaxWidth(0.8f),
                                value = trainingTime,
                                onValueChange = {
                                    trainingTime = it
                                },
                                placeholder = {
                                    Text(text = "hours-minutes")
                                },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Outlined.AccessTime,
                                        contentDescription = "Calendar"
                                    )
                                },
                                label = {
                                    Text(text = "Enter training time")
                                },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color.Black,
                                    unfocusedBorderColor = Color.Black,
                                    focusedLabelColor = Color.Black
                                )
                            )
                            OutlinedTextField(
                                modifier = Modifier
                                    .padding(bottom = 10.dp)
                                    .fillMaxWidth(0.8f),
                                value = wholeTime,
                                onValueChange = {
                                    wholeTime = it
                                },
                                placeholder = {
                                    Text(text = "hours-minutes")
                                },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Outlined.Timer,
                                        contentDescription = "Time"
                                    )
                                },
                                label = {
                                    Text(text = "Enter whole time")
                                },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color.Black,
                                    unfocusedBorderColor = Color.Black,
                                    focusedLabelColor = Color.Black
                                )
                            )
                            OutlinedTextField(
                                modifier = Modifier
                                    .padding(bottom = 10.dp)
                                    .fillMaxWidth(0.8f),
                                value = startTime,
                                onValueChange = {
                                    startTime = it
                                },
                                placeholder = {
                                    Text(text = "hour-minute")
                                },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Outlined.PlayArrow,
                                        contentDescription = "Start"
                                    )
                                },
                                label = {
                                    Text(text = "Enter start time")
                                },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color.Black,
                                    unfocusedBorderColor = Color.Black,
                                    focusedLabelColor = Color.Black
                                )
                            )
                            OutlinedTextField(
                                modifier = Modifier
                                    .padding(bottom = 10.dp)
                                    .fillMaxWidth(0.8f),
                                value = finishTime,
                                onValueChange = {
                                    finishTime = it
                                },
                                placeholder = {
                                    Text(text = "hour-minute")
                                },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Outlined.Stop,
                                        contentDescription = "Stop"
                                    )
                                },
                                label = {
                                    Text(text = "Enter finish time")
                                },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color.Black,
                                    unfocusedBorderColor = Color.Black,
                                    focusedLabelColor = Color.Black
                                ),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Decimal
                                )
                            )
                            OutlinedTextField(
                                modifier = Modifier
                                    .padding(bottom = 10.dp)
                                    .fillMaxWidth(0.8f),
                                value = distance,
                                onValueChange = {
                                    distance = it
                                },
                                textStyle = LocalTextStyle.current.copy(
                                    textAlign = TextAlign.Right
                                ),
                                placeholder = {
                                    Text(text = "km")
                                },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Outlined.Route,
                                        contentDescription = "Distance"
                                    )
                                },
                                label = {
                                    Text(text = "Enter training distance")
                                },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color.Black,
                                    unfocusedBorderColor = Color.Black,
                                    focusedLabelColor = Color.Black
                                ),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Decimal
                                ),
                                suffix = { Text(text = "km") }
                            )
                            OutlinedTextField(
                                modifier = Modifier
                                    .padding(bottom = 10.dp)
                                    .fillMaxWidth(0.8f),
                                value = avgSpeed,
                                onValueChange = {
                                    avgSpeed = it
                                },
                                textStyle = LocalTextStyle.current.copy(
                                    textAlign = TextAlign.Right
                                ),
                                placeholder = {
                                    Text(text = "km/h")
                                },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Outlined.ShutterSpeed,
                                        contentDescription = "Avg speed"
                                    )
                                },
                                label = {
                                    Text(text = "Enter avg speed")
                                },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color.Black,
                                    unfocusedBorderColor = Color.Black,
                                    focusedLabelColor = Color.Black
                                ),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Decimal
                                ),
                                suffix = { Text(text = "km/h") }
                            )
                            OutlinedTextField(
                                modifier = Modifier
                                    .padding(bottom = 10.dp)
                                    .fillMaxWidth(0.8f),
                                value = maxSpeed,
                                onValueChange = {
                                    maxSpeed = it
                                },
                                textStyle = LocalTextStyle.current.copy(
                                    textAlign = TextAlign.Right
                                ),
                                placeholder = {
                                    Text(text = "km/h")
                                },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Outlined.Speed,
                                        contentDescription = "Max speed"
                                    )
                                },
                                label = {
                                    Text(text = "Enter max speed")
                                },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color.Black,
                                    unfocusedBorderColor = Color.Black,
                                    focusedLabelColor = Color.Black
                                ),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Decimal
                                ),
                                suffix = { Text(text = "km/h") }
                            )
                            OutlinedTextField(
                                modifier = Modifier
                                    .padding(bottom = 10.dp)
                                    .fillMaxWidth(0.8f),
                                value = averagePulse,
                                onValueChange = {
                                    averagePulse = it
                                },
                                textStyle = LocalTextStyle.current.copy(
                                    textAlign = TextAlign.Right
                                ),
                                placeholder = {
                                    Text(text = "beats/minute")
                                },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Outlined.FavoriteBorder,
                                        contentDescription = "Avg pulse"
                                    )
                                },
                                label = {
                                    Text(text = "Enter avg pulse")
                                },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color.Black,
                                    unfocusedBorderColor = Color.Black,
                                    focusedLabelColor = Color.Black
                                ),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Decimal
                                ),
                                suffix = { Text(text = "bts/min") }
                            )
                            OutlinedTextField(
                                modifier = Modifier
                                    .padding(bottom = 10.dp)
                                    .fillMaxWidth(0.8f),
                                value = maxPulse,
                                onValueChange = {
                                    maxPulse = it
                                },
                                textStyle = LocalTextStyle.current.copy(
                                    textAlign = TextAlign.Right
                                ),
                                placeholder = {
                                    Text(text = "MAx pulse")
                                },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Filled.FavoriteBorder,
                                        contentDescription = "beats/minute"
                                    )
                                },
                                label = {
                                    Text(text = "Enter max pulse")
                                },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color.Black,
                                    unfocusedBorderColor = Color.Black,
                                    focusedLabelColor = Color.Black
                                ),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Decimal
                                ),
                                suffix = { Text(text = "bts/min") }
                            )
                            OutlinedTextField(
                                modifier = Modifier
                                    .padding(bottom = 10.dp)
                                    .fillMaxWidth(0.8f),
                                value = calories,
                                onValueChange = {
                                    calories = it
                                },
                                textStyle = LocalTextStyle.current.copy(
                                    textAlign = TextAlign.Right
                                ),
                                placeholder = {
                                    Text(text = "Calories")
                                },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Outlined.LocalFireDepartment,
                                        contentDescription = "cal"
                                    )
                                },
                                label = {
                                    Text(text = "Enter calories")
                                },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color.Black,
                                    unfocusedBorderColor = Color.Black,
                                    focusedLabelColor = Color.Black
                                ),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Decimal
                                ),
                                suffix = { Text(text = "cal") }
                            )
                            OutlinedTextField(
                                modifier = Modifier
                                    .padding(bottom = 10.dp)
                                    .fillMaxWidth(0.8f),
                                value = avgPace,
                                onValueChange = {
                                    avgPace = it
                                },
                                textStyle = LocalTextStyle.current.copy(
                                    textAlign = TextAlign.Right
                                ),
                                placeholder = {
                                    Text(text = "Avg pace")
                                },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Outlined.DirectionsRun,
                                        contentDescription = "Pace"
                                    )
                                },
                                label = {
                                    Text(text = "Enter avg pace")
                                },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color.Black,
                                    unfocusedBorderColor = Color.Black,
                                    focusedLabelColor = Color.Black
                                ),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Decimal
                                ),
                                suffix = { Text(text = "Avg pace") }
                            )
                            OutlinedTextField(
                                modifier = Modifier
                                    .padding(bottom = 10.dp)
                                    .fillMaxWidth(0.8f),
                                value = maxPace,
                                onValueChange = {
                                    maxPace = it
                                },
                                textStyle = LocalTextStyle.current.copy(
                                    textAlign = TextAlign.Right
                                ),
                                placeholder = {
                                    Text(text = "Max pace")
                                },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Filled.DirectionsRun,
                                        contentDescription = "pace"
                                    )
                                },
                                label = {
                                    Text(text = "Enter max pace")
                                },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color.Black,
                                    unfocusedBorderColor = Color.Black,
                                    focusedLabelColor = Color.Black
                                ),
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Decimal
                                ),
                                suffix = { Text(text = "Max pace") }
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 20.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            FilledTonalButton(
                                colors = ButtonDefaults.filledTonalButtonColors(
                                    containerColor = schemeColor("sec", isSystemInDarkTheme()),
                                    contentColor = schemeColor("black", isSystemInDarkTheme())
                                ),
                                onClick = {
                                    val training = makeTraining(
                                        index = 0,
                                        trainingDate = trainingDate,
                                        trainingTime = trainingTime,
                                        wholeTime = wholeTime,
                                        distance = distance,
                                        averageSpeed = avgSpeed,
                                        maxSpeed = maxSpeed,
                                        averagePulse = averagePulse.toInt(),
                                        maxPulse = averagePulse.toInt(),
                                        calories = calories.toInt(),
                                        averagePace = avgPace,
                                        maxPace = maxPace,
                                        startTime = startTime,
                                        finishTime = finishTime,
                                        )

                                    trainingViewModel.insert(training)
                                    navController.navigate(Screen.Trainings.route)
                                }
                            ) {
                                Text(text = "Add Training")
                            }
                        }
                    }
                }
            }
        }
    }
}