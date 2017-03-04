package com.meteorhead.leave.database.realm

import com.meteorhead.leave.database.dbabstract.HolidaysDbService
import com.meteorhead.leave.database.realm.base.RealmService
import com.meteorhead.leave.data.Holiday
import com.meteorhead.leave.data.SupportedCountry
import com.meteorhead.leave.remoteDatabase.firebase.holidays.model.HolidaysList
import io.realm.Realm
import io.realm.RealmResults
import org.joda.time.LocalDate
import java.util.Date

/**
 * Created by wierzchanowskig on 15.10.2016.
 */

class HolidaysRealmService : RealmService<Holiday>, HolidaysDbService {

    constructor() : super(Holiday::class.java, RealmService.mainThreadRealmInstance)

    constructor(realm: Realm) : super(Holiday::class.java, realm)

    override fun insertHolidays(countryCode: String, holidaysList: HolidaysList) {
        serviceRealm.executeTransaction { inRealm ->
            holidaysList.freeDays?.forEach {
                inRealm.insertOrUpdate(Holiday(
                    SupportedCountry(countryCode), null,
                    LocalDate().withYear(it.year).withDayOfYear(it.dayOfYear).toDate()))
            }
        }
    }

    override fun getHolidays(countryCode: String): RealmResults<Holiday> {
        return serviceRealm.where(Holiday::class.java)
            .equalTo(fn(Holiday::country.name, SupportedCountry::countryCode.name), countryCode)
            .findAll()
    }

    override fun getHolidaysBetweenDates(countryCode: String, from: Date, to: Date): List<Holiday> {
        return serviceRealm.where(Holiday::class.java)
            .equalTo(fn(Holiday::country.name, SupportedCountry::countryCode.name), countryCode)
            .between(Holiday::holidayDate.name, from, to)
            .findAll()
    }

    override fun finish() {
        close()
    }
}
