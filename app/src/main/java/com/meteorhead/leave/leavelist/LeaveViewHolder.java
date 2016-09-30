package com.meteorhead.leave.leavelist;

import android.support.v7.widget.RecyclerView;

import com.meteorhead.leave.databinding.LeaveListItemBinding;

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
