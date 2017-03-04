/*
 * Copyright (c) 2016. All rights reserved.
 */

package com.meteorhead.leave.database.realm.base

import com.meteorhead.leave.database.dbabstract.base.DatabaseCallback
import com.meteorhead.leave.database.realm.base.interfaces.RealmCallback

import io.realm.Realm
import io.realm.RealmObject
import io.realm.exceptions.RealmException
import java.util.Arrays

/**
 * Created by Grzegorz Wierzchanowski on 2016-09-16.
 * MeteorHead - All rights reserved.
 */

abstract class RealmService<E : RealmObject>(clazz: Class<E>, realm: Realm?) {

    val dataObjectClass: Class<E> = clazz
    val serviceRealm: Realm = realm?:mainThreadRealmInstance

    protected fun convertCallback(callback: DatabaseCallback): RealmCallback {
        if (callback is RealmCallback) {
            return callback
        } else {
            throw IllegalArgumentException("Every RealmService should use RealmCallback")
        }
    }

    protected fun getNextPrimaryKey(realm: Realm): Any {
        val max = realm.where(dataObjectClass).max(FIELD_ID)
        if (max != null) {
            return max.toInt() + 1
        } else {
            return 1
        }
    }

    fun close() {
        if (serviceRealm != mainThreadRealmInstance) {
            serviceRealm.close()
        }
    }

    fun fn(vararg fieldNames: String): String {
        var result: String = ""
        fieldNames.forEach { field ->
            result += field
            if (fieldNames.last() != field) {
                result += "."
            }
        }
        return result
    }

    companion object {
        val mainThreadRealmInstance = Realm.getDefaultInstance()
        var FIELD_ID = "id"
    }
}
