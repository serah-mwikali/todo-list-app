package com.example.todolistapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todolistapp.viewmodel.TaskViewModel
import com.example.todolistapp.model.Task

@Composable
fun TaskScreen(
    taskViewModel: TaskViewModel = viewModel()
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        OutlinedTextField(
            value = taskViewModel.taskText.value,
            onValueChange = {
                taskViewModel.taskText.value = it
            },
            label = { Text("Enter Task") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = { taskViewModel.addTask() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Task")
        }

        Spacer(modifier = Modifier.height(20.dp))

        LazyColumn {

            items(taskViewModel.taskList) { task: Task ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Text(text = task.task)

                        Button(
                            onClick = {
                                taskViewModel.deleteTask(task)
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