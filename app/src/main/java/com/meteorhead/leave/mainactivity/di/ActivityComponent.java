package com.meteorhead.leave.mainactivity.di;

import com.meteorhead.leave.mainactivity.MainActivity;
import dagger.Subcomponent;

/**
 * Created by wierzchanowskig on 26.11.2016.
 */
@ActivityScope
@Subcomponent(modules = {ActivityModule.class})
public interface ActivityComponent {
    void inject(MainActivity activity);
}
