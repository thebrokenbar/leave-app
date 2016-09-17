package com.meterohead.leave.leavelist;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.meterohead.leave.R;
import com.meterohead.leave.databinding.LeaveListItemBinding;
import com.meterohead.leave.models.Leave;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

/**
 * Created by Lenovo on 2016-09-13.
 */

public class LeaveListAdapter extends RealmRecyclerViewAdapter<Leave, LeaveViewHolder> {

    public LeaveListAdapter(Context activityContext, OrderedRealmCollection<Leave> data) {
        super(activityContext, data, true);
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
        itemBinding.setLeave(getData().get(position));
        itemBinding.executePendingBindings();
    }




}
