/*
 * Copyright (c) 2016. All rights reserved.
 */

package com.meterohead.leave.database.dbabstract;

import com.meterohead.leave.database.dbabstract.base.IDatabaseCallback;
import com.meterohead.leave.models.Leave;

import java.sql.Date;
import java.util.List;

public interface ILeaveDbService {
    List<Leave> findLeaveBetweenDates(final Date startDate, final Date endDate);
    List<Leave> getAllLeaves();

    void addNewLeave(Leave leaveToAdd, IDatabaseCallback callback);
}
