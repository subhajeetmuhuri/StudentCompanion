package org.technoindiahooghly.studentcompanion

import android.app.Application
import org.technoindiahooghly.studentcompanion.data.student.StudentDatabase
import org.technoindiahooghly.studentcompanion.data.tasks.TaskDatabase
import org.technoindiahooghly.studentcompanion.utils.student.createRoutineChannel
import org.technoindiahooghly.studentcompanion.utils.tasks.createTaskChannel

class StudentApplication : Application() {
    val studentDatabase: StudentDatabase by lazy { StudentDatabase.getDatabase(this) }
    val taskDatabase: TaskDatabase by lazy { TaskDatabase.getDatabase(this) }

    override fun onCreate() {
        super.onCreate()

        createRoutineChannel(
            this.getString(R.string.routine_bottom_nav),
            this,
            "${this.getString(R.string.routine_bottom_nav)} notification channel")

        createTaskChannel(
            this.getString(R.string.tasks_bottom_nav),
            this,
            "${this.getString(R.string.tasks_bottom_nav)} notification channel")
    }
}
