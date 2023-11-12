package com.assignment.todo.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.assignment.todo.data.model.Task

@Dao
interface TaskDao {
    @Query("SELECT * FROM task_table ORDER BY id DESC")
    suspend fun getAllTasks(): List<Task>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTasks(tasks: List<Task>)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("UPDATE task_table SET completed = 1 WHERE id = :taskId")
    suspend fun markTaskAsCompleted(taskId: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)

    @Query("SELECT * FROM task_table WHERE completed = 0 AND date <= :currentDate")
    suspend fun getUncompletedTasks(currentDate: String): List<Task>
}
