package com.meterohead.leave.leavedetails;

import android.databinding.BindingAdapter;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.SeekBar;

/**
 * Created by wierzchanowskig on 24.09.2016.
 */

public class LeaveDetailsBindingAdapter {

    @BindingAdapter("onSeekBarChange")
    public static void setOnSeekBarChangeListener(SeekBar seekBar, SeekBar.OnSeekBarChangeListener listener) {
        seekBar.setOnSeekBarChangeListener(listener);
    }

    @BindingAdapter("android:visibility")
    public static void setFabVisibility(FloatingActionButton fab, boolean visibility) {
        if(visibility) {
            fab.show();
        } else {
            fab.hide();
        }
    }
}
