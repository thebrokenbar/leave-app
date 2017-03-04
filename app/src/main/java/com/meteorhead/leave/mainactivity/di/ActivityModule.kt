package com.meteorhead.leave.mainactivity.di

import com.meteorhead.leave.mainactivity.ActivityViewModel

import dagger.Module
import dagger.Provides

/**
 * Created by wierzchanowskig on 26.11.2016.
 */
@Module
class ActivityModule {

    @ActivityScope
    @Provides
    internal fun provideActivityViewModel(): ActivityViewModel {
        return ActivityViewModel()
    }
}
