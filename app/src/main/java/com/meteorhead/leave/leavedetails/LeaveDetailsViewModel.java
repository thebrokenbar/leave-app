package com.meteorhead.leave.leavedetails;

import android.databinding.Bindable;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.widget.SeekBar;

import com.android.databinding.library.baseAdapters.BR;

import com.meteorhead.leave.base.ViewModel;
import com.meteorhead.leave.leavelist.LeaveListResult;
import com.meteorhead.leave.mainactivity.ActivityViewModel;
import com.meteorhead.leave.models.Leave;
import com.meteorhead.leave.models.helpers.WorkingDays;
import com.meteorhead.leave.models.helpers.impl.RealmWorkingDays;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wierzchanowskig on 18.09.2016.
 */
public class LeaveDetailsViewModel extends ViewModel {

    private static final int SEEK_BAR_MINIMUM_VALUE = 1;
    private static final int SEEK_BAR_MAXIMUM_VALUE = 30;
    private static final int DEFAULT_NUMBER_OF_DAYS = 5;

    @NonNull
    private LeaveDetailsViewController viewController;

    @NonNull
    public Leave leaveObject;
    public final ObservableInt leaveDays;
    @NonNull
    private final ActivityViewModel activityViewModel;
    private WorkingDays workingDays;
    private boolean appBarCollapsed;

    public LeaveDetailsViewModel(@NonNull LeaveDetailsViewController viewController, @NonNull ActivityViewModel activityViewModel,
                                 @NonNull Leave leaveObject, @NonNull WorkingDays workingDays,
                                 @NonNull ObservableInt leaveDays) {
        this.viewController = viewController;
        this.activityViewModel = activityViewModel;
        this.workingDays = workingDays;
        this.leaveDays = leaveDays;
        this.leaveObject = leaveObject;
        this.leaveDays.set(DEFAULT_NUMBER_OF_DAYS);
    }

    @Bindable
    public boolean isAppBarCollapsed() {
        return appBarCollapsed;
    }

    @Bindable
    public void setAppBarCollapsed(boolean isAppBarCollapsed) {
        this.appBarCollapsed = isAppBarCollapsed;
        notifyPropertyChanged(BR.appBarCollapsed);
        activityViewModel.titleVisibility.set(isAppBarCollapsed);
        if(isAppBarCollapsed) {
            activityViewModel.title.set(leaveObject.getTitle());
        }
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
                    leaveObject.setDuration(workingDays.getWorkingDaysCount());
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

    public void showStartDatePickerDialog() {
        viewController.showStartDatePickerDialog(leaveObject.getDateStart());
    }

    public void showEndDatePickerDialog() {
        viewController.showEndDatePickerDialog(leaveObject.getDateEnd());
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

    public void onStartDateResult(Date date) {
        setStartDate(date);
    }

    public void onEndDateResult(Date date) {
        setEndDate(date);
    }

    @Bindable
    public void setStartDate(Date date) {
        leaveObject.setDateStart(date);
        workingDays.setStartDate(date);
        if(leaveObject.getDateEnd() == null) {
            setEndDate(workingDays.getLastWorkingDay(DEFAULT_NUMBER_OF_DAYS));
        }
        leaveObject.setDuration(workingDays.getWorkingDaysCount());
        notifyChange();
    }

    @Bindable
    public void setEndDate(Date date) {
        leaveObject.setDateEnd(date);
        leaveObject.setDuration(workingDays.getWorkingDaysCount());
        notifyChange();
    }

    public AppBarLayout.OnOffsetChangedListener onBarCollapseListener() {
        return (appBarLayout, verticalOffset) -> {
            if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange() * 0.25f) {
                setAppBarCollapsed(true);
            }
            else if(isAppBarCollapsed()){
                setAppBarCollapsed(false);
            }
        };
    }

    public void removeLeave() {
        viewController.returnResult(LeaveListResult.RESULT_CODE_REMOVE, this.leaveObject);
    }

    public void returnLeave(Leave leaveObject) {
        viewController.returnResult(LeaveListResult.RESULT_CODE_ADD_OR_EDIT, leaveObject);
    }
}
