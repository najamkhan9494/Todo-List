package com.assignment.todo.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    private const val DATE_FORMAT_PATTERN = "MM-dd-yyyy"

    fun formatToDateString(date: Date?): String {
        date ?: return ""
        val dateFormat = SimpleDateFormat(DATE_FORMAT_PATTERN, Locale.getDefault())
        return dateFormat.format(date)
    }

}

