/*
 * Copyright (c) 2016. All rights reserved.
 */

package com.meteorhead.leave.database.dbabstract.base;

/**
 * Created by Grzegorz Wierzchanowski on 2016-09-16.
 * MeteorHead - All rights reserved.
 */

public interface IDatabaseCallback {
    void onSuccess();
    void onError(Throwable error);
}
