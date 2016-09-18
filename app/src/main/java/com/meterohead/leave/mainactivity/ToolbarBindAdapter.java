package com.meterohead.leave.mainactivity;

import android.databinding.BindingAdapter;
import android.support.design.widget.AppBarLayout;
import android.view.View;
import android.view.ViewGroup;

import com.transitionseverywhere.ChangeBounds;
import com.transitionseverywhere.ChangeText;
import com.transitionseverywhere.TransitionManager;
import com.transitionseverywhere.TransitionSet;

public class ToolbarBindAdapter {
    @BindingAdapter("android:layout_height")
    public static void setLayoutHeight(AppBarLayout view, float height) {
        ViewGroup transitionLayout = (ViewGroup) view.getParent();
        TransitionManager.beginDelayedTransition(transitionLayout, new TransitionSet()
                .addTransition(new ChangeBounds().setDuration(100))
                .addTransition(new ChangeText().setChangeBehavior(ChangeText.CHANGE_BEHAVIOR_IN))
        );
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) height;
        view.setLayoutParams(layoutParams);
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