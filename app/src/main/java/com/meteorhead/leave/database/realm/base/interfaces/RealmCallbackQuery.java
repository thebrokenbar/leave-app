package com.meteorhead.leave.database.realm.base.interfaces;

import com.meteorhead.leave.database.dbabstract.base.DatabaseCallbackQuery;

import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by wierzchanowskig on 05.10.2016.
 */

public interface RealmCallbackQuery<T extends RealmObject>
        extends DatabaseCallbackQuery<RealmResults<T>> {
}
