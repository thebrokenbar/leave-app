package com.meteorhead.leave.database.realm;

import com.meteorhead.leave.database.dbabstract.HolidaysDbService;
import com.meteorhead.leave.database.realm.base.RealmService;
import com.meteorhead.leave.models.Holiday;
import com.meteorhead.leave.models.HolidayFields;
import com.meteorhead.leave.models.SupportedCountry;
import com.meteorhead.leave.remoteDatabase.firebase.holidays.model.HolidaysList;

import org.joda.time.LocalDate;

import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by wierzchanowskig on 15.10.2016.
 */

public class HolidaysRealmService extends RealmService<Holiday> implements HolidaysDbService{

    public HolidaysRealmService() {
        super(Holiday.class, getMainThreadRealmInstance());
    }

    public HolidaysRealmService(Realm realm) {
        super(Holiday.class, realm);
    }

    @Override
    public void insertHolidays(final String countryCode, final HolidaysList holidaysList) {
        getServiceRealm().executeTransaction(inRealm -> {
            for (HolidaysList.Holiday holiday :
                    holidaysList.getFreeDays()) {
                inRealm.insertOrUpdate(new Holiday(
                        new SupportedCountry(countryCode),
                        new LocalDate().withDayOfYear(holiday.dayOfYear).toDate()));
            }
        });
    }

    @Override
    public RealmResults<Holiday> getHolidays(final String countryCode) {
        return getServiceRealm().where(Holiday.class)
                .equalTo(HolidayFields.COUNTRY.COUNTRY_CODE, countryCode)
                .findAll();
    }

    @Override
    public List<Holiday> getHolidaysBetweenDates(String countryCode, Date from, Date to) {
        return getServiceRealm().where(Holiday.class)
                .equalTo(HolidayFields.COUNTRY.COUNTRY_CODE, countryCode)
                .between(HolidayFields.HOLIDAY_DATE, from, to)
                .findAll();
    }

    @Override
    public void finish() {
        close();
    }
}
