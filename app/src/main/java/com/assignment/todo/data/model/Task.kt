package com.assignment.todo.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "task_table")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @SerializedName("Title")
    val title: String,
    @SerializedName("Category")
    val category: String,
    @SerializedName("todo")
    val description: String,
    val completed: Boolean,
    val userId: Int,
    val date: String,
    val priority: String
)
