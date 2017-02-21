package com.meteorhead.leave.selecting.seasons;

import android.databinding.Bindable;
import com.android.databinding.library.baseAdapters.BR;
import com.meteorhead.leave.base.ViewModel;
import com.meteorhead.leave.models.Leave;
import java.util.ArrayList;
import java.util.List;
import org.joda.time.LocalDate;

import static com.meteorhead.leave.Constants.AVAILABLE_YEARS_COUNT;

/**
 * Created by wierzchanowskig on 18.02.2017.
 */

public class SeasonsViewModel extends ViewModel {
    private SeasonsViewActions viewActions;
    private int selectedYear;

    public SeasonsViewModel(SeasonsViewActions viewActions) {
        this.viewActions = viewActions;
    }

    @Bindable
    public List<Integer> getAvailableYears(){
        ArrayList<Integer> years = new ArrayList<>();
        LocalDate date = LocalDate.now();
        int year = date.getYear();
        while(years.size() < AVAILABLE_YEARS_COUNT) {
            years.add(year++);
        }
        return years;
    }

    @Bindable
    public int getSelectedYear() {
        return selectedYear;
    }

    public void setSelectedYear(int selectedYear) {
        this.selectedYear = selectedYear;
        notifyPropertyChanged(BR.selectedYear);
    }


    public void onSpringClick() {
        viewActions.showLeavePropositions(selectedYear, Leave.SPRING);
    }

    public void onSummerClick() {

    }

    public void onAutumnClick() {

    }

    public void onWinterClick() {

    }
}
