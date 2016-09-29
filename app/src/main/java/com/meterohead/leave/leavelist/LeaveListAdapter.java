package com.meterohead.leave.leavelist;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meterohead.leave.R;
import com.meterohead.leave.databinding.LeaveListItemBinding;
import com.meterohead.leave.models.Leave;

import java.text.SimpleDateFormat;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;
import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by Lenovo on 2016-09-13.
 */

public class LeaveListAdapter extends RealmRecyclerViewAdapter<Leave, LeaveViewHolder> {

    private PublishSubject<Leave> onClickSubject = PublishSubject.create();

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
        if(getData() != null) {
            final Leave element = getData().get(position);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickSubject.onNext(element);
                }
            });
            LeaveListItemBinding itemBinding = holder.getItemBinding();
            itemBinding.setLeave(getData().get(position));
            itemBinding.executePendingBindings();
        }
    }

    public Observable<Leave> getItemClickObservable() {
        return onClickSubject.asObservable();
    }
}
