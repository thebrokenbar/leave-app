package com.meterohead.leave.leavelist;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.databinding.ObservableList;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.meterohead.leave.database.dbabstract.ILeaveDbService;
import com.meterohead.leave.database.realm.LeaveRealmService;
import com.meterohead.leave.mainactivity.ToolbarViewModel;
import com.meterohead.leave.models.Leave;

import java.util.Collection;
import java.util.List;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by Lenovo on 2016-09-10.
 */

public class LeaveListViewModel extends BaseObservable {

    private ToolbarViewModel toolbarViewModel;
    ILeaveDbService leaveDbService;

    public LeaveListViewModel(ToolbarViewModel toolbarViewModel, ILeaveDbService leaveDbService) {
        this.toolbarViewModel = toolbarViewModel;
        this.leaveDbService = leaveDbService;
    }

    public ToolbarViewModel getToolbarViewModel() {
        return toolbarViewModel;
    }

    @BindingAdapter("items")
    public static void setItems(RecyclerView recyclerView, Collection<Leave> items) {
        RealmResults<Leave> realmResults = (RealmResults<Leave>) items;
        LeaveListAdapter adapter = new LeaveListAdapter(recyclerView.getContext(), realmResults);
        recyclerView.setAdapter(adapter);
        if(recyclerView.getLayoutManager() == null) {
            recyclerView.setLayoutManager(
                    new LinearLayoutManager(
                            recyclerView.getContext(),
                            LinearLayoutManager.VERTICAL,
                            false
                    )
            );
        }
    }

    @Bindable
    public Collection<Leave> getItemsList(){
        return leaveDbService.getAllLeaves();
    }



}
