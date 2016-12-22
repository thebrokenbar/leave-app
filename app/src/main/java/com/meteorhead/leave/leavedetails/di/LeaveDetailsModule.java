package com.meteorhead.leave.leavedetails.di;

import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.meteorhead.leave.application.di.ViewScope;
import com.meteorhead.leave.leavedetails.LeaveDetailsViewController;
import com.meteorhead.leave.leavedetails.LeaveDetailsViewHandler;
import com.meteorhead.leave.leavedetails.LeaveDetailsViewModel;
import com.meteorhead.leave.mainactivity.ActivityViewModel;
import com.meteorhead.leave.models.Leave;
import com.meteorhead.leave.models.helpers.WorkingDays;
import com.meteorhead.leave.models.helpers.impl.RealmWorkingDays;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;

import static com.meteorhead.leave.database.realm.di.RealmModule.MAIN_THREAD;

/**
 * Created by wierzchanowskig on 12.11.2016.
 */
@Module
public class LeaveDetailsModule {

    @Nullable
    private Leave leaveObject;

    @NonNull
    private LeaveDetailsViewController leaveDetailsViewController;

    public LeaveDetailsModule(@Nullable Leave leaveObject, @NonNull LeaveDetailsViewController leaveDetailsViewController) {
        this.leaveObject = leaveObject;
        this.leaveDetailsViewController = leaveDetailsViewController;
    }

    @Provides
    @ViewScope
    LeaveDetailsViewController provideLeaveDetailsViewController() {
        return this.leaveDetailsViewController;
    }

    @Provides
    @Nullable
    @ViewScope
    Leave provideLeaveObject(@Named(MAIN_THREAD) Realm realmInstance) {
        if(leaveObject == null)
            return null;
        return realmInstance.copyFromRealm(leaveObject);
    }

    @Provides
    @ViewScope
    LeaveDetailsViewModel provideLeaveDetailsViewModel(
            LeaveDetailsViewController fragmentController, @Nullable Leave leaveObject,
            @NonNull WorkingDays workingDays, @NonNull ObservableInt observableInt, @NonNull ActivityViewModel activityViewModel) {
        if (leaveObject == null) {
            leaveObject = new Leave(null, null, null, 0, 0);
        }
        return new LeaveDetailsViewModel(fragmentController, activityViewModel, leaveObject, workingDays, observableInt);
    }

    @Provides
    @NonNull
    WorkingDays provideWorkingDays(@Nullable Leave leaveObject) {
        if(leaveObject == null) {
            return new RealmWorkingDays(null, null);
        } else {
            return new RealmWorkingDays(leaveObject.getDateStart(), leaveObject.getDateEnd());
        }
    }

    @Provides
    @NonNull
    ObservableInt provideObservableInt() {
        return new ObservableInt();
    }

    @Provides
    @ViewScope
    LeaveDetailsViewHandler provideLeaveDetailsViewHandler(LeaveDetailsViewModel leaveDetailsViewModel) {
        return new LeaveDetailsViewHandler(leaveDetailsViewModel);
    }
}
