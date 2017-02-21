package com.meteorhead.leave.selecting.seasons;

import com.meteorhead.leave.models.Leave;

/**
 * Created by wierzchanowskig on 18.02.2017.
 */

public interface SeasonsViewActions {
    void showLeavePropositions(int year, @Leave.Season int season);
}
