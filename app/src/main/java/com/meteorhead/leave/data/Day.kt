package com.meteorhead.leave.data

import org.parceler.Parcel
import org.parceler.Parcel.Serialization
import org.parceler.ParcelConstructor

/**
 * Created by wierzchanowskig on 23.02.2017.
 */
@Parcel(Serialization.BEAN)
data class Day @ParcelConstructor constructor(var year: Int, var month: Int, var dayOfMonth: Int)