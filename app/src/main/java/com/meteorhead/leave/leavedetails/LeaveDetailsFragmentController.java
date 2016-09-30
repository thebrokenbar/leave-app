package com.meteorhead.leave.leavedetails;


import java.util.Date;

import javax.annotation.Nullable;

/**
 * Created by wierzchanowskig on 24.09.2016.
 */

public interface LeaveDetailsFragmentController {
    int START_DATE_PICKER_REQUEST_CODE = 101;
    int END_DATE_PICKER_REQUEST_CODE = 102;

    void showStartDatePickerDialog(@Nullable Date date);
    void showEndDatePickerDialog(@Nullable Date date);
}
