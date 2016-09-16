package com.meterohead.leave.leavelist;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.databinding.ObservableList;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.meterohead.leave.mainactivity.ToolbarViewModel;
import com.meterohead.leave.models.Leave;

import java.util.Collection;

/**
 * Created by Lenovo on 2016-09-10.
 */

public class LeaveListViewModel extends BaseObservable {

    private ToolbarViewModel toolbarViewModel;
    private ObservableList<Leave> itemsList;

    public LeaveListViewModel(ToolbarViewModel toolbarViewModel) {
        this.toolbarViewModel = toolbarViewModel;
    }

    public ToolbarViewModel getToolbarViewModel() {
        return toolbarViewModel;
    }

    @BindingAdapter("items")
    public static void setItems(View view, Collection<Leave> items) {
        RecyclerView recyclerView = (RecyclerView) view;
        LeaveListAdapter adapter = (LeaveListAdapter) recyclerView.getAdapter();
        adapter.setItemsList(items);
    }

    @Bindable
    public Collection<Leave> getItems(){
        return null;
    }

}
