package com.meteorhead.leave.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.changehandler.AutoTransitionChangeHandler;
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler;
import com.bluelinelabs.conductor.changehandler.HorizontalChangeHandler;
import com.bluelinelabs.conductor.changehandler.TransitionChangeHandlerCompat;
import com.bluelinelabs.conductor.changehandler.VerticalChangeHandler;
import com.meteorhead.leave.application.Application;
import com.meteorhead.leave.application.di.ApplicationComponent;
import com.meteorhead.leave.mainactivity.conductor.MainActivity;
import com.meteorhead.leave.mainactivity.di.ActivityComponent;
import com.meteorhead.leave.views.changehandlers.CircularRevealChangeHandlerCompat;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by wierzchanowskig on 09.11.2016.
 */

public abstract class BaseView<V extends ViewDataBinding> extends Controller {

    private static final int FADE_ANIMATION_DURATION = 100;
    private Unbinder butterknifeUnbinder;
    private V binding;
    private OnViewResult onViewResultListener;

    public BaseView() {
    }

    public BaseView(Bundle args) {
        super(args);
    }

    protected ActivityComponent getActivityComponent() {
        return ((MainActivity)getActivity()).getActivityComponent();
    }

    @NonNull
    protected abstract int getLayoutId();

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        View view = binding.getRoot();
        butterknifeUnbinder = ButterKnife.bind(this, view);
        onViewBound(binding);
        return view;
    }

    protected void onViewBound(@NonNull V binding) {}

    protected void onViewUnbound(@NonNull V binding) {}

    @Override
    protected void onDestroyView(View view) {
        super.onDestroyView(view);
        onViewUnbound(binding);
        binding.unbind();
        butterknifeUnbinder.unbind();
        butterknifeUnbinder = null;
    }

    public void setOnViewResultListener(OnViewResult onViewResult) {
        onViewResultListener = onViewResult;
    }

    protected void returnResult(int resultCode, Bundle params) {
        getRouter().popController(this);
        onViewResultListener.onResult(resultCode, params);
    }

    protected void showViewForResult(BaseView view, OnViewResult onViewResult) {
        view.setTargetController(this);
        view.setOnViewResultListener(onViewResult);
        getRouter().pushController(RouterTransaction.with(view)
                .pushChangeHandler(new FadeChangeHandler(FADE_ANIMATION_DURATION))
                .popChangeHandler(new FadeChangeHandler(FADE_ANIMATION_DURATION))
                .tag(view.getClass().getSimpleName())
        );
    }

    public AppCompatActivity getAppCompatActivity() {
        return (AppCompatActivity) getActivity();
    }
}
