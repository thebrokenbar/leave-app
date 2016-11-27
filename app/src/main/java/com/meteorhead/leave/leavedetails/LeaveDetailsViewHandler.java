package com.meteorhead.leave.leavedetails;

import com.meteorhead.leave.leavelist.LeaveListResult;
import com.meteorhead.leave.models.Leave;

/**
 * Created by wierzchanowskig on 30.10.2016.
 */

public class LeaveDetailsViewHandler {

    private LeaveDetailsViewModel viewModel;

    public LeaveDetailsViewHandler(LeaveDetailsViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public void onConfirm(Leave leaveObject) {
        viewModel.returnLeave(leaveObject);
    }

    public void onStartDateClick() {
        viewModel.showStartDatePickerDialog();
    }

    public void onEndDateClick() {
        viewModel.showEndDatePickerDialog();
    }
}
