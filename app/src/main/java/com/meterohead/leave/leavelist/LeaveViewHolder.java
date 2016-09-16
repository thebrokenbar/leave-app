package com.meterohead.leave.leavelist;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.meterohead.leave.databinding.LeaveListItemBinding;
import com.meterohead.leave.models.Leave;

/**
 * Created by Lenovo on 2016-09-13.
 */

public class LeaveViewHolder extends RecyclerView.ViewHolder {
    private LeaveListItemBinding itemBinding;

    public LeaveViewHolder(LeaveListItemBinding viewBinding) {
        super(viewBinding.getRoot());
        itemBinding = viewBinding;
    }

    public LeaveListItemBinding getItemBinding() {
        return itemBinding;
    }
}
