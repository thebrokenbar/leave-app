/*
 * Copyright (c) 2016. All rights reserved.
 */

package com.meterohead.leave.database.realm.base.interfaces;

import com.meterohead.leave.database.dbabstract.base.IDatabaseCallback;

import io.realm.Realm;

/**
 * Created by Grzegorz Wierzchanowski on 2016-09-16.
 * MeteorHead - All rights reserved.
 */

public interface IRealmCallback extends IDatabaseCallback, Realm.Transaction.OnSuccess, Realm.Transaction.OnError {
}
