package com.example.cyclingstats.screens

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DirectionsBike
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.DirectionsBike
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.cyclingstats.MainActivity
import com.example.cyclingstats.db.Training
import com.example.cyclingstats.functions.schemeColor
import com.example.cyclingstats.navigation.NavigationItem
import com.example.cyclingstats.navigation.Screen
import com.example.cyclingstats.ui.theme.CyclingStatsTheme
import com.example.cyclingstats.viewModel.TrainingViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrainingView(navController: NavController, trainingViewModel: TrainingViewModel, index: Int?) {
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
                title = "Your Training",
                selectedIcon = Icons.Filled.DirectionsBike,
                unselectedIcon = Icons.Outlined.DirectionsBike,
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
                                    if(item.route != "None") navController.navigate(item.route)
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
                                    text = "Your Training",
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
                    bottomBar = {
                        BottomAppBar(
                            actions = {
                                IconButton(onClick = {
                                    trainingViewModel.delete(
                                        Training(
                                            index = index?.toInt() ?: -1,
                                            trainingDate = "",
                                            trainingTime = "",
                                            wholeTime = "",
                                            distance = 0f,
                                            averageSpeed = 0f,
                                            maxSpeed = 0f,
                                            averagePulse = 0,
                                            maxPulse = 0,
                                            calories = 0,
                                            averagePace = "",
                                            maxPace = "",
                                            startTime = "",
                                            finishTime = "",
                                        )
                                    )
                                    navController.navigate(Screen.Trainings.route)
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Delete training",
                                        tint = schemeColor(
                                            "black",
                                            isSystemInDarkTheme()
                                        )
                                    )
                                }
                                IconButton(onClick = {
                                    if(index != null){
                                        navController.navigate(
                                            Screen.EditTraining.withArgs(
                                                index.toString()
                                            )
                                        )
                                    }
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Edit,
                                        contentDescription = "Edit training",
                                        tint = schemeColor(
                                            "black",
                                            isSystemInDarkTheme()
                                        )
                                    )
                                }
                            },
                            floatingActionButton = {
                                FloatingActionButton(
                                    onClick = { navController.navigate(Screen.AddTraining.route) },
                                    containerColor = schemeColor(
                                        "sec",
                                        isSystemInDarkTheme()
                                    )
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = "New training",
                                        tint = schemeColor(
                                            "black",
                                            isSystemInDarkTheme()
                                        )
                                    )
                                }
                            }
                        )
                    }
                ) { values ->
                    val trainingsList by trainingViewModel.trainings.collectAsStateWithLifecycle(emptyList())
                    val training = MainActivity.defaultTraining(-3)

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

                    for(it in trainingsList) {
                        if(it.index == index) {
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
                }
            }
        }
    }
}