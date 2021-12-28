package org.technoindiahooghly.studentcompanion.alarm.tasks

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

fun alarmHandler(
    context: Context,
    id: Int,
    taskName: String,
    deadlineTime: String,
    setAlarm: Boolean
) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    val finalAlarmCalendar = Calendar.getInstance(Locale.US)
    finalAlarmCalendar.timeInMillis = deadlineTime.toLong()
    val finalAlarmTime = finalAlarmCalendar.timeInMillis

    val date = SimpleDateFormat("MMMM d", Locale.US).format(finalAlarmCalendar.time)

    val firstAlarmCalendar = Calendar.getInstance()
    firstAlarmCalendar.timeInMillis = finalAlarmTime
    firstAlarmCalendar.add(Calendar.MINUTE, -(13 * 60))
    val firstAlarmTime = firstAlarmCalendar.timeInMillis

    val firstAlarmRequestCode = "${id}0${1}".toInt()
    val finalAlarmRequestCode = "${id}0${2}".toInt()

    val alarmIntent = Intent(context, AlarmReceiver::class.java)
    alarmIntent.action = context.getString(R.string.tasks_bottom_nav)

    val firstAlarmBundleExtra = Bundle()
    firstAlarmBundleExtra.putString("task", taskName)
    firstAlarmBundleExtra.putString("deadline", date)
    firstAlarmBundleExtra.putInt("id", firstAlarmRequestCode)
    alarmIntent.putExtras(firstAlarmBundleExtra)

    val finalAlarmBundleExtra = Bundle()
    finalAlarmBundleExtra.putString("task", taskName)
    finalAlarmBundleExtra.putString("deadline", date)
    finalAlarmBundleExtra.putInt("id", finalAlarmRequestCode)
    alarmIntent.putExtras(finalAlarmBundleExtra)

    if (setAlarm) {
        val firstAlarmPendingIntent =
            PendingIntent.getBroadcast(
                context,
                firstAlarmRequestCode,
                alarmIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        Log.d("alarm set", "$taskName-$firstAlarmTime-$firstAlarmRequestCode-$date")
        alarmManager.set(AlarmManager.RTC_WAKEUP, firstAlarmTime, firstAlarmPendingIntent)

        val finalAlarmPendingIntent =
            PendingIntent.getBroadcast(
                context,
                finalAlarmRequestCode,
                alarmIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        Log.d("alarm set", "$taskName-$finalAlarmTime-$finalAlarmRequestCode-$date")
        alarmManager.set(AlarmManager.RTC_WAKEUP, finalAlarmTime, finalAlarmPendingIntent)
    } else if (!setAlarm) {
        val firstAlarmPendingIntent =
            PendingIntent.getBroadcast(
                context,
                firstAlarmRequestCode,
                alarmIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        Log.d("alarm cancelled", "$taskName-$firstAlarmTime-$firstAlarmRequestCode-$date")
        alarmManager.cancel(firstAlarmPendingIntent)

        val finalAlarmPendingIntent =
            PendingIntent.getBroadcast(
                context,
                finalAlarmRequestCode,
                alarmIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        Log.d("alarm cancelled", "$taskName-$finalAlarmTime-$finalAlarmRequestCode-$date")
        alarmManager.cancel(finalAlarmPendingIntent)
    }
}
