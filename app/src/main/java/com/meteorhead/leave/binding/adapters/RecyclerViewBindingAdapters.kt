package com.meteorhead.leave.binding.adapters

import android.databinding.BindingAdapter
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.meteorhead.leave.data.Leave
import com.meteorhead.leave.screens.bestleaves.list.LeaveListAdapter

/**
 * Created by wierzchanowskig on 01.03.2017.
 */

@BindingAdapter("items")
fun bindLeaveItems(view: RecyclerView, leaveList: List<Leave>) {
    if (view.layoutManager == null) {
        view.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
    }
    view.adapter = LeaveListAdapter(leaveList)
    view.adapter.notifyDataSetChanged()
}