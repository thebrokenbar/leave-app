package com.meteorhead.leave.mainactivity.di;

import com.meteorhead.leave.leavedetails.di.LeaveDetailsComponent;
import com.meteorhead.leave.leavedetails.di.LeaveDetailsModule;
import com.meteorhead.leave.leavelist.di.LeaveListComponent;
import com.meteorhead.leave.leavelist.di.LeaveListModule;
import com.meteorhead.leave.leavepropose.di.LeaveProposeComponent;
import com.meteorhead.leave.leavepropose.di.LeaveProposeModule;
import com.meteorhead.leave.mainactivity.conductor.MainActivity;

import dagger.Subcomponent;

/**
 * Created by wierzchanowskig on 26.11.2016.
 */
@ActivityScope
@Subcomponent(modules = {ActivityModule.class})
public interface ActivityComponent {
    void inject(MainActivity activity);

    LeaveListComponent plus(LeaveListModule leaveListModule);
    LeaveDetailsComponent plus(LeaveDetailsModule leaveDetailsModule);
    LeaveProposeComponent plus(LeaveProposeModule leaveProposeModule);
}
