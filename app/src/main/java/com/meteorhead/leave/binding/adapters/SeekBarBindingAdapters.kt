package com.meteorhead.leave.binding.adapters

import android.databinding.BindingAdapter
import android.databinding.InverseBindingListener
import android.support.annotation.LayoutRes
import android.widget.SeekBar

/**
 * Created by wierzchanowskig on 23.02.2017.
 */

interface OnProgressChanged {
    fun onProgressChanged(progress: Int, fromUser: Boolean): Int
}
//
//@BindingAdapter("onProgressChanged", "progressValueAttrChanged")
//fun bindOnProgressChanged(seekBar: SeekBar, listener: OnProgressChanged, inverseBindingListener: InverseBindingListener) {
//    seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
//        override fun onProgressChanged(p0: SeekBar?, progress: Int, fromUser: Boolean) {
//            if(fromUser) {
//                seekBar.progress = listener.onProgressChanged(progress, fromUser)
//                inverseBindingListener.onChange()
//            }
//        }
//
//        override fun onStartTrackingTouch(p0: SeekBar?) {
//
//        }
//
//        override fun onStopTrackingTouch(p0: SeekBar?) {
//
//        }
//    })
//}
//
