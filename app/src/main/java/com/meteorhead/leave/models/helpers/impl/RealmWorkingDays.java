package com.meteorhead.leave.models.helpers.impl;

import com.meteorhead.leave.database.realm.HolidaysRealmService;
import com.meteorhead.leave.models.Holiday;
import com.meteorhead.leave.models.HolidayFields;
import com.meteorhead.leave.models.helpers.WorkingDays;
import com.meteorhead.leave.utils.DateConverter;
import com.orhanobut.logger.Logger;

import org.joda.time.DateTimeConstants;
import org.joda.time.Days;
import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.Nullable;

import io.realm.RealmResults;

/**
 * Created by wierzchanowskig on 25.09.2016.
 */
public class RealmWorkingDays implements WorkingDays {

    private LocalDate dateFrom;
    private LocalDate dateTo;
    private int daysCount = 0;
    private HolidaysRealmService holidaysDbService;
    private RealmResults<Holiday> holidayCache;

    public RealmWorkingDays(@Nullable Date from, @Nullable Date to) {
        if(from != null) {
            this.dateFrom = new LocalDate(from.getTime());
        }
        if(to != null) {
            this.dateTo = new LocalDate(to.getTime());
        }

        holidaysDbService = new HolidaysRealmService();
    }

    @Override
    public Date getLastWorkingDay(int days) {
        dateTo = dateFrom.plusDays(days-1);
        int workingDays = getWorkingDaysCount();
        int tempDays = workingDays;
        while (workingDays != days) {
            dateTo = dateFrom.plusDays(tempDays++);
            workingDays = getWorkingDaysCount();
        }
        return dateTo.toDate();
    }

    @Override
    public Date getFirstWorkingDay(int days) {
        dateTo = dateFrom.plusDays(0);
        dateFrom = dateTo.minusDays(days-1);
        int workingDays = getWorkingDaysCount();
        while (workingDays != days) {
            dateFrom = dateFrom.minusDays(1);
            workingDays = getWorkingDaysCount();
        }
        return dateFrom.toDate();
    }

    @Override
    public int getWorkingDaysCount() {
        return getAllDays() - getHolidaysCount() - getSaturdays() - getSundays();
    }

    @Override
    public int getHolidaysCount() {
        String countryCode = getDefaultCountryCode();
        List<Holiday> holidaysList = getHolidaysFromDb(dateFrom.toDate(), dateTo.toDate(), countryCode);
        int holidaysCount = 0;
        for (Holiday holiday :
                holidaysList) {
            LocalDate localDate = new LocalDate(holiday.getHolidayDate().getTime());
            if(localDate.getDayOfWeek() < 6){
                holidaysCount++;
            }
        }
        return holidaysCount;
    }

    @Override
    public int getAllDays() {
        daysCount = Days.daysBetween(dateFrom, dateTo).getDays() + 1;
        return daysCount;
    }

    @Override
    public int getSundays() {
        int sundays = daysCount / 7;
        int rest = daysCount % 7;
        if (dateFrom.getDayOfWeek() + rest > DateTimeConstants.SUNDAY) {
            sundays++;
        }
        return sundays;
    }

    @Override
    public int getSaturdays() {
        int saturdays = daysCount / 7;
        int rest = daysCount % 7;
        if (dateFrom.getDayOfWeek() + rest > DateTimeConstants.SATURDAY) {
            saturdays++;
        }
        return saturdays;
    }

    @Override
    public void setStartDate(Date startDate) {
        holidayCache = null;
        this.dateFrom = new LocalDate(startDate.getTime());
    }

    @Override
    public Calendar[] getAllFreeDays() {
        String countryCode = getDefaultCountryCode();
        List<Calendar> freeDaysList = new ArrayList<>();
        List<Holiday> holidays = getHolidaysFromDb(dateFrom.toDate(), dateTo.toDate(), countryCode);
        for (Holiday holiday : holidays) {
            Calendar holidayDate = DateConverter.getAsCalendar(holiday.getHolidayDate());
            freeDaysList.add(holidayDate);
        }

        LocalDate date = new LocalDate(dateFrom.toDate().getTime());
        int dayOfTheWeek = date.getDayOfWeek();
        int diffToSaturday = 6 - dayOfTheWeek;
        date = date.plusDays(diffToSaturday);
        while(date.toDate().getTime() < dateTo.toDate().getTime()) {
            freeDaysList.add(DateConverter.getAsCalendar(date.toDate()));
            date = date.plusDays(1);
            freeDaysList.add(DateConverter.getAsCalendar(date.toDate()));
            date = date.plusDays(6);
            Logger.i("loop " + date.toString());
        }

        return freeDaysList.toArray(new Calendar[0]);
    }

    private List<Holiday> getHolidaysFromDb(Date from, Date to, String countryCode) {
        if(holidayCache == null) {
            holidayCache = holidaysDbService.getHolidays(countryCode);
        }
        return holidayCache.where()
                .between(HolidayFields.HOLIDAY_DATE, from, to)
                .findAll();
    }

    private String getDefaultCountryCode() {
        return Locale.getDefault().getLanguage();
    }
}
