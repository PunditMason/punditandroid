package com.softuvo.ipundit.config;

import android.content.ComponentCallbacks2;
import android.support.multidex.MultiDexApplication;

import com.crashlytics.android.Crashlytics;
import com.softuvo.ipundit.activities.LiveBroadCastingActivity;
import com.softuvo.ipundit.activities.LiveListeningActivity;
import com.softuvo.ipundit.api.ApiHelper;
import com.softuvo.ipundit.receivers.ConnectivityReceivers;

import org.joda.time.LocalDateTime;

import io.fabric.sdk.android.Fabric;

public class App extends MultiDexApplication {
    private static App appInstance;
    private static ApiHelper apiHelper;
    private static AppPreferences appPreferences;
    public LocalDateTime setDate, selectedDate;

    @Override
    public void onCreate() {
        super.onCreate();
        appInstance = this;
        apiHelper = ApiHelper.init();
        appPreferences = AppPreferences.init(appInstance);
        Fabric.with(this, new Crashlytics());
//        generateHash();
        //getHashKey();
    }

    public static App getAppContext() {
        return appInstance;
    }

    public static ApiHelper getApiHelper() {
        return apiHelper;
    }

    public static AppPreferences getAppPreferences() {
        return appPreferences;
    }


    public void setConnectivityListener(ConnectivityReceivers.ConnectivityListener connectivityListener) {
        ConnectivityReceivers.connectivityListener = connectivityListener;
    }

    public void setDate(LocalDateTime setDate) {
        this.setDate = setDate;
    }

    /*getting the current week*/
    public LocalDateTime getDate() {
        return setDate;
    }

    /*getting the selected week*/

    public LocalDateTime getSelected() {
        return selectedDate;
    }

    /*Setting selected week */
    public void setSelected(LocalDateTime selectedDate) {
        this.selectedDate = selectedDate;
    }


    /*private void generateHash() {
        try {
            @SuppressLint
                    ("PackageManagerGetSignatures") PackageInfo info = getPackageManager().getPackageInfo("com.softuvo.ipundit", PackageManager.GET_SIGNATURES);
            for (android.content.pm.Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException e) {
            Log.e("KeyHash:", e.toString());
        }
    }*/

//    private void getHashKey() {
//        try {
//            PackageInfo info = getPackageManager().getPackageInfo(
//                    getPackageName(), PackageManager.GET_SIGNATURES);
//            for (Signature signature : info.signatures) {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                Log.e("MY_KEY_HASH:",
//                        Base64.encodeToString(md.digest(), Base64.DEFAULT));
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//        } catch (NoSuchAlgorithmException e) {
//        } }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (level == ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN) {
            if (AppPreferences.init(appInstance).getString(AppConstant.User_CURRENT_STATE).equalsIgnoreCase("1")) {
                LiveBroadCastingActivity.stopBroadcastAppBackground();
            }
           else if (AppPreferences.init(appInstance).getString(AppConstant.User_CURRENT_STATE).equalsIgnoreCase("2")) {
                LiveListeningActivity liveListeningActivity = new LiveListeningActivity();
                liveListeningActivity.stopListenAppBackground();
            }
            else if (AppPreferences.init(appInstance).getString(AppConstant.User_CURRENT_STATE).equalsIgnoreCase("3")) {
                //Do Nothing
            }
        }
    }
}
