package com.meterohead.leave.models;

import java.sql.Timestamp;

/**
 * Created by Lenovo on 2016-09-13.
 */

public class Leave {

    private Timestamp dateStart;
    private Timestamp dateEnd;
    private String title;
    private int leavePurpose;

    public Leave(Timestamp dateStart, Timestamp dateEnd, String title, int leavePurpose) {
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.title = title;
        this.leavePurpose = leavePurpose;
    }

    public Timestamp getDateStart() {
        return dateStart;
    }

    public void setDateStart(Timestamp dateStart) {
        this.dateStart = dateStart;
    }

    public Timestamp getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Timestamp dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLeavePurpose() {
        return leavePurpose;
    }

    public void setLeavePurpose(int leavePurpose) {
        this.leavePurpose = leavePurpose;
    }
}
