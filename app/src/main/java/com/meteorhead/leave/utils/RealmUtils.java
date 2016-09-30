package com.meteorhead.leave.utils;

/**
 * Created by wierzchanowskig on 26.09.2016.
 */

public class RealmUtils {
    public static String dot(String... fields) {
        String result = "";
        for (int i = 0; i< fields.length; i++) {
            if(i > 0) {
                result += ".";
            }
            result += fields[i];
        }
        return result;
    }

}
