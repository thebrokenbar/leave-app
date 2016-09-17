package com.meterohead.leave.mainactivity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.crash.FirebaseCrash;
import com.meterohead.leave.R;
import com.meterohead.leave.database.realm.LeaveRealmService;
import com.meterohead.leave.database.realm.base.interfaces.IRealmCallback;
import com.meterohead.leave.databinding.ActivityMainBinding;
import com.meterohead.leave.models.Leave;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, IActivityController {

    DrawerLayout drawerLayout;
    ToolbarViewModel toolbarViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RealmConfiguration defaultRealmConfig = new RealmConfiguration.Builder(this)
                .schemaVersion(0)
                .name("leaveDb.realm")
                .build();
        Realm.setDefaultConfiguration(defaultRealmConfig);

        //
        LeaveRealmService service = new LeaveRealmService(Realm.getDefaultInstance());
        SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy", Locale.getDefault());
        try {
            Date start = sdf.parse("16-09-2016");
            Date end = sdf.parse("20-09-2016");
            service.addNewLeave(new Leave(start, end, "title", 0), new IRealmCallback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError(Throwable error) {
                    FirebaseCrash.report(error);
                    FirebaseCrash.log(error.getMessage());
                }
            });
        } catch (ParseException e) {
            FirebaseCrash.report(e);
            FirebaseCrash.log(e.getMessage());
        }

        RealmResults<Leave> alllist = service.getAllLeaves();

        //

        toolbarViewModel =
                new ToolbarViewModel(getResources().getDimensionPixelSize(R.dimen.scrollDefaultHeight));
        toolbarViewModel.setScrollEnabled(false);
        toolbarViewModel.setTitle(getString(R.string.app_name));
        ActivityMainBinding activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        activityBinding.setToolbarViewModel(toolbarViewModel);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupMenu(toolbar);
    }

    private void setupMenu(Toolbar toolbar) {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public ToolbarViewModel getToolbarViewModel() {
        return toolbarViewModel;
    }
}
