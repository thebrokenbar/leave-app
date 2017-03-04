package com.meteorhead.leave.data

import java.util.Date

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.Index

/**
 * Created by wierzchanowskig on 25.09.2016.
 */

open class Holiday(var country: SupportedCountry?,
                   var region: String?,
                   @Index var holidayDate: Date)
    : RealmObject() {
    constructor(): this(null, "", Date(0))
}