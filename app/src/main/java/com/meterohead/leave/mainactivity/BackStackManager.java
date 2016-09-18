package com.meterohead.leave.mainactivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;

import com.meterohead.leave.R;

/**
 * Created by wierzchanowskig on 18.09.2016.
 */

public class BackStackManager implements FragmentManager.OnBackStackChangedListener {
    private FragmentManager supportFragmentManager;
    private IToolbarUpdater toolbarUpdater;

    public BackStackManager(FragmentManager supportFragmentManager, IToolbarUpdater toolbarUpdater) {
        this.supportFragmentManager = supportFragmentManager;
        this.toolbarUpdater = toolbarUpdater;
    }

    @Override
    public void onBackStackChanged() {
        Fragment fragment = supportFragmentManager.findFragmentById(R.id.single_pane_activity_container);
        if(fragment instanceof BaseFragment) {
            BaseFragment baseFragment = (BaseFragment) fragment;
            baseFragment.onBackStackResume();
        }

        toolbarUpdater.updateToolbarBackIcon(supportFragmentManager.getBackStackEntryCount());
    }
}
