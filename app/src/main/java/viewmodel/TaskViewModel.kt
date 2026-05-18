package com.example.todolistapp.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class TaskViewModel : ViewModel() {

    var taskText = mutableStateOf("")

    var taskList = mutableStateListOf<String>()

    fun addTask() {

        if (taskText.value.isNotEmpty()) {

            taskList.add(taskText.value)

            taskText.value = ""
        }
    }

    fun deleteTask(task: String) {
        taskList.remove(task)
    }
}
