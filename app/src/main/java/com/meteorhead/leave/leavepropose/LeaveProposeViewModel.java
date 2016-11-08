package com.meteorhead.leave.leavepropose;

import android.databinding.Bindable;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;

import com.android.databinding.library.baseAdapters.BR;
import com.meteorhead.leave.ViewModel;
import com.meteorhead.leave.database.dbabstract.HolidaysDbService;
import com.meteorhead.leave.database.realm.HolidaysRealmService;
import com.meteorhead.leave.models.Holiday;
import com.meteorhead.leave.models.Leave;
import com.meteorhead.leave.models.helpers.WorkingDays;
import com.meteorhead.leave.models.helpers.impl.RealmWorkingDays;

import org.joda.time.Days;
import org.joda.time.LocalDate;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.meteorhead.leave.models.Leave.AUTUMN;
import static com.meteorhead.leave.models.Leave.SPRING;
import static com.meteorhead.leave.models.Leave.SUMMER;
import static com.meteorhead.leave.models.Leave.WINTER;

/**
 * Created by wierzchanowskig on 30.10.2016.
 */

public class LeaveProposeViewModel extends ViewModel {
    private static final int DEFAULT_NUMBER_OF_DAYS = 5;
    private static final int SEEK_BAR_MAXIMUM_VALUE = 30;
    private static final int SEEK_BAR_MINIMUM_VALUE = 1;
    private static final int MAX_PROPOSITIONS = 10;

    @NonNull
    private Leave leaveObject;
    private boolean appBarCollapsed;
    private int days = DEFAULT_NUMBER_OF_DAYS;

    private int selectedYear = 2016;
    private List<Leave> proposedLeave;

    public LeaveProposeViewModel() {
        this.leaveObject = new Leave();
    }

    public AppBarLayout.OnOffsetChangedListener onBarCollapseListener() {
        return (appBarLayout, verticalOffset) -> {
            if (verticalOffset == 0) {
                setAppBarCollapsed(false);
            } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                setAppBarCollapsed(true);
            }
        };
    }

    @Bindable
    public boolean isAppBarCollapsed() {
        return appBarCollapsed;
    }

    @Bindable
    public void setAppBarCollapsed(boolean appBarCollapsed) {
        this.appBarCollapsed = appBarCollapsed;
        notifyPropertyChanged(BR.appBarCollapsed);
    }

    @NonNull
    @Bindable
    public Leave getLeaveObject() {
        return leaveObject;
    }

    @Bindable
    public void setLeaveObject(@NonNull Leave leaveObject) {
        this.leaveObject = leaveObject;
        notifyPropertyChanged(BR.leaveObject);
    }

    @Bindable
    public int getDays() {
        return days;
    }

    @Bindable
    public void setDays(int days) {
        this.days = Math.max(SEEK_BAR_MINIMUM_VALUE,days);
        notifyPropertyChanged(BR.days);
    }

    @Bindable
    public int getSeekBarMaximumValue() {
        return SEEK_BAR_MAXIMUM_VALUE;
    }

    @Bindable
    public int getCurrentYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    @Bindable
    public List<Leave> getProposedLeave() {
        return proposedLeave;
    }

    @Bindable
    public void setProposedLeave(List<Leave> proposedLeave) {
        this.proposedLeave = proposedLeave;
        notifyPropertyChanged(BR.proposedLeave);
    }

    @Bindable
    public int getSelectedYear() {
        return selectedYear;
    }

    @Bindable
    public void setSelectedYear(int selectedYear) {
        this.selectedYear = selectedYear;
        notifyPropertyChanged(BR.selectedYear);
    }

    void showBestLeaveForSeason(@Leave.Season final int season, int days) {
        List<Leave> proposedLeave = new ArrayList<>();
        switch (season) {
            case SPRING:
                proposedLeave.addAll(getPropositions(
                        new LocalDate(selectedYear, 3, 1),
                        new LocalDate(selectedYear, 6, 30), days)
                );
                break;
            case SUMMER:
                proposedLeave.addAll(getPropositions(
                        new LocalDate(selectedYear, 6, 1),
                        new LocalDate(selectedYear, 9, 30), days)
                );
                break;
            case AUTUMN:
                proposedLeave.addAll(getPropositions(
                        new LocalDate(selectedYear, 9, 1),
                        new LocalDate(selectedYear, 12, 30), days)
                );
                break;
            case WINTER:
                proposedLeave.addAll(getPropositions(
                        new LocalDate(selectedYear, 1, 1),
                        new LocalDate(selectedYear, 3, 30), days)
                );
                proposedLeave.addAll(getPropositions(
                        new LocalDate(selectedYear, 12, 1),
                        new LocalDate(selectedYear, 12, 22), days)
                );
                break;
            default:
                throw new IllegalStateException("Undefined season");
        }
        sortPropositions(proposedLeave);
        setProposedLeave(trimPropositions(proposedLeave, MAX_PROPOSITIONS));
    }

    private void sortPropositions(List<Leave> proposedLeave) {
        Collections.sort(proposedLeave, (leave, t1) -> {
            int firstDaysBetween = Days.daysBetween(
                    new LocalDate(leave.getDateStart().getTime()),
                    new LocalDate(leave.getDateEnd().getTime())
            ).getDays();
            int secondDaysBetween = Days.daysBetween(
                    new LocalDate(t1.getDateStart().getTime()),
                    new LocalDate(t1.getDateEnd().getTime())
            ).getDays();

            return secondDaysBetween - firstDaysBetween;
        });
    }

    private List<Leave> trimPropositions(List<Leave> proposedLeave, int maxPropositions) {
        return proposedLeave.subList(0, Math.min(proposedLeave.size(), maxPropositions));
    }

    @NonNull
    private List<Leave> getPropositions(LocalDate from, LocalDate to, int days) {
        HolidaysDbService holidaysDbService = new HolidaysRealmService();
        List<Holiday> holidays = holidaysDbService.getHolidaysBetweenDates(
                Locale.getDefault().getLanguage(),
                from.toDate(), to.toDate());

        WorkingDays workingDays = new RealmWorkingDays(null, null);
        List<Leave> proposedLeave = new ArrayList<>();
        for (Holiday holiday : holidays) {
            LocalDate dayFrom = new LocalDate(holiday.getHolidayDate().getTime());
            if(dayFrom.getDayOfWeek() > 6) {
                dayFrom = dayFrom.plusDays(6 - dayFrom.getDayOfWeek());
            }
            workingDays.setStartDate(dayFrom.toDate());
            proposedLeave.add(new Leave(dayFrom.toDate(),
                    workingDays.getLastWorkingDay(days),
                    null, 0));
            if(dayFrom.getDayOfWeek() > 5) {
                dayFrom = dayFrom.plusDays(8 - dayFrom.getDayOfWeek());
            }
            workingDays.setStartDate(dayFrom.toDate());
            proposedLeave.add(new Leave(workingDays.getFirstWorkingDay(days),
                    dayFrom.toDate(),
                    null, 0));
        }

        return proposedLeave;
    }
}
