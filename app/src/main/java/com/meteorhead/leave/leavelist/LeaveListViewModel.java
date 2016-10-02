package com.meteorhead.leave.leavelist;

import android.databinding.Bindable;

import com.android.databinding.library.baseAdapters.BR;
import com.meteorhead.leave.ViewModel;
import com.meteorhead.leave.database.dbabstract.LeaveDbService;
import com.meteorhead.leave.models.Leave;

import java.util.Collection;

import rx.Observable;
import rx.functions.Action1;
import rx.subjects.PublishSubject;

public class LeaveListViewModel extends ViewModel {
    private LeaveListFragmentController fragmentController;
    private LeaveDbService leaveDbService;
    private boolean selectionMode = false;

    private PublishSubject<Object> removeTaskPublishSubject = PublishSubject.create();

    public LeaveListViewModel(LeaveListFragmentController fragmentController, LeaveDbService leaveDbService) {
        this.fragmentController = fragmentController;
        this.leaveDbService = leaveDbService;
    }

    @Bindable
    public Collection<Leave> getItemsList(){
        return leaveDbService.getAllLeaves();
    }

    public void onAddLeaveClick() {
        openLeaveDetailsScreenAdd();
    }

    public void onRemoveLeaveClick() {
        removeTaskPublishSubject.onNext(0);
    }

    public Observable<Object> getRemoveTaskObservable() {
        return removeTaskPublishSubject.asObservable();
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

    @Bindable
    public boolean getSelectionMode() {
        return selectionMode;
    }

    @Bindable
    public void setSelectionMode(boolean selectionMode) {
        this.selectionMode = selectionMode;
        notifyPropertyChanged(BR.selectionMode);
    }

    public LeaveDbService getLeaveDbService() {
        return leaveDbService;
    }
}
