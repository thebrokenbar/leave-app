/*
 * Copyright (c) 2016. All rights reserved.
 */

package com.meteorhead.leave.database.dbabstract;

import com.meteorhead.leave.database.dbabstract.base.IDatabaseCallback;
import com.meteorhead.leave.models.Leave;

import java.sql.Date;
import java.util.List;

public interface LeaveDbService {
    List<Leave> findLeaveBetweenDates(final Date startDate, final Date endDate);
    List<Leave> getAllLeaves();

    void addOrUpdate(Leave leaveToAdd, IDatabaseCallback callback);
    void removeLeave(Leave leaveToRemove);
}
