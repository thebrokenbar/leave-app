package com.meteorhead.leave.mainactivity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import com.meteorhead.leave.FragmentActivity;
import com.meteorhead.leave.R;
import com.meteorhead.leave.databinding.ActivityMainBinding;
import com.meteorhead.leave.leavedetails.LeaveDetailsActivity;
import com.meteorhead.leave.leavelist.LeaveListFragment;
import com.meteorhead.leave.models.Leave;


public class MainActivity extends FragmentActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainActivityController {

    DrawerLayout drawerLayout;
    ActivityViewModel viewModel;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ActivityViewModel();
        ActivityMainBinding activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        activityBinding.setActivityViewModel(viewModel);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setupToolbar(toolbar);

        changeFragment(LeaveListFragment.newInstance());
    }

    @NonNull
    @Override
    public ActivityViewModel getViewModel() {
        return viewModel;
    }

    private void setupToolbar(Toolbar toolbar) {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, 0, 0);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if(getSupportFragmentManager().getBackStackEntryCount() <= 1) {
                finish();
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                boolean showBackButton = getSupportFragmentManager().getBackStackEntryCount() > 1;

                if (showBackButton) {
                    onBackPressed();
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

}
