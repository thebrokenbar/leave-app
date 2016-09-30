/*
 * Copyright (c) 2016. All rights reserved.
 */

package com.meteorhead.leave.database.realm.base;

import com.meteorhead.leave.database.dbabstract.base.IDatabaseCallback;
import com.meteorhead.leave.database.realm.base.interfaces.IRealmCallback;

import io.realm.Realm;
import io.realm.RealmObject;

/**
 * Created by Grzegorz Wierzchanowski on 2016-09-16.
 * MeteorHead - All rights reserved.
 */

public abstract class RealmService<E extends RealmObject> {
    public static String FIELD_ID = "id";

    private Class<E> dataObjectClass;
    private final Realm serviceRealm;

    private RealmService() {
        this.serviceRealm = null;
    }

    public RealmService(Class<E> clazz, Realm realm) {
        this.dataObjectClass = clazz;
        this.serviceRealm = realm;
    }

    public Realm getServiceRealm() {
        return serviceRealm;
    }

    public Class<E> getDataObjectClass() {
        return dataObjectClass;
    }

    protected IRealmCallback convertCallback(IDatabaseCallback callback) {
        if(callback instanceof IRealmCallback) {
            return (IRealmCallback) callback;
        } else {
            throw new IllegalArgumentException("Every RealmService should use IRealmCallback");
        }
    }

    protected Object getNextPrimaryKey(Realm realm) {
        Number max = realm.where(dataObjectClass).max(FIELD_ID);
        if(max != null) {
            return max.intValue() + 1;
        } else {
            return 1;
        }
    }

    public void close() {
        if (serviceRealm != null) {
            serviceRealm.close();
        }
    }
}
