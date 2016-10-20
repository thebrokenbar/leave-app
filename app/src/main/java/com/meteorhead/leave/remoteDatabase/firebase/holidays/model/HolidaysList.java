package com.meteorhead.leave.remoteDatabase.firebase.holidays.model;

import java.util.List;

/**
 * Created by wierzchanowskig on 15.10.2016.
 */

public class HolidaysList {

    public List<Holiday> freeDays;

    public HolidaysList() {
    }

    public HolidaysList(List<Holiday> freeDays) {
        this.freeDays = freeDays;
    }

    public static class Holiday {
        public int dayOfYear;
        public int daysDuration;

        public Holiday() {
        }

        public Holiday(int dayOfYear, int daysDuration) {
            this.dayOfYear = dayOfYear;
            this.daysDuration = daysDuration;
        }
    }
}
