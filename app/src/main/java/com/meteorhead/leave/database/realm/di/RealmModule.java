package com.meteorhead.leave.database.realm.di;

import android.content.Context;

import com.meteorhead.leave.database.realm.LeaveRealmService;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by wierzchanowskig on 12.11.2016.
 */
@Module
public class RealmModule {

    private static final int SCHEMA_VERSION = 0;
    public static final String MAIN_THREAD = "MainThread";

    @Provides
    @Singleton
    @Named(MAIN_THREAD)
    Realm provideRealmMainThreadInstance() {
        RealmConfiguration defaultRealmConfig = new RealmConfiguration.Builder()
                .schemaVersion(SCHEMA_VERSION)
                .build();
        Realm.setDefaultConfiguration(defaultRealmConfig);
        return Realm.getDefaultInstance();
    }

    @Provides
    Realm provideThreadRealmInstance() {
        return Realm.getDefaultInstance();
    }

    @Provides
    @Singleton
    @Named(MAIN_THREAD)
    LeaveRealmService provideMainThreadLeaveRealmService(@Named(MAIN_THREAD) Realm realm) {
        return new LeaveRealmService(realm);
    }

    @Provides
    LeaveRealmService provideThreadLeaveRealmService(Realm realm) {
        return new LeaveRealmService(realm);
    }
}
