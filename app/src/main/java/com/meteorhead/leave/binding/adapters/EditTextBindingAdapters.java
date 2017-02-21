package com.meteorhead.leave.binding.adapters;

import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.databinding.InverseBindingListener;
import android.databinding.InverseBindingMethod;
import android.databinding.InverseBindingMethods;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Created by wierzchanowskig on 19.02.2017.
 */

public class EditTextBindingAdapters {
    @BindingAdapter("android:text")
    public static void setText(EditText view, int value) {
        String text = view.getText().toString();
        int number = -9999999;
        if (!text.isEmpty()) {
            number = Integer.parseInt(text);
        }
        if (value != number) {
            view.setText(Integer.toString(value));
        }
    }

    @InverseBindingAdapter(attribute = "android:text")
    public static int getText(EditText view) {
        String text = view.getText().toString();
        if (!text.isEmpty()) {
            return Integer.parseInt(view.getText().toString());
        }
        return 0;
    }
}