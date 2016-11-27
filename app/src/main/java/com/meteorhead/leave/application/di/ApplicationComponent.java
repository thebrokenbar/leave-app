package com.meteorhead.leave.application.di;

import com.meteorhead.leave.application.Application;
import com.meteorhead.leave.database.realm.di.RealmModule;
import com.meteorhead.leave.leavedetails.di.LeaveDetailsComponent;
import com.meteorhead.leave.leavedetails.di.LeaveDetailsModule;
import com.meteorhead.leave.leavelist.di.LeaveListComponent;
import com.meteorhead.leave.leavelist.di.LeaveListModule;
import com.meteorhead.leave.mainactivity.di.ActivityComponent;
import com.meteorhead.leave.mainactivity.di.ActivityModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by wierzchanowskig on 12.11.2016.
 */
@Component(modules = {ApplicationModule.class, RealmModule.class})
@Singleton
public interface ApplicationComponent {
    void inject(Application application);
    ActivityComponent plus(ActivityModule module);
}
