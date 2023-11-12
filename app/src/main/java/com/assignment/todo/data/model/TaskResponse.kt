package com.assignment.todo.data.model

import com.google.gson.annotations.SerializedName

data class TaskResponse(
    @SerializedName("todos")
    val tasks: List<Task>
)
