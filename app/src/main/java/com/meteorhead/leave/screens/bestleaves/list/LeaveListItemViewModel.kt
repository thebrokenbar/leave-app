package com.meteorhead.leave.screens.bestleaves.list

import android.databinding.Bindable
import com.meteorhead.leave.BR
import com.meteorhead.leave.base.ViewModel
import com.meteorhead.leave.data.Leave
import org.joda.time.Days
import org.joda.time.LocalDate
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * Created by wierzchanowskig on 28.02.2017.
 */
class LeaveListItemViewModel(leaveParam: Leave? = null) : ViewModel() {

    var leave = leaveParam
    set(value) {
        field = value
        leaveDaysRangeString = dateFormat.format(leave?.dateStart) + " - " + dateFormat.format(leave?.dateEnd)
        notifyPropertyChanged(BR.leaveDaysRangeString)
        leaveTotalDays = Days.daysBetween(LocalDate.fromDateFields(leave?.dateStart),
                         LocalDate.fromDateFields(leave?.dateEnd)).days + 1
        notifyPropertyChanged(BR.leaveTotalDays)
    }

    private val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())

    @get:Bindable
    var leaveDaysRangeString: String = ""

    @get:Bindable
    var leaveTotalDays: Int = 0
}