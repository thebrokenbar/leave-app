package com.meteorhead.leave.binding.adapters

import android.databinding.BindingAdapter
import android.support.annotation.LayoutRes
import android.widget.ArrayAdapter
import android.widget.ListView
import com.meteorhead.leave.data.Leave

/**
 * Created by wierzchanowskig on 19.02.2017.
 */

@BindingAdapter(value = *arrayOf("items", "itemLayout"), requireAll = false)
fun bindLeaveList(listView: ListView, leaveList: List<Leave>?,
             @LayoutRes itemLayoutId: Int) {
    var itemLayoutId = itemLayoutId
    if (leaveList == null) {
        return
    }
    if (itemLayoutId == 0) {
        itemLayoutId = android.R.layout.simple_list_item_1
    }
    listView.adapter = ArrayAdapter(listView.context, itemLayoutId,
                                    android.R.id.text1, leaveList)
}

