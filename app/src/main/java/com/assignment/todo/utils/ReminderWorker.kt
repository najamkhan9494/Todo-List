package com.assignment.todo.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.assignment.todo.R
import com.assignment.todo.domain.usecase.TaskUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.Calendar

@HiltWorker
class ReminderWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val taskUseCase: TaskUseCase
) : Worker(context, params) {

    override fun doWork(): Result {
        val currentDate: Calendar = Calendar.getInstance()

        GlobalScope.launch {
            val uncompletedTasks =
                taskUseCase.getUncompletedTasks(DateUtils.formatToDateString(currentDate.time))

            if (uncompletedTasks.isNotEmpty()) {
                sendNotification()
            }
        }
        return Result.success()
    }

    private fun sendNotification() {
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setContentTitle("Uncompleted Tasks Reminder")
            .setContentText("You have uncompleted tasks. Check your to-do list!")
            .setSmallIcon(R.drawable.ic_launcher_foreground) // use app icon here
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        // TODO: Create a notification channel if needed
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Uncompleted Tasks Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        // Notify using the notification manager
        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    companion object {
        private const val CHANNEL_ID = "UncompletedTasksChannel"
        private const val NOTIFICATION_ID = 1
    }
}
