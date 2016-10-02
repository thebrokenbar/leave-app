package com.meteorhead.leave.leavelist;

import android.databinding.BindingAdapter;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.meteorhead.leave.models.Leave;

import java.util.Collection;

import io.realm.RealmResults;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class LeaveListBindAdapter {
    @BindingAdapter({"items","android:onClick", "viewModel"})
    public static void setItems(RecyclerView recyclerView, Collection<Leave> items, Action1<Leave> rxOnItemClickListener,
                                LeaveListViewModel leaveListViewModel) {
        RealmResults<Leave> realmResults = (RealmResults<Leave>) items;
        LeaveListRecyclerAdapter adapter = (LeaveListRecyclerAdapter) recyclerView.getAdapter();
        if(recyclerView.getLayoutManager() == null) {
            recyclerView.setLayoutManager(
                    new LinearLayoutManager(
                            recyclerView.getContext(),
                            LinearLayoutManager.VERTICAL,
                            false
                    )
            );
        }
        if(adapter == null) {
            adapter = new LeaveListRecyclerAdapter(recyclerView.getContext(), realmResults, leaveListViewModel);
            recyclerView.setAdapter(adapter);
        }
        adapter.updateData(realmResults);
        if(rxOnItemClickListener != null) {
            adapter.getItemClickObservable()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.computation())
                    .subscribe(rxOnItemClickListener);
        }
        adapter.notifyDataSetChanged();
    }

    @BindingAdapter("android:visibility")
    public static void setVisibility(final FloatingActionButton fab, boolean visibility) {
        if(visibility) {
            fab.show();
        } else {
            fab.hide();
        }
    }

    @BindingAdapter({"selected", "unselectedBackgroundColor", "selectedBackgroundColor"})
    public static void setSelected(CardView view, Boolean selected, int unselectedColor, int selectedColor) {
        if(selected) {
            view.setCardBackgroundColor(selectedColor);
        } else {
            view.setCardBackgroundColor(unselectedColor);
        }
    }
}