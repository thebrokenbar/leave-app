package com.meteorhead.leave.splashscreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.meteorhead.leave.R;
import com.meteorhead.leave.database.dbabstract.HolidaysDbService;
import com.meteorhead.leave.database.realm.HolidaysRealmService;
import com.meteorhead.leave.remoteDatabase.firebase.holidays.HolidaysRemoteDb;
import com.meteorhead.leave.remoteDatabase.firebase.holidays.model.HolidaysList;

import java.util.Locale;

import io.realm.Realm;
import rx.Observable;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

public class SplashScreenActivity extends AppCompatActivity {

    PublishSubject<Boolean> holidaysDownloadSubject = PublishSubject.create();

    private Action1<Boolean> startMainActivityAction = aBoolean -> {
        if(aBoolean) {
            startMainActivity();
        }
    };

    private void startMainActivity() {
        startActivity(new Intent(SplashScreenActivity.this,
                com.meteorhead.leave.mainactivity.conductor.MainActivity.class));
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
            holidaysDownloadObservable.subscribe(holidaysList -> {
                Schedulers.io().createWorker().schedule(() -> {
                    HolidaysDbService holidaysDbService1 = new HolidaysRealmService(Realm.getDefaultInstance());
                    holidaysDbService1.insertHolidays(Locale.getDefault().getLanguage(), holidaysList);
                    holidaysDownloadSubject.onNext(true);
                    holidaysDbService1.finish();
                });
            });
        } else {
            holidaysDownloadSubject.onNext(true);
        }
    }
}
