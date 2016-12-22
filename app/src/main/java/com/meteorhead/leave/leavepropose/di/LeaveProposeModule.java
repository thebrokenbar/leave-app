package com.meteorhead.leave.leavepropose.di;

import com.meteorhead.leave.application.di.ViewScope;
import com.meteorhead.leave.leavepropose.LeaveProposeViewController;
import com.meteorhead.leave.leavepropose.LeaveProposeViewHandler;
import com.meteorhead.leave.leavepropose.LeaveProposeViewModel;

import com.meteorhead.leave.mainactivity.ActivityViewModel;
import dagger.Module;
import dagger.Provides;

/**
 * Created by wierzchanowskig on 29.11.2016.
 */
@Module
public class LeaveProposeModule {
    private LeaveProposeViewController viewController;

    public LeaveProposeModule(LeaveProposeViewController viewController) {
        this.viewController = viewController;
    }

    @ViewScope
    @Provides
    LeaveProposeViewModel provideLeaveProposeViewModel(ActivityViewModel activityViewModel) {
        return new LeaveProposeViewModel(activityViewModel);
    }

    @ViewScope
    @Provides
    LeaveProposeViewHandler provideLeaveProposeViewHandler(LeaveProposeViewModel viewModel) {
        return new LeaveProposeViewHandler(viewModel, viewController);
    }
}
