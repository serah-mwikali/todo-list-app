package com.example.todolistapp.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.todolistapp.model.Task
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class TaskViewModel : ViewModel() {

    private val db = Firebase.firestore

    var taskText = mutableStateOf("")

    var taskList = mutableStateListOf<Task>()

    fun addTask() {

        if (taskText.value.isNotEmpty()) {

            val document =
                db.collection("tasks").document()

            val newTask = Task(
                id = document.id,
                task = taskText.value,
                completed = false
            )

            document.set(newTask)

            taskList.add(newTask)

            taskText.value = ""
        }
    }

    fun deleteTask(task: Task) {

        db.collection("tasks")
            .document(task.id)
            .delete()

        taskList.remove(task)
    }

    fun toggleTaskCompleted(task: Task) {

        val updatedTask = task.copy(
            completed = !task.completed
        )

        db.collection("tasks")
            .document(task.id)
            .set(updatedTask)

        val index = taskList.indexOf(task)

        if (index != -1) {

            taskList[index] = updatedTask
        }
    }

    fun editTask(
        oldTask: Task,
        newText: String
    ) {

        val updatedTask = oldTask.copy(
            task = newText
        )

        db.collection("tasks")
            .document(oldTask.id)
            .set(updatedTask)

        val index = taskList.indexOf(oldTask)

        if (index != -1) {

            taskList[index] = updatedTask
        }
    }
}