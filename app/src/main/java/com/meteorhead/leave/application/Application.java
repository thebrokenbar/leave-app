package com.meteorhead.leave.application;


import android.support.multidex.MultiDexApplication;

import com.facebook.stetho.Stetho;
import com.meteorhead.leave.BuildConfig;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import net.danlew.android.joda.JodaTimeAndroid;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by wierzchanowskig on 17.09.2016.
 */

public class Application extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();

        initializeRealm();
        JodaTimeAndroid.init(this);

        if(BuildConfig.DEBUG) {
            initializeStetho();
        }
    }

    private void initializeRealm() {
        Realm.init(this);
        RealmConfiguration defaultRealmConfig = new RealmConfiguration.Builder()
                .schemaVersion(0)
                .name("leaveDb.realm")
                .build();
        Realm.setDefaultConfiguration(defaultRealmConfig);
    }

    private void initializeStetho() {
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this)
                                .build())
                        .build());
    }
}
