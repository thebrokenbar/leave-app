package com.meteorhead.leave.leavedetails;

import android.databinding.BindingAdapter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;

import com.meteorhead.leave.R;
import com.meteorhead.leave.base.BaseView;
import com.meteorhead.leave.databinding.LeaveDetailsFragmentContainerBinding;
import com.meteorhead.leave.leavedetails.di.LeaveDetailsModule;
import com.meteorhead.leave.models.Leave;
import com.meteorhead.leave.models.helpers.WorkingDays;
import com.meteorhead.leave.models.helpers.impl.RealmWorkingDays;
import com.meteorhead.leave.utils.DateConverter;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.joda.time.LocalDate;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.Nullable;
import javax.inject.Inject;

/**
 * Created by wierzchanowskig on 09.11.2016.
 */

public class LeaveDetailsView extends BaseView<LeaveDetailsFragmentContainerBinding> implements LeaveDetailsViewController {

    @Inject
    protected LeaveDetailsViewModel viewModel;
    @Inject
    protected LeaveDetailsViewHandler viewHandler;

    public LeaveDetailsView() {
    }

    public LeaveDetailsView(Bundle args) {
        super(args);
    }

    @Override
    protected void onViewBound(@NonNull LeaveDetailsFragmentContainerBinding binding) {
        Bundle args = this.getArgs();
        Leave leaveObject = null;
        if(args != null) {
            leaveObject = args.getParcelable(Leave.PARAM_NAME);
        }
        getActivityComponent()
                .plus(new LeaveDetailsModule(leaveObject, this))
                .inject(this);
        binding.setViewHandler(viewHandler);
        binding.setViewModel(viewModel);

        setHasOptionsMenu(leaveObject != null);
    }

    @Override
    protected void onAttach(@NonNull View view) {
        super.onAttach(view);
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
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showStartDatePickerDialog(@Nullable Date date) {
        showDatePicker(date, (view, year, monthOfYear, dayOfMonth) ->
                viewModel.onStartDateResult(
                        new LocalDate(year, monthOfYear + 1, dayOfMonth).toDate()
                )
        );
    }

    @Override
    public void showEndDatePickerDialog(@Nullable Date date) {
        showDatePicker(date, (view, year, monthOfYear, dayOfMonth) ->
                viewModel.onEndDateResult(
                        new LocalDate(year, monthOfYear + 1, dayOfMonth).toDate()
                )
        );
    }

    @Override
    public void returnResult(int resultCode, Leave leaveObject) {
        Bundle params = new Bundle(1);
        params.putParcelable(Leave.PARAM_NAME, leaveObject);
        this.returnResult(resultCode, params);
    }

    private void showDatePicker(@Nullable Date date, DatePickerDialog.OnDateSetListener listener) {
        Calendar calendar;
        if(date != null) {
            calendar = DateConverter.getAsCalendar(date);
        } else {
            calendar = Calendar.getInstance();
        }

        WorkingDays workingDaysHelper = new RealmWorkingDays(
                LocalDate.now().toDate(),
                new LocalDate(calendar.get(Calendar.YEAR) + 1, 12, 31).toDate()
        );

        DatePickerDialog dpd = DatePickerDialog.newInstance(
                listener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        dpd.dismissOnPause(true);
        dpd.setMinDate(Calendar.getInstance());
        dpd.setYearRange(calendar.get(Calendar.YEAR), calendar.get(Calendar.YEAR) + 1);
        dpd.setDisabledDays(workingDaysHelper.getAllFreeDays());
        dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");
    }

    @NonNull
    @Override
    protected int getLayoutId() {
        return R.layout.leave_details_fragment_container;
    }

    @BindingAdapter("onSeekBarChange")
    public static void setOnSeekBarChangeListener(SeekBar seekBar, SeekBar.OnSeekBarChangeListener listener) {
        seekBar.setOnSeekBarChangeListener(listener);
    }

    @BindingAdapter("android:visibility")
    public static void setFabVisibility(FloatingActionButton fab, boolean visibility) {
        if(visibility) {
            fab.show();
        } else {
            fab.hide();
        }
    }

    @BindingAdapter("onTextChange")
    public static void setOnTextChangeListener(EditText editText, TextWatcher watcher) {
        editText.addTextChangedListener(watcher);
    }

    @BindingAdapter("collapseListener")
    public static void setCollapseListener(AppBarLayout appBarLayout, AppBarLayout.OnOffsetChangedListener listener) {
        appBarLayout.addOnOffsetChangedListener(listener);
    }
}
