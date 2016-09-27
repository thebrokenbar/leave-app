package com.meterohead.leave.leavedetails;

import com.meterohead.leave.ActivityController;
import com.meterohead.leave.models.Leave;

/**
 * Created by wierzchanowskig on 27.09.2016.
 */

public interface LeaveDetailsActivityController extends ActivityController {
    void returnResult(Leave leaveObject);
}
