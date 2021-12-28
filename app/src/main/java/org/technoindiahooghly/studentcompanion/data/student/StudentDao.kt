package org.technoindiahooghly.studentcompanion.data.student

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface StudentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun insert(entry: Student)

    @Query(
        "UPDATE student_table SET subject_name = :subject, teacher_name = :teacher WHERE id = :id")
    suspend fun update(id: Int, subject: String, teacher: String)

    @Delete suspend fun delete(entry: Student)

    @Query("SELECT * from student_table ORDER BY subject_name ASC")
    fun getAll(): Flow<List<Student>>

    @Query("SELECT subject_name from student_table ORDER BY subject_name ASC")
    fun getSubjects(): Flow<List<String>>

    @Query("SELECT * from student_table WHERE id = :id") fun getEntry(id: Int): Flow<Student>

    @Query(
        "SELECT id, subject_name, teacher_name, monday_start_time, monday_end_time, monday_notification from student_table WHERE(monday_start_time <> '' AND monday_end_time <> '') ORDER BY monday_start_time ASC")
    fun getMonday(): Flow<List<MondayData>>

    @Query("UPDATE student_table SET monday_start_time = '', monday_end_time = '' WHERE id = :id")
    suspend fun deleteMonday(id: Int)

    @Query(
        "UPDATE student_table SET monday_start_time = :mondayStartTime, monday_end_time = :mondayEndTime WHERE subject_name = :subject")
    suspend fun setMonday(subject: String, mondayStartTime: String, mondayEndTime: String)

    @Query("UPDATE student_table SET monday_notification = :monday_notification WHERE id = :id")
    suspend fun setMondayNotification(monday_notification: Boolean, id: Int)

    @Query(
        "SELECT id, subject_name, teacher_name, tuesday_start_time, tuesday_end_time, tuesday_notification from student_table WHERE(tuesday_start_time <> '' AND tuesday_end_time <> '') ORDER BY tuesday_start_time ASC")
    fun getTuesday(): Flow<List<TuesdayData>>

    @Query("UPDATE student_table SET tuesday_start_time = '', tuesday_end_time = '' WHERE id = :id")
    suspend fun deleteTuesday(id: Int)

    @Query(
        "UPDATE student_table SET tuesday_start_time = :tuesdayStartTime, tuesday_end_time = :tuesdayEndTime WHERE subject_name = :subject")
    suspend fun setTuesday(subject: String, tuesdayStartTime: String, tuesdayEndTime: String)

    @Query("UPDATE student_table SET tuesday_notification = :tuesday_notification WHERE id = :id")
    suspend fun setTuesdayNotification(tuesday_notification: Boolean, id: Int)

    @Query(
        "SELECT id, subject_name, teacher_name, wednesday_start_time, wednesday_end_time, wednesday_notification from student_table WHERE(wednesday_start_time <> '' AND wednesday_end_time <> '') ORDER BY wednesday_start_time ASC")
    fun getWednesday(): Flow<List<WednesdayData>>

    @Query(
        "UPDATE student_table SET wednesday_start_time = '', wednesday_end_time = '' WHERE id = :id")
    suspend fun deleteWednesday(id: Int)

    @Query(
        "UPDATE student_table SET wednesday_start_time = :wednesdayStartTime, wednesday_end_time = :wednesdayEndTime WHERE subject_name = :subject")
    suspend fun setWednesday(subject: String, wednesdayStartTime: String, wednesdayEndTime: String)

    @Query(
        "UPDATE student_table SET wednesday_notification = :wednesday_notification WHERE id = :id")
    suspend fun setWednesdayNotification(wednesday_notification: Boolean, id: Int)

    @Query(
        "SELECT id, subject_name, teacher_name, thursday_start_time, thursday_end_time, thursday_notification from student_table WHERE(thursday_start_time <> '' AND thursday_end_time <> '') ORDER BY thursday_start_time ASC")
    fun getThursday(): Flow<List<ThursdayData>>

    @Query(
        "UPDATE student_table SET thursday_start_time = '', thursday_end_time = '' WHERE id = :id")
    suspend fun deleteThursday(id: Int)

    @Query(
        "UPDATE student_table SET thursday_start_time = :thursdayStartTime, thursday_end_time = :thursdayEndTime WHERE subject_name = :subject")
    suspend fun setThursday(subject: String, thursdayStartTime: String, thursdayEndTime: String)

    @Query("UPDATE student_table SET thursday_notification = :thursday_notification WHERE id = :id")
    suspend fun setThursdayNotification(thursday_notification: Boolean, id: Int)

    @Query(
        "SELECT id, subject_name, teacher_name, friday_start_time, friday_end_time, friday_notification from student_table WHERE(friday_start_time <> '' AND friday_end_time <> '') ORDER BY friday_start_time ASC")
    fun getFriday(): Flow<List<FridayData>>

    @Query("UPDATE student_table SET friday_start_time = '', friday_end_time = '' WHERE id = :id")
    suspend fun deleteFriday(id: Int)

    @Query(
        "UPDATE student_table SET friday_start_time = :fridayStartTime, friday_end_time = :fridayEndTime WHERE subject_name = :subject")
    suspend fun setFriday(subject: String, fridayStartTime: String, fridayEndTime: String)

    @Query("UPDATE student_table SET friday_notification = :friday_notification WHERE id = :id")
    suspend fun setFridayNotification(friday_notification: Boolean, id: Int)

    @Query(
        "SELECT id, subject_name, teacher_name, saturday_start_time, saturday_end_time, saturday_notification from student_table WHERE(saturday_start_time <> '' AND saturday_end_time <> '') ORDER BY saturday_start_time ASC")
    fun getSaturday(): Flow<List<SaturdayData>>

    @Query(
        "UPDATE student_table SET saturday_start_time = '', saturday_end_time = '' WHERE id = :id")
    suspend fun deleteSaturday(id: Int)

    @Query(
        "UPDATE student_table SET saturday_start_time = :saturdayStartTime, saturday_end_time = :saturdayEndTime WHERE subject_name = :subject")
    suspend fun setSaturday(subject: String, saturdayStartTime: String, saturdayEndTime: String)

    @Query("UPDATE student_table SET saturday_notification = :saturday_notification WHERE id = :id")
    suspend fun setSaturdayNotification(saturday_notification: Boolean, id: Int)
}
