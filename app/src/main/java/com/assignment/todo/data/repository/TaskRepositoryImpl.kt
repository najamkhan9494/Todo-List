package com.assignment.todo.data.repository

import com.assignment.todo.data.db.TaskDao
import com.assignment.todo.data.model.Task
import com.assignment.todo.data.remote.ApiService
import com.assignment.todo.domain.repository.TaskRepository
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val taskDao: TaskDao
) : TaskRepository {

    override suspend fun fetchAndSaveTasks() {
        val taskResponse = apiService.getTasks()
        // Save fetched tasks to Room database
        taskDao.insertTasks(taskResponse.tasks)
    }

    override suspend fun getTasks(): List<Task> {
        // Fetch tasks from Room database
        return taskDao.getAllTasks()
    }

    override suspend fun deleteTask(task: Task) {
        // Delete task from Room database
        taskDao.deleteTask(task)
    }

    override suspend fun markTaskAsCompleted(task: Task) {
        // Update task from Room database
        taskDao.markTaskAsCompleted(task.id)
    }

    override suspend fun addTask(task: Task) {
        taskDao.insertTask(task)
    }

    override suspend fun updateTask(task: Task) {
        taskDao.updateTask(task)
    }

    override suspend fun getUncompletedTasks(currentDate: String): List<Task> {
        return taskDao.getUncompletedTasks(currentDate)
    }

}
