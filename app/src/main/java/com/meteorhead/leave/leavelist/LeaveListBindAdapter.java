package com.meteorhead.leave.leavelist;

import android.databinding.BindingAdapter;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.meteorhead.leave.models.Leave;
import com.orhanobut.logger.Logger;

import java.util.Collection;

import io.realm.RealmResults;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class LeaveListBindAdapter {
    @BindingAdapter({"items","android:onClick", "viewModel"})
    public static void setItems(final RecyclerView recyclerView, Collection<Leave> items, final Action1<Leave> rxOnItemClickListener,
                                final LeaveListViewModel leaveListViewModel) {
        if(items == null) {
            return;
        }

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
            if(rxOnItemClickListener != null) {
                adapter.getItemClickObservable()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.computation())
                        .subscribe(rxOnItemClickListener);
            }
        }
        adapter.updateData(realmResults);
        adapter.notifyDataSetChanged();
    }

    @BindingAdapter("android:visibility")
    public static void setVisibility(final FloatingActionMenu fabMenu, boolean visibility) {
        if(visibility) {
            fabMenu.showMenuButton(true);
        } else {
            fabMenu.hideMenuButton(true);
        }
    }

    @BindingAdapter("closeOnTouchOutside")
    public static void setClosedOnTouchOutside(final FloatingActionMenu floatingActionMenu, boolean close) {
        floatingActionMenu.setClosedOnTouchOutside(close);
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