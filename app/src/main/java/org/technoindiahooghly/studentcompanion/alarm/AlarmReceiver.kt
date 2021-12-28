package org.technoindiahooghly.studentcompanion.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import org.technoindiahooghly.studentcompanion.R
import org.technoindiahooghly.studentcompanion.utils.student.createRoutineNotification
import org.technoindiahooghly.studentcompanion.utils.tasks.createTaskNotification

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context != null && intent != null && intent.action != null) {
            val intentAction = intent.action!!
            val bundle = intent.extras
            if (bundle != null) {
                if (intentAction == context.getString(R.string.routine_bottom_nav)) {
                    val subject = bundle.getString("subject")
                    val timeInMills = bundle.getString("time")
                    val id = bundle.getInt("id")
                    val day = bundle.getString("day")

                    if (subject != null && day != null && timeInMills != null) {
                        Log.d("intent received", "$subject-$timeInMills-$id-$day")
                        createRoutineNotification(
                            context, intentAction, subject, timeInMills, id, day)
                    }
                } else if (intentAction == context.getString(R.string.tasks_bottom_nav)) {
                    val task = bundle.getString("task")
                    val deadline = bundle.getString("deadline")
                    val id = bundle.getInt("id")

                    if (task != null && deadline != null) {
                        Log.d("intent received", "$task-$deadline-$id")
                        createTaskNotification(context, intentAction, task, deadline, id)
                    }
                }
            }
        }
    }
}
