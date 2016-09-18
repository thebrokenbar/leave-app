package com.meterohead.leave.mainactivity;

import android.support.design.widget.AppBarLayout;

/**
 * Created by wierzchanowskig on 18.09.2016.
 */

public abstract class AppBarStateChangeListener implements AppBarLayout.OnOffsetChangedListener {

    public enum State {
        EXPANDED,
        COLLAPSED
    }

    private State mCurrentState = State.COLLAPSED;

    @Override
    public final void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        if (i == 0) {
            if (mCurrentState != State.EXPANDED) {
                onStateChanged(appBarLayout, State.EXPANDED);
            }
            mCurrentState = State.EXPANDED;
        } else {
            if (mCurrentState != State.COLLAPSED) {
                onStateChanged(appBarLayout, State.COLLAPSED);
            }
            mCurrentState = State.COLLAPSED;
        }
    }

    public abstract void onStateChanged(AppBarLayout appBarLayout, State state);
}
