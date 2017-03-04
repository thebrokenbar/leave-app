package com.meteorhead.leave.database.realm

import com.meteorhead.leave.database.dbabstract.LeaveDbService
import com.meteorhead.leave.database.dbabstract.base.DatabaseCallback
import com.meteorhead.leave.database.realm.base.RealmService
import com.meteorhead.leave.database.realm.base.interfaces.RealmCallback
import com.meteorhead.leave.data.Leave
import io.realm.Realm
import io.realm.RealmResults
import io.realm.Sort
import java.sql.Date

/**
 * Created by Lenovo on 2016-09-16.
 */

class LeaveRealmService : RealmService<Leave>, LeaveDbService {

    constructor() : super(Leave::class.java, null)

    constructor(realm: Realm) : super(Leave::class.java, realm)

    override fun findLeaveBetweenDates(startDate: Date, endDate: Date): RealmResults<Leave> {
        return serviceRealm.where(Leave::class.java)
            .between(Leave::dateStart.name, startDate, endDate)
            .between(Leave::dateEnd.name, startDate, endDate)
            .findAll()
    }

    override fun getAllLeaves(): RealmResults<Leave> {
        return serviceRealm.where(Leave::class.java)
            .findAllSorted(Leave::dateStart.name, Sort.ASCENDING)
    }

    override fun getAllLeavesFuture(): RealmResults<Leave> {
        return serviceRealm.where(Leave::class.java)
            .findAllSortedAsync(Leave::dateStart.name, Sort.ASCENDING)
    }

    override fun getLeaveById(id: Int): Leave {
        return serviceRealm.where(Leave::class.java).equalTo(Leave::id.name, id).findFirst()
    }

    @Throws(IllegalArgumentException::class)
    override fun addOrUpdate(leaveToAdd: Leave, callback: DatabaseCallback) {
        val realmCallback = convertCallback(callback)

        var id = leaveToAdd.id
        if (leaveToAdd.id == -1) {
            id = getNextPrimaryKey(serviceRealm) as Int
        }
        val primaryKey = id
        serviceRealm.executeTransactionAsync(Realm.Transaction { realm ->
            leaveToAdd.id = primaryKey
            realm.insertOrUpdate(leaveToAdd)
        }, realmCallback, realmCallback)
    }

    override fun insertLeave(leaveToInsert: Leave, callback: DatabaseCallback) {
        val realmCallback = convertCallback(callback)
        serviceRealm.executeTransactionAsync(
            Realm.Transaction { realm ->
                realm.copyToRealm(leaveToInsert)
            }, realmCallback, realmCallback)
    }

    override fun insertLeaves(leavesToInsert: List<Leave>) {
        serviceRealm.executeTransaction { realm -> realm.insert(leavesToInsert) }
    }

    override fun removeLeave(leaveToRemove: Leave) {
        serviceRealm.executeTransaction { realm -> leaveToRemove.deleteFromRealm() }
    }

    override fun removeLeaves(leavesToRemove: List<Leave>) {
        serviceRealm.executeTransaction { realm ->
            for (leave in leavesToRemove) {
                leave.deleteFromRealm()
            }
        }
    }

    override fun finish() {
        close()
    }

    override fun copy(leave: Leave): Leave {
        return serviceRealm.copyFromRealm(leave)
    }

    override fun copy(leaves: List<Leave>): List<Leave> {
        return serviceRealm.copyFromRealm(leaves)
    }

}
