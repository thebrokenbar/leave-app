package com.meterohead.leave.leavelist;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import com.meterohead.leave.BaseFragment;
import com.meterohead.leave.R;
import com.meterohead.leave.database.realm.LeaveRealmService;
import com.meterohead.leave.databinding.FragmentLeaveListBinding;
import com.meterohead.leave.mainactivity.ActivityViewModel;
import com.meterohead.leave.mainactivity.MainActivityController;
import com.meterohead.leave.models.Leave;

import io.realm.Realm;

/**
 * Created by Lenovo on 2016-09-10.
 */
@FragmentWithArgs
public class LeaveListFragment extends BaseFragment implements LeaveListFragmentController {
    LeaveListViewModel viewModel;
    private ActivityViewModel activityViewModel;
    MainActivityController activityController;

    public static LeaveListFragment newInstance() {
        return new LeaveListFragmentBuilder().build();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        activityController = (MainActivityController) context;

        activityViewModel = activityController.getViewModel();
        LeaveRealmService leaveRealmService = new LeaveRealmService(Realm.getDefaultInstance());
        viewModel = new LeaveListViewModel(this, leaveRealmService);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentLeaveListBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_leave_list,
                container, false);
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected void onBackStackResume() {
        activityViewModel.setTitle("Leave List");
    }

    @Override
    public void addNewLeave() {
        activityController.openAddLeaveScreen();
    }

    @Override
    public void editLeave(Leave leaveObject) {
        activityController.openLeaveDetailsScreen(leaveObject);
    }
}
