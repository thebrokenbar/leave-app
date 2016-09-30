package com.meteorhead.leave.leavelist;

import com.meteorhead.leave.models.Leave;

/**
 * Created by wierzchanowskig on 23.09.2016.
 */

public interface LeaveListFragmentController {
    void addNewLeave();
    void editLeave(Leave leaveObject);
}
