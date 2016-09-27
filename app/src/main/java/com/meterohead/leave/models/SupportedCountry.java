package com.meterohead.leave.models;

import io.realm.RealmObject;
import io.realm.annotations.Index;

/**
 * Created by wierzchanowskig on 25.09.2016.
 */

public class SupportedCountry extends RealmObject {
    public static String FIELD_COUNTRY_CODE = "countryCode";
    @Index
    private String countryCode;

    public static String FIELD_COUNTRY_NAME = "countryName";
    private String countryName;

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
}
