package com.meteorhead.leave.bestleaves;

import android.databinding.Bindable;
import com.meteorhead.leave.BR;
import com.meteorhead.leave.Constants;
import com.meteorhead.leave.base.ViewModel;
import com.meteorhead.leave.models.Leave;
import com.meteorhead.leave.models.helpers.WorkingDays;
import java.text.ParseException;
import java.util.List;
import javax.inject.Inject;

import static com.meteorhead.leave.Constants.DEFAULT_DAYS_COUNT;

/**
 * Created by wierzchanowskig on 18.02.2017.
 */

public class BestLeavesViewModel extends ViewModel {

    private int year;
    @Leave.Season
    private int season;

    private Integer days = DEFAULT_DAYS_COUNT;

    @Inject
    WorkingDays workingDaysService;

    private List<Leave> leaves;

    @Bindable
    public List<Leave> getLeaves() {
        return leaves;
    }

    @Bindable
    public String getDaysString() {
        return this.days.toString();
    }

    public void setDaysString(String days) {
        if (!days.isEmpty()) {
            try {
                int iDays = Integer.parseInt(days);
                setDays(iDays);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

    @Bindable
    public Integer getDays() {
        return this.days;
    }
    
    public void setDays(Integer days) {
        if (days > Constants.MAX_LEAVE_DAYS) {
            days = Constants.MAX_LEAVE_DAYS;
        }
        this.days = days;
        notifyPropertyChanged(BR.days);
    }

    public void calculateLeaves() {
        this.notifyPropertyChanged(BR.leaves);
    }

    public BestLeavesViewModel(int year, @Leave.Season int season) {
        this.year = year;
        this.season = season;
    }
}
