package com.meteorhead.leave.rest;

import com.meteorhead.leave.rest.objects.EnricoHoliday;
import java.util.List;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by wierzchanowskig on 18.02.2017.
 */

public interface EnricoHolidayService {
    @GET("enrico/json/v1.0/?action=getPublicHolidaysForYear")
    public Observable<List<EnricoHoliday>> getHolidayForYear(@Query("year") int year,
        @Query("country") String country);
}
