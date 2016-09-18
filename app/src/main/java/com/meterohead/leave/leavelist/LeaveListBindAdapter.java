package com.meterohead.leave.leavelist;

import android.databinding.BindingAdapter;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.meterohead.leave.models.Leave;

import java.util.Collection;
import java.util.TimerTask;

import io.realm.RealmResults;

public class LeaveListBindAdapter {
    @BindingAdapter("items")
    public static void setItems(RecyclerView recyclerView, Collection<Leave> items) {
        RealmResults<Leave> realmResults = (RealmResults<Leave>) items;
        LeaveListAdapter adapter = new LeaveListAdapter(recyclerView.getContext(), realmResults);
        recyclerView.setAdapter(adapter);
        if (recyclerView.getLayoutManager() == null) {
            recyclerView.setLayoutManager(
                    new LinearLayoutManager(
                            recyclerView.getContext(),
                            LinearLayoutManager.VERTICAL,
                            false
                    )
            );
        }
    }

    @BindingAdapter("showed")
    public static void setVisibility(final FloatingActionButton fab, boolean visibility) {
        if(visibility) {
            fab.show();
        } else {
            fab.hide();
        }
    }
}