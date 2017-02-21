package com.meteorhead.leave.models.helpers;

import android.support.annotation.NonNull;
import com.meteorhead.leave.models.Leave;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.joda.time.LocalDate;

/**
 * Created by wierzchanowskig on 26.09.2016.
 */
public interface WorkingDays {
    Date getLastWorkingDay(int days);
    Date getFirstWorkingDay(int days);
    int getWorkingDaysCount();
    int getHolidaysCount();
    int getAllDays();
    int getSundays();
    int getSaturdays();
    void setStartDate(Date startDate);
    Calendar[] getAllFreeDays();
    @NonNull
    List<Leave> getPropositions(LocalDate from, LocalDate to, int days);
}
