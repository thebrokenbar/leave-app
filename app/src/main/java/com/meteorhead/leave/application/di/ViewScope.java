package com.meteorhead.leave.application.di;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by wierzchanowskig on 12.11.2016.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface ViewScope {
}
