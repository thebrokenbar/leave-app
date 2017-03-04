package com.meteorhead.leave.application


import android.databinding.adapters.SeekBarBindingAdapter
import android.support.multidex.MultiDexApplication

import com.facebook.stetho.Stetho
import com.meteorhead.leave.BuildConfig
import com.meteorhead.leave.application.di.ApplicationComponent
import com.meteorhead.leave.application.di.ApplicationModule
import com.meteorhead.leave.application.di.DaggerApplicationComponent
import com.meteorhead.leave.database.realm.di.RealmModule
import com.uphyca.stetho_realm.RealmInspectorModulesProvider

import net.danlew.android.joda.JodaTimeAndroid

import javax.inject.Inject
import javax.inject.Named

import io.realm.Realm

import com.meteorhead.leave.database.realm.di.RealmModule.MAIN_THREAD

/**
 * Created by wierzchanowskig on 17.09.2016.
 */

class Application : MultiDexApplication() {

    lateinit var applicationComponent: ApplicationComponent
        private set

    @Inject
    @Named(MAIN_THREAD)
    internal lateinit var realmInstance: Realm

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)

        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .realmModule(RealmModule()).build()
        applicationComponent.inject(this)

        JodaTimeAndroid.init(this)

        if (BuildConfig.DEBUG) {
            initializeStetho()
        }
    }

    private fun initializeStetho() {
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this)
                                .build())
                        .build())
    }
}
