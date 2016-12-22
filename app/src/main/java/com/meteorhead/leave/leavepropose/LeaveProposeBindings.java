package com.meteorhead.leave.leavepropose;

import android.content.res.Resources;
import android.databinding.BindingAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.meteorhead.leave.R;
import com.meteorhead.leave.models.Leave;

import org.joda.time.Days;
import org.joda.time.LocalDate;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by wierzchanowskig on 30.10.2016.
 */

public class LeaveProposeBindings {
    public interface OnLeavePropositionClickListener {
        void onClick(Leave leaveObject);
    }

    @BindingAdapter({"items", "onItemClick"})
    public static void setEntries(LinearLayout listLayout, List<Leave> entries,
                                  final OnLeavePropositionClickListener onItemClickListener) {
        if (entries != null) {
            listLayout.removeAllViews();
            LayoutInflater inflater = LayoutInflater.from(listLayout.getContext());
            for (final Leave entry : entries) {
                TextView textView =
                    (TextView) inflater.inflate(android.R.layout.simple_list_item_1, listLayout,
                        false);
                Resources res = listLayout.getContext().getResources();
                int totalDaysCount = Days.daysBetween(LocalDate.fromDateFields(entry.getDateStart()),
                        LocalDate.fromDateFields(entry.getDateEnd())).getDays() + 1;
                String days =
                    res.getQuantityString(R.plurals.numberOfDays, totalDaysCount, totalDaysCount);
                String text =
                    SimpleDateFormat.getDateInstance().format(entry.getDateStart())
                        + " - "
                        + SimpleDateFormat.getDateInstance().format(entry.getDateEnd())
                        + " ("
                        + days
                        + ")";
                textView.setText(text);
                textView.setOnClickListener(view -> onItemClickListener.onClick(entry));
                listLayout.addView(textView);
            }
        }
    }
}
