package com.example.todolistapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todolistapp.model.Task
import com.example.todolistapp.viewmodel.TaskViewModel

@Composable
fun TaskScreen(
    taskViewModel: TaskViewModel = viewModel()
) {

    val navyBlue = Color(0xFF001F54)
    val backgroundGray = Color(0xFFE0E0E0)
    val cardGray = Color(0xFFBDBDBD)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundGray)
            .padding(16.dp)
    ) {

        Text(
            text = "My Tasks",
            style = MaterialTheme.typography.headlineSmall,
            color = navyBlue,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = taskViewModel.taskText.value,
            onValueChange = { taskViewModel.taskText.value = it },
            placeholder = { Text("Enter Task") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = { taskViewModel.addTask() },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = navyBlue
            )
        ) {
            Text("Add Task")
        }

        Spacer(modifier = Modifier.height(20.dp))

        LazyColumn(
            modifier = Modifier.fillMaxHeight()
                .weight(1f)
        )
        {

            items(
                items = taskViewModel.taskList,
                key = { it.id }
            ) { task ->

                var isEditing by remember(task.id) { mutableStateOf(false) }
                var editedText by remember(task.id) { mutableStateOf(task.task) }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = cardGray),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {

                    Column(modifier = Modifier.padding(16.dp)) {

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {

                            Text(
                                text = task.task,
                                color = Color.Black,
                                textDecoration = TextDecoration.None
                            )

                            Checkbox(
                                checked = task.completed,
                                onCheckedChange = {
                                    taskViewModel.toggleTaskCompleted(task)
                                }
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        if (isEditing) {

                            OutlinedTextField(
                                value = editedText,
                                onValueChange = { editedText = it },
                                modifier = Modifier.fillMaxWidth()
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Button(
                                onClick = {
                                    taskViewModel.editTask(task, editedText)
                                    isEditing = false
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = navyBlue
                                )
                            ) {
                                Text("Save")
                            }

                        } else {

                            Row {

                                Button(
                                    onClick = { isEditing = true },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = navyBlue
                                    )
                                ) {
                                    Text("Edit")
                                }

                                Spacer(modifier = Modifier.width(8.dp))

                                Button(
                                    onClick = { taskViewModel.deleteTask(task) },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = navyBlue
                                    )
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