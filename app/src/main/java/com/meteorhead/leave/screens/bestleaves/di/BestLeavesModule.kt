package com.meteorhead.leave.screens.bestleaves.di

import com.meteorhead.leave.application.di.ViewScope
import com.meteorhead.leave.models.WorkingDays
import com.meteorhead.leave.models.impl.RealmWorkingDays
import dagger.Module
import dagger.Provides

@Module
@ViewScope
class BestLeavesModule {
    @Provides
    fun provideWorkingDaysService(): WorkingDays = RealmWorkingDays(null, null)
}