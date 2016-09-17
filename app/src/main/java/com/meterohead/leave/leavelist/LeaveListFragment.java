package com.meterohead.leave.leavelist;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meterohead.leave.R;
import com.meterohead.leave.database.realm.LeaveRealmService;
import com.meterohead.leave.databinding.LeaveListBinding;
import com.meterohead.leave.mainactivity.IActivityController;

import io.realm.Realm;

/**
 * Created by Lenovo on 2016-09-10.
 */

public class LeaveListFragment extends Fragment {
    LeaveListViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        IActivityController activityController = (IActivityController) context;
        LeaveRealmService leaveRealmService = new LeaveRealmService(Realm.getDefaultInstance());
        viewModel = new LeaveListViewModel(activityController.getToolbarViewModel(), leaveRealmService);
        viewModel.getToolbarViewModel().setTitle("Leave list");
        viewModel.getToolbarViewModel().setScrollEnabled(false);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LeaveListBinding binding = DataBindingUtil.inflate(inflater, R.layout.leave_list,
                container, true);
        binding.setLeaveListViewModel(viewModel);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
