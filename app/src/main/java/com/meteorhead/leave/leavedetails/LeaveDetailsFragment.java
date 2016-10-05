package com.meteorhead.leave.leavedetails;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.crash.FirebaseCrash;
import com.meteorhead.leave.BaseFragment;
import com.meteorhead.leave.R;
import com.meteorhead.leave.databinding.LeaveDetailsFragmentContainerBinding;
import com.meteorhead.leave.models.Leave;
import com.orhanobut.logger.Logger;


import java.util.Date;

import javax.annotation.Nullable;

import rx.Observable;
import rx.subjects.PublishSubject;

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
        Activity activity = (Activity) context;
        Intent intent = activity.getIntent();
        if(intent != null) {
            try {
                leaveObject = intent.getParcelableExtra(Leave.PARAM_NAME);
            } catch (ClassCastException e) {
                Logger.e(e, e.getMessage());
                FirebaseCrash.report(e);
            }
        }

        setHasOptionsMenu(leaveObject != null);

        viewModel = new LeaveDetailsViewModel(this,
                (LeaveDetailsActivityController) activity,
                leaveObject);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LeaveDetailsFragmentContainerBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.leave_details_fragment_container, container, false);
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.details_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.details_menu_remove:
                viewModel.removeLeave();
                break;
        }

        return super.onOptionsItemSelected(item);
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
