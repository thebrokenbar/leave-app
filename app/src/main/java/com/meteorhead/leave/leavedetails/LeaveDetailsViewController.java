package com.meteorhead.leave.leavedetails;


import com.meteorhead.leave.models.Leave;

import java.util.Date;

import javax.annotation.Nullable;

/**
 * Created by wierzchanowskig on 24.09.2016.
 */

public interface LeaveDetailsViewController {

    void showStartDatePickerDialog(@Nullable Date date);
    void showEndDatePickerDialog(@Nullable Date date);

    void returnResult(int resultCode, Leave leaveObject);
}
