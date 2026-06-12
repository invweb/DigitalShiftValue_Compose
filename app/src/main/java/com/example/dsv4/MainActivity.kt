package com.example.dsv4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.dsv4.presentation.ui.AboutScreen
import com.example.dsv4.presentation.ui.CounterScreen
import com.example.dsv4.presentation.ui.CounterViewModel
import com.example.dsv4.ui.theme.DSV4Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DSV4Theme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        NavigationBar {
                            NavigationBarItem(
                                selected = currentRoute == "CounterScreen",
                                onClick = {
                                    navController.navigate("CounterScreen") {
                                        popUpTo(navController.graph.startDestinationId) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                icon = { Icon(Icons.Default.Home, contentDescription = null) },
                                label = { Text(stringResource(R.string.menu_counter)) }
                            )
                            NavigationBarItem(
                                selected = currentRoute == "AboutScreen",
                                onClick = {
                                    navController.navigate("AboutScreen") {
                                        popUpTo(navController.graph.startDestinationId) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                icon = { Icon(Icons.Default.Info, contentDescription = null) },
                                label = { Text(stringResource(R.string.menu_about)) }
                            )
                        }
                    }
                ) { paddingValues ->
                    NavHost(
                        startDestination = "CounterScreen",
                        navController = navController,
                        modifier = Modifier.padding(paddingValues)
                    ) {
                        composable("CounterScreen") {
                            val viewModel: CounterViewModel by viewModels()
                            CounterScreen(viewModel = viewModel)
                        }
                        composable("AboutScreen") {
                            AboutScreen()
                        }
                    }
                }
            }
        }
    }
}
