package com.meteorhead.leave.screens.bestleaves.list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.meteorhead.leave.data.Leave
import com.meteorhead.leave.databinding.BestLeaveItemBinding

/**
 * Created by wierzchanowskig on 01.03.2017.
 */
class LeaveListAdapter(var data: List<Leave>) : RecyclerView.Adapter<LeaveViewHolder>() {
    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: LeaveViewHolder?, position: Int) {
        holder?.bind(data[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeaveViewHolder {
        val binding = BestLeaveItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.viewModel = LeaveListItemViewModel()
        return LeaveViewHolder(binding)
    }
}