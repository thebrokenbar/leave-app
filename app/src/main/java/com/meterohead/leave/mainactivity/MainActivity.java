package com.meterohead.leave.mainactivity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.meterohead.leave.R;
import com.meterohead.leave.databinding.ActivityMainBinding;
import com.meterohead.leave.leavelist.LeaveListFragment;
import com.transitionseverywhere.TransitionManager;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, IActivityController, IToolbarUpdater {

    private static final int HEADER_FADE_ANIMATION_TIME_MSEC = 100;
    DrawerLayout drawerLayout;
    ToolbarViewModel toolbarViewModel;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbarViewModel =
                new ToolbarViewModel(getResources().getDimensionPixelSize(R.dimen.scrollDefaultHeight));
        ActivityMainBinding activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        activityBinding.setToolbarViewModel(toolbarViewModel);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);
        final View toolbarHeader = findViewById(R.id.action_bar_toolbar_header);
        appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                switch (state) {
                    case COLLAPSED:
                        toolbarHeader.animate().alpha(0).setDuration(HEADER_FADE_ANIMATION_TIME_MSEC);
                        break;
                    case EXPANDED:
                        toolbarHeader.animate().alpha(1).setDuration(HEADER_FADE_ANIMATION_TIME_MSEC);
                        break;
                    default:
                }
            }
        });

        setupMenu(toolbar);

        changeFragment(LeaveListFragment.newInstance());
        getSupportFragmentManager()
                .addOnBackStackChangedListener(new BackStackManager(getSupportFragmentManager(), this));
    }

    private void setupMenu(Toolbar toolbar) {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, 0, 0);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void updateToolbarBackIcon(int backStackSize) {
        if(backStackSize > 1) {
            drawerToggle.setDrawerIndicatorEnabled(false);
        } else {
            drawerToggle.setDrawerIndicatorEnabled(true);
        }
        drawerToggle.syncState();
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
    public ToolbarViewModel getToolbarViewModel() {
        return toolbarViewModel;
    }

    @Override
    public void changeFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if(getSupportFragmentManager().getBackStackEntryCount() > 0) {
            transaction.replace(R.id.single_pane_activity_container, fragment);
        } else {
            transaction.add(R.id.single_pane_activity_container, fragment);
        }
        transaction.addToBackStack(null);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.commit();
    }
}
