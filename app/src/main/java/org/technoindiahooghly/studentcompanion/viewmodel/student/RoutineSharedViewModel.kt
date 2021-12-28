package org.technoindiahooghly.studentcompanion.viewmodel.student

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RoutineSharedViewModel : ViewModel() {
    private val _startTime = MutableLiveData(String())
    val startTime: LiveData<String> = _startTime

    private val _endTime = MutableLiveData(String())
    val endTime: LiveData<String> = _endTime

    private val _viewPagerSelected = MutableLiveData<Int>()
    val viewPagerSelected: LiveData<Int> = _viewPagerSelected

    fun setStartTime(timeInMills: String) {
        _startTime.value = timeInMills
    }

    fun setEndTime(timeInMills: String) {
        _endTime.value = timeInMills
    }

    fun updatePageSelected(newSelection: Int) {
        _viewPagerSelected.postValue(newSelection)
    }
}
