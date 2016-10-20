package com.meteorhead.leave.models;

import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

/**
 * Created by wierzchanowskig on 25.09.2016.
 */

public class SupportedCountry extends RealmObject {
    @PrimaryKey
    private String countryCode;

    public SupportedCountry() {
    }

    public SupportedCountry(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
}
