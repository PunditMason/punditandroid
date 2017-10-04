package com.softuvo.ipundit.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.softuvo.ipundit.R;
import com.softuvo.ipundit.api.ApiCallBack;
import com.softuvo.ipundit.config.App;
import com.softuvo.ipundit.config.AppConstant;
import com.softuvo.ipundit.models.DataModelBgImg;
import com.softuvo.ipundit.receivers.ConnectivityReceivers;
import com.softuvo.ipundit.utils.SnackbarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends BaseActivity {
    private AppCompatActivity mContext;
    private static int SPLASH_TIME_OUT =6000;

    @BindView(R.id.gif_view)
    ImageView gifView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mContext = SplashActivity.this;
        ButterKnife.bind(mContext);
        Glide.with(mContext).load(R.drawable.splash_gif).into(gifView);
        checkConnection();
        goNextActivity();
    }

    private void goNextActivity() {

       /* PropertyValuesHolder myView_Y = PropertyValuesHolder.ofFloat(findViewById(R.id.loading_view).TRANSLATION_Y, -40.0f);
        PropertyValuesHolder myView_X = PropertyValuesHolder.ofFloat(findViewById(R.id.loading_view).TRANSLATION_X, 0);
        ObjectAnimator waveOneAnimator = ObjectAnimator.ofPropertyValuesHolder(findViewById(R.id.loading_view), myView_X, myView_Y);
        waveOneAnimator.setRepeatCount(-1);        // -1 for infinite
        waveOneAnimator.setRepeatMode(ValueAnimator.REVERSE);
        waveOneAnimator.setDuration(300);
        waveOneAnimator.start();*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        App.getAppContext().setConnectivityListener(this);
    }

    private void checkConnection() {
        if (ConnectivityReceivers.isConnected()) {
            getBackgrounds();
        } else {
            SnackbarUtil.showWarningLongSnackbar(mContext,getResources().getString(R.string.internet_not_connected_text));
        }
    }


    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if (isConnected) {
            SnackbarUtil.showSuccessLongSnackbar(SplashActivity.this, getResources().getString(R.string.internet_connected_text));

            getBackgrounds();
        } else {
            SnackbarUtil.showSuccessLongSnackbar(SplashActivity.this, getResources().getString(R.string.internet_not_connected_text));

        }
    }


    private void getBackgrounds() {
        App.getApiHelper().getBackgroundImages(new ApiCallBack<DataModelBgImg>() {
            @Override
            public void onSuccess(final DataModelBgImg dataModelBgImg) {
                if (dataModelBgImg != null) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // This method will be executed once the timer is over
                            Intent intent = new Intent(mContext, DashboardActivity.class);
                            intent.putExtra(AppConstant.MAP_IMG, dataModelBgImg);
                            startActivity(intent);
                            finish();
                        }
                    }, SPLASH_TIME_OUT);

                }
            }

            @Override
            public void onFailure(String message) {
                SnackbarUtil.showErrorLongSnackbar(mContext, message);
            }
        });
    }
}

