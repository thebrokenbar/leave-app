package com.meteorhead.leave.binding.adapters;

import android.databinding.BindingAdapter;
import android.databinding.InverseBindingListener;
import android.databinding.InverseBindingMethod;
import android.databinding.InverseBindingMethods;
import android.widget.SeekBar;

/**
 * Created by wierzchanowskig on 03.03.2017.
 */
@InverseBindingMethods({
    @InverseBindingMethod(type = SeekBar.class, attribute = "android:progress", event = "android:progressAttrChanged", method = "getProgress"),
})
public class SeekBarBindingAdapters {
    @BindingAdapter({"onProgressChanged", "android:progressAttrChanged"})
    public static void bindOnProgressChanged(SeekBar seekBar, OnProgressChanged onProgressChanged, InverseBindingListener inverseBindingListener) {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser) {
                    seekBar.setProgress(onProgressChanged.onProgressChanged(progress, true));
                    inverseBindingListener.onChange();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
