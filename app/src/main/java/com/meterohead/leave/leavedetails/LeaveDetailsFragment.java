package com.meterohead.leave.leavedetails;


import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meterohead.leave.ActivityController;
import com.meterohead.leave.BaseFragment;
import com.meterohead.leave.R;
import com.meterohead.leave.databinding.FragmentLeaveDetailsBinding;
import com.meterohead.leave.models.Leave;

import java.util.Date;

import javax.annotation.Nullable;

public class LeaveDetailsFragment extends BaseFragment implements LeaveDetailsFragmentController{

    Leave leaveObject;

    LeaveDetailsViewModel viewModel;

    @Override
    public void onCreate(@android.support.annotation.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ActivityController activityController = (ActivityController) context;
        viewModel = new LeaveDetailsViewModel(this,
                (LeaveDetailsActivityViewModel) activityController.getViewModel(),
                leaveObject);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentLeaveDetailsBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_leave_details, container, false);
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }

    @Override
    protected void onBackStackResume() {

    }

    @Override
    public void onResume() {
        super.onResume();
        if(viewModel.leaveObject.getDateStart() == null) {
            showStartDatePickerDialog(null);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Date date;
        switch (requestCode) {
            case START_DATE_PICKER_REQUEST_CODE:
                date = (Date) data.getSerializableExtra(DateSetFragment.DATE_PARAM);
                viewModel.onStartDateResult(date);
                break;
            case END_DATE_PICKER_REQUEST_CODE:
                date = (Date) data.getSerializableExtra(DateSetFragment.DATE_PARAM);
                viewModel.onEndDateResult(date);
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void showStartDatePickerDialog(@Nullable Date date) {
        DateSetFragment.newInstance(this, date, START_DATE_PICKER_REQUEST_CODE).show(
                getFragmentManager(), DateSetFragment.class.getSimpleName()
        );
    }

    @Override
    public void showEndDatePickerDialog(@Nullable Date date) {
        DateSetFragment.newInstance(this, date, END_DATE_PICKER_REQUEST_CODE).show(
                getFragmentManager(), DateSetFragment.class.getSimpleName()
        );
    }
}
