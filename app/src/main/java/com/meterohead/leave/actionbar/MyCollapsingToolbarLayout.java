package com.meterohead.leave.actionbar;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.util.AttributeSet;

/**
 * Created by wierzchanowskig on 10.09.2016.
 */

public class MyCollapsingToolbarLayout extends CollapsingToolbarLayout {
    public MyCollapsingToolbarLayout(Context context) {
        super(context);
    }

    public MyCollapsingToolbarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyCollapsingToolbarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setScrollFlags(@AppBarLayout.LayoutParams.ScrollFlags int flags) {
        AppBarLayout.LayoutParams layoutParams = (AppBarLayout.LayoutParams) getLayoutParams();
        layoutParams.setScrollFlags(flags);
    }

}
