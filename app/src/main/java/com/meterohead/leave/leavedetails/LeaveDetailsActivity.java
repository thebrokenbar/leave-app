package com.meterohead.leave.leavedetails;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.meterohead.leave.R;
import com.meterohead.leave.databinding.ActivityLeaveDetailsBinding;
import com.meterohead.leave.mainactivity.ActivityViewModel;
import com.meterohead.leave.models.Leave;

import org.parceler.Parcels;

public class LeaveDetailsActivity extends AppCompatActivity implements LeaveDetailsActivityController {

    LeaveDetailsActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new LeaveDetailsActivityViewModel(this);
        ActivityLeaveDetailsBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_leave_details);
        binding.setViewModel(viewModel);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @NonNull
    @Override
    public ActivityViewModel getViewModel() {
        return viewModel;
    }


    @Override
    public void returnResult(Leave leaveObject) {
        Intent result = new Intent();
        result.putExtra(Leave.PARAM_NAME, Parcels.wrap(leaveObject));
        setResult(0, result);
        finish();
    }
}
