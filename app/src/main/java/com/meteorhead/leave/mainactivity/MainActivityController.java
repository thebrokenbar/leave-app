package com.meteorhead.leave.mainactivity;

import android.support.annotation.NonNull;

import com.meteorhead.leave.models.Leave;

/**
 * Created by wierzchanowskig on 23.09.2016.
 */

public interface MainActivityController extends com.meteorhead.leave.ActivityController {
    void openAddLeaveScreen();
    void openLeaveDetailsScreen(@NonNull Leave leaveObject);
}
