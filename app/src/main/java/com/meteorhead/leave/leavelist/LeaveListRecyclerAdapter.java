package com.meteorhead.leave.leavelist;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v4.util.SparseArrayCompat;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.meteorhead.leave.R;
import com.meteorhead.leave.databinding.LeaveListItemBinding;
import com.meteorhead.leave.models.Leave;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;
import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by Lenovo on 2016-09-13.
 */

public class LeaveListRecyclerAdapter extends RealmRecyclerViewAdapter<Leave, LeaveListViewHolder> {

    private PublishSubject<Leave> onClickSubject = PublishSubject.create();
    private SparseArrayCompat<RecyclerViewAdapterSelector> selectionArray;
    private final LeaveListViewModel leaveListViewModel;

    public LeaveListRecyclerAdapter(Context activityContext, OrderedRealmCollection<Leave> data, LeaveListViewModel leaveListViewModel) {
        super(activityContext, data, true);
        this.selectionArray = new SparseArrayCompat<>(data.size());
        this.leaveListViewModel = leaveListViewModel;
        subscribeOnRemoveTask();
    }

    private void subscribeOnRemoveTask() {
        leaveListViewModel.getRemoveTaskObservable().subscribe(o -> {
            List<Leave> selectedItems = getSelectedItems();
            if(selectedItems != null) {
                leaveListViewModel.removeLeaves(selectedItems);
                setSelectionToAll(false);
            }
        });
    }

    @Override
    public LeaveListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LeaveListItemBinding viewBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.leave_list_item, parent, false);
        return new LeaveListViewHolder(viewBinding);
    }

    @Override
    public void onBindViewHolder(final LeaveListViewHolder holder, final int position) {
        if(getData() != null) {
            final Leave element = getData().get(position);
            final int pos = holder.getAdapterPosition();
            final RecyclerViewAdapterSelector selector = selectionArray.get(pos, new RecyclerViewAdapterSelector(false));
            selectionArray.put(pos, selector);
            holder.itemView.setOnClickListener(view -> {
                if(leaveListViewModel.isSelectionMode.get()) {
                    selector.setSelection(!selector.getSelection());
                    if(!isAnySelected()) {
                        leaveListViewModel.isSelectionMode.set(false);
                    }
                } else {
                    onClickSubject.onNext(element);
                }
            });

            holder.itemView.setOnLongClickListener(view -> {
                leaveListViewModel.isSelectionMode.set(true);
                selector.setSelection(!selector.getSelection());
                return true;
            });
            LeaveListItemBinding itemBinding = holder.getItemBinding();
            itemBinding.setLeave(getData().get(position));
            itemBinding.setSelector(selector);
            itemBinding.executePendingBindings();
        }
    }

    public void setSelectionToAll(boolean selected) {
        if (getData() != null) {
            for (int i = 0; i < getData().size(); i++) {
                RecyclerViewAdapterSelector selector = selectionArray.get(i, new RecyclerViewAdapterSelector(false));
                selector.setSelection(selected);
            }
        }
        if(!selected) {
            leaveListViewModel.isSelectionMode.set(false);
        }
    }

    public boolean isAnySelected() {
        for (int i = 0; i < selectionArray.size(); i++) {
            if(selectionArray.valueAt(i).getSelection()) {
                return true;
            }
        }
        return false;
    }

    @Nullable
    public List<Leave> getSelectedItems() {
        if (getData() != null) {
            List<Leave> result = new ArrayList<>();
            for (int i = 0; i < selectionArray.size(); i++) {
                int position = selectionArray.keyAt(i);
                RecyclerViewAdapterSelector selector = selectionArray.valueAt(i);
                if(selector.getSelection()) {
                    result.add(getData().get(position));
                }
            }
            return result;
        }
        return null;
    }

    @NonNull
    public Observable<Leave> getItemClickObservable() {
        return onClickSubject.asObservable();
    }
}
