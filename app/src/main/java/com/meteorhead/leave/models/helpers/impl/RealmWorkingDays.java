package com.meteorhead.leave.models.helpers.impl;

import com.meteorhead.leave.models.Holiday;
import com.meteorhead.leave.models.SupportedCountry;
import com.meteorhead.leave.models.helpers.WorkingDays;

import org.joda.time.DateTimeConstants;
import org.joda.time.Days;
import org.joda.time.LocalDate;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.Nullable;

import io.realm.Realm;
import io.realm.RealmResults;

import static com.meteorhead.leave.utils.RealmUtils.dot;

/**
 * Created by wierzchanowskig on 25.09.2016.
 */
public class RealmWorkingDays implements WorkingDays {

    private LocalDate dateFrom;
    private LocalDate dateTo;
    private int daysCount = 0;
    RealmResults<Holiday> holidayCache;

    public RealmWorkingDays(@Nullable Date from, @Nullable Date to) {
        if(from != null) {
            this.dateFrom = new LocalDate(from.getTime());
        }
        if(to != null) {
            this.dateTo = new LocalDate(to.getTime());
        }
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
    public int getWorkingDaysCount() {
        return getAllDays() - getHolidaysCount() - getSaturdays() - getSundays();
    }

    @Override
    public int getHolidaysCount() {
        String countryCode = getDefaultCountryCode();
        List<Holiday> holidaysList = getHolidaysFromDb(dateFrom.toDate(), dateTo.toDate(), countryCode);
        int holidays = 0;
        for (Holiday holiday : holidaysList) {
            holidays += holiday.getDaysNumber();
        }
        return holidays;
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

    private List<Holiday> getHolidaysFromDb(Date from, Date to, String countryCode) {
        if(holidayCache == null) {
            Realm realm = Realm.getDefaultInstance();
            holidayCache = realm.where(Holiday.class)
                    .equalTo(dot(Holiday.FIELD_COUNTRY, SupportedCountry.FIELD_COUNTRY_CODE), countryCode)
                    .findAll();
            realm.close();
        }
        return holidayCache.where()
                .between(Holiday.FIELD_HOLIDAY_DATE, from, to)
                .findAll();
    }

    private String getDefaultCountryCode() {
        return Locale.getDefault().getISO3Country();
    }
}
