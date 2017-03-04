package com.meteorhead.leave.screens.bestleaves.di

import com.meteorhead.leave.screens.bestleaves.BestLeavesView
import dagger.Subcomponent

/**
 * Created by wierzchanowskig on 01.03.2017.
 */
@Subcomponent(modules = arrayOf(BestLeavesModule::class))
interface BestLeavesComponent {
    fun inject(view: BestLeavesView)
}

