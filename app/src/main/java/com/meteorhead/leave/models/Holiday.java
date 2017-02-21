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
    private String region;
    @Index
    private Date holidayDate;

    public Holiday() {
    }

    public Holiday(SupportedCountry country, Date holidayDate) {
        this.country = country;
        this.holidayDate = holidayDate;
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

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
