package com.meteorhead.leave.leavelist;

import android.databinding.Bindable;
import android.databinding.ObservableBoolean;
import com.android.databinding.library.baseAdapters.BR;
import com.google.firebase.crash.FirebaseCrash;
import com.meteorhead.leave.base.ViewModel;
import com.meteorhead.leave.database.dbabstract.LeaveDbService;
import com.meteorhead.leave.database.realm.LeaveRealmService;
import com.meteorhead.leave.database.realm.base.interfaces.RealmCallback;
import com.meteorhead.leave.models.Leave;
import com.orhanobut.logger.Logger;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import rx.Observable;
import rx.subjects.PublishSubject;

public class LeaveListViewModel extends ViewModel {
    private LeaveListViewController fragmentController;
    private LeaveRealmService leaveDbService;

    private PublishSubject<Object> removeTaskPublishSubject = PublishSubject.create();
    private final List<Leave> recentlyRemovedItems = new ArrayList<>();
    private RealmResults<Leave> itemsList = null;
    public ObservableBoolean isSelectionMode = new ObservableBoolean(false);

    private RealmChangeListener<RealmResults<Leave>> itemsListChangeListener =
        element -> notifyPropertyChanged(BR.itemsList);

    public LeaveListViewModel(LeaveListViewController fragmentController,
        LeaveRealmService leaveDbService) {
        this.fragmentController = fragmentController;
        this.leaveDbService = leaveDbService;
    }

    public void onStart() {
        setItemsList(leaveDbService.getAllLeavesFuture());
    }

    @Bindable
    public Collection<Leave> getItemsList() {
        return itemsList;
    }

    private void setItemsList(RealmResults<Leave> items) {
        if (this.itemsList != null) {
            this.itemsList.removeChangeListeners();
        }
        this.itemsList = items;
        this.itemsList.addChangeListener(itemsListChangeListener);
        notifyPropertyChanged(BR.itemsList);
    }

    public void onRemoveLeaveClick() {
        removeTaskPublishSubject.onNext(0);
    }

    Observable<Object> getRemoveTaskObservable() {
        return removeTaskPublishSubject.asObservable();
    }

    private void showUndoSnackBar(int removedItemsCount) {
        fragmentController.showUndoRemoveSnackBar(removedItemsCount).subscribe(actionClicked -> {
            if (actionClicked) {
                restoreRecentlyRemovedLeaves();
            }
        });
    }

    void openLeaveDetailsScreenAdd() {
        fragmentController.showAddNewLeaveView();
    }

    void openLeaveDetailsScreenEdit(Leave leaveObject) {
        fragmentController.showEditLeaveView(leaveObject);
    }

    void openLeaveProposeScreenAdd() {
        fragmentController.showProposeNewLeaveView();
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
        this.recentlyRemovedItems.clear();

        final LeaveDbService leaveDbService = new LeaveRealmService();
        for (Leave item : leaves) {
            this.recentlyRemovedItems.add(leaveDbService.copy(item));
        }
        leaveDbService.removeLeaves(leaves);
        leaveDbService.finish();
        showUndoSnackBar(leaves.size());
    }

    private void restoreRecentlyRemovedLeaves() {
        leaveDbService.insertLeaves(recentlyRemovedItems);
        recentlyRemovedItems.clear();
        notifyPropertyChanged(BR.itemsList);
    }

    public void removeLeave(int leaveId) {
        Leave leaveToRemove = leaveDbService.getLeaveById(leaveId);
        recentlyRemovedItems.add(leaveDbService.copy(leaveToRemove));
        leaveDbService.removeLeave(leaveToRemove);
        showUndoSnackBar(1);
    }

    public static String getDaysCount(String pluralString, Leave leave) {
        LocalDate dateStart = LocalDate.fromDateFields(leave.getDateStart());
        LocalDate dateEnd = LocalDate.fromDateFields(leave.getDateEnd());
        int totalDays = Days.daysBetween(dateStart, dateEnd).getDays() + 1;
        return "(" + totalDays + "-" + pluralString + ")";
    }
}
