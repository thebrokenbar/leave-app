package com.meteorhead.leave.leavepropose;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import butterknife.BindView;
import com.meteorhead.leave.R;
import com.meteorhead.leave.base.BaseView;
import com.meteorhead.leave.databinding.LeaveProposeFragmentContainerBinding;
import com.meteorhead.leave.leavelist.LeaveListResult;
import com.meteorhead.leave.leavepropose.di.LeaveProposeModule;
import com.meteorhead.leave.models.Leave;
import javax.inject.Inject;

/**
 * Created by wierzchanowskig on 29.11.2016.
 */

public class LeaveProposeView extends BaseView<LeaveProposeFragmentContainerBinding> implements LeaveProposeViewController {

    @Inject
    LeaveProposeViewHandler viewHandler;
    @Inject
    LeaveProposeViewModel viewModel;

    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;

    @NonNull
    @Override
    protected int getLayoutId() {
        return R.layout.leave_propose_fragment_container;
    }

    @Override
    protected void onViewBound(@NonNull LeaveProposeFragmentContainerBinding binding) {
        getActivityComponent().plus(new LeaveProposeModule(this))
                .inject(this);

        binding.setViewHandler(viewHandler);
        binding.setViewModel(viewModel);
    }

    @Override
    public void scrollToBottom() {
        appBarLayout.setExpanded(false, true);
    }

    @Override
    public void returnResult(@LeaveListResult.Code int resultCode, Leave leaveObject) {
        Bundle params = new Bundle(1);
        params.putParcelable(Leave.PARAM_NAME, leaveObject);
        this.returnResult(resultCode, params);
    }
}
