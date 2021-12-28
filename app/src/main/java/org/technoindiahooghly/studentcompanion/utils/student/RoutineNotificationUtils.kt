package org.technoindiahooghly.studentcompanion.utils.student

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import org.technoindiahooghly.studentcompanion.R

fun createRoutineChannel(channelId: String, context: Context, description: String) {
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

fun createRoutineNotification(
    context: Context,
    channelId: String,
    subject: String,
    timeInMills: String,
    id: Int,
    day: String
) {
    val formattedTime = getFormattedTime(context, timeInMills)

    val builder =
        NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_routine)
            .setContentTitle("$day classes")
            .setContentText("You have $subject from $formattedTime")
            .setAutoCancel(false)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

    val notificationManager = NotificationManagerCompat.from(context)
    notificationManager.notify(id, builder.build())
}
