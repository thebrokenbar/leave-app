package com.meteorhead.leave.models;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Index;

/**
 * Created by wierzchanowskig on 25.09.2016.
 */

public class Holiday extends RealmObject {

    public static String FIELD_COUNTRY = "country";
    private RealmList<SupportedCountry> country;

    public static String FIELD_HOLIDAY_DATE = "holidayDate";
    @Index
    private Date holidayDate;

    public static String FIELD_DAYS_NUMBER = "daysNumber";
    private int daysNumber;


    public RealmList<SupportedCountry> getCountry() {
        return country;
    }

    public void setCountry(RealmList<SupportedCountry> country) {
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
