package com.meteorhead.leave.leavelist;

import android.databinding.Bindable;
import android.databinding.ObservableBoolean;

import com.android.databinding.library.baseAdapters.BR;
import com.google.firebase.crash.FirebaseCrash;
import com.meteorhead.leave.ViewModel;
import com.meteorhead.leave.database.dbabstract.LeaveDbService;
import com.meteorhead.leave.database.realm.LeaveRealmService;
import com.meteorhead.leave.database.realm.base.interfaces.RealmCallback;
import com.meteorhead.leave.models.Leave;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import rx.Observable;
import rx.functions.Action1;
import rx.subjects.PublishSubject;

public class LeaveListViewModel extends ViewModel {
    private LeaveListFragmentController fragmentController;
    private LeaveRealmService leaveDbService;

    private PublishSubject<Object> removeTaskPublishSubject = PublishSubject.create();
    private final List<Leave> recentlyRemovedItems = new ArrayList<>();
    private RealmResults<Leave> itemsList = null;
    public ObservableBoolean isSelectionMode = new ObservableBoolean(false);

    private RealmChangeListener<RealmResults<Leave>> itemsListChangeListener = new RealmChangeListener<RealmResults<Leave>>() {
        @Override
        public void onChange(RealmResults<Leave> element) {
            notifyPropertyChanged(BR.itemsList);
        }
    };

    LeaveListViewModel(LeaveListFragmentController fragmentController, LeaveRealmService leaveDbService) {
        this.fragmentController = fragmentController;
        this.leaveDbService = leaveDbService;
    }

    void onStart() {
        setItemsList(leaveDbService.getAllLeavesFuture());
    }

    @Bindable
    public Collection<Leave> getItemsList(){
        return itemsList;
    }

    private void setItemsList(RealmResults<Leave> items) {
        if(this.itemsList != null) {
            this.itemsList.removeChangeListeners();
        }
        this.itemsList = items;
        this.itemsList.addChangeListener(itemsListChangeListener);
        notifyPropertyChanged(BR.itemsList);
    }

    void onRemoveLeaveClick() {
        removeTaskPublishSubject.onNext(0);
    }

    Observable<Object> getRemoveTaskObservable() {
        return removeTaskPublishSubject.asObservable();
    }

    LeaveDbService getLeaveDbService() {
        return leaveDbService;
    }

    private void showUndoSnackBar(int removedItemsCount) {
        fragmentController.showUndoSnackBar(removedItemsCount).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean actionClicked) {
                if(actionClicked) {
                    restoreRecentlyRemovedLeaves();
                }
            }
        });
    }

    void openLeaveDetailsScreenAdd() {
        fragmentController.addNewLeave();
    }

    void openLeaveDetailsScreenEdit(Leave leaveObject) {
        fragmentController.editLeave(leaveObject);
    }

    void openLeaveProposeScreenAdd() {
        fragmentController.proposeNewLeave();
    }

    void addOrUpdateLeave(Leave leave) {
        final LeaveDbService leaveDbService = new LeaveRealmService();
        leaveDbService.addOrUpdate(leave, new RealmCallback() {
            @Override
            public void onSuccess() {
                Logger.i("saved Leave in Database");
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

    void removeLeaves(List<Leave> leaves) {
        recentlyRemovedItems.clear();

        final LeaveDbService leaveDbService = new LeaveRealmService();
        for (Leave item : leaves) {
            Leave itemCopy = new Leave();
            itemCopy.set(item);
            itemCopy.setId(item.getId());
            recentlyRemovedItems.add(itemCopy);
        }
        leaveDbService.removeLeaves(leaves);

        showUndoSnackBar(leaves.size());
        leaveDbService.finish();
    }

    private void restoreRecentlyRemovedLeaves() {
        final LeaveDbService leaveDbService = new LeaveRealmService();
        leaveDbService.insertLeaves(recentlyRemovedItems, new RealmCallback() {
            @Override
            public void onSuccess() {
                recentlyRemovedItems.clear();
                leaveDbService.finish();
            }

            @Override
            public void onError(Throwable error) {
                Logger.e(error, error.getMessage());
                FirebaseCrash.report(error);
                recentlyRemovedItems.clear();
                leaveDbService.finish();
            }
        });
    }
}
