package com.assignment.todo.data.remote

import com.assignment.todo.data.model.TaskResponse
import retrofit2.http.GET

interface ApiService {
    @GET("970ec59d-1762-492b-90c0-2e60fa2d1bb4")
    suspend fun getTasks(): TaskResponse
}