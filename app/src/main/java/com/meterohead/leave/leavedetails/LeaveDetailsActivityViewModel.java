package com.meterohead.leave.leavedetails;

import android.databinding.Bindable;
import android.view.View;

import com.meterohead.leave.mainactivity.ActivityViewModel;
import com.android.databinding.library.baseAdapters.BR;
import com.meterohead.leave.models.Leave;

import org.parceler.Parcel;

/**
 * Created by wierzchanowskig on 23.09.2016.
 */
public class LeaveDetailsActivityViewModel extends ActivityViewModel {
    private boolean confirmButtonVisibility;
    private View.OnClickListener onConfirmClickListener;
    private LeaveDetailsActivityController activityController;

    public LeaveDetailsActivityViewModel(LeaveDetailsActivityController activityController) {
        this.activityController = activityController;
    }

    @Bindable
    public boolean isConfirmButtonVisibility() {
        return confirmButtonVisibility;
    }

    @Bindable
    public void setConfirmButtonVisibility(boolean confirmButtonVisibility) {
        this.confirmButtonVisibility = confirmButtonVisibility;
        notifyPropertyChanged(BR.confirmButtonVisibility);
    }

    public void setOnConfirmClickListener(View.OnClickListener listener) {
        onConfirmClickListener = listener;
    }

    public void onConfirmClick(View view) {
        onConfirmClickListener.onClick(view);
    }

    public void finishActivityWithResult(Leave leaveObject) {
        activityController.returnResult(leaveObject);
    }
}
