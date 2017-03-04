package com.meteorhead.leave.data

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import org.parceler.Parcel
import org.parceler.Parcel.Serialization.BEAN
import org.parceler.ParcelConstructor
import org.parceler.ParcelProperty
import java.util.Date

/**
 * Created by Lenovo on 2016-09-13.
 */

open class Leave
(@PrimaryKey var id: Int, var dateStart: Date,
                               var dateEnd: Date,
                               var duration: Int) : RealmObject() {
    companion object {
        val PARAM_NAME = "LEAVE_OBJECT"
    }

    constructor()
        : this(0, Date(0), Date(0), 0)

    constructor(dateStart: Date, dateEnd: Date, duration: Int)
        : this(0, dateStart, dateEnd, duration)

    constructor(leave: Leave)
        : this(leave.id, leave.dateStart, leave.dateEnd, leave.duration)

    fun set(other: Leave) {
        this.dateStart = other.dateStart
        this.dateEnd = other.dateEnd
        this.duration = other.duration
    }
}