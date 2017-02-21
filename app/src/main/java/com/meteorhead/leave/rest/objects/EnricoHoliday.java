package com.meteorhead.leave.rest.objects;

/**
 * Created by wierzchanowskig on 18.02.2017.
 */

public class EnricoHoliday {
    private EnricoDate date;
    private String localName;
    private String englishName;

    public EnricoDate getDate() {
        return date;
    }

    public void setDate(EnricoDate date) {
        this.date = date;
    }

    public String getLocalName() {
        return localName;
    }

    public void setLocalName(String localName) {
        this.localName = localName;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }
}
