package com.meteorhead.leave.application;


import android.support.multidex.MultiDexApplication;

import com.facebook.stetho.Stetho;
import com.meteorhead.leave.BuildConfig;
import com.meteorhead.leave.application.di.ApplicationComponent;
import com.meteorhead.leave.application.di.ApplicationModule;
import com.meteorhead.leave.application.di.DaggerApplicationComponent;
import com.meteorhead.leave.database.realm.di.RealmModule;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import net.danlew.android.joda.JodaTimeAndroid;

import javax.inject.Inject;
import javax.inject.Named;

import io.realm.Realm;

import static com.meteorhead.leave.database.realm.di.RealmModule.MAIN_THREAD;

/**
 * Created by wierzchanowskig on 17.09.2016.
 */

public class Application extends MultiDexApplication {

    ApplicationComponent applicationComponent;
    @Inject
    @Named(MAIN_THREAD)
    Realm realmInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .realmModule(new RealmModule()).build();
        applicationComponent.inject(this);

        JodaTimeAndroid.init(this);

        if(BuildConfig.DEBUG) {
            initializeStetho();
        }
    }

    private void initializeStetho() {
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this)
                                .build())
                        .build());
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
