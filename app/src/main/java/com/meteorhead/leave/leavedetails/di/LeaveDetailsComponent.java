package com.meteorhead.leave.leavedetails.di;

import com.meteorhead.leave.application.di.ViewScope;
import com.meteorhead.leave.leavedetails.conductor.LeaveDetailsView;

import dagger.Subcomponent;

/**
 * Created by wierzchanowskig on 12.11.2016.
 */
@ViewScope
@Subcomponent(modules = {LeaveDetailsModule.class})
public interface LeaveDetailsComponent {
    void inject(LeaveDetailsView leaveDetailsView);
}
