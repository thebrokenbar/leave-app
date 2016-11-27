package com.meteorhead.leave.models;


import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Lenovo on 2016-09-13.
 */
public class Leave extends RealmObject implements Parcelable {
    public static final String PARAM_NAME = "LEAVE_OBJECT";


    @Retention(RetentionPolicy.SOURCE)
    @IntDef({SPRING, SUMMER, AUTUMN, WINTER})
    public @interface Season{}
    public static final int SPRING = 0;
    public static final int SUMMER = 1;
    public static final int AUTUMN = 2;
    public static final int WINTER = 3;

    @PrimaryKey
    private int id = -1;
    @Index
    private Date dateStart;
    @Index
    private Date dateEnd;
    private String title = "";
    private int leavePurpose;
    private int duration;

    public Leave() {
    }

    public Leave(Leave leave){
        this.set(leave);
    }

    public Leave(Date dateStart, Date dateEnd, String title, int leavePurpose, int duration) {
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.title = title;
        this.leavePurpose = leavePurpose;
        this.duration = duration;
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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void set(Leave other) {
        this.setDateStart(other.getDateStart());
        this.setDateEnd(other.getDateEnd());
        this.setLeavePurpose(other.getLeavePurpose());
        this.setTitle(other.getTitle());
        this.setDuration(other.getDuration());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeLong(this.dateStart != null ? this.dateStart.getTime() : -1);
        dest.writeLong(this.dateEnd != null ? this.dateEnd.getTime() : -1);
        dest.writeString(this.title);
        dest.writeInt(this.leavePurpose);
        dest.writeInt(this.duration);
    }

    protected Leave(Parcel in) {
        this.id = in.readInt();
        long tmpDateStart = in.readLong();
        this.dateStart = tmpDateStart == -1 ? null : new Date(tmpDateStart);
        long tmpDateEnd = in.readLong();
        this.dateEnd = tmpDateEnd == -1 ? null : new Date(tmpDateEnd);
        this.title = in.readString();
        this.leavePurpose = in.readInt();
        this.duration = in.readInt();
    }

    public static final Parcelable.Creator<Leave> CREATOR = new Parcelable.Creator<Leave>() {
        @Override
        public Leave createFromParcel(Parcel source) {
            return new Leave(source);
        }

        @Override
        public Leave[] newArray(int size) {
            return new Leave[size];
        }
    };
}