package org.technoindiahooghly.studentcompanion.viewmodel.tasks

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import org.technoindiahooghly.studentcompanion.data.tasks.Task
import org.technoindiahooghly.studentcompanion.data.tasks.TaskDao

class TaskViewModel(private val taskDao: TaskDao) : ViewModel() {
    val getTasks: LiveData<List<Task>> = taskDao.getTasks().asLiveData()

    fun isTaskValid(taskName: String, taskDeadline: String): Boolean {
        if (taskName.isBlank() || taskDeadline.isBlank()) return false
        return true
    }

    private fun getUpdatedTask(id: Int, taskName: String, taskDeadline: String): Task {
        return Task(id = id, taskName = taskName, taskDeadline = taskDeadline)
    }

    private fun updateTask(task: Task) {
        viewModelScope.launch { taskDao.update(task) }
    }

    fun updateTask(id: Int, taskName: String, taskDeadline: String) {
        val updatedTask = getUpdatedTask(id, taskName, taskDeadline)
        updateTask(updatedTask)
    }

    private fun getNewTask(taskName: String, taskDeadline: String): Task {
        return Task(taskName = taskName, taskDeadline = taskDeadline)
    }

    private fun insertTask(task: Task) {
        viewModelScope.launch { taskDao.insert(task) }
    }

    fun addNewTask(taskName: String, taskDeadline: String) {
        val newTask = getNewTask(taskName, taskDeadline)
        insertTask(newTask)
    }

    fun retrieveTask(id: Int): LiveData<Task> {
        return taskDao.getTask(id).asLiveData()
    }

    fun deleteEntry(task: Task) {
        viewModelScope.launch { taskDao.delete(task) }
    }

    fun setNotify(notification: Boolean, id: Int) {
        viewModelScope.launch { taskDao.setNotification(notification, id) }
    }

    fun setDone(done: Boolean, id: Int) {
        viewModelScope.launch { taskDao.setDone(done, id) }
    }
}

class TaskViewModelFactory(private val taskDao: TaskDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST") return TaskViewModel(taskDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
