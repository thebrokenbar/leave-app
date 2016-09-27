package com.meterohead.leave.application;


import com.facebook.stetho.BuildConfig;
import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import net.danlew.android.joda.JodaTimeAndroid;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by wierzchanowskig on 17.09.2016.
 */

public class Application extends android.app.Application {
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
        RealmConfiguration defaultRealmConfig = new RealmConfiguration.Builder(this)
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
