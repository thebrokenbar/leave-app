package com.meteorhead.leave.leavedetails;

import com.meteorhead.leave.models.Leave;
import com.meteorhead.leave.leavelist.LeaveListResult;

/**
 * Created by wierzchanowskig on 27.09.2016.
 */

public interface LeaveDetailsActivityController{

    void returnResult(@LeaveListResult.Code int resultCode, Leave leaveObject);
}
