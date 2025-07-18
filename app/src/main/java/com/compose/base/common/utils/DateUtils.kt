package com.compose.base.common.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateUtils {

    private const val DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss"

    fun now(format: String = DEFAULT_FORMAT): String {
        val sdf = SimpleDateFormat(format, Locale.getDefault())
        return sdf.format(Date())
    }

    fun format(
        input: String,
        fromFormat: String,
        toFormat: String,
    ): String {
        return try {
            val parser = SimpleDateFormat(fromFormat, Locale.getDefault())
            val formatter = SimpleDateFormat(toFormat, Locale.getDefault())
            val date = parser.parse(input)
            if (date != null) formatter.format(date) else ""
        } catch (e: Exception) {
            ""
        }
    }

    fun fromMillis(millis: Long, format: String = DEFAULT_FORMAT): String {
        val sdf = SimpleDateFormat(format, Locale.getDefault())
        return sdf.format(Date(millis))
    }

    fun toMillis(dateStr: String, format: String = DEFAULT_FORMAT): Long {
        return try {
            val sdf = SimpleDateFormat(format, Locale.getDefault())
            val date = sdf.parse(dateStr)
            date?.time ?: 0L
        } catch (e: Exception) {
            0L
        }
    }

    fun isSameDay(date1: Date, date2: Date): Boolean {
        val fmt = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return fmt.format(date1) == fmt.format(date2)
    }
}
