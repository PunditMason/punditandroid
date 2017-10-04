package com.softuvo.ipundit.utils;

import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import com.softuvo.ipundit.R;
import com.softuvo.ipundit.config.App;

public class SnackbarUtil {
    public static void showSuccessLongSnackbar(Activity activity, String message) {
        Snackbar snackbar = Snackbar.make(activity.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(App.getAppContext(), R.color.colorGreen));
        snackbar.show();
    }

    public static void showSuccessShortSnackbar(Activity activity, String message) {
        Snackbar snackbar = Snackbar.make(activity.findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(App.getAppContext(), R.color.colorGreen));
        snackbar.show();
    }

    public static void showErrorLongSnackbar(Activity activity, String message) {
        Snackbar snackbar = Snackbar.make(activity.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(App.getAppContext(), R.color.colorRed));

        snackbar.show();
    }

    public static void showErrorShortSnackbar(Activity activity, String message) {
        Snackbar snackbar = Snackbar.make(activity.findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(App.getAppContext(), R.color.colorRed));
        snackbar.show();
    }

    public static void showWarningLongSnackbar(Activity activity, String message) {
        Snackbar snackbar = Snackbar.make(activity.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(App.getAppContext(), R.color.colorOrange));
        snackbar.show();
    }

    public static void showWarningShortSnackbar(Activity activity, String message) {
        Snackbar snackbar = Snackbar.make(activity.findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(App.getAppContext(), R.color.colorOrange));
        snackbar.show();
    }
}
