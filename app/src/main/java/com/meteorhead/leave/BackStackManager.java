package com.meteorhead.leave;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.meteorhead.leave.R;

/**
 * Created by wierzchanowskig on 18.09.2016.
 */

public class BackStackManager implements FragmentManager.OnBackStackChangedListener {
    private FragmentManager supportFragmentManager;

    public BackStackManager(FragmentManager supportFragmentManager) {
        this.supportFragmentManager = supportFragmentManager;
    }

    @Override
    public void onBackStackChanged() {

    }
}
