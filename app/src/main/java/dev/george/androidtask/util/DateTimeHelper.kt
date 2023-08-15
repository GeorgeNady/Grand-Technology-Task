package dev.george.androidtask.util

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.regex.Pattern

object DateTimeHelper {

    // 2023-08-11T19:00:00Z
    private const val dateFormat = "yyyy-MM-dd'T'HH:mm:ss'Z'"

    fun getDateFromUTCDateFormat(date: String) = date.split(Pattern.compile("T")).first()

    fun getDayOfWeekFromDate(date: String) =
        SimpleDateFormat("EEEE", Locale.ENGLISH).format(fromDateToLong(date))

    fun getGroupDateFormatFromDate(date: String) = SimpleDateFormat("dd MMMM, yyyy", Locale.ENGLISH).format(fromDateToLong(date))

    fun getTimeFromDate(date: String) =
        SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(fromDateToLong(date))

    fun fromDateToLong(date: String): Long {
        val dateFormat = SimpleDateFormat(dateFormat, Locale.ENGLISH)
        return dateFormat.parse(date)?.time ?: 0
    }

}