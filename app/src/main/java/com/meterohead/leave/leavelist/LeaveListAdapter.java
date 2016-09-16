package com.meterohead.leave.leavelist;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.meterohead.leave.R;
import com.meterohead.leave.databinding.LeaveListItemBinding;
import com.meterohead.leave.models.Leave;

import java.util.Collection;

/**
 * Created by Lenovo on 2016-09-13.
 */

public class LeaveListAdapter extends RecyclerView.Adapter<LeaveViewHolder> {
    private ObservableList<Leave> itemsList;

    public LeaveListAdapter(ObservableList<Leave> itemsList) {
        this.itemsList = itemsList;
    }

    @Override
    public LeaveViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LeaveListItemBinding viewBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.leave_list_item, parent, false);
        return new LeaveViewHolder(viewBinding);
    }

    @Override
    public void onBindViewHolder(LeaveViewHolder holder, int position) {
        LeaveListItemBinding itemBinding = holder.getItemBinding();
        itemBinding.setLeave(itemsList.get(position));
        itemBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public void setItemsList(@NonNull Collection<Leave> items) {
        if(items instanceof ObservableList) {
            itemsList = (ObservableList<Leave>) items;
        } else {
            itemsList = new ObservableArrayList<>();
            itemsList.addAll(items);
        }
    }
}
