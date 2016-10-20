package com.meteorhead.leave.leavelist;

import android.databinding.Bindable;
import android.databinding.ObservableBoolean;

import com.android.databinding.library.baseAdapters.BR;
import com.google.firebase.crash.FirebaseCrash;
import com.meteorhead.leave.ViewModel;
import com.meteorhead.leave.database.dbabstract.LeaveDbService;
import com.meteorhead.leave.database.dbabstract.base.DatabaseCallbackQuery;
import com.meteorhead.leave.database.realm.LeaveRealmService;
import com.meteorhead.leave.database.realm.base.interfaces.RealmCallback;
import com.meteorhead.leave.models.Leave;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;
import rx.functions.Action1;
import rx.subjects.PublishSubject;

public class LeaveListViewModel extends ViewModel {
    private LeaveListFragmentController fragmentController;
    private LeaveDbService leaveDbService;
    private boolean selectionMode = false;

    private PublishSubject<Object> removeTaskPublishSubject = PublishSubject.create();
    private final List<Leave> recentlyRemovedItems = new ArrayList<>();
    private Collection<Leave> itemsList = null;


    public LeaveListViewModel(LeaveListFragmentController fragmentController, LeaveDbService leaveDbService) {
        this.fragmentController = fragmentController;
        this.leaveDbService = leaveDbService;
    }

    public void onStart() {
        leaveDbService.getAllLeavesAsync(new DatabaseCallbackQuery<RealmResults<Leave>>() {
            @Override
            public void onSuccess(RealmResults<Leave> result) {
                setItemsList(result);
            }

            @Override
            public void onError(Throwable error) {
                Logger.e(error, error.getMessage());
                FirebaseCrash.report(error);
            }
        });
    }

    @Bindable
    public Collection<Leave> getItemsList(){
        return itemsList;
    }

    @Bindable
    public void setItemsList(Collection<Leave> items) {
        this.itemsList = items;
        notifyPropertyChanged(BR.itemsList);
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

    public void removeLeaves(List<Leave> leaves) {
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
