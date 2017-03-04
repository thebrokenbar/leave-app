package com.meteorhead.leave.mainactivity.di

import com.meteorhead.leave.mainactivity.MainActivity
import com.meteorhead.leave.screens.bestleaves.di.BestLeavesComponent
import dagger.Subcomponent

/**
 * Created by wierzchanowskig on 26.11.2016.
 */
@ActivityScope
@Subcomponent(modules = arrayOf(ActivityModule::class))
interface ActivityComponent {
    fun inject(activity: MainActivity)
    fun plus(): BestLeavesComponent
}
