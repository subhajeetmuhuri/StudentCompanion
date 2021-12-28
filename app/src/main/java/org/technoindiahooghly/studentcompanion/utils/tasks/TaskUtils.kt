package org.technoindiahooghly.studentcompanion.utils.tasks

import java.text.SimpleDateFormat
import java.util.*

fun getFormattedTime(timeInMills: String): String {
    return SimpleDateFormat("EEE, d MMM yyyy", Locale.US).format(Date(timeInMills.toLong()))
}
