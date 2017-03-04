package com.meteorhead.leave.screens.bestleaves

import android.os.Bundle
import com.meteorhead.leave.R
import com.meteorhead.leave.application.Application
import com.meteorhead.leave.base.BaseView
import com.meteorhead.leave.base.Layout
import com.meteorhead.leave.data.DaysRange
import com.meteorhead.leave.databinding.BestLeavesViewBinding
import com.meteorhead.leave.mainactivity.MainActivity
import com.meteorhead.leave.models.WorkingDays
import org.parceler.Parcels
import javax.inject.Inject

/**
 * Created by wierzchanowskig on 18.02.2017.
 */
@Layout(R.layout.best_leaves_view)
class BestLeavesView(args: Bundle) : BaseView<BestLeavesViewBinding>(args) {

    private lateinit var viewModel: BestLeavesViewModel

    @Inject
    internal lateinit var workingDaysService: WorkingDays




    override fun onViewBound(binding: BestLeavesViewBinding) {
        (activity as MainActivity).activityComponent.plus().inject(this)
        val daysRange: MutableList<DaysRange> = Parcels.unwrap(args.getParcelable(SELECTED_DAYS_RANGE_PARAM))
        viewModel = BestLeavesViewModel(workingDaysService, daysRange)
        binding.viewModel = viewModel
    }

    companion object {
        val SELECTED_DAYS_RANGE_PARAM = "SELECTED_DAYS_RANGE_PARAM"
    }
}
