package com.example.todolistapp.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class TaskViewModel : ViewModel() {

    private val db = Firebase.firestore

    var taskText = mutableStateOf("")

    var taskList = mutableStateListOf<String>()

    fun addTask() {

        if (taskText.value.isNotEmpty()) {

            val task = hashMapOf(
                "task" to taskText.value
            )

            db.collection("tasks")
                .add(task)

            taskList.add(taskText.value)

            taskText.value = ""
        }
    }

    fun deleteTask(task: String) {
        taskList.remove(task)
    }
}
