package com.meteorhead.leave.remoteDatabase.firebase.holidays.model

/**
 * Created by wierzchanowskig on 15.10.2016.
 */

data class HolidaysList(var freeDays: MutableList<Holiday>? = null) {
    data class Holiday(var dayOfYear: Int, var year: Int)
}
