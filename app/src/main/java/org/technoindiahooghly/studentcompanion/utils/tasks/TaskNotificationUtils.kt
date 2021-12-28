package org.technoindiahooghly.studentcompanion.utils.tasks

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import org.technoindiahooghly.studentcompanion.R

fun createTaskChannel(channelId: String, context: Context, description: String) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val notificationChannel =
            NotificationChannel(channelId, channelId, NotificationManager.IMPORTANCE_DEFAULT)

        notificationChannel.enableLights(true)
        notificationChannel.lightColor = Color.RED
        notificationChannel.enableVibration(true)
        notificationChannel.description = description
        notificationChannel.setShowBadge(false)

        val notificationManager = context.getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(notificationChannel)
    }
}

fun createTaskNotification(
    context: Context,
    channelId: String,
    task: String,
    deadline: String,
    id: Int
) {
    val builder =
        NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_tasks)
            .setContentTitle("$deadline Tasks")
            .setContentText(task)
            .setAutoCancel(false)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

    val notificationManager = NotificationManagerCompat.from(context)
    notificationManager.notify(id, builder.build())
}
