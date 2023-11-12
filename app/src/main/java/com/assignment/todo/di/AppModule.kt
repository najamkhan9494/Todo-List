package com.assignment.todo.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.assignment.todo.data.repository.TaskRepositoryImpl
import com.assignment.todo.data.db.TaskDao
import com.assignment.todo.data.db.TaskDatabase
import com.assignment.todo.data.remote.ApiService
import com.assignment.todo.domain.repository.TaskRepository
import com.assignment.todo.domain.usecase.TaskUseCase
import com.assignment.todo.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideRemoteApi(@ApplicationContext appContext: Context): ApiService {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(
                OkHttpClient.Builder()
                    .connectionSpecs(listOf(
                        ConnectionSpec.COMPATIBLE_TLS,
                        ConnectionSpec.MODERN_TLS,
                        ConnectionSpec.CLEARTEXT))
                    .connectTimeout(120L, TimeUnit.SECONDS)
                    .readTimeout(120L, TimeUnit.SECONDS)
                    .writeTimeout(120L, TimeUnit.SECONDS)
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(app: Application): TaskDatabase {
        return Room.databaseBuilder(
            app,
            TaskDatabase::class.java,
            Constants.DB_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideTaskDao(database: TaskDatabase): TaskDao {
        return database.taskDao()
    }

    @Provides
    @Singleton
    fun provideTaskRepository(apiService: ApiService, taskDao: TaskDao): TaskRepository {
        return TaskRepositoryImpl(apiService, taskDao)
    }

    @Provides
    @Singleton
    fun provideTaskUseCase(repository: TaskRepository): TaskUseCase {
        return TaskUseCase(repository)
    }

}