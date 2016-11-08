package com.meteorhead.leave.leavelist;

import android.support.annotation.IntDef;

/**
 * Created by wierzchanowskig on 30.10.2016.
 */

public interface LeaveListResult {
    @IntDef({RESULT_CODE_ADD, RESULT_CODE_REMOVE})
    @interface Code {}
    int RESULT_CODE_ADD = 1;
    int RESULT_CODE_REMOVE = 2;
}
