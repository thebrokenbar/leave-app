package com.meteorhead.leave.screens.bestleaves

import android.databinding.Bindable
import android.util.Log
import com.meteorhead.leave.BR
import com.meteorhead.leave.DEFAULT_DAYS_COUNT
import com.meteorhead.leave.MAX_LEAVES_VISIBLE
import com.meteorhead.leave.MAX_LEAVE_DAYS
import com.meteorhead.leave.base.ViewModel
import com.meteorhead.leave.data.Day
import com.meteorhead.leave.data.DaysRange
import com.meteorhead.leave.data.Leave
import com.meteorhead.leave.models.WorkingDays
import com.meteorhead.leave.models.impl.RealmWorkingDays
import org.joda.time.Days
import org.joda.time.LocalDate
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subjects.PublishSubject
import java.util.concurrent.TimeUnit.SECONDS
import kotlin.comparisons.compareBy

/**
 * Created by wierzchanowskig on 18.02.2017.
 */

class BestLeavesViewModel(private val workingDaysService: WorkingDays,
                          private val daysRange: List<DaysRange>) : ViewModel() {

    private val daysObservable = PublishSubject.create<Int>()

    init {
        daysObservable.debounce(1, SECONDS)
            .flatMap { days ->
                Observable.from(daysRange).flatMap { calculateLeaves(days, it) }
            }
            .buffer(daysRange.size)
            .map { mergeLeaveLists(it) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ this.leaves = it.sortedByDescending {
                Days.daysBetween(LocalDate.fromDateFields(it.dateStart),
                                 LocalDate.fromDateFields(it.dateEnd)).days
            } }, Throwable::printStackTrace)
    }

    private fun calculateLeaves(days: Int,
                                daysRange: DaysRange): Observable<List<Leave>>? {
        val workingDays = RealmWorkingDays(null, null)
        return Observable.just(workingDays.getPropositions(
            dayToLocalDate(daysRange.dayFrom), dayToLocalDate(daysRange.dayTo), days))
    }

    private fun mergeLeaveLists(
        listOfLeaveList: MutableList<List<Leave>>): MutableList<Leave> {
        val resultList: MutableList<Leave> = mutableListOf()
        listOfLeaveList.forEach { resultList.addAll(it) }
        Log.e(":", "3")
        return resultList
    }

    @get:Bindable
    var days: Int = DEFAULT_DAYS_COUNT()
        set(days) {
            var _days = days
            if (_days > MAX_LEAVE_DAYS()) {
                _days = MAX_LEAVE_DAYS()
            }
            field = _days
            notifyPropertyChanged(BR.days)
            daysObservable.onNext(field)
        }

    @get:Bindable
    var leaves: List<Leave> = emptyList()
        set(value) {
            field = value
            notifyPropertyChanged(BR.leaves)
        }

    val minLeaveDays = 1

    @get:Bindable
    val maxLeaveDays = MAX_LEAVE_DAYS()

    fun onSeekBarProgressChange(progress: Int, fromUser: Boolean): Int {
        return progress + minLeaveDays
    }

    private fun dayToLocalDate(day: Day): LocalDate = LocalDate(day.year, day.month, day.dayOfMonth)

}
