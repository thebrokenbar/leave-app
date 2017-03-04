package com.meteorhead.leave.binding.adapters

import android.R.id
import android.databinding.BindingAdapter
import android.databinding.InverseBindingAdapter
import android.databinding.InverseBindingListener
import android.support.annotation.LayoutRes
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.meteorhead.leave.R

/**
 * Created by wierzchanowskig on 22.02.2017.
 */

@BindingAdapter(value = *arrayOf("items", "itemLayout"), requireAll = false)
fun bindYears(spinner: Spinner, availableYears: List<Int>,
              @LayoutRes itemLayoutId: Int) {
    var _itemLayoutId = itemLayoutId
    if (_itemLayoutId == 0) {
        _itemLayoutId = R.layout.support_simple_spinner_dropdown_item
    }
    spinner.adapter = ArrayAdapter(spinner.context, _itemLayoutId, id.text1,
                                   availableYears)
}

@BindingAdapter(value = *arrayOf("selectedValue", "selectedValueAttrChanged"), requireAll = false)
fun bindSpinnerData(pAppCompatSpinner: Spinner, newSelectedValue: Int,
                    newTextAttrChanged: InverseBindingListener) {
    pAppCompatSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int,
                                    id: Long) {
            newTextAttrChanged.onChange()
        }

        override fun onNothingSelected(parent: AdapterView<*>) {}
    }

    val pos = (pAppCompatSpinner.adapter as ArrayAdapter<Int>).getPosition(newSelectedValue)
    pAppCompatSpinner.setSelection(pos, true)
}

@InverseBindingAdapter(attribute = "selectedValue", event = "selectedValueAttrChanged")
fun captureSelectedValue(appCompatSpinner: Spinner): Int {
    return appCompatSpinner.selectedItem as Int
}
