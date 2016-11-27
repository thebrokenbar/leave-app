package com.meteorhead.leave.leavelist.di;

import com.meteorhead.leave.application.di.ViewScope;
import com.meteorhead.leave.leavelist.conductor.LeaveListView;

import dagger.Subcomponent;

/**
 * Created by wierzchanowskig on 12.11.2016.
 */
@ViewScope
@Subcomponent(modules = {LeaveListModule.class})
public interface LeaveListComponent {
    void inject(LeaveListView leaveListView);
}
