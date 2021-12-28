package org.technoindiahooghly.studentcompanion.data.tasks

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE) suspend fun insert(task: Task)

    @Update suspend fun update(task: Task)

    @Delete suspend fun delete(task: Task)

    @Query("SELECT * from task_table ORDER BY id ASC") fun getTasks(): Flow<List<Task>>

    @Query("SELECT * from task_table WHERE id = :id") fun getTask(id: Int): Flow<Task>

    @Query("UPDATE task_table SET task_notification = :task_notification WHERE id = :id")
    suspend fun setNotification(task_notification: Boolean, id: Int)

    @Query("UPDATE task_table SET task_done = :task_done WHERE id = :id")
    suspend fun setDone(task_done: Boolean, id: Int)
}
