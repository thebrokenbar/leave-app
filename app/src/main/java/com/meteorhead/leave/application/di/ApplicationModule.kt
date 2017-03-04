package com.meteorhead.leave.application.di

import android.content.Context

import com.meteorhead.leave.application.Application

import javax.inject.Singleton

import dagger.Module
import dagger.Provides

/**
 * Created by wierzchanowskig on 12.11.2016.
 */
@Module
class ApplicationModule(private val application: Application) {

    @Provides
    @Singleton
    internal fun provideApplicationContext(): Context {
        return this.application
    }

}
