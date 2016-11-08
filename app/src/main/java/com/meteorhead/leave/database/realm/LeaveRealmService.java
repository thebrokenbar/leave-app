package com.meteorhead.leave.database.realm;

import com.meteorhead.leave.database.dbabstract.LeaveDbService;
import com.meteorhead.leave.database.dbabstract.base.DatabaseCallback;
import com.meteorhead.leave.database.dbabstract.base.DatabaseCallbackQuery;
import com.meteorhead.leave.database.realm.base.RealmService;
import com.meteorhead.leave.database.realm.base.interfaces.RealmCallback;
import com.meteorhead.leave.models.Leave;
import com.meteorhead.leave.models.LeaveFields;

import java.sql.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.realm.Sort;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by Lenovo on 2016-09-16.
 */

public class LeaveRealmService extends RealmService<Leave> implements LeaveDbService {

    public LeaveRealmService() {
        super(Leave.class, getMainThreadRealmInstance());
    }

    public LeaveRealmService(Realm realm) {
        super(Leave.class, realm);
    }

    public RealmResults<Leave> findLeaveBetweenDates(final Date startDate, final Date endDate) {
        return getServiceRealm().where(Leave.class)
                .between(LeaveFields.DATE_START, startDate, endDate)
                .between(LeaveFields.DATE_END, startDate, endDate)
                .findAll();
    }

    @Override
    public RealmResults<Leave> getAllLeaves() {
        return getServiceRealm().where(Leave.class)
                .findAllSorted(LeaveFields.DATE_START, Sort.ASCENDING);
    }

    @Override
    public RealmResults<Leave> getAllLeavesFuture() {
        return getServiceRealm().where(Leave.class)
                .findAllSortedAsync(LeaveFields.DATE_START, Sort.ASCENDING);
    }

    @Override
    public Leave getLeaveById(int id) {
        return getServiceRealm().where(Leave.class).equalTo(LeaveFields.ID, id).findFirst();
    }

    @Override
    public void addOrUpdate(final Leave leaveToAdd, DatabaseCallback callback) throws IllegalArgumentException{
        final Leave found = getServiceRealm().where(Leave.class).equalTo(LeaveFields.ID, leaveToAdd.getId()).findFirst();
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
            RealmCallback realmCallback = convertCallback(callback);
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
    public void insertLeave(final Leave leaveToInsert, DatabaseCallback callback) {
        RealmCallback realmCallback = convertCallback(callback);
        getServiceRealm().executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(leaveToInsert);
            }
        }, realmCallback, realmCallback);
    }

    @Override
    public void insertLeaves(final List<Leave> leavesToInsert, DatabaseCallback callback) {
        RealmCallback realmCallback = convertCallback(callback);
        getServiceRealm().executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(leavesToInsert);
            }
        }, realmCallback, realmCallback);
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

    @Override
    public void removeLeaves(final List<Leave> leavesToRemove) {
        getServiceRealm().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                for (Leave leave : leavesToRemove) {
                    leave.deleteFromRealm();
                }
            }
        });
    }

    @Override
    public void finish() {
        close();
    }

}
