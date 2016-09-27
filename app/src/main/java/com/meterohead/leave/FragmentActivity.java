package com.meterohead.leave;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.meterohead.leave.mainactivity.ActivityViewModel;

public abstract class FragmentActivity extends AppCompatActivity implements ActivityController {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void changeFragment(Fragment fragment) {
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

    @NonNull
    @Override
    public abstract ActivityViewModel getViewModel();
}
