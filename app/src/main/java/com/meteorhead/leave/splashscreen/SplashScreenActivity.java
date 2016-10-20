package com.meteorhead.leave.splashscreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.meteorhead.leave.R;
import com.meteorhead.leave.database.dbabstract.HolidaysDbService;
import com.meteorhead.leave.database.realm.HolidaysRealmService;
import com.meteorhead.leave.mainactivity.MainActivity;
import com.meteorhead.leave.remoteDatabase.firebase.holidays.HolidaysRemoteDb;
import com.meteorhead.leave.remoteDatabase.firebase.holidays.model.HolidaysList;

import java.util.Locale;

import io.realm.Realm;
import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

public class SplashScreenActivity extends AppCompatActivity {

    PublishSubject<Boolean> holidaysDownloadSubject = PublishSubject.create();

    private Action1<Boolean> startMainActivityAction = new Action1<Boolean>() {
        @Override
        public void call(Boolean aBoolean) {
            if(aBoolean) {
                startMainActivity();
            }
        }
    };

    private void startMainActivity() {
        startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash_screen);

        View mContentView = findViewById(R.id.splashContent);

        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);

        holidaysDownloadSubject.subscribe(startMainActivityAction);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        HolidaysDbService holidaysDbService = new HolidaysRealmService();
        if(holidaysDbService.getHolidays(Locale.getDefault().getLanguage()).isEmpty()) {
            HolidaysRemoteDb holidaysRemoteDb = new HolidaysRemoteDb();
            Observable<HolidaysList> holidaysDownloadObservable = holidaysRemoteDb.getFreeDaysObservable();
            holidaysDownloadObservable.subscribe(new Action1<HolidaysList>() {
                @Override
                public void call(final HolidaysList holidaysList) {
                    Schedulers.io().createWorker().schedule(new Action0() {
                        @Override
                        public void call() {
                            HolidaysDbService holidaysDbService = new HolidaysRealmService(Realm.getDefaultInstance());
                            holidaysDbService.insertHolidays(Locale.getDefault().getLanguage(), holidaysList);
                            holidaysDownloadSubject.onNext(true);
                            holidaysDbService.finish();
                        }
                    });
                }
            });
        } else {
            holidaysDownloadSubject.onNext(true);
        }
    }
}
