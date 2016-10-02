package com.meteorhead.leave.leavelist;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
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
import rx.functions.Action1;
import rx.subjects.PublishSubject;

/**
 * Created by Lenovo on 2016-09-13.
 */

public class LeaveListRecyclerAdapter extends RealmRecyclerViewAdapter<Leave, LeaveViewHolder> {

    private PublishSubject<Leave> onClickSubject = PublishSubject.create();
    private SparseArrayCompat<RecyclerViewAdapterSelector> selectionArray;
    private final LeaveListViewModel leaveListViewModel;
    private final List<Leave> recentlyRemovedItems = new ArrayList<>();

    public LeaveListRecyclerAdapter(Context activityContext, OrderedRealmCollection<Leave> data, LeaveListViewModel leaveListViewModel) {
        super(activityContext, data, true);
        this.selectionArray = new SparseArrayCompat<>(data.size());
        this.leaveListViewModel = leaveListViewModel;
        subscribeOnRemoveTask();
        subscribeOnRemoveUndoTask();
    }

    private void subscribeOnRemoveTask() {
        leaveListViewModel.getRemoveTaskObservable().subscribe(new Action1<Object>() {
            @Override
            public void call(Object o) {
                recentlyRemovedItems.clear();
                List<Leave> selectedItems = getSelectedItems();
                if(selectedItems != null) {
                    for (Leave item : selectedItems) {
                        Leave itemCopy = new Leave();
                        itemCopy.set(item);
                        itemCopy.setId(item.getId());
                        recentlyRemovedItems.add(itemCopy);
                        leaveListViewModel.getLeaveDbService().removeLeave(item);
                    }
                    leaveListViewModel.showUndoSnackBar(selectedItems.size());
                    setSelectionToAll(false);
                }
            }
        });
    }

    private void subscribeOnRemoveUndoTask() {
        leaveListViewModel.getRemoveUndoTaskObservable().subscribe(new Action1<Object>() {
            @Override
            public void call(Object o) {
                for (Leave item : recentlyRemovedItems) {
                    leaveListViewModel.getLeaveDbService().insertLeave(item);
                }
            }
        });
    }

    @Override
    public LeaveViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LeaveListItemBinding viewBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.leave_list_item, parent, false);
        return new LeaveViewHolder(viewBinding);
    }

    @Override
    public void onBindViewHolder(final LeaveViewHolder holder, final int position) {
        if(getData() != null) {
            final Leave element = getData().get(position);
            final int pos = holder.getAdapterPosition();
            final RecyclerViewAdapterSelector selector = selectionArray.get(pos, new RecyclerViewAdapterSelector(false));
            selectionArray.put(pos, selector);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(leaveListViewModel.getSelectionMode()) {
                        selector.setSelecttion(!selector.getSelection());
                        if(!isAnySelected()) {
                            leaveListViewModel.setSelectionMode(false);
                        }
                    } else {
                        onClickSubject.onNext(element);
                    }
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    leaveListViewModel.setSelectionMode(true);
                    selector.setSelecttion(!selector.getSelection());
                    return true;
                }
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
                selector.setSelecttion(selected);
            }
        }
        if(!selected) {
            leaveListViewModel.setSelectionMode(false);
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
