package com.meteorhead.leave.leavelist.di;

import com.meteorhead.leave.application.di.ViewScope;
import com.meteorhead.leave.database.realm.LeaveRealmService;
import com.meteorhead.leave.leavelist.LeaveListViewController;
import com.meteorhead.leave.leavelist.LeaveListViewHandler;
import com.meteorhead.leave.leavelist.LeaveListViewModel;
import com.meteorhead.leave.leavelist.LeaveListView;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

import static com.meteorhead.leave.database.realm.di.RealmModule.MAIN_THREAD;

/**
 * Created by wierzchanowskig on 12.11.2016.
 */
@Module
public class LeaveListModule {
    private LeaveListView leaveListView;

    public LeaveListModule(LeaveListView leaveListView) {
        this.leaveListView = leaveListView;
    }

    @Provides
    @ViewScope
    LeaveListViewController provideLeaveListViewController() {
        return leaveListView;
    }

    @Provides
    @ViewScope
    LeaveListViewModel provideLeaveListViewModel(LeaveListViewController leaveListViewController,
                                                 @Named(MAIN_THREAD) LeaveRealmService leaveRealmService) {
        return new LeaveListViewModel(leaveListViewController, leaveRealmService);
    }

    @Provides
    @ViewScope
    LeaveListViewHandler provideLeaveListViewHandler(LeaveListViewModel viewModel) {
        return new LeaveListViewHandler(viewModel);
    }
}
