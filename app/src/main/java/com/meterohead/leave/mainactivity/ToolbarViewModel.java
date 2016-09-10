package com.meterohead.leave.mainactivity;

import android.support.design.widget.AppBarLayout;

/**
 * Created by wierzchanowskig on 10.09.2016.
 */

public class ToolbarViewModel {
    public boolean collapsingToolbarEnabled = true;

    public int scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL |
            AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP |
            AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED;
}
