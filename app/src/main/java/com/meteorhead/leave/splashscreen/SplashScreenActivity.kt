package com.meteorhead.leave.splashscreen

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.meteorhead.leave.AVAILABLE_YEARS_COUNT
import com.meteorhead.leave.R
import com.meteorhead.leave.database.realm.HolidaysRealmService
import com.meteorhead.leave.mainactivity.MainActivity
import com.meteorhead.leave.remoteDatabase.firebase.holidays.model.HolidaysList
import com.meteorhead.leave.remoteDatabase.firebase.holidays.model.HolidaysList.Holiday
import com.meteorhead.leave.rest.EnricoHolidayService
import com.meteorhead.leave.rest.objects.EnricoHoliday
import io.realm.Realm
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.joda.time.LocalDate
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Action1
import rx.schedulers.Schedulers
import java.util.ArrayList
import java.util.Locale

class SplashScreenActivity : AppCompatActivity() {

    private fun startMainActivity() {
        startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash_screen)

        val mContentView = findViewById(R.id.splashContent)

        mContentView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        loadHolidaysFromEnrico()
    }

    private fun loadHolidaysFromEnrico() {
        val holidaysDbService = HolidaysRealmService()
        val requiredStartDate = LocalDate(LocalDate.now().year().get() + AVAILABLE_YEARS_COUNT(), 1,
                                          1).toDate()
        val requiredEndDate = LocalDate(LocalDate.now().year().get() + AVAILABLE_YEARS_COUNT(), 12,
                                        31).toDate()
        val iso3Country = Locale.getDefault().isO3Country
        if (holidaysDbService.getHolidaysBetweenDates(iso3Country, requiredStartDate,
                                                      requiredEndDate).isEmpty()) {
            val logInterceptor = HttpLoggingInterceptor()
            logInterceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder().addInterceptor(logInterceptor).build()
            val retrofit = Retrofit.Builder().addCallAdapterFactory(
                RxJavaCallAdapterFactory.create())
                .baseUrl("http://kayaposoft.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            val service = retrofit.create(EnricoHolidayService::class.java)
            val requests = ArrayList<Observable<List<EnricoHoliday>>>(AVAILABLE_YEARS_COUNT())
            (0..AVAILABLE_YEARS_COUNT() - 1).mapTo(requests) {
                Log.e(":", it.toString())
                service.getHolidayForYear(LocalDate.now().year().get() + it, iso3Country)
            }

            Observable.zip<List<EnricoHoliday>>(requests) { zipEnricoResponses(it) }
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map({ createHolidayListFromEnricoResponse(it) })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .doOnNext({ saveHolidaysToDb(it, iso3Country) })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ startMainActivity() }, { it.printStackTrace() })
        } else {
            startMainActivity()
        }
        holidaysDbService.finish()
    }

    private fun zipEnricoResponses(
        args: Array<Any>): ArrayList<EnricoHoliday> {
        val list = ArrayList<EnricoHoliday>()
        for (holidaysList in args) {
            list.addAll(holidaysList as List<EnricoHoliday>)
        }
        return list
    }

    private fun saveHolidaysToDb(
        holidaysList: HolidaysList,
        iso3Country: String) {
        val threadHolidaysDbService = HolidaysRealmService(
            Realm.getDefaultInstance())
        threadHolidaysDbService.insertHolidays(iso3Country, holidaysList)
        threadHolidaysDbService.finish()
    }

    private fun createHolidayListFromEnricoResponse(
        enricoHolidays: List<EnricoHoliday>): HolidaysList {
        val holidaysList = HolidaysList(ArrayList<Holiday>(enricoHolidays.size))
        for ((date) in enricoHolidays) {
            val localDate = LocalDate(date!!.year,
                                      date.month,
                                      date.day)
            holidaysList.freeDays?.add(Holiday(localDate.dayOfYear, localDate.year))
        }
        return holidaysList
    }
}
