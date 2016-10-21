package com.meteorhead.leave.leavedetails;

import android.databinding.Bindable;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.SeekBar;

import com.android.databinding.library.baseAdapters.BR;

import com.meteorhead.leave.ViewModel;
import com.meteorhead.leave.models.Leave;
import com.meteorhead.leave.models.helpers.WorkingDays;
import com.meteorhead.leave.models.helpers.impl.RealmWorkingDays;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Nullable;

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
    private boolean appBarCollapsed;

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
        activityController.returnResult(LeaveDetailsActivityController.RESULT_CODE_ADD,leaveObject);
    }

    @Bindable
    public boolean isAppBarCollapsed() {
        return appBarCollapsed;
    }

    @Bindable
    public void setAppBarCollapsed(boolean appBarCollapsed) {
        this.appBarCollapsed = appBarCollapsed;
        notifyPropertyChanged(BR.appBarCollapsed);
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

    public void removeLeave() {
        activityController.returnResult(LeaveDetailsActivityController.RESULT_CODE_REMOVE, leaveObject);
    }

    public TextWatcher getOnTitleTextChangeListener() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!leaveObject.getTitle().equals(charSequence.toString())) {
                    leaveObject.setTitle(charSequence.toString());
                    notifyPropertyChanged(BR.leaveObject);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
    }

    public AppBarLayout.OnOffsetChangedListener onBarCollapseListener() {
        return new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset == 0) {
                    setAppBarCollapsed(false);
                } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                    setAppBarCollapsed(true);
                }
            }
        };
    }
}
