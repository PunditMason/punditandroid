package com.softuvo.ipundit.utils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;


public class CommanUtil {

    public static boolean checkAndRequestPermission(Activity activity, String permission, int REQUEST_CODE) {
        if (ContextCompat.checkSelfPermission(activity, permission)
                == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            ActivityCompat.requestPermissions(activity,
                    new String[]{permission},
                    REQUEST_CODE);
            return false;
        }
    }
}

