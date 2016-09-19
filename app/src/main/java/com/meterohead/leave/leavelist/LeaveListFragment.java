package com.meterohead.leave.leavelist;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import com.meterohead.leave.R;
import com.meterohead.leave.database.realm.LeaveRealmService;
import com.meterohead.leave.databinding.FragmentLeaveListBinding;
import com.meterohead.leave.mainactivity.BaseFragment;
import com.meterohead.leave.mainactivity.IActivityController;

import java.util.TimerTask;

import io.realm.Realm;

/**
 * Created by Lenovo on 2016-09-10.
 */
@FragmentWithArgs
public class LeaveListFragment extends BaseFragment {
    private static final int SHOW_FAB_BUTTON_DELAY_MILLIS = 100;
    LeaveListViewModel viewModel;
    Handler handler;



    public static LeaveListFragment newInstance() {
        return new LeaveListFragmentBuilder().build();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        IActivityController activityController = (IActivityController) context;
        LeaveRealmService leaveRealmService = new LeaveRealmService(Realm.getDefaultInstance());
        viewModel = new LeaveListViewModel(activityController, activityController.getToolbarViewModel(), leaveRealmService);
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
        if(viewModel.getToolbarViewModel() != null) {
            viewModel.getToolbarViewModel().setTitle("Leave list");
            viewModel.getToolbarViewModel().setScrollEnabled(false);
            handler.postDelayed(new TimerTask() {
                @Override
                public void run() {
                    viewModel.setFabVisibility(true);
                }
            }, SHOW_FAB_BUTTON_DELAY_MILLIS);
        }
    }
}
