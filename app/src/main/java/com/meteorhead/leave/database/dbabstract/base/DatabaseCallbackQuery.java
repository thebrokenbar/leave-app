package com.meteorhead.leave.database.dbabstract.base;

/**
 * Created by wierzchanowskig on 05.10.2016.
 */

public interface DatabaseCallbackQuery<T> {
    void onSuccess(T result);
    void onError(Throwable error);
}
