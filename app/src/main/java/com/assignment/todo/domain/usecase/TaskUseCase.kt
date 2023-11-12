package com.assignment.todo.domain.usecase

import com.assignment.todo.data.model.Task
import com.assignment.todo.domain.repository.TaskRepository
import javax.inject.Inject

class TaskUseCase @Inject constructor(private val repository: TaskRepository) {

    suspend fun fetchAndSaveTasks() {
        repository.fetchAndSaveTasks()
    }

    suspend fun getTasks(): List<Task> {
        return repository.getTasks()
    }

    suspend fun deleteTask(task: Task) {
        repository.deleteTask(task)
    }

    suspend fun markTaskAsCompleted(task: Task) {
        repository.markTaskAsCompleted(task)
    }

    suspend fun addTask(task: Task) {
        repository.addTask(task)
    }

    suspend fun updateTask(task: Task) {
        repository.updateTask(task)
    }

    suspend fun getUncompletedTasks(currentDate: String): List<Task> {
        return repository.getUncompletedTasks(currentDate)
    }

}
