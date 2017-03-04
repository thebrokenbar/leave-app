package com.meteorhead.leave.rest

import com.meteorhead.leave.rest.objects.EnricoHoliday
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

/**
 * Created by wierzchanowskig on 18.02.2017.
 */

interface EnricoHolidayService {
    @GET("enrico/json/v1.0/?action=getPublicHolidaysForYear")
    fun getHolidayForYear(@Query("year") year: Int,
                          @Query("country") country: String): Observable<List<EnricoHoliday>>
}
