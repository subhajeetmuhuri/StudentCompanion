package org.technoindiahooghly.studentcompanion.utils.student

import android.content.Context
import android.text.format.DateFormat
import android.widget.Button
import androidx.fragment.app.FragmentManager
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.text.SimpleDateFormat
import java.util.*
import org.technoindiahooghly.studentcompanion.viewmodel.student.RoutineSharedViewModel

fun getFormattedTime(context: Context, timeInMills: String): String {
    return if (DateFormat.is24HourFormat(context)) {
        SimpleDateFormat("HH:mm", Locale.US).format(Date(timeInMills.toLong()))
    } else {
        SimpleDateFormat("hh:mm aa", Locale.US).format(Date(timeInMills.toLong()))
    }
}

fun getFormattedTime(context: Context, hour: Int, min: Int): String {
    val calendar = Calendar.getInstance(Locale.US)
    calendar.set(Calendar.HOUR_OF_DAY, hour)
    calendar.set(Calendar.MINUTE, min)

    return if (DateFormat.is24HourFormat(context)) {
        SimpleDateFormat("HH:mm", Locale.US).format(calendar.time)
    } else {
        SimpleDateFormat("hh:mm aa", Locale.US).format(calendar.time)
    }
}

fun setTimeInMills(hour: Int, min: Int, day: Int): String {
    val currentTime = Calendar.getInstance(Locale.US)
    val finalTime = Calendar.getInstance(Locale.US)

    finalTime.timeInMillis = currentTime.timeInMillis
    finalTime.set(Calendar.DAY_OF_WEEK, day)
    finalTime.set(Calendar.HOUR_OF_DAY, hour)
    finalTime.set(Calendar.MINUTE, min)

    if (finalTime.timeInMillis < currentTime.timeInMillis) {
        finalTime.add(Calendar.WEEK_OF_YEAR, 1)
    }

    return finalTime.timeInMillis.toString()
}

fun timePickerStartTime(
    button: Button,
    context: Context,
    childFragmentManager: FragmentManager,
    sharedViewModel: RoutineSharedViewModel,
    day: Int,
    timeInMills: String
) {
    val picker = openTimePicker(context, childFragmentManager, timeInMills)

    picker.addOnPositiveButtonClickListener {
        val buttonText: String = getFormattedTime(context, picker.hour, picker.minute)
        button.text = buttonText
        sharedViewModel.setStartTime(setTimeInMills(picker.hour, picker.minute, day))
    }
}

fun timePickerEndTime(
    button: Button,
    context: Context,
    childFragmentManager: FragmentManager,
    sharedViewModel: RoutineSharedViewModel,
    day: Int,
    timeInMills: String
) {
    val picker = openTimePicker(context, childFragmentManager, timeInMills)

    picker.addOnPositiveButtonClickListener {
        val buttonText: String = getFormattedTime(context, picker.hour, picker.minute)
        button.text = buttonText
        sharedViewModel.setEndTime(setTimeInMills(picker.hour, picker.minute, day))
    }
}

fun openTimePicker(
    context: Context,
    childFragmentManager: FragmentManager,
    timeInMills: String
): MaterialTimePicker {
    val clockFormat =
        if (DateFormat.is24HourFormat(context)) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H

    val picker =
        if (timeInMills.isBlank()) {
            MaterialTimePicker.Builder().setTimeFormat(clockFormat).setHour(12).setMinute(0).build()
        } else {
            val calendar = Calendar.getInstance(Locale.US)
            calendar.timeInMillis = timeInMills.toLong()

            MaterialTimePicker.Builder()
                .setTimeFormat(clockFormat)
                .setHour(calendar.get(Calendar.HOUR_OF_DAY))
                .setMinute(calendar.get(Calendar.MINUTE))
                .build()
        }

    picker.show(childFragmentManager, "TAG")
    return picker
}
