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

    init {
        loadTasks()
    }

    fun addTask() {

        if (taskText.value.isNotEmpty()) {

            val taskMap = hashMapOf(
                "task" to taskText.value
            )

            db.collection("tasks")
                .add(taskMap)
                .addOnSuccessListener {
                    taskText.value = ""
                }
                .addOnFailureListener { e ->
                    e.printStackTrace()
                }
        }
    }

    private fun loadTasks() {

        db.collection("tasks")
            .addSnapshotListener { snapshot, error ->

                if (error != null) return@addSnapshotListener

                val list = mutableListOf<Task>()

                for (doc in snapshot!!) {

                    val task = Task(
                        id = doc.id,
                        task = doc.getString("task") ?: ""
                    )

                    list.add(task)
                }

                taskList.clear()
                taskList.addAll(list)
            }
    }
    fun deleteTask(task: Task) {

        db.collection("tasks")
            .document(task.id)
            .delete()
            .addOnFailureListener { e ->
                e.printStackTrace()
            }
    }
}