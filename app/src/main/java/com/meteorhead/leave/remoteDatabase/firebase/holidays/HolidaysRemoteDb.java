package com.meteorhead.leave.remoteDatabase.firebase.holidays;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.meteorhead.leave.remoteDatabase.firebase.base.RemoteDb;
import com.meteorhead.leave.remoteDatabase.firebase.holidays.model.HolidaysList;

import rx.Observable;
import rx.subjects.PublishSubject;


/**
 * Created by wierzchanowskig on 14.10.2016.
 */

public class HolidaysRemoteDb extends RemoteDb {
    @Override
    protected String getDbNodeName() {
        return "holidays";
    }

    public Observable<HolidaysList> getFreeDaysObservable() {
        final PublishSubject<HolidaysList> subject = PublishSubject.create();
        final Query query = getReference().orderByKey().equalTo("pl-poland");
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                subject.onNext(dataSnapshot.getValue(HolidaysList.class));
                query.removeEventListener(this);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                //not used
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                //not used
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                //not used
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                subject.onError(databaseError.toException());
                query.removeEventListener(this);
            }
        });
        return subject.asObservable();
    }
}
