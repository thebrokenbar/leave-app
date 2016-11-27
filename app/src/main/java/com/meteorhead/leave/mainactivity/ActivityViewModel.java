package com.meteorhead.leave.mainactivity;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import com.meteorhead.leave.base.ViewModel;

/**
 * Created by Lenovo on 2016-09-09.
 */

public class ActivityViewModel extends ViewModel{
    public ObservableField<String> title = new ObservableField<>("");
    public ObservableBoolean titleVisibility = new ObservableBoolean(true);
}
