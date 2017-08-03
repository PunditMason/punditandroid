package com.softuvo.ipundit.utils;

/**
 * Created by softuvo on 6/14/2017.
 */

public class StringUtil {
    public static boolean isEmpty(String data) {
        if (data.trim().length() == 0) {
            return true;
        }
        return false;
    }
}
