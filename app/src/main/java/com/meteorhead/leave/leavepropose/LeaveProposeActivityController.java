package com.meteorhead.leave.leavepropose;

import com.meteorhead.leave.leavelist.LeaveListResult;
import com.meteorhead.leave.models.Leave;

/**
 * Created by wierzchanowskig on 30.10.2016.
 */

public interface LeaveProposeActivityController {
    void returnResult(@LeaveListResult.Code int resultCode, Leave leaveObject);
}
