package com.example.cyclingstats.screens

import android.content.Context
import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.DirectionsBike
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.DirectionsBike
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.cyclingstats.MainActivity
import com.example.cyclingstats.MyPreferences
import com.example.cyclingstats.functions.*
import com.example.cyclingstats.navigation.NavigationItem
import com.example.cyclingstats.navigation.Screen
import com.example.cyclingstats.ui.theme.CyclingStatsTheme
import com.example.cyclingstats.viewModel.TrainingViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OverviewScreen(navController: NavController, trainingViewModel: TrainingViewModel, context: Context) {
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
        )
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
            val scope = rememberCoroutineScope()
            var selectedItemIndex by rememberSaveable {
                mutableStateOf(0)
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
                                label = { Text(text = item.title, color = schemeColor("black", isSystemInDarkTheme())) },
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
                                    text = "Your Overview",
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
                            actions = {},
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
                    val totalDistance = remember { mutableStateOf(0f) }
                    val longestTime = remember { mutableStateOf("0:00 h") }
                    val highestSpeed = remember { mutableStateOf("0 km/h") }
                    val longestDistance = remember { mutableStateOf("0 km") }

                    val trainingsList by trainingViewModel.trainings.collectAsState(emptyList()) // Pobieranie listy treningów

                    LaunchedEffect(trainingsList) {
                        val distance = trainingViewModel.getTotalDistance()
                        val time = trainingViewModel.getLongestTime()
                        val speed = trainingViewModel.getHighestSpeed()
                        val route = trainingViewModel.getLongestDistance()

                        totalDistance.value = distance
                        longestTime.value = time
                        highestSpeed.value = speed
                        longestDistance.value = route
                    }
                    Column(
                        verticalArrangement = Arrangement.Bottom,
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.875f)
                            .padding(horizontal = 16.dp)
                    ) {
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .padding(horizontal = 10.dp)
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "Total distance",
                                        fontSize = 25.sp,
                                        textAlign = TextAlign.Center,
                                        color = Color.Black
                                    )
                                    Spacer(modifier = Modifier.height(20.dp))
                                    Text(
                                        text = "${totalDistance.value} km",
                                        fontSize = 35.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Black
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = schemeColor(
                                    "sec",
                                    isSystemInDarkTheme()
                                )
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(170.dp)
                                .padding(horizontal = 10.dp)
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "The longest distance",
                                        fontSize = 25.sp,
                                        textAlign = TextAlign.Center,
                                        color = Color.Black
                                    )
                                    Spacer(modifier = Modifier.height(20.dp))
                                    Text(
                                        text = longestDistance.value,
                                        fontSize = 35.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Black,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        Row(
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp)
                        ) {
                            Card(
                                colors = CardDefaults.cardColors(
                                    containerColor = schemeColor(
                                        "ter",
                                        isSystemInDarkTheme()
                                    )
                                ),
                                modifier = Modifier
                                    .size(200.dp)
                            ) {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(
                                            text = "The highest speed",
                                            fontSize = 25.sp,
                                            textAlign = TextAlign.Center,
                                            color = Color.Black
                                        )
                                        Spacer(modifier = Modifier.height(20.dp))
                                        Text(
                                            text = highestSpeed.value,
                                            fontSize = 35.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.Black
                                        )
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Card(
                                colors = CardDefaults.cardColors(
                                    containerColor = schemeColor(
                                        "fou",
                                        isSystemInDarkTheme()
                                    )
                                ),
                                modifier = Modifier
                                    .height(200.dp)
                            ) {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(
                                            text = "The longest training time",
                                            fontSize = 25.sp,
                                            textAlign = TextAlign.Center,
                                            color = Color.Black
                                        )
                                        Spacer(modifier = Modifier.height(20.dp))
                                        Text(
                                            text = longestTime.value,
                                            fontSize = 35.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.Black
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}