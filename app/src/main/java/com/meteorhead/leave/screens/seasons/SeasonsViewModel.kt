package com.meteorhead.leave.screens.seasons

import android.databinding.Bindable
import android.view.View
import com.meteorhead.leave.AVAILABLE_YEARS_COUNT
import com.meteorhead.leave.BR
import com.meteorhead.leave.base.ViewModel
import com.meteorhead.leave.data.Day
import com.meteorhead.leave.data.DaysRange
import org.joda.time.LocalDate

import com.meteorhead.leave.screens.seasons.SeasonsViewModel.Seasons.SPRING
import java.util.*

/**
 * Created by wierzchanowskig on 18.02.2017.
 */

class SeasonsViewModel(private val viewActions: SeasonsViewActions) : ViewModel() {
    enum class Seasons {
        SPRING, SUMMER, AUTUMN, WINTER
    }

    @get:Bindable
    var selectedYear: Int = 0
        set(selectedYear) {
            field = selectedYear
            notifyPropertyChanged(BR.selectedYear)
        }

    @get:Bindable
    var availableYears: List<Int> = arrayListOf()
        get() {
            val years = ArrayList<Int>()
            val date = LocalDate.now()
            var year = date.year
            while (years.size < AVAILABLE_YEARS_COUNT()) {
                years.add(year++)
            }
            return years
        }
        set(value) {
            field = value
            notifyPropertyChanged(BR.availableYears)
        }

    fun onSpringClick() {
        viewActions.showLeavePropositions(listOf(
                DaysRange(
                        Day(year = selectedYear, month = 3, dayOfMonth = 1),
                        Day(year = selectedYear, month = 6, dayOfMonth = 30))
        ))
    }

    fun onSummerClick() {
        viewActions.showLeavePropositions(listOf(
                DaysRange(
                        Day(year = selectedYear, month = 6, dayOfMonth = 1),
                        Day(year = selectedYear, month = 9, dayOfMonth = 30))
        ))
    }

    fun onAutumnClick() {
        viewActions.showLeavePropositions(listOf(
                DaysRange(
                        Day(year = selectedYear, month = 9, dayOfMonth = 1),
                        Day(year = selectedYear, month = 12, dayOfMonth = 30))
        ))
    }

    fun onWinterClick() {
        viewActions.showLeavePropositions(listOf(
                DaysRange(
                        Day(year = selectedYear, month = 1, dayOfMonth = 1),
                        Day(year = selectedYear, month = 3, dayOfMonth = 30)),
                DaysRange(
                        Day(year = selectedYear, month = 12, dayOfMonth = 1),
                        Day(year = selectedYear + 1, month = 3, dayOfMonth = 30))
        ))
    }
}
