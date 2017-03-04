package com.meteorhead.leave.mainactivity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.bluelinelabs.conductor.Conductor;
import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.ControllerChangeHandler;
import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;
import com.meteorhead.leave.R;
import com.meteorhead.leave.application.Application;
import com.meteorhead.leave.base.BaseView;
import com.meteorhead.leave.databinding.ActivityMainBinding;
import com.meteorhead.leave.mainactivity.di.ActivityComponent;
import com.meteorhead.leave.mainactivity.di.ActivityModule;
import com.meteorhead.leave.screens.seasons.SeasonsView;
import com.meteorhead.leave.utils.RealmExporter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener {

    private Router router;
    @BindView(R.id.single_pane_activity_container)
    ViewGroup container;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @Inject
    ActivityViewModel activityViewModel;
    private ActivityMainBinding binding;
    private ActivityComponent activityComponent;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityComponent = ((Application)getApplication()).getApplicationComponent()
                .plus(new ActivityModule());
        activityComponent.inject(this);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setActivityViewModel(activityViewModel);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupNavigationMenu();

        router = Conductor.attachRouter(this, container, savedInstanceState);
        if (!router.hasRootController()) {
            router.setRoot(RouterTransaction.with(getDefaultView()));
        }
        router.addChangeListener(new ControllerChangeHandler.ControllerChangeListener() {
            @Override
            public void onChangeStarted(Controller to, Controller from, boolean isPush, ViewGroup container, ControllerChangeHandler handler) {
                activityViewModel.title.set("");
                if (router.getBackstackSize() > 1) {
                    drawerToggle.setHomeAsUpIndicator(R.drawable.ic_back_arrow);
                } else {
                    drawerToggle.setHomeAsUpIndicator(R.drawable.ic_hamburger);
                }
                drawerToggle.syncState();
                hideKeyboard();
            }

            @Override
            public void onChangeCompleted(Controller to, Controller from, boolean isPush, ViewGroup container, ControllerChangeHandler handler) {
            }
        });
    }

    @NonNull
    private BaseView getDefaultView() {
        return new SeasonsView();
    }

    private void hideKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if (!router.handleBack()) {
                super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                boolean showBackButton = router.getBackstackSize() > 1;

                if (showBackButton) {
                    onBackPressed();
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void setupNavigationMenu() {
        drawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, 0, 0);
        drawerToggle.setDrawerIndicatorEnabled(false);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.setHomeAsUpIndicator(R.drawable.ic_hamburger);
        drawerToggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_export_database:
                RealmExporter.INSTANCE.exportDatabase(this);
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public ActivityComponent getActivityComponent() {
        return this.activityComponent;
    }
}
