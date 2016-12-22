package com.meteorhead.leave.leavepropose;

import com.meteorhead.leave.leavelist.LeaveListResult;
import com.meteorhead.leave.models.Leave;

/**
 * Created by wierzchanowskig on 30.10.2016.
 */

public class LeaveProposeViewHandler {

    private LeaveProposeViewModel viewModel;
    private LeaveProposeViewController viewController;

    public LeaveProposeViewHandler(LeaveProposeViewModel viewModel, LeaveProposeViewController viewController) {
        this.viewModel = viewModel;
        this.viewController = viewController;
    }

    public void onConfirm(Leave leaveObject) {
        this.viewController.returnResult(LeaveListResult.RESULT_CODE_ADD_OR_EDIT, leaveObject);
    }

    public void onProposedItemClick(Leave leaveObject) {
        onConfirm(leaveObject);
    }

    public void onSeasonButtonClick(@Leave.Season int season, int days) {
        this.viewModel.showBestLeaveForSeason(season, days);
        this.viewController.scrollToBottom();
    }
}
