package com.meteorhead.leave.screens.seasons

import android.os.Bundle
import com.meteorhead.leave.R
import com.meteorhead.leave.base.BaseView
import com.meteorhead.leave.base.Layout
import com.meteorhead.leave.data.DaysRange
import com.meteorhead.leave.screens.bestleaves.BestLeavesView
import com.meteorhead.leave.databinding.SeasonsViewBinding
import org.parceler.Parcels
import java.util.*

/**
 * Created by wierzchanowskig on 18.02.2017.
 */
@Layout(R.layout.seasons_view)
class SeasonsView : BaseView<SeasonsViewBinding>(), SeasonsViewActions {
    val viewModel = SeasonsViewModel(this)

    override fun onViewBound(binding: SeasonsViewBinding) {
        binding.viewModel = this.viewModel
    }

    override fun showLeavePropositions(daysRangeList: List<DaysRange>) {
        val bundle = Bundle()
        val daysRangeArrayList = ArrayList<DaysRange>(daysRangeList)
        bundle.putParcelable(BestLeavesView.SELECTED_DAYS_RANGE_PARAM,
                Parcels.wrap(daysRangeArrayList))
        this.showView(BestLeavesView(bundle))
    }
}
