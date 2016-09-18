package com.meterohead.leave.leavedetails;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.f2prateek.dart.Dart;
import com.f2prateek.dart.InjectExtra;
import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import com.hannesdorfmann.fragmentargs.bundler.ParcelerArgsBundler;
import com.meterohead.leave.R;
import com.meterohead.leave.databinding.FragmentLeaveDetailsBinding;
import com.meterohead.leave.databinding.FragmentLeaveListBinding;
import com.meterohead.leave.databinding.ToolbarHeaderLeaveDetailsBinding;
import com.meterohead.leave.mainactivity.BaseFragment;
import com.meterohead.leave.mainactivity.IActivityController;
import com.meterohead.leave.models.Leave;

import org.parceler.Parcels;

import javax.annotation.Nullable;
@FragmentWithArgs
public class LeaveDetailsFragment extends BaseFragment{

    @Arg(required = false, bundler = ParcelerArgsBundler.class)
    Leave leaveObject;

    LeaveDetailsViewModel viewModel;

    public static LeaveDetailsFragment newInstance(@Nullable Leave leaveObject) {
        LeaveDetailsFragmentBuilder builder = new LeaveDetailsFragmentBuilder();
        if(leaveObject != null) {
            builder.leaveObject(leaveObject);
        }
        return builder.build();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        IActivityController activityController = (IActivityController) context;
        viewModel = new LeaveDetailsViewModel(
                activityController,
                activityController.getToolbarViewModel(),
                leaveObject
        );
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentLeaveDetailsBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_leave_details, container, false);
        binding.setViewModel(viewModel);
        ToolbarHeaderLeaveDetailsBinding toolbarBinding = DataBindingUtil.inflate(
                inflater, R.layout.toolbar_header_leave_details,
                (ViewGroup) getActivity().findViewById(R.id.action_bar_toolbar_header),
                true
        );
        toolbarBinding.setViewModel(viewModel);
        return binding.getRoot();
    }

    @Override
    protected void onBackStackResume() {
        viewModel.getToolbarViewModel().setScrollEnabled(true);
        viewModel.getToolbarViewModel().setTitle("Title");
    }
}
