package com.meteorhead.leave.models;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Index;

/**
 * Created by wierzchanowskig on 25.09.2016.
 */

public class Holiday extends RealmObject {

    private SupportedCountry country;
    @Index
    private Date holidayDate;
    private int daysNumber;

    public Holiday() {
    }

    public Holiday(SupportedCountry country, Date holidayDate, int daysNumber) {
        this.country = country;
        this.holidayDate = holidayDate;
        this.daysNumber = daysNumber;
    }

    public SupportedCountry getCountry() {
        return country;
    }

    public void setCountry(SupportedCountry country) {
        this.country = country;
    }

    public Date getHolidayDate() {
        return holidayDate;
    }

    public void setHolidayDate(Date holidayDate) {
        this.holidayDate = holidayDate;
    }

    public int getDaysNumber() {
        return daysNumber;
    }

    public void setDaysNumber(int daysNumber) {
        this.daysNumber = daysNumber;
    }
}
