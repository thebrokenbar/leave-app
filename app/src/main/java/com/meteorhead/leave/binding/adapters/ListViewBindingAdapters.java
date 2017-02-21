package com.meteorhead.leave.binding.adapters;

import android.databinding.BindingAdapter;
import android.support.annotation.LayoutRes;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.meteorhead.leave.models.Leave;
import java.util.List;

/**
 * Created by wierzchanowskig on 19.02.2017.
 */

public class ListViewBindingAdapters {
    @BindingAdapter(value = { "items", "itemLayout" }, requireAll = false)
    public static void bindList(ListView listView, List<Leave> leaveList,
        @LayoutRes int itemLayoutId) {
        if(leaveList == null) {
            return;
        }
        if (itemLayoutId == 0) {
            itemLayoutId = android.R.layout.simple_list_item_1;
        }
        listView.setAdapter(new ArrayAdapter<>(listView.getContext(), itemLayoutId,
            android.R.id.text1, leaveList));
    }
}
