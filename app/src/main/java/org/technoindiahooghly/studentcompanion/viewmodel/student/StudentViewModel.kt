package org.technoindiahooghly.studentcompanion.viewmodel.student

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import org.technoindiahooghly.studentcompanion.data.student.*

class StudentViewModel(private val studentDao: StudentDao) : ViewModel() {
    val getAll: LiveData<List<Student>> = studentDao.getAll().asLiveData()
    val getSubjects: LiveData<List<String>> = studentDao.getSubjects().asLiveData()
    val getMonday: LiveData<List<MondayData>> = studentDao.getMonday().asLiveData()
    val getTuesday: LiveData<List<TuesdayData>> = studentDao.getTuesday().asLiveData()
    val getWednesday: LiveData<List<WednesdayData>> = studentDao.getWednesday().asLiveData()
    val getThursday: LiveData<List<ThursdayData>> = studentDao.getThursday().asLiveData()
    val getFriday: LiveData<List<FridayData>> = studentDao.getFriday().asLiveData()
    val getSaturday: LiveData<List<SaturdayData>> = studentDao.getSaturday().asLiveData()

    fun isEntryValid(firstEntry: String, secondEntry: String): Boolean {
        if (firstEntry.isBlank() || secondEntry.isBlank()) return false
        return true
    }

    fun updateEntry(id: Int, subjectName: String, teacherName: String) {
        viewModelScope.launch { studentDao.update(id, subjectName, teacherName) }
    }

    private fun getNewEntry(subjectName: String, teacherName: String): Student {
        return Student(subjectName = subjectName, teacherName = teacherName)
    }

    private fun insertEntry(entry: Student) {
        viewModelScope.launch { studentDao.insert(entry) }
    }

    fun addNewEntry(subjectName: String, teacherName: String) {
        val newEntry = getNewEntry(subjectName, teacherName)
        insertEntry(newEntry)
    }

    fun retrieveEntry(id: Int): LiveData<Student> {
        return studentDao.getEntry(id).asLiveData()
    }

    fun deleteEntry(entry: Student) {
        viewModelScope.launch { studentDao.delete(entry) }
    }

    fun deleteEntry(id: Int, day: String) {
        viewModelScope.launch {
            when (day) {
                "Monday" -> studentDao.deleteMonday(id)
                "Tuesday" -> studentDao.deleteTuesday(id)
                "Wednesday" -> studentDao.deleteWednesday(id)
                "Thursday" -> studentDao.deleteThursday(id)
                "Friday" -> studentDao.deleteFriday(id)
                "Saturday" -> studentDao.deleteSaturday(id)
            }
        }
    }

    fun isEntryValid(subject: String, startTime: String, endTime: String): Boolean {
        if (subject.isBlank() || startTime.isBlank() || endTime.isBlank()) return false
        return true
    }

    fun addUpdateNewEntry(subject: String, startTime: String, endTime: String, day: String) {
        viewModelScope.launch {
            when (day) {
                "Monday" -> studentDao.setMonday(subject, startTime, endTime)
                "Tuesday" -> studentDao.setTuesday(subject, startTime, endTime)
                "Wednesday" -> studentDao.setWednesday(subject, startTime, endTime)
                "Thursday" -> studentDao.setThursday(subject, startTime, endTime)
                "Friday" -> studentDao.setFriday(subject, startTime, endTime)
                "Saturday" -> studentDao.setSaturday(subject, startTime, endTime)
            }
        }
    }

    fun setNotify(notification: Boolean, id: Int, day: String) {
        viewModelScope.launch {
            when (day) {
                "Monday" -> studentDao.setMondayNotification(notification, id)
                "Tuesday" -> studentDao.setTuesdayNotification(notification, id)
                "Wednesday" -> studentDao.setWednesdayNotification(notification, id)
                "Thursday" -> studentDao.setThursdayNotification(notification, id)
                "Friday" -> studentDao.setFridayNotification(notification, id)
                "Saturday" -> studentDao.setSaturdayNotification(notification, id)
            }
        }
    }
}

class StudentViewModelFactory(private val studentDao: StudentDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StudentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST") return StudentViewModel(studentDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
