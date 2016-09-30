package com.meteorhead.leave.leavedetails;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.meteorhead.leave.R;
import com.meteorhead.leave.mainactivity.ActivityViewModel;
import com.meteorhead.leave.models.Leave;


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
        result.putExtra(Leave.PARAM_NAME, leaveObject);
        setResult(0, result);
        finish();
    }

    @Override
    public ActivityViewModel getViewModel() {
        return null;
    }
}
