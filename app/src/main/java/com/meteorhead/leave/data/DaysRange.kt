package com.meteorhead.leave.data

import org.parceler.Parcel
import org.parceler.ParcelConstructor

/**
 * Created by wierzchanowskig on 28.02.2017.
 */
@Parcel(Parcel.Serialization.BEAN)
data class DaysRange @ParcelConstructor constructor(var dayFrom: Day, var dayTo: Day)