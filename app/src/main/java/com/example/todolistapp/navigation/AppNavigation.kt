package com.example.todolistapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.*
import com.example.todolistapp.screens.HomeScreen
import com.example.todolistapp.screens.TaskScreen

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {

        composable("home") {
            HomeScreen(navController)
        }

        composable("tasks") {
            TaskScreen()
        }
    }
}

