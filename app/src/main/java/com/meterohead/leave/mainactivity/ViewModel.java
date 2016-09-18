package com.meterohead.leave.mainactivity;

import android.databinding.BaseObservable;

/**
 * Created by wierzchanowskig on 18.09.2016.
 */

public class ViewModel extends BaseObservable {
    private ToolbarViewModel toolbarViewModel;
    private IActivityController activityController;

    public ViewModel(IActivityController activityController, ToolbarViewModel toolbarViewModel) {
        this.toolbarViewModel = toolbarViewModel;
        this.activityController = activityController;
    }

    public ToolbarViewModel getToolbarViewModel() {
        return toolbarViewModel;
    }

    public IActivityController getActivityController() {
        return activityController;
    }
}
