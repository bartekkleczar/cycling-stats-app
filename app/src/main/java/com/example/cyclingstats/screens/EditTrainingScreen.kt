package com.example.cyclingstats.screens

import android.util.Log
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
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DirectionsBike
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.DirectionsBike
import androidx.compose.material.icons.outlined.DirectionsRun
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.LocalFireDepartment
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material.icons.outlined.Route
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.ShutterSpeed
import androidx.compose.material.icons.outlined.Speed
import androidx.compose.material.icons.outlined.Stop
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
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
import com.example.cyclingstats.MainActivity
import com.example.cyclingstats.db.Training
import com.example.cyclingstats.functions.formatFloat
import com.example.cyclingstats.functions.makeTraining
import com.example.cyclingstats.functions.schemeColor
import com.example.cyclingstats.navigation.NavigationItem
import com.example.cyclingstats.navigation.Screen
import com.example.cyclingstats.ui.theme.CyclingStatsTheme
import com.example.cyclingstats.viewModel.TrainingViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTraining(
    navController: NavController,
    trainingViewModel: TrainingViewModel,
    index: Int?,

    ) {
    if (index == -1 || index == null) navController.navigate(Screen.AddTraining.route)
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
                title = "Edit Your Training",
                selectedIcon = Icons.Filled.Edit,
                unselectedIcon = Icons.Outlined.Edit,
                route = "None"
            ),
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
                                    if (item.route != "None") navController.navigate(item.route)
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
                                    text = "Edit Your Training",
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
                    }
                ) { values ->
                    val trainingsList by trainingViewModel.trainings.collectAsStateWithLifecycle(
                        emptyList()
                    )
                    var training = MainActivity.defaultTraining(-3)

                    var trainingDate by remember { mutableStateOf(training.trainingDate) }

                    var trainingTime by remember { mutableStateOf(training.trainingTime) }

                    var wholeTime by remember { mutableStateOf(training.wholeTime) }

                    var startTime by remember { mutableStateOf(training.startTime) }

                    var finishTime by remember { mutableStateOf(training.finishTime) }

                    var distance by remember { mutableStateOf(training.distance.toString()) }

                    var avgSpeed by remember { mutableStateOf(training.averageSpeed.toString()) }

                    var maxSpeed by remember { mutableStateOf(training.maxSpeed.toString()) }

                    var avgPulse by remember { mutableStateOf(training.averagePulse.toString()) }

                    var maxPulse by remember { mutableStateOf(training.maxPulse.toString()) }

                    var calories by remember { mutableStateOf(training.calories.toString()) }

                    var avgPace by remember { mutableStateOf(training.averagePace) }

                    var maxPace by remember { mutableStateOf(training.maxPace) }

                    for (it in trainingsList) {
                        if (it.index == index) {
                            Log.d("Main", it.index.toString())
                            Log.d("Main", "${it.index} -- $index || $it")

                            trainingDate = it.trainingDate
                            trainingTime = it.trainingTime
                            wholeTime = it.wholeTime
                            startTime = it.startTime
                            finishTime = it.finishTime
                            distance = it.distance.toString()
                            avgSpeed = it.averageSpeed.toString()
                            maxSpeed = it.maxSpeed.toString()
                            avgPulse = it.averagePulse.toString()
                            maxPulse = it.maxPulse.toString()
                            calories = it.calories.toString()
                            avgPace = it.averagePace
                            maxPace = it.maxPace
                        }
                    }

                    Column {

                        var trainingDateEdit by remember {
                            mutableStateOf("")
                        }

                        var trainingTimeEdit by remember {
                            mutableStateOf("")
                        }

                        var wholeTimeEdit by remember {
                            mutableStateOf("")
                        }

                        var startTimeEdit by remember {
                            mutableStateOf("")
                        }

                        var finishTimeEdit by remember {
                            mutableStateOf("")
                        }

                        var distanceEdit by remember {
                            mutableStateOf("")
                        }

                        var avgSpeedEdit by remember {
                            mutableStateOf("")
                        }

                        var maxSpeedEdit by remember {
                            mutableStateOf("")
                        }

                        var avgPulseEdit by remember {
                            mutableStateOf("")
                        }

                        var maxPulseEdit by remember {
                            mutableStateOf("")
                        }

                        var caloriesEdit by remember {
                            mutableStateOf("")
                        }

                        var avgPaceEdit by remember {
                            mutableStateOf("")
                        }

                        var maxPaceEdit by remember {
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
                            /*TODO->DATE-PICKER*/
                            OutlinedTextField(
                                modifier = Modifier
                                    .padding(bottom = 10.dp)
                                    .fillMaxWidth(0.8f),
                                value = trainingDateEdit,
                                onValueChange = {
                                    trainingDateEdit = it
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
                                    Text(text = trainingDate)
                                },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color.Black,
                                    unfocusedBorderColor = Color.Black,
                                    focusedLabelColor = Color.Black
                                )
                            )
                            /*TODO->TIME-PICKER*/
                            OutlinedTextField(
                                modifier = Modifier
                                    .padding(bottom = 10.dp)
                                    .fillMaxWidth(0.8f),
                                value = trainingTimeEdit,
                                onValueChange = {
                                    trainingTimeEdit = it
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
                                    Text(text = trainingTime)
                                },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color.Black,
                                    unfocusedBorderColor = Color.Black,
                                    focusedLabelColor = Color.Black
                                )
                            )
                            /*TODO->TIME-PICKER*/
                            OutlinedTextField(
                                modifier = Modifier
                                    .padding(bottom = 10.dp)
                                    .fillMaxWidth(0.8f),
                                value = wholeTimeEdit,
                                onValueChange = {
                                    wholeTimeEdit = it
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
                                    Text(text = wholeTime)
                                },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color.Black,
                                    unfocusedBorderColor = Color.Black,
                                    focusedLabelColor = Color.Black
                                )
                            )
                            /*TODO->TIME-PICKER*/
                            OutlinedTextField(
                                modifier = Modifier
                                    .padding(bottom = 10.dp)
                                    .fillMaxWidth(0.8f),
                                value = startTimeEdit,
                                onValueChange = {
                                    startTimeEdit = it
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
                                    Text(text = startTime)
                                },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color.Black,
                                    unfocusedBorderColor = Color.Black,
                                    focusedLabelColor = Color.Black
                                )
                            )
                            /*TODO->TIME-PICKER*/
                            OutlinedTextField(
                                modifier = Modifier
                                    .padding(bottom = 10.dp)
                                    .fillMaxWidth(0.8f),
                                value = finishTimeEdit,
                                onValueChange = {
                                    finishTimeEdit = it
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
                                    Text(text = finishTime)
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
                                value = distanceEdit,
                                onValueChange = {
                                    distanceEdit = it
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
                                    Text(text = distance)
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
                                value = avgSpeedEdit,
                                onValueChange = {
                                    avgSpeedEdit = it
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
                                    Text(text = avgSpeed)
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
                                value = maxSpeedEdit,
                                onValueChange = {
                                    maxSpeedEdit = it
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
                                    Text(text = maxSpeed)
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
                                value = avgPulseEdit,
                                onValueChange = {
                                    avgPulseEdit = it
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
                                    Text(text = avgPulse)
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
                                value = maxPulseEdit,
                                onValueChange = {
                                    maxPulseEdit = it
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
                                    Text(text = maxPulse)
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
                                value = caloriesEdit,
                                onValueChange = {
                                    caloriesEdit = it
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
                                    Text(text = calories)
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
                                value = avgPaceEdit,
                                onValueChange = {
                                    avgPaceEdit = it
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
                                    Text(text = avgPace)
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
                                value = maxPaceEdit,
                                onValueChange = {
                                    maxPaceEdit = it
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
                                    Text(text = maxPace)
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
                                    trainingViewModel.update(
                                        makeTraining(
                                            index = training.index,
                                            trainingDate = trainingDate,
                                            trainingTime = trainingTime,
                                            wholeTime = wholeTime,
                                            distance = distance,
                                            averageSpeed = avgSpeed,
                                            maxSpeed = maxSpeed,
                                            averagePulse = avgPulse.toInt(),
                                            maxPulse = avgPulse.toInt(),
                                            calories = calories.toInt(),
                                            averagePace = avgPace,
                                            maxPace = maxPace,
                                            startTime = startTime,
                                            finishTime = finishTime,
                                        )
                                    )
                                    navController.navigate(Screen.Trainings.route)
                                }
                            ) {
                                Text(text = "Update Training")
                            }
                        }
                    }
                }
            }
        }
    }
}