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
import com.meteorhead.leave.remoteDatabase.firebase.holidays.model.HolidaysList;
import com.meteorhead.leave.rest.EnricoHolidayService;
import com.meteorhead.leave.rest.objects.EnricoHoliday;
import io.realm.Realm;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.joda.time.LocalDate;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.meteorhead.leave.Constants.AVAILABLE_YEARS_COUNT;

public class SplashScreenActivity extends AppCompatActivity {

    private void startMainActivity() {
        startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash_screen);

        View mContentView = findViewById(R.id.splashContent);

        mContentView.setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        loadHolidaysFromEnrico();
    }

    private void loadHolidaysFromEnrico() {
        HolidaysDbService holidaysDbService = new HolidaysRealmService();
        Date requiredStartDate =
            new LocalDate(LocalDate.now().year().get() + AVAILABLE_YEARS_COUNT, 1, 1).toDate();
        Date requiredEndDate =
            new LocalDate(LocalDate.now().year().get() + AVAILABLE_YEARS_COUNT, 12, 31).toDate();

        String iso3Country = Locale.getDefault().getISO3Country();
        if (holidaysDbService.getHolidaysBetweenDates(iso3Country, requiredStartDate,
            requiredEndDate).isEmpty()) {
            holidaysDbService.finish();

            HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
            logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(logInterceptor).build();
            Retrofit retrofit =
                new Retrofit.Builder().addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .baseUrl("http://kayaposoft.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();

            EnricoHolidayService service = retrofit.create(EnricoHolidayService.class);
            List<Observable<List<EnricoHoliday>>> requests = new ArrayList<>(AVAILABLE_YEARS_COUNT);
            for (int i = 0; i < AVAILABLE_YEARS_COUNT; i++) {
                requests.add(
                    service.getHolidayForYear(LocalDate.now().year().get() + i, iso3Country));
            }

            Observable.zip(requests, args -> {
                List<EnricoHoliday> list = new ArrayList<>();
                for (Object holidaysList : args) {
                    list.addAll((List<EnricoHoliday>) holidaysList);
                }
                return list;
            })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(enricoHolidays -> {
                    HolidaysList holidaysList =
                        new HolidaysList(new ArrayList<>(enricoHolidays.size()));
                    for (EnricoHoliday enricoHoliday : enricoHolidays) {
                        LocalDate localDate = new LocalDate(enricoHoliday.getDate().getYear(),
                            enricoHoliday.getDate().getMonth(), enricoHoliday.getDate().getDay());
                        holidaysList.getFreeDays()
                            .add(new HolidaysList.Holiday(localDate.getDayOfYear(), 1));
                    }
                    return holidaysList;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .doOnNext(holidaysList -> {
                    HolidaysRealmService threadHolidaysDbService =
                        new HolidaysRealmService(Realm.getDefaultInstance());
                    threadHolidaysDbService.insertHolidays(iso3Country, holidaysList);
                    threadHolidaysDbService.finish();
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(any -> startMainActivity(), Throwable::printStackTrace);
        } else {
            startMainActivity();
        }
    }
}
