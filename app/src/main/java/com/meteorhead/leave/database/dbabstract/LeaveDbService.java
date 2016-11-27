/*
 * Copyright (c) 2016. All rights reserved.
 */

package com.meteorhead.leave.database.dbabstract;

import com.meteorhead.leave.database.dbabstract.base.DatabaseCallback;
import com.meteorhead.leave.database.dbabstract.base.DatabaseCallbackQuery;
import com.meteorhead.leave.models.Leave;

import java.sql.Date;
import java.util.List;

public interface LeaveDbService {
    List<Leave> findLeaveBetweenDates(final Date startDate, final Date endDate);
    List<Leave> getAllLeaves();
    List<Leave> getAllLeavesFuture();
    Leave getLeaveById(int id);

    void addOrUpdate(Leave leaveToAdd, DatabaseCallback callback);
    void insertLeave(Leave leaveToInsert, DatabaseCallback callback);
    void insertLeaves(List<Leave> leavesToInsert);
    void removeLeave(Leave leaveToRemove);
    void removeLeaves(List<Leave> leaveToRemove);
    void finish();
    Leave copy(Leave leave);
    List<Leave> copy(List<Leave> leaves);
}
