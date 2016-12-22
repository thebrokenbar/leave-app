package com.meteorhead.leave.leavelist;

import android.databinding.BindingAdapter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.github.clans.fab.FloatingActionMenu;
import com.meteorhead.leave.R;
import com.meteorhead.leave.base.BaseView;
import com.meteorhead.leave.databinding.FragmentLeaveListBinding;
import com.meteorhead.leave.leavedetails.LeaveDetailsView;
import com.meteorhead.leave.leavelist.di.LeaveListModule;
import com.meteorhead.leave.leavepropose.LeaveProposeView;
import com.meteorhead.leave.models.Leave;

import java.util.Collection;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmResults;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

/**
 * Created by wierzchanowskig on 08.11.2016.
 */

public class LeaveListView extends BaseView<FragmentLeaveListBinding> implements LeaveListViewController {

    @BindView(R.id.rvLeaveList)
    protected RecyclerView rvLeaveList;

    @Inject
    protected LeaveListViewModel viewModel;
    @Inject
    protected LeaveListViewHandler viewHandler;

    private final android.databinding.Observable.OnPropertyChangedCallback selectionPropertyChangedCallback = new android.databinding.Observable.OnPropertyChangedCallback() {
        @Override
        public void onPropertyChanged(android.databinding.Observable observable, int i) {
            getActivity().invalidateOptionsMenu();
        }
    };

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        return super.onCreateView(inflater, container);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.list_menu, menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.details_menu_remove)
                .setVisible(viewModel.isSelectionMode.get());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.details_menu_remove:
                viewModel.onRemoveLeaveClick();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @NonNull
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_leave_list;
    }

    @Override
    protected void onViewBound(@NonNull FragmentLeaveListBinding binding) {
        ButterKnife.bind(this, binding.getRoot());
        getActivityComponent()
                .plus(new LeaveListModule(this))
                .inject(this);

        setHasOptionsMenu(true);
        binding.setViewModel(viewModel);
        binding.setViewHandler(viewHandler);

        viewModel.isSelectionMode.addOnPropertyChangedCallback(selectionPropertyChangedCallback);
        viewModel.onStart();
    }

    @Override
    protected void onViewUnbound(@NonNull FragmentLeaveListBinding binding) {
        binding.getViewModel().isSelectionMode
                .removeOnPropertyChangedCallback(selectionPropertyChangedCallback);
    }

    @Override
    public void showAddNewLeaveView() {
        showViewForResult(new LeaveDetailsView(), this::onLeaveEditResult);
    }

    @Override
    public void showEditLeaveView(Leave leaveObject) {
        Bundle viewParams = new Bundle();
        viewParams.putParcelable(Leave.PARAM_NAME, leaveObject);
        showViewForResult(new LeaveDetailsView(viewParams), this::onLeaveEditResult);
    }

    private void onLeaveEditResult(int resultCode, Bundle params) {
        switch (resultCode) {
            case LeaveListResult.RESULT_CODE_ADD_OR_EDIT:
                viewModel.addOrUpdateLeave(params.getParcelable(Leave.PARAM_NAME));
                break;
            case LeaveListResult.RESULT_CODE_REMOVE:
                Leave leaveToRemove = params.getParcelable(Leave.PARAM_NAME);
                viewModel.removeLeave(leaveToRemove.getId());
                break;
            default:
                throw new IllegalArgumentException("Unhandled result code");
        }
    }

    @Override
    public void showProposeNewLeaveView() {
        this.showViewForResult(new LeaveProposeView(), this::onLeaveEditResult);
    }

    @Override
    public Observable<Boolean> showUndoRemoveSnackBar(int removedItemsCount) {
        String text = getResources().getQuantityString(R.plurals.leave_removing_snackbar_text,
                removedItemsCount, removedItemsCount);
        PublishSubject<Boolean> removeSnackbarSubject = PublishSubject.create();
        Snackbar.make(rvLeaveList, text, Snackbar.LENGTH_LONG)
                .setAction(R.string.undo_action_text, view -> removeSnackbarSubject.onNext(true))
                .show();
        return removeSnackbarSubject.asObservable();
    }

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