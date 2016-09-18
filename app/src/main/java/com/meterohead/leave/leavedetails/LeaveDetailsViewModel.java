package com.meterohead.leave.leavedetails;

import android.databinding.Bindable;

import com.meterohead.leave.mainactivity.IActivityController;
import com.meterohead.leave.mainactivity.ToolbarViewModel;
import com.meterohead.leave.mainactivity.ViewModel;
import com.meterohead.leave.models.Leave;

import javax.annotation.Nullable;

/**
 * Created by wierzchanowskig on 18.09.2016.
 */
public class LeaveDetailsViewModel extends ViewModel {

    @Nullable
    private Leave leaveObject;

    public LeaveDetailsViewModel(IActivityController activityController, ToolbarViewModel toolbarViewModel,
                                 @Nullable Leave leaveObject) {
        super(activityController, toolbarViewModel);
        this.leaveObject = leaveObject;
    }

    @Nullable
    @Bindable
    public Leave getLeave() {
        return leaveObject;
    }
}
