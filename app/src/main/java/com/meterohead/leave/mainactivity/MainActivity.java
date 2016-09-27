package com.meterohead.leave.mainactivity;

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
import com.meterohead.leave.FragmentActivity;
import com.meterohead.leave.R;
import com.meterohead.leave.databinding.ActivityMainBinding;
import com.meterohead.leave.leavedetails.LeaveDetailsActivity;
import com.meterohead.leave.leavedetails.LeaveDetailsFragment;
import com.meterohead.leave.leavelist.LeaveListFragment;
import com.meterohead.leave.models.Leave;

import org.parceler.Parcel;
import org.parceler.Parcels;

public class MainActivity extends FragmentActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainActivityController {

    public static final int ADD_NEW_LEAVE_REQUEST_CODE = 101;
    public static final int VIEW_LEAVE_REQUEST_CODE = 102;


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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
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


    @Override
    public void openAddLeaveScreen() {
        Intent leaveDetailsActivityIntent = new Intent(this, LeaveDetailsActivity.class);
        startActivityForResult(leaveDetailsActivityIntent, ADD_NEW_LEAVE_REQUEST_CODE);
    }

    @Override
    public void openLeaveDetailsScreen(@NonNull Leave leaveObject) {
        Intent leaveDetailsActivityIntent = new Intent(this, LeaveDetailsActivity.class);
        Bundle options = new Bundle(1);
        options.putParcelable(Leave.PARAM_NAME, Parcels.wrap(leaveObject));
        startActivityForResult(leaveDetailsActivityIntent, VIEW_LEAVE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ADD_NEW_LEAVE_REQUEST_CODE:

                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
