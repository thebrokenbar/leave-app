package com.meteorhead.leave.leavepropose;

import com.meteorhead.leave.leavelist.LeaveListResult;
import com.meteorhead.leave.models.Leave;

/**
 * Created by wierzchanowskig on 06.11.2016.
 */
public interface LeaveProposeViewController {
    void scrollToBottom();
    void returnResult(@LeaveListResult.Code int resultCode, Leave leaveObject);
}
