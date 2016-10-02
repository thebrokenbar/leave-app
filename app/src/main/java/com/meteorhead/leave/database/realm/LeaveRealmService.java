package com.meteorhead.leave.database.realm;

import com.meteorhead.leave.database.dbabstract.base.IDatabaseCallback;
import com.meteorhead.leave.database.dbabstract.LeaveDbService;
import com.meteorhead.leave.database.realm.base.RealmService;
import com.meteorhead.leave.database.realm.base.interfaces.IRealmCallback;
import com.meteorhead.leave.models.Leave;

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
    public void addOrUpdate(final Leave leaveToAdd, IDatabaseCallback callback) throws IllegalArgumentException{
        final Leave found = getServiceRealm().where(Leave.class).equalTo(Leave.FIELD_ID, leaveToAdd.getId()).findFirst();
        if(found != null) {
            try {
                getServiceRealm().executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        found.set(leaveToAdd);
                    }
                });
                callback.onSuccess();
            } catch (Exception e) {
                callback.onError(e);
            }
        } else {
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

    @Override
    public void insertLeave(final Leave leaveToInsert) {
        getServiceRealm().executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(leaveToInsert);
            }
        });
    }

    @Override
    public void removeLeave(final Leave leaveToRemove) {
        getServiceRealm().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                leaveToRemove.deleteFromRealm();
            }
        });
    }

}
