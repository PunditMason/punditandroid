package com.softuvo.ipundit.utils;

import android.content.Context;
import android.widget.Toast;

/*
 * Created by Neha Kalia on 6/14/2017.
 */

public class ToastUtil {
    public static void showLongToast(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void showSmallToast(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
