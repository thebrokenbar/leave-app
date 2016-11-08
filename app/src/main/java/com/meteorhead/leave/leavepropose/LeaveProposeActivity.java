package com.meteorhead.leave.leavepropose;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.meteorhead.leave.R;
import com.meteorhead.leave.leavelist.LeaveListResult;
import com.meteorhead.leave.models.Leave;

public class LeaveProposeActivity extends AppCompatActivity implements LeaveProposeActivityController {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_propose);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void returnResult(@LeaveListResult.Code int resultCode, Leave leaveObject) {
        Intent result = new Intent();
        result.putExtra(Leave.PARAM_NAME, leaveObject);
        setResult(resultCode, result);
        finish();
    }
}
