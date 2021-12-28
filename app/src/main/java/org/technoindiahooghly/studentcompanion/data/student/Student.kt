package org.technoindiahooghly.studentcompanion.data.student

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "student_table", indices = [Index(value = ["subject_name"], unique = true)])
data class Student(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "subject_name") val subjectName: String,
    @ColumnInfo(name = "teacher_name") val teacherName: String,
    @ColumnInfo(name = "monday_start_time") var mondayStartTime: String = "",
    @ColumnInfo(name = "monday_end_time") var mondayEndTime: String = "",
    @ColumnInfo(name = "monday_notification") var mondayNotification: Boolean = false,
    @ColumnInfo(name = "tuesday_start_time") var tuesdayStartTime: String = "",
    @ColumnInfo(name = "tuesday_end_time") var tuesdayEndTime: String = "",
    @ColumnInfo(name = "tuesday_notification") var tuesdayNotification: Boolean = false,
    @ColumnInfo(name = "wednesday_start_time") var wednesdayStartTime: String = "",
    @ColumnInfo(name = "wednesday_end_time") var wednesdayEndTime: String = "",
    @ColumnInfo(name = "wednesday_notification") var wednesdayNotification: Boolean = false,
    @ColumnInfo(name = "thursday_start_time") var thursdayStartTime: String = "",
    @ColumnInfo(name = "thursday_end_time") var thursdayEndTime: String = "",
    @ColumnInfo(name = "thursday_notification") var thursdayNotification: Boolean = false,
    @ColumnInfo(name = "friday_start_time") var fridayStartTime: String = "",
    @ColumnInfo(name = "friday_end_time") var fridayEndTime: String = "",
    @ColumnInfo(name = "friday_notification") var fridayNotification: Boolean = false,
    @ColumnInfo(name = "saturday_start_time") var saturdayStartTime: String = "",
    @ColumnInfo(name = "saturday_end_time") var saturdayEndTime: String = "",
    @ColumnInfo(name = "saturday_notification") var saturdayNotification: Boolean = false
)

data class MondayData(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "subject_name") val subjectName: String,
    @ColumnInfo(name = "teacher_name") val teacherName: String,
    @ColumnInfo(name = "monday_start_time") var mondayStartTime: String = "",
    @ColumnInfo(name = "monday_end_time") var mondayEndTime: String = "",
    @ColumnInfo(name = "monday_notification") var mondayNotification: Boolean = false
)

data class TuesdayData(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "subject_name") val subjectName: String,
    @ColumnInfo(name = "teacher_name") val teacherName: String,
    @ColumnInfo(name = "tuesday_start_time") var tuesdayStartTime: String = "",
    @ColumnInfo(name = "tuesday_end_time") var tuesdayEndTime: String = "",
    @ColumnInfo(name = "tuesday_notification") var tuesdayNotification: Boolean = false
)

data class WednesdayData(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "subject_name") val subjectName: String,
    @ColumnInfo(name = "teacher_name") val teacherName: String,
    @ColumnInfo(name = "wednesday_start_time") var wednesdayStartTime: String = "",
    @ColumnInfo(name = "wednesday_end_time") var wednesdayEndTime: String = "",
    @ColumnInfo(name = "wednesday_notification") var wednesdayNotification: Boolean = false
)

data class ThursdayData(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "subject_name") val subjectName: String,
    @ColumnInfo(name = "teacher_name") val teacherName: String,
    @ColumnInfo(name = "thursday_start_time") var thursdayStartTime: String = "",
    @ColumnInfo(name = "thursday_end_time") var thursdayEndTime: String = "",
    @ColumnInfo(name = "thursday_notification") var thursdayNotification: Boolean = false
)

data class FridayData(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "subject_name") val subjectName: String,
    @ColumnInfo(name = "teacher_name") val teacherName: String,
    @ColumnInfo(name = "friday_start_time") var fridayStartTime: String = "",
    @ColumnInfo(name = "friday_end_time") var fridayEndTime: String = "",
    @ColumnInfo(name = "friday_notification") var fridayNotification: Boolean = false
)

data class SaturdayData(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "subject_name") val subjectName: String,
    @ColumnInfo(name = "teacher_name") val teacherName: String,
    @ColumnInfo(name = "saturday_start_time") var saturdayStartTime: String = "",
    @ColumnInfo(name = "saturday_end_time") var saturdayEndTime: String = "",
    @ColumnInfo(name = "saturday_notification") var saturdayNotification: Boolean = false
)
