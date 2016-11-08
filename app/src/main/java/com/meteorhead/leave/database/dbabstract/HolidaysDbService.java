package com.meteorhead.leave.database.dbabstract;

import com.meteorhead.leave.models.Holiday;
import com.meteorhead.leave.remoteDatabase.firebase.holidays.model.HolidaysList;

import java.util.Date;
import java.util.List;

/**
 * Created by wierzchanowskig on 15.10.2016.
 */

public interface HolidaysDbService {
    void insertHolidays(String countryCode, HolidaysList holidaysList);
    List<Holiday> getHolidays(String countryCode);
    List<Holiday> getHolidaysBetweenDates(String countryCode, Date from, Date to);
    void finish();
}
