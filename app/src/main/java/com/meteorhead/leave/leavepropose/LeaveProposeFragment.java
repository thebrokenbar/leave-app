package com.meteorhead.leave.leavepropose;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meteorhead.leave.BaseFragment;
import com.meteorhead.leave.R;
import com.meteorhead.leave.databinding.LeaveProposeFragmentContainerBinding;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LeaveProposeFragment extends BaseFragment implements LeaveProposeFragmentController {

    private LeaveProposeViewHandler viewHandler;
    private LeaveProposeViewModel viewModel;

    @BindView(R.id.nsvLeavePropose)
    NestedScrollView nsvLeavePropose;

    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;

    public LeaveProposeFragment() {
        // Required empty public constructor
    }

    public static LeaveProposeFragment newInstance() {
        return new LeaveProposeFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        viewModel = new LeaveProposeViewModel();
        viewHandler = new LeaveProposeViewHandler((LeaveProposeActivityController) context, viewModel, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LeaveProposeFragmentContainerBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.leave_propose_fragment_container, container, false);

        binding.setViewModel(viewModel);
        binding.setViewHandler(viewHandler);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    @Override
    public void scrollToBottom() {
        appBarLayout.setExpanded(false, true);
//        nsvLeavePropose.fullScroll(View.FOCUS_DOWN);
    }
}
