package com.meteorhead.leave.leavepropose;

import com.meteorhead.leave.leavelist.LeaveListResult;
import com.meteorhead.leave.models.Leave;

/**
 * Created by wierzchanowskig on 30.10.2016.
 */

public class LeaveProposeViewHandler {

    private LeaveProposeActivityController activityController;
    private LeaveProposeViewModel viewModel;
    private LeaveProposeFragmentController fragmentController;

    public LeaveProposeViewHandler(LeaveProposeActivityController activityController, LeaveProposeViewModel viewModel, LeaveProposeFragmentController fragmentController) {
        this.activityController = activityController;
        this.viewModel = viewModel;
        this.fragmentController = fragmentController;
    }

    public void onConfirm(Leave leaveObject) {
        activityController.returnResult(LeaveListResult.RESULT_CODE_ADD, leaveObject);
    }

    public void onProposedItemClick(Leave leaveObject) {
        onConfirm(leaveObject);
    }

    public void onSeasonButtonClick(@Leave.Season int season, int days) {
        viewModel.showBestLeaveForSeason(season, days);
        fragmentController.scrollToBottom();
    }
}
