package com.meterohead.leave.database.realm;

import com.meterohead.leave.database.dbabstract.base.IDatabaseCallback;
import com.meterohead.leave.database.dbabstract.LeaveDbService;
import com.meterohead.leave.database.realm.base.RealmService;
import com.meterohead.leave.database.realm.base.interfaces.IRealmCallback;
import com.meterohead.leave.models.Leave;

import java.sql.Date;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Lenovo on 2016-09-16.
 */

public class LeaveRealmService extends RealmService<Leave> implements LeaveDbService {

    public LeaveRealmService(Realm realm) {
        super(Leave.class, realm);
    }

    public RealmResults<Leave> findLeaveBetweenDates(final Date startDate, final Date endDate) {
        return getServiceRealm().where(Leave.class)
                .between(Leave.FIELD_DATE_START, startDate, endDate)
                .between(Leave.FIELD_DATE_END, startDate, endDate)
                .findAll();
    }

    @Override
    public RealmResults<Leave> getAllLeaves() {
        return getServiceRealm().where(Leave.class)
                .findAll();
    }

    @Override
    public void addNewLeave(final Leave leaveToAdd, IDatabaseCallback callback) throws IllegalArgumentException{
        IRealmCallback realmCallback = convertCallback(callback);
        getServiceRealm().executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                leaveToAdd.setId((Integer) getNextPrimaryKey(realm));
                realm.copyToRealm(leaveToAdd);
            }
        }, realmCallback, realmCallback);
    }

}
