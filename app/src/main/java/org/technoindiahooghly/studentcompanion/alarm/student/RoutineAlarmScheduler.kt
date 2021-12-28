package org.technoindiahooghly.studentcompanion.alarm.student

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*
import org.technoindiahooghly.studentcompanion.R
import org.technoindiahooghly.studentcompanion.alarm.AlarmReceiver

fun alarmHandler(context: Context, id: Int, subject: String, startTime: String, setAlarm: Boolean) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    val calendar = Calendar.getInstance(Locale.US)
    calendar.timeInMillis = startTime.toLong()

    val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
    val day = SimpleDateFormat("EEEE", Locale.US).format(calendar.time)

    calendar.add(Calendar.MINUTE, -30)
    val finalTime = calendar.timeInMillis

    val requestCode = "${dayOfWeek}0${id}".toInt()

    val alarmIntent = Intent(context, AlarmReceiver::class.java)
    alarmIntent.action = context.getString(R.string.routine_bottom_nav)

    val bundleExtra = Bundle()
    bundleExtra.putString("subject", subject)
    bundleExtra.putString("time", startTime)
    bundleExtra.putInt("id", requestCode)
    bundleExtra.putString("day", day)
    alarmIntent.putExtras(bundleExtra)

    if (setAlarm) {
        val pendingIntent =
            PendingIntent.getBroadcast(
                context,
                requestCode,
                alarmIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        Log.d("alarm set", "$subject-$startTime-$requestCode-$day")
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP, finalTime, AlarmManager.INTERVAL_DAY * 7, pendingIntent)
    } else if (!setAlarm) {
        val pendingIntent =
            PendingIntent.getBroadcast(
                context,
                requestCode,
                alarmIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        Log.d("alarm cancelled", "$subject-$startTime-$requestCode-$day")
        alarmManager.cancel(pendingIntent)
    }
}
