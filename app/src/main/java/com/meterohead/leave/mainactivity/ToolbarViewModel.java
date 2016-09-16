package com.meterohead.leave.mainactivity;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.support.design.widget.AppBarLayout;
import android.view.View;
import android.view.ViewGroup;

import com.android.databinding.library.baseAdapters.BR;

/**
 * Created by wierzchanowskig on 10.09.2016.
 */

public class ToolbarViewModel extends BaseObservable {
    private static final int DEFAULT_SCROLL_FLAGS = AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL |
            AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP |
            AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED;
    private static final int DEFAULT_HEIGHT = ViewGroup.LayoutParams.WRAP_CONTENT;

    private final int scrolledHeight;
    private boolean collapsingToolbarEnabled = true;
    private int scrollFlags = DEFAULT_SCROLL_FLAGS;
    private int toolbarHeight = DEFAULT_HEIGHT;
    private String title;

    public ToolbarViewModel(int scrolledHeight) {
        this.scrolledHeight = scrolledHeight;
    }

    public void setScrollEnabled(boolean enabled) {
        collapsingToolbarEnabled = enabled;
        scrollFlags = enabled ? DEFAULT_SCROLL_FLAGS : AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED;
        toolbarHeight = enabled ? scrolledHeight : DEFAULT_HEIGHT;

        notifyChange();
    }

    @Bindable
    public int getScrollFlags() {
        return scrollFlags;
    }

    @Bindable
    public int getToolbarHeight() {
        return toolbarHeight;
    }

    @Bindable
    public boolean isCollapsingToolbarEnabled() {
        return collapsingToolbarEnabled;
    }

    @BindingAdapter("android:layout_height")
    public static void setLayoutHeight(View view, float height) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) height;
        view.setLayoutParams(layoutParams);
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
    }

    @BindingAdapter("app:expand")
    public static void setExpand(View view, boolean expand) {
        AppBarLayout appBarLayout = (AppBarLayout) view;
        appBarLayout.setExpanded(expand, false);
    }

    @BindingAdapter("app:scrollFlags")
    public static void setScrollFlags(View view, int scrollFlags) {
        AppBarLayout.LayoutParams layoutParams = (AppBarLayout.LayoutParams) view.getLayoutParams();
        layoutParams.setScrollFlags(scrollFlags);
    }
}
