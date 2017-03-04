package com.meteorhead.leave.models.impl

import com.meteorhead.leave.data.Holiday
import com.meteorhead.leave.data.Leave
import com.meteorhead.leave.database.realm.HolidaysRealmService
import com.meteorhead.leave.models.WorkingDays
import com.meteorhead.leave.utils.DateConverter
import io.realm.Realm
import io.realm.RealmResults
import org.joda.time.DateTimeConstants
import org.joda.time.Days
import org.joda.time.LocalDate
import java.util.*

/**
 * Created by wierzchanowskig on 25.09.2016.
 */

class RealmWorkingDays(from: Date?, to: Date?) : WorkingDays {

    private var dateFrom: LocalDate? = null
    private var dateTo: LocalDate? = null
    private var daysCount = 0
    private val holidaysDbService: HolidaysRealmService = HolidaysRealmService(Realm.getDefaultInstance())
    private var holidayCache: RealmResults<Holiday>? = null

    init {
        if (from != null) {
            this.dateFrom = LocalDate(from.time)
        }
        if (to != null) {
            this.dateTo = LocalDate(to.time)
        }
    }

    override fun getLastWorkingDay(days: Int): Date {
        dateTo = dateFrom!!.plusDays(days - 1)
        var workingDays = workingDaysCount
        var tempDays = workingDays
        while (workingDays != days) {
            dateTo = dateFrom!!.plusDays(tempDays++)
            workingDays = workingDaysCount
        }
        return dateTo!!.toDate()
    }

    override fun getFirstWorkingDay(days: Int): Date {
        dateTo = dateFrom!!.plusDays(0)
        dateFrom = dateTo!!.minusDays(days - 1)
        var workingDays = workingDaysCount
        while (workingDays != days) {
            dateFrom = dateFrom!!.minusDays(1)
            workingDays = workingDaysCount
        }
        return dateFrom!!.toDate()
    }

    override fun getWorkingDaysCount(): Int {
        return allDays - holidaysCount - saturdays - sundays
    }

    override fun getHolidaysCount(): Int {
        val countryCode = defaultCountryCode
        val holidaysList = getHolidaysFromDb(dateFrom!!.toDate(), dateTo!!.toDate(), countryCode)
        val holidaysCount = holidaysList
            .map { LocalDate(it.holidayDate!!.time) }
            .count { it.dayOfWeek < 6 }
        return holidaysCount
    }

    override fun getAllDays(): Int {
        daysCount = Days.daysBetween(dateFrom!!, dateTo!!).days + 1
        return daysCount
    }

    override fun getSundays(): Int {
        var sundays = daysCount / 7
        val rest = daysCount % 7
        if (dateFrom!!.dayOfWeek + rest > DateTimeConstants.SUNDAY) {
            sundays++
        }
        return sundays
    }

    override fun getSaturdays(): Int {
        var saturdays = daysCount / 7
        val rest = daysCount % 7
        if (dateFrom!!.dayOfWeek + rest > DateTimeConstants.SATURDAY) {
            saturdays++
        }
        return saturdays
    }

    override fun setStartDate(startDate: Date) {
        holidayCache = null
        this.dateFrom = LocalDate(startDate.time)
    }

    override fun getAllFreeDays(): Array<Calendar> {
        val countryCode = defaultCountryCode
        val freeDaysList = ArrayList<Calendar>()
        val holidays = getHolidaysFromDb(dateFrom!!.toDate(), dateTo!!.toDate(), countryCode)
        for (holiday in holidays) {
            val holidayDate = DateConverter.getAsCalendar(holiday.holidayDate)
            freeDaysList.add(holidayDate)
        }

        var date = LocalDate(dateFrom!!.toDate().time)
        val dayOfTheWeek = date.dayOfWeek
        val diffToSaturday = 6 - dayOfTheWeek
        date = date.plusDays(diffToSaturday)
        while (date.toDate().time < dateTo!!.toDate().time) {
            freeDaysList.add(DateConverter.getAsCalendar(date.toDate()))
            date = date.plusDays(1)
            freeDaysList.add(DateConverter.getAsCalendar(date.toDate()))
            date = date.plusDays(6)
        }

        return freeDaysList.toTypedArray()
    }

    private fun getHolidaysFromDb(from: Date, to: Date, countryCode: String): List<Holiday> {
        if (holidayCache == null) {
            holidayCache = holidaysDbService.getHolidays(countryCode)
        }
        return holidayCache!!.where()
            .between(Holiday::holidayDate.name, from, to)
            .findAll()
    }

    private val defaultCountryCode: String
        get() = Locale.getDefault().language

    override fun getPropositions(from: LocalDate, to: LocalDate, days: Int): List<Leave> {
        var fromDate = from
        var toDate = to
        fromDate = fromDate.minusDays(days)
        toDate = toDate.plusDays(days)

        val holidays = holidaysDbService.getHolidaysBetweenDates(
            Locale.getDefault().isO3Country,
            fromDate.toDate(), toDate.toDate())

        val proposedLeave = ArrayList<Leave>()
        for (holiday in holidays) {
            var dayTo = LocalDate(holiday.holidayDate.time)
            if (dayTo.dayOfWeek > 6) {
                dayTo = dayTo.plusDays(6 - dayTo.dayOfWeek)
            }
            this.setStartDate(dayTo.toDate())
            proposedLeave.add(Leave(dayTo.toDate(), this.getLastWorkingDay(days), days))
            if (dayTo.dayOfWeek > 5) {
                dayTo = dayTo.plusDays(8 - dayTo.dayOfWeek)
            }
            this.setStartDate(dayTo.toDate())
            val duration = Days.daysBetween(dateFrom, dateTo).days + 1
            proposedLeave.add(Leave(this.getFirstWorkingDay(days), dayTo.toDate(), duration))
        }

        return proposedLeave
    }
}