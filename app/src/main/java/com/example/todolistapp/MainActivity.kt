package com.example.todolistapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.*
import androidx.navigation.NavHostController

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

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
    }
}

@Composable
fun HomeScreen(navController: NavHostController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),

        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Welcome To Your To-Do App",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                navController.navigate("tasks")
            },

            modifier = Modifier.fillMaxWidth()
        ) {

            Text("Open Tasks")
        }
    }
}

@Composable
fun TaskScreen() {

    var taskText by remember {
        mutableStateOf("")
    }

    val taskList = remember {
        mutableStateListOf<String>()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        OutlinedTextField(
            value = taskText,

            onValueChange = {
                taskText = it
            },

            label = {
                Text("Enter Task")
            },

            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {

                if (taskText.isNotEmpty()) {
                    taskList.add(taskText)
                    taskText = ""
                }

            },

            modifier = Modifier.fillMaxWidth()
        ) {

            Text("Add Task")
        }

        Spacer(modifier = Modifier.height(20.dp))

        LazyColumn {

            items(taskList) { task ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),

                        horizontalArrangement =
                            Arrangement.SpaceBetween
                    ) {

                        Text(text = task)

                        Button(
                            onClick = {
                                taskList.remove(task)
                            }
                        ) {
                            Text("Delete")
                        }
                    }
                }
            }
        }
    }
}