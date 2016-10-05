package com.meteorhead.leave.leavedetails;

import android.support.annotation.IntDef;

import com.meteorhead.leave.ActivityController;
import com.meteorhead.leave.models.Leave;

/**
 * Created by wierzchanowskig on 27.09.2016.
 */

public interface LeaveDetailsActivityController extends ActivityController {
    @IntDef({RESULT_CODE_ADD, RESULT_CODE_REMOVE})
    @interface ResultCodes {}
    int RESULT_CODE_ADD = 1;
    int RESULT_CODE_REMOVE = 2;

    void returnResult(@LeaveDetailsActivity.ResultCodes int resultCode, Leave leaveObject);
}
