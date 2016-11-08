package com.meteorhead.leave.leavelist;

import android.databinding.Bindable;
import android.view.View;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.meteorhead.leave.models.Leave;

import rx.functions.Action1;

/**
 * Created by wierzchanowskig on 30.10.2016.
 */

public class LeaveListViewHandler {
    private LeaveListViewModel viewModel;

    public LeaveListViewHandler(LeaveListViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public void onAddLeaveClick(View view) {
        if(view instanceof FloatingActionButton) {
            FloatingActionMenu parentMenu = (FloatingActionMenu) view.getParent();
            parentMenu.close(false);
        }

        viewModel.openLeaveDetailsScreenAdd();
    }

    public void onAddProposedLeaveClick(View view) {
        if(view instanceof FloatingActionButton) {
            FloatingActionMenu parentMenu = (FloatingActionMenu) view.getParent();
            parentMenu.close(false);
        }

        viewModel.openLeaveProposeScreenAdd();
    }

    public Action1<Leave> getOnItemClickListener() {
        return new Action1<Leave>() {
            @Override
            public void call(Leave leave) {
                viewModel.openLeaveDetailsScreenEdit(leave);
            }
        };
    }

}
