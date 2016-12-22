package com.meteorhead.leave.leavepropose.di;

import com.meteorhead.leave.application.di.ViewScope;
import com.meteorhead.leave.leavepropose.LeaveProposeView;

import dagger.Subcomponent;

/**
 * Created by wierzchanowskig on 29.11.2016.
 */
@ViewScope
@Subcomponent(modules = {LeaveProposeModule.class})
public interface LeaveProposeComponent {
    void inject(LeaveProposeView leaveProposeView);
}
