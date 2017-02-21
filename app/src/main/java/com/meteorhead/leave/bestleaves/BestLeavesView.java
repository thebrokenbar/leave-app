package com.meteorhead.leave.bestleaves;

import android.databinding.BindingAdapter;
import android.databinding.BindingMethod;
import android.databinding.BindingMethods;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import butterknife.BindView;
import butterknife.OnTextChanged;
import com.meteorhead.leave.R;
import com.meteorhead.leave.base.BaseView;
import com.meteorhead.leave.base.Layout;
import com.meteorhead.leave.databinding.BestLeavesViewBinding;
import com.meteorhead.leave.models.Leave;
import java.util.List;

/**
 * Created by wierzchanowskig on 18.02.2017.
 */
@Layout(R.layout.best_leaves_view)
public class BestLeavesView extends BaseView<BestLeavesViewBinding> {
    public static final String SELECTED_YEAR_PARAM = "SELECTED_YEAR_PARAM";
    public static final String SELECTED_SEASON_PARAM = "SELECTED_SEASON_PARAM";
    private BestLeavesViewModel viewModel;

    //@OnTextChanged(value = R.id.etDays, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    //void onDaysEditText(CharSequence text){
    //    viewModel.setDays(text.toString());
    //}

    public BestLeavesView(Bundle args) {
        super(args);
        int year = args.getInt(SELECTED_YEAR_PARAM);
        @Leave.Season int season = args.getInt(SELECTED_SEASON_PARAM);
        viewModel = new BestLeavesViewModel(year, season);
    }

    @Override
    protected void onViewBound(@NonNull BestLeavesViewBinding binding) {
        binding.setViewModel(viewModel);
    }
}
