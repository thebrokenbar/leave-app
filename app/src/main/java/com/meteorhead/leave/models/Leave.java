package com.meteorhead.leave.models;


import android.os.Parcel;
import android.os.Parcelable;
import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Lenovo on 2016-09-13.
 */
public class Leave extends RealmObject implements Parcelable {
    public static final String PARAM_NAME = "LEAVE_OBJECT";

    public static String FIELD_ID = "id";
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

    public void set(Leave other) {
        this.setDateEnd(other.getDateEnd());
        this.setDateStart(other.getDateStart());
        this.setLeavePurpose(other.getLeavePurpose());
        this.setTitle(other.getTitle());
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeLong(this.dateStart != null ? this.dateStart.getTime() : -1);
        dest.writeLong(this.dateEnd != null ? this.dateEnd.getTime() : -1);
        dest.writeString(this.title);
        dest.writeInt(this.leavePurpose);
    }

    protected Leave(Parcel in) {
        this.id = in.readInt();
        long tmpDateStart = in.readLong();
        this.dateStart = tmpDateStart == -1 ? null : new Date(tmpDateStart);
        long tmpDateEnd = in.readLong();
        this.dateEnd = tmpDateEnd == -1 ? null : new Date(tmpDateEnd);
        this.title = in.readString();
        this.leavePurpose = in.readInt();
    }

    public static final Parcelable.Creator<Leave> CREATOR = new Parcelable.Creator<Leave>() {
        @Override
        public Leave createFromParcel(android.os.Parcel source) {
            return new Leave(source);
        }

        @Override
        public Leave[] newArray(int size) {
            return new Leave[size];
        }
    };
}
