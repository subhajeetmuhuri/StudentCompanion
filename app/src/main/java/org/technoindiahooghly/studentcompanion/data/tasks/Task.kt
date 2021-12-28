package org.technoindiahooghly.studentcompanion.data.tasks

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_table")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "task_name") val taskName: String,
    @ColumnInfo(name = "task_deadline") val taskDeadline: String,
    @ColumnInfo(name = "task_notification") var taskNotification: Boolean = false,
    @ColumnInfo(name = "task_done") var taskDone: Boolean = false
)
