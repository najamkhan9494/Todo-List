package com.assignment.todo.domain.repository

import com.assignment.todo.data.model.Task

interface TaskRepository {
    suspend fun fetchAndSaveTasks()
    suspend fun getTasks(): List<Task>
    suspend fun deleteTask(task: Task)
    suspend fun markTaskAsCompleted(task: Task)
    suspend fun addTask(task: Task)
    suspend fun updateTask(task: Task)
    suspend fun getUncompletedTasks(currentDate: String): List<Task>
}
