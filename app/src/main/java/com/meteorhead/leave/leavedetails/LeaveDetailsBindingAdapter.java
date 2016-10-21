package com.meteorhead.leave.leavedetails;

import android.databinding.BindingAdapter;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.text.TextWatcher;
import android.widget.EditText;
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

    @BindingAdapter("onTextChange")
    public static void setOnTextChangeListener(EditText editText, TextWatcher watcher) {
        editText.addTextChangedListener(watcher);
    }

    @BindingAdapter("collapseListener")
    public static void setCollapseListener(AppBarLayout appBarLayout, AppBarLayout.OnOffsetChangedListener listener) {
        appBarLayout.addOnOffsetChangedListener(listener);
    }
}
