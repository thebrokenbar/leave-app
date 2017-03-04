package com.meteorhead.leave.utils


import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar
import java.util.Locale
import java.util.TimeZone

/**
 * Created by wierzchanowskig on 24.09.2016.
 */

object DateConverter {
    private val calendar = GregorianCalendar(TimeZone.getDefault(), Locale.getDefault())

    fun getDate(year: Int, month: Int, day: Int): Date {
        calendar.set(year, month, day)
        return Date(calendar.timeInMillis)
    }

    fun getDate(year: Int, month: Int, day: Int, hour: Int, minute: Int, second: Int): Date {
        calendar.set(year, month, day, hour, minute, second)
        return Date(calendar.timeInMillis)
    }

    fun getAsCalendar(date: Date): Calendar {
        val c = GregorianCalendar()
        c.time = date
        return c
    }
}
