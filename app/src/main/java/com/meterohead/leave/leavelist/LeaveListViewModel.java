package com.meterohead.leave.leavelist;

import android.databinding.Bindable;
import android.view.View;

import com.android.databinding.library.baseAdapters.BR;
import com.meterohead.leave.database.dbabstract.ILeaveDbService;
import com.meterohead.leave.leavedetails.LeaveDetailsFragment;
import com.meterohead.leave.mainactivity.IActivityController;
import com.meterohead.leave.mainactivity.ToolbarViewModel;
import com.meterohead.leave.mainactivity.ViewModel;
import com.meterohead.leave.models.Leave;

import java.util.Collection;
import java.util.TimerTask;

public class LeaveListViewModel extends ViewModel {

    private ILeaveDbService leaveDbService;
    private boolean visibility = false;

    public LeaveListViewModel(IActivityController activityController, ToolbarViewModel toolbarViewModel,
                              ILeaveDbService leaveDbService) {
        super(activityController, toolbarViewModel);
        this.leaveDbService = leaveDbService;
    }

    @Bindable
    public Collection<Leave> getItemsList(){
        return leaveDbService.getAllLeaves();
    }

    public void onAddLeaveClick(View view) {
        setFabVisibility(false);
        view.postDelayed(new TimerTask() {
            @Override
            public void run() {
                openLeaveDetailsScreenAdd();
            }
        }, 300);
    }

    private void openLeaveDetailsScreenAdd() {
        getActivityController().changeFragment(LeaveDetailsFragment.newInstance(null));
    }

    @Bindable
    public boolean getFabVisibility() {
        return this.visibility;
    }

    @Bindable
    public void setFabVisibility(boolean visibility) {
        this.visibility = visibility;
        notifyPropertyChanged(BR.fabVisibility);
    }
}
