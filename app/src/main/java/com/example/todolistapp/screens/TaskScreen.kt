package com.example.todolistapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todolistapp.model.Task
import com.example.todolistapp.viewmodel.TaskViewModel

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

            label = {
                Text("Enter Task")
            },

            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                taskViewModel.addTask()
            },

            modifier = Modifier.fillMaxWidth()
        ) {

            Text("Add Task")
        }

        Spacer(modifier = Modifier.height(20.dp))

        LazyColumn {

            items(taskViewModel.taskList) { task: Task ->

                var isEditing by remember {
                    mutableStateOf(false)
                }

                var editedText by remember {
                    mutableStateOf(task.task)
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {

                        Row(
                            modifier =
                                Modifier.fillMaxWidth(),

                            horizontalArrangement =
                                Arrangement.SpaceBetween
                        ) {

                            Text(
                                text =
                                    if (task.completed)
                                        "✅ ${task.task}"
                                    else
                                        task.task
                            )

                            Checkbox(
                                checked = task.completed,

                                onCheckedChange = {

                                    taskViewModel
                                        .toggleTaskCompleted(task)
                                }
                            )
                        }

                        Spacer(
                            modifier = Modifier.height(8.dp)
                        )

                        if (isEditing) {

                            OutlinedTextField(
                                value = editedText,

                                onValueChange = {
                                    editedText = it
                                },

                                modifier =
                                    Modifier.fillMaxWidth()
                            )

                            Spacer(
                                modifier =
                                    Modifier.height(8.dp)
                            )

                            Button(
                                onClick = {

                                    taskViewModel.editTask(
                                        task,
                                        editedText
                                    )

                                    isEditing = false
                                }
                            ) {

                                Text("Save")
                            }

                        } else {

                            Row {

                                Button(
                                    onClick = {
                                        isEditing = true
                                    }
                                ) {
                                    Text("Edit")
                                }

                                Spacer(
                                    modifier =
                                        Modifier.width(8.dp)
                                )

                                Button(
                                    onClick = {

                                        taskViewModel
                                            .deleteTask(task)
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
    }
}