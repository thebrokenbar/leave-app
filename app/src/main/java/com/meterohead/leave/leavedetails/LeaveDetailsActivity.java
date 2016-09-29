package com.meterohead.leave.leavedetails;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.meterohead.leave.R;
import com.meterohead.leave.mainactivity.ActivityViewModel;
import com.meterohead.leave.models.Leave;

import org.parceler.Parcels;

public class LeaveDetailsActivity extends AppCompatActivity implements LeaveDetailsActivityController {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_details);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void returnResult(Leave leaveObject) {
        Intent result = new Intent();
        result.putExtra(Leave.PARAM_NAME, Parcels.wrap(leaveObject));
        setResult(0, result);
        finish();
    }

    @Override
    public ActivityViewModel getViewModel() {
        return null;
    }
}
