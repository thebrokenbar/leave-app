package com.meterohead.leave.models.helpers;

import org.parceler.Parcel;

import java.util.Date;

/**
 * Created by wierzchanowskig on 26.09.2016.
 */
public interface WorkingDays {
    Date getLastWorkingDay(int days);
    int getWorkingDaysCount();
    int getHolidaysCount();
    int getAllDays();
    int getSundays();
    int getSaturdays();
    void setStartDate(Date startDate);
}
