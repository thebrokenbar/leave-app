package com.meteorhead.leave.leavedetails;

import com.meteorhead.leave.ActivityController;
import com.meteorhead.leave.models.Leave;

/**
 * Created by wierzchanowskig on 27.09.2016.
 */

public interface LeaveDetailsActivityController extends ActivityController {
    void returnResult(Leave leaveObject);
}
