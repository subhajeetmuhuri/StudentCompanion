package org.technoindiahooghly.studentcompanion.alarm.tasks

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import org.technoindiahooghly.studentcompanion.R
import org.technoindiahooghly.studentcompanion.utils.tasks.createTaskNotification

class TaskAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context != null && intent != null && intent.action != null) {
            val intentAction = intent.action!!
            if (intentAction == context.getString(R.string.tasks_bottom_nav)) {
                val bundle = intent.extras
                if (bundle != null) {
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
