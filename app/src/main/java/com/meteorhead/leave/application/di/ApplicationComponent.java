package com.meteorhead.leave.application.di;

import com.meteorhead.leave.application.Application;
import com.meteorhead.leave.database.realm.di.RealmModule;
import com.meteorhead.leave.mainactivity.di.ActivityComponent;
import com.meteorhead.leave.mainactivity.di.ActivityModule;
import dagger.Component;
import javax.inject.Singleton;

/**
 * Created by wierzchanowskig on 12.11.2016.
 */
@Component(modules = {ApplicationModule.class, RealmModule.class})
@Singleton
public interface ApplicationComponent {
    void inject(Application application);
    ActivityComponent plus(ActivityModule module);
}
