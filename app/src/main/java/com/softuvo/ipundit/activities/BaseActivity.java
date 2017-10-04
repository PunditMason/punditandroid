package com.softuvo.ipundit.activities;

import android.Manifest;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.google.firebase.messaging.FirebaseMessaging;
import com.softuvo.ipundit.R;
import com.softuvo.ipundit.config.AppConstant;
import com.softuvo.ipundit.config.NotificationConstants;
import com.softuvo.ipundit.receivers.ConnectivityReceivers;
import com.softuvo.ipundit.utils.NotificationUtils;

import java.util.ArrayList;
import java.util.List;

public class BaseActivity extends AppCompatActivity implements ConnectivityReceivers.ConnectivityListener {

    public BroadcastReceiver mRegistrationBroadcastReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        fireLocalNotification();
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
     /*   String message;
        if (isConnected) {
            message = getResources().getString(R.string.internet_connected_text);
            SnackbarUtil.showSuccessLongSnackbar(BaseActivity.this, message);

        } else {
            message = getResources().getString(R.string.internet_not_connected_text);
            SnackbarUtil.showErrorLongSnackbar(BaseActivity.this, message);

        }*/
    }


    public void disableUserIntraction() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    public void enableUserIntraction() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    public void hideKeyboard() {
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
        }
    }

    public boolean checkForPermission() {
        int permissionCAMERA = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        int storagePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int audioRecordPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (storagePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (permissionCAMERA != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (audioRecordPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.RECORD_AUDIO);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), AppConstant.MY_PERMISSIONS_REQUEST_CAMERA);
            return false;
        }

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case AppConstant.MY_PERMISSIONS_REQUEST_CAMERA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    //Permission Granted Successfully. Write working code here.
                } else {
                    checkForPermission();
                }
                break;
        }
    }

    public void fireLocalNotification(){

    mRegistrationBroadcastReceiver =new BroadcastReceiver() {
        @Override
        public void onReceive (Context context, Intent intent){
            if (intent.getAction().equals(NotificationConstants.REGISTRATION_COMPLETE)) {
                FirebaseMessaging.getInstance().subscribeToTopic(NotificationConstants.TOPIC_GLOBAL);
            } else if (intent.getAction().equals(NotificationConstants.PUSH_NOTIFICATION)) {
                String message = intent.getStringExtra("message");
                //Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(BaseActivity.this);
                mBuilder.setSmallIcon(R.drawable.notifiction_icon);
                mBuilder.setContentTitle("Pundit");
                mBuilder.setContentText(message);
               /* Intent notificationIntent = new Intent(BaseActivity.this, BaseActivity.class);
                PendingIntent contentIntent = PendingIntent.getActivity(BaseActivity.this, 0, notificationIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);*/
//                mBuilder.setContentIntent(contentIntent);
                NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                manager.notify(0, mBuilder.build());
            }
        }
    };

}

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(NotificationConstants.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(NotificationConstants.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }
}
