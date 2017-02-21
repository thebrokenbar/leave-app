package com.meteorhead.leave.selecting.seasons;

import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.databinding.InverseBindingListener;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import com.meteorhead.leave.R;
import com.meteorhead.leave.base.BaseView;
import com.meteorhead.leave.base.Layout;
import com.meteorhead.leave.bestleaves.BestLeavesView;
import com.meteorhead.leave.databinding.SeasonsViewBinding;
import com.meteorhead.leave.models.Leave;
import java.util.List;

/**
 * Created by wierzchanowskig on 18.02.2017.
 */
@Layout(R.layout.seasons_view)
public class SeasonsView extends BaseView<SeasonsViewBinding>
    implements SeasonsViewActions {
    private SeasonsViewModel viewModel = new SeasonsViewModel(this);

    @Override
    protected void onViewBound(@NonNull SeasonsViewBinding binding) {
        binding.setViewModel(this.viewModel);
    }

    @BindingAdapter(value = { "items", "itemLayout" }, requireAll = false)
    public static void bindYears(Spinner spinner, List<Integer> availableYears,
        @LayoutRes int itemLayoutId) {
        if (itemLayoutId == 0) {
            itemLayoutId = R.layout.support_simple_spinner_dropdown_item;
        }
        spinner.setAdapter(
            new ArrayAdapter<>(spinner.getContext(), itemLayoutId, android.R.id.text1,
                availableYears));
    }

    @Override
    public void showLeavePropositions(int year, @Leave.Season int season) {
        Bundle bundle = new Bundle();
        bundle.putInt(BestLeavesView.SELECTED_YEAR_PARAM, year);
        bundle.putInt(BestLeavesView.SELECTED_SEASON_PARAM, season);
        this.showView(new BestLeavesView(bundle));
    }

    @BindingAdapter(value = { "selectedValue", "selectedValueAttrChanged" }, requireAll = false)
    public static void bindSpinnerData(Spinner pAppCompatSpinner, int newSelectedValue,
        final InverseBindingListener newTextAttrChanged) {
        pAppCompatSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                newTextAttrChanged.onChange();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        int pos =
            ((ArrayAdapter<Integer>) pAppCompatSpinner.getAdapter()).getPosition(newSelectedValue);
        pAppCompatSpinner.setSelection(pos, true);
    }

    @InverseBindingAdapter(attribute = "selectedValue", event = "selectedValueAttrChanged")
    public static int captureSelectedValue(Spinner appCompatSpinner) {
        return (int) appCompatSpinner.getSelectedItem();
    }
}
