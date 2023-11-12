package com.assignment.todo.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assignment.todo.data.model.Task
import com.assignment.todo.domain.usecase.TaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val taskUseCase: TaskUseCase) : ViewModel() {

    private val _taskList = MutableLiveData<List<Task>>()
    val taskList: LiveData<List<Task>> get() = _taskList

    fun fetchDataAndStoreLocally() {
        viewModelScope.launch {
            taskUseCase.fetchAndSaveTasks()
            _taskList.postValue(taskUseCase.getTasks())
        }
    }

    suspend fun deleteTask(task: Task) {
        viewModelScope.launch {
            taskUseCase.deleteTask(task)
            _taskList.postValue(taskUseCase.getTasks())
        }
    }

    suspend fun markTaskAsCompleted(task: Task) {
        viewModelScope.launch {
            taskUseCase.markTaskAsCompleted(task)
            _taskList.postValue(taskUseCase.getTasks())
        }
    }

    suspend fun addTask(task: Task) {
        viewModelScope.launch {
            taskUseCase.addTask(task)
            _taskList.postValue(taskUseCase.getTasks())
        }
    }

    suspend fun updateTask(task: Task) {
        viewModelScope.launch {
            taskUseCase.updateTask(task)
            _taskList.postValue(taskUseCase.getTasks())
        }
    }

    suspend fun getUniqueCategories(): List<String> {
        val tasks = taskUseCase.getTasks()
        // Use distinctBy to get unique categories
        return tasks.map { it.category }.distinctBy { it }
    }


}