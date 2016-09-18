package com.meterohead.leave.mainactivity;

import android.support.v4.app.Fragment;

/**
 * Created by Lenovo on 2016-09-10.
 */

public interface IActivityController {
    ToolbarViewModel getToolbarViewModel();
    void changeFragment(Fragment fragment);
}
