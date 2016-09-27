package com.meterohead.leave.leavelist;

import android.databinding.Bindable;
import android.view.View;

import com.android.databinding.library.baseAdapters.BR;
import com.meterohead.leave.ViewModel;
import com.meterohead.leave.database.dbabstract.LeaveDbService;
import com.meterohead.leave.models.Leave;

import java.util.Collection;

public class LeaveListViewModel extends ViewModel {
    private LeaveListFragmentController fragmentController;
    private LeaveDbService leaveDbService;
    private boolean visibility = true;

    public LeaveListViewModel(LeaveListFragmentController fragmentController, LeaveDbService leaveDbService) {
        this.fragmentController = fragmentController;
        this.leaveDbService = leaveDbService;
    }

    @Bindable
    public Collection<Leave> getItemsList(){
        return leaveDbService.getAllLeaves();
    }

    public void onAddLeaveClick(View view) {
        openLeaveDetailsScreenAdd();
    }

    private void openLeaveDetailsScreenAdd() {
        fragmentController.addNewLeave();
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
