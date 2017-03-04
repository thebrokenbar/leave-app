package com.meteorhead.leave.binding.adapters

import android.databinding.BindingAdapter
import android.databinding.InverseBindingAdapter
import android.widget.EditText

/**
 * Created by wierzchanowskig on 19.02.2017.
 */

@BindingAdapter("android:text")
fun setText(view: EditText, value: Int) {

    val text = view.text.toString()
    var number = -9999999
    if (!text.isEmpty()) {
        number = Integer.parseInt(text)
    }
    if (value != number && value > 0) {
        view.setText(Integer.toString(value))
    }
}

@InverseBindingAdapter(attribute = "android:text")
fun getText(view: EditText): Int {
    val text = view.text.toString()
    if (!text.isEmpty()) {
        return Integer.parseInt(view.text.toString())
    }
    return 0
}
