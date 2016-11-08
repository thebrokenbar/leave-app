package com.meteorhead.leave.leavedetails;

import com.meteorhead.leave.leavelist.LeaveListResult;
import com.meteorhead.leave.models.Leave;

/**
 * Created by wierzchanowskig on 30.10.2016.
 */

public class LeaveDetailsViewHandler {

    private LeaveDetailsActivityController activityController;

    public LeaveDetailsViewHandler(LeaveDetailsActivityController activityController) {
        this.activityController = activityController;
    }

    public void onConfirm(Leave leaveObject) {
        activityController.returnResult(LeaveListResult.RESULT_CODE_ADD, leaveObject);
    }
}
