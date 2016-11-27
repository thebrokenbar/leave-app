package com.meteorhead.leave.mainactivity.di;

import com.meteorhead.leave.mainactivity.ActivityViewModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by wierzchanowskig on 26.11.2016.
 */
@Module
public class ActivityModule {

    @ActivityScope
    @Provides
    ActivityViewModel provideActivityViewModel() {
        return new ActivityViewModel();
    }
}
