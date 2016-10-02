package com.meteorhead.leave.leavelist;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

/**
 * Created by wierzchanowskig on 01.10.2016.
 */

public class RecyclerViewAdapterSelector extends BaseObservable {

    private Boolean selection;

    public RecyclerViewAdapterSelector(boolean selection) {
        this.selection = selection;
    }

    @Bindable
    public boolean getSelection() {
        return selection;
    }

    @Bindable
    public void setSelecttion(Boolean selected) {
        this.selection = selected;
        notifyChange();
    }
}
