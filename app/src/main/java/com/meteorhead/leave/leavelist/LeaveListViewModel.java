package com.meteorhead.leave.leavelist;

import android.databinding.Bindable;
import android.view.View;

import com.android.databinding.library.baseAdapters.BR;
import com.meteorhead.leave.ViewModel;
import com.meteorhead.leave.database.dbabstract.LeaveDbService;
import com.meteorhead.leave.models.Leave;

import java.util.Collection;

import rx.functions.Action1;

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

    @Bindable
    public boolean getFabVisibility() {
        return this.visibility;
    }

    @Bindable
    public void setFabVisibility(boolean visibility) {
        this.visibility = visibility;
        notifyPropertyChanged(BR.fabVisibility);
    }

    @Bindable
    public Action1<Leave> getOnItemClickListener() {
        return new Action1<Leave>() {
            @Override
            public void call(Leave leave) {
                openLeaveDetailsScreenEdit(leave);
            }
        };
    }

    private void openLeaveDetailsScreenAdd() {
        fragmentController.addNewLeave();
    }

    private void openLeaveDetailsScreenEdit(Leave leaveObject) {
        fragmentController.editLeave(leaveObject);
    }

}
