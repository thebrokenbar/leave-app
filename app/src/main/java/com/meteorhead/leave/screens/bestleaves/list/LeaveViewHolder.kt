package com.meteorhead.leave.screens.bestleaves.list

import android.support.v7.widget.RecyclerView
import com.meteorhead.leave.data.Leave
import com.meteorhead.leave.databinding.BestLeaveItemBinding

/**
 * Created by wierzchanowskig on 01.03.2017.
 */
class LeaveViewHolder(var binding: BestLeaveItemBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(leave: Leave) {
        binding.viewModel.leave = leave
    }
}