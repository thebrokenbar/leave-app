package com.meteorhead.leave.leavelist;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;
import com.meteorhead.leave.BaseFragment;
import com.meteorhead.leave.R;
import com.meteorhead.leave.database.realm.LeaveRealmService;
import com.meteorhead.leave.databinding.FragmentLeaveListBinding;
import com.meteorhead.leave.leavedetails.LeaveDetailsActivity;
import com.meteorhead.leave.leavepropose.LeaveProposeActivity;
import com.meteorhead.leave.mainactivity.ActivityViewModel;
import com.meteorhead.leave.mainactivity.MainActivityController;
import com.meteorhead.leave.models.Leave;

import java.util.ArrayList;

import rx.subjects.PublishSubject;

import static com.meteorhead.leave.leavelist.LeaveListResult.RESULT_CODE_ADD;
import static com.meteorhead.leave.leavelist.LeaveListResult.RESULT_CODE_REMOVE;

/**
 * Created by Lenovo on 2016-09-10.
 */
@FragmentWithArgs
public class LeaveListFragment extends BaseFragment implements LeaveListFragmentController {
    public static final int LEAVE_EDIT_FORM = 101;

    LeaveListViewModel viewModel;
    private ActivityViewModel activityViewModel;
    MainActivityController activityController;
    private RecyclerView rvLeaveList;
    private FragmentLeaveListBinding binding;
    private final Observable.OnPropertyChangedCallback selectionPropertyChangedCallback = new Observable.OnPropertyChangedCallback() {
        @Override
        public void onPropertyChanged(Observable observable, int i) {
            getActivity().invalidateOptionsMenu();
        }
    };

    @NonNull
    public static LeaveListFragment newInstance() {
        return new LeaveListFragmentBuilder().build();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activityController = (MainActivityController) context;

        setHasOptionsMenu(true);

        activityViewModel = activityController.getViewModel();
        LeaveRealmService leaveRealmService = new LeaveRealmService();
        viewModel = new LeaveListViewModel(this, leaveRealmService);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_leave_list,
                container, false);
        LeaveListViewHandler viewHandler = new LeaveListViewHandler(viewModel);
        binding.setViewModel(viewModel);
        binding.setViewHandler(viewHandler);
        rvLeaveList = (RecyclerView) binding.getRoot().findViewById(R.id.rvLeaveList);
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        viewModel.onStart();
        binding.getViewModel().isSelectionMode
                .addOnPropertyChangedCallback(selectionPropertyChangedCallback);
    }

    @Override
    public void onStop() {
        super.onStop();
        binding.getViewModel().isSelectionMode
                .removeOnPropertyChangedCallback(selectionPropertyChangedCallback);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(),
                LinearLayoutManager.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.item_divider));
        rvLeaveList.addItemDecoration(dividerItemDecoration);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.list_menu, menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.details_menu_remove)
                .setVisible(binding.getViewModel().isSelectionMode.get());
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

    @Override
    public void addNewLeave() {
        Intent leaveDetailsActivityIntent = new Intent(getActivity(), LeaveDetailsActivity.class);
        startActivityForResult(leaveDetailsActivityIntent, LEAVE_EDIT_FORM);
    }

    @Override
    public void proposeNewLeave() {
        Intent leaveProposeActivityIntent = new Intent(getActivity(), LeaveProposeActivity.class);
        startActivityForResult(leaveProposeActivityIntent, LEAVE_EDIT_FORM);
    }

    @Override
    public void editLeave(Leave leaveObject) {
        Intent leaveDetailsActivityIntent = new Intent(getActivity(), LeaveDetailsActivity.class);
        leaveDetailsActivityIntent.putExtra(Leave.PARAM_NAME, leaveObject);
        startActivityForResult(leaveDetailsActivityIntent, LEAVE_EDIT_FORM);
    }

    @Override
    public rx.Observable<Boolean> showUndoSnackBar(int removedItemsCount) {
        final PublishSubject<Boolean> undoClickTaskPublishSubject = PublishSubject.create();
        Snackbar.make(rvLeaveList,
                getResources().getQuantityString(R.plurals.leave_removing_snackbar_text, removedItemsCount, removedItemsCount),
                Snackbar.LENGTH_LONG)
                .setAction(R.string.undo_action_text, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        undoClickTaskPublishSubject.onNext(true);
                    }
                })
                .setCallback(new Snackbar.Callback() {
                    @Override
                    public void onDismissed(Snackbar snackbar, int event) {
                        undoClickTaskPublishSubject.onNext(false);
                        super.onDismissed(snackbar, event);
                    }
                }).show();
        return undoClickTaskPublishSubject.asObservable();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == LEAVE_EDIT_FORM) {
            if(resultCode == 0) {
                return;
            }
            Leave leaveObject = data.getParcelableExtra(Leave.PARAM_NAME);
            switch (resultCode) {
                case RESULT_CODE_ADD:
                    viewModel.addOrUpdateLeave(leaveObject);
                    break;
                case RESULT_CODE_REMOVE:
                    leaveObject = viewModel.getLeaveDbService().getLeaveById(leaveObject.getId());
                    ArrayList<Leave> leavesToRemove = new ArrayList<Leave>(1);
                    leavesToRemove.add(leaveObject);
                    viewModel.removeLeaves(leavesToRemove);
                    break;
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
