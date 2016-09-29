package com.meterohead.leave.leavedetails;

import android.databinding.Bindable;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.widget.SeekBar;

import com.google.firebase.crash.FirebaseCrash;
import com.meterohead.leave.ViewModel;
import com.meterohead.leave.database.realm.LeaveRealmService;
import com.meterohead.leave.database.realm.base.interfaces.IRealmCallback;
import com.meterohead.leave.models.Leave;
import com.meterohead.leave.models.helpers.WorkingDays;
import com.meterohead.leave.models.helpers.impl.RealmWorkingDays;
import com.orhanobut.logger.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Nullable;

import io.realm.Realm;

/**
 * Created by wierzchanowskig on 18.09.2016.
 */
public class LeaveDetailsViewModel extends ViewModel {

    private static final int SEEK_BAR_MINIMUM_VALUE = 1;
    private static final int SEEK_BAR_MAXIMUM_VALUE = 30;
    private static final int DEFAULT_NUMBER_OF_DAYS = 5;

    @NonNull
    private LeaveDetailsFragmentController fragmentController;
    @NonNull
    private final LeaveDetailsActivityController activityController;

    @NonNull
    public Leave leaveObject;
    public final ObservableInt leaveDays;
    private WorkingDays workingDays;

    public LeaveDetailsViewModel(@NonNull LeaveDetailsFragmentController fragmentController,
                                 @NonNull LeaveDetailsActivityController activityController,
                                 @Nullable Leave leaveObject) {
        this.fragmentController = fragmentController;
        this.activityController = activityController;
        if(leaveObject == null) {
            this.leaveObject = new Leave();
            workingDays = new RealmWorkingDays(null, null);
        } else {
            this.leaveObject = leaveObject;
            workingDays = new RealmWorkingDays(this.leaveObject.getDateStart(), this.leaveObject.getDateEnd());
        }
        this.leaveDays = new ObservableInt(DEFAULT_NUMBER_OF_DAYS);
    }

    public void onConfirm() {
        LeaveRealmService leaveDb = new LeaveRealmService(Realm.getDefaultInstance());
        leaveDb.addNewLeave(leaveObject, new IRealmCallback() {
            @Override
            public void onSuccess() {
                activityController.returnResult(leaveObject);
            }

            @Override
            public void onError(Throwable error) {
                Logger.e(error, error.getMessage());
                FirebaseCrash.report(error);
            }
        });
    }

    @Bindable
    public SeekBar.OnSeekBarChangeListener getSeekBarChangeListener() {
        return new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(i < SEEK_BAR_MINIMUM_VALUE){
                    seekBar.setProgress(SEEK_BAR_MINIMUM_VALUE);
                    return;
                }
                if(b) {
                    setEndDate(workingDays.getLastWorkingDay(i));
                    notifyChange();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        };
    }

    @Bindable
    @NonNull
    public Leave getLeaveObject() {
        return leaveObject;
    }

    @Bindable
    public int getSeekBarMinimumValue() {
        return SEEK_BAR_MINIMUM_VALUE;
    }

    @Bindable
    public int getSeekBarMaximumValue() {
        return SEEK_BAR_MAXIMUM_VALUE;
    }

    public void onStartDateClick() {
        fragmentController.showStartDatePickerDialog(leaveObject.getDateStart());
    }

    public void onEndDateClick() {
        fragmentController.showEndDatePickerDialog(leaveObject.getDateEnd());
    }

    public String formatDate(Date date) {
        if(date == null) {
            return null;
        }
        return SimpleDateFormat.getDateInstance().format(date);
    }

    @Bindable
    public int getDaysDifference() {
        if(leaveObject.getDateEnd() == null || leaveObject.getDateStart() == null) {
            return DEFAULT_NUMBER_OF_DAYS;
        }
        WorkingDays workingDays = new RealmWorkingDays(leaveObject.getDateStart(), leaveObject.getDateEnd());
        return workingDays.getWorkingDaysCount();
    }

    void onStartDateResult(Date date) {
        setStartDate(date);
    }

    void onEndDateResult(Date date) {
        setEndDate(date);
    }

    @Bindable
    public void setStartDate(Date date) {
        leaveObject.setDateStart(date);
        workingDays.setStartDate(date);
        if(leaveObject.getDateEnd() == null) {
            setEndDate(workingDays.getLastWorkingDay(DEFAULT_NUMBER_OF_DAYS));
        }
        notifyChange();
    }

    @Bindable
    public void setEndDate(Date date) {
        leaveObject.setDateEnd(date);
        notifyChange();
    }

}
