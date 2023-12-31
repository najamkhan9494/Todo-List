package com.assignment.todo.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.assignment.todo.data.model.Task

@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}
