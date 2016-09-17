package com.meterohead.leave.models;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Lenovo on 2016-09-13.
 */

public class Leave extends RealmObject{

    @PrimaryKey
    private int id;
    public static String FIELD_DATE_START = "dateStart";
    @Index
    private Date dateStart;
    public static String FIELD_DATE_END = "dateEnd";
    @Index
    private Date dateEnd;
    public static String FIELD_TITLE = "title";
    private String title;
    public static String FIELD_LEAVE_PURPOSE = "leavePurpose";
    private int leavePurpose;

    public Leave() {
    }

    public Leave(Date dateStart, Date dateEnd, String title, int leavePurpose) {
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.title = title;
        this.leavePurpose = leavePurpose;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
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
