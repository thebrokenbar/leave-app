package com.meteorhead.leave.leavelist;

import com.meteorhead.leave.models.Leave;

import rx.Observable;

/**
 * Created by wierzchanowskig on 23.09.2016.
 */

public interface LeaveListViewController {
    void showAddNewLeaveView();
    void showProposeNewLeaveView();
    void showEditLeaveView(Leave leaveObject);
    Observable<Boolean> showUndoRemoveSnackBar(int removedItemsCount);
}
