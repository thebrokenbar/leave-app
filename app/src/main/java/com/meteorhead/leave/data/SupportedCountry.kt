package com.meteorhead.leave.data

import io.realm.RealmObject
import io.realm.annotations.Index
import io.realm.annotations.PrimaryKey

/**
 * Created by wierzchanowskig on 25.09.2016.
 */

open class SupportedCountry(@PrimaryKey var countryCode: String) : RealmObject() {
    constructor(): this("")
}
