package com.meteorhead.leave.leavelist;

import android.databinding.Bindable;

import com.android.databinding.library.baseAdapters.BR;
import com.google.firebase.crash.FirebaseCrash;
import com.meteorhead.leave.ViewModel;
import com.meteorhead.leave.database.dbabstract.LeaveDbService;
import com.meteorhead.leave.database.realm.LeaveRealmService;
import com.meteorhead.leave.database.realm.base.interfaces.IRealmCallback;
import com.meteorhead.leave.models.Leave;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.realm.Realm;
import rx.Observable;
import rx.functions.Action1;
import rx.subjects.PublishSubject;

public class LeaveListViewModel extends ViewModel {
    private LeaveListFragmentController fragmentController;
    private LeaveDbService leaveDbService;
    private boolean selectionMode = false;

    private PublishSubject<Object> removeTaskPublishSubject = PublishSubject.create();
    private final List<Leave> recentlyRemovedItems = new ArrayList<>();


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

    public void showUndoSnackBar(int removedItemsCount) {
        fragmentController.showUndoSnackBar(removedItemsCount).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean actionClicked) {
                if(actionClicked) {
                    restoreRecentlyRemovedLeaves();
                }
            }
        });
    }

    public void addOrUpdateLeave(Leave leave) {
        final LeaveDbService leaveDbService = new LeaveRealmService(Realm.getDefaultInstance());
        leaveDbService.addOrUpdate(leave, new IRealmCallback() {
            @Override
            public void onSuccess() {
                leaveDbService.finish();
            }

            @Override
            public void onError(Throwable error) {
                Logger.e(error, error.getMessage());
                FirebaseCrash.report(error);
                leaveDbService.finish();
            }
        });
    }

    public void removeLeaves(List<Leave> leaves) {
        recentlyRemovedItems.clear();

        final LeaveDbService leaveDbService = new LeaveRealmService(Realm.getDefaultInstance());
        for (Leave item : leaves) {
            Leave itemCopy = new Leave();
            itemCopy.set(item);
            itemCopy.setId(item.getId());
            recentlyRemovedItems.add(itemCopy);
        }
        leaveDbService.removeLeaves(leaves);
        leaveDbService.finish();

        showUndoSnackBar(leaves.size());
    }

    private void restoreRecentlyRemovedLeaves() {
        final LeaveDbService leaveDbService = new LeaveRealmService(Realm.getDefaultInstance());
        leaveDbService.insertLeaves(recentlyRemovedItems, new IRealmCallback() {
            @Override
            public void onSuccess() {
                leaveDbService.finish();
                recentlyRemovedItems.clear();
            }

            @Override
            public void onError(Throwable error) {
                Logger.e(error, error.getMessage());
                FirebaseCrash.report(error);
                leaveDbService.finish();
                recentlyRemovedItems.clear();
            }
        });
    }
}
