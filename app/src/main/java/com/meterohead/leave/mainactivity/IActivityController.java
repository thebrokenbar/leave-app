package com.meterohead.leave.mainactivity;

import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * Created by Lenovo on 2016-09-10.
 */

public interface IActivityController {
    ToolbarViewModel getToolbarViewModel();
    void changeFragment(Fragment fragment);
    <T extends ViewDataBinding> T setToolbarHeaderViewBinding(@LayoutRes int layoutRes);
}
