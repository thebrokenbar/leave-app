package com.meterohead.leave.mainactivity;

import android.support.annotation.NonNull;

import com.meterohead.leave.models.Leave;

/**
 * Created by wierzchanowskig on 23.09.2016.
 */

public interface MainActivityController extends com.meterohead.leave.ActivityController {
    void openAddLeaveScreen();
    void openLeaveDetailsScreen(@NonNull Leave leaveObject);
}
