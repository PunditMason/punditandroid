package com.softuvo.ipundit.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.softuvo.ipundit.R;
import com.softuvo.ipundit.api.ApiCallBack;
import com.softuvo.ipundit.config.App;
import com.softuvo.ipundit.config.AppConstant;
import com.softuvo.ipundit.config.AppPreferences;
import com.softuvo.ipundit.models.AboutUsModel;
import com.softuvo.ipundit.receivers.ConnectivityReceivers;
import com.softuvo.ipundit.utils.SnackbarUtil;
import com.softuvo.ipundit.views.CustomRelativeLayout;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.softuvo.ipundit.config.AppConstant.APP_BACKGROUND;

public class AboutUsActivity extends BaseActivity {
    private Activity mContext;
    private String privacyPolicy;

    @BindView(R.id.tv_aboutus_content)
    TextView tvAboutUsContent;

    @BindView(R.id.btn_privacy_policy)
    Button btnPrivacyPolicy;

    @BindView(R.id.rl_about_us_main_background)
    CustomRelativeLayout rlAboutUsMainBackground;

    @BindView(R.id.progress_bar_about_us)
    ProgressBar progressBarAboutUs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        mContext = AboutUsActivity.this;
        ButterKnife.bind(mContext);
        getAboutPrivacyContent();

    }

    private void getAboutPrivacyContent() {
        if (ConnectivityReceivers.isConnected()) {
            if (AppPreferences.init(mContext).getString(APP_BACKGROUND) != null)
                Picasso.with(mContext).load(AppPreferences.init(mContext).getString(APP_BACKGROUND)).into(rlAboutUsMainBackground);
            progressBarAboutUs.setVisibility(View.VISIBLE);
            disableUserIntraction();
            App.getApiHelper().getAboutUs(new ApiCallBack<AboutUsModel>() {
                @Override
                public void onSuccess(AboutUsModel aboutUsModel) {
                    progressBarAboutUs.setVisibility(View.GONE);
                    enableUserIntraction();
                    if (aboutUsModel != null) {
                        if ((aboutUsModel.getData().get(0).getContent() != null) && (aboutUsModel.getData().get(1).getContent() != null))
                            tvAboutUsContent.setText(aboutUsModel.getData().get(0).getContent());
                        privacyPolicy = aboutUsModel.getData().get(1).getContent();
                    }
                }

                @Override
                public void onFailure(String message) {
                    SnackbarUtil.showErrorLongSnackbar(mContext, message);
                }
            });
        }
        else{
            SnackbarUtil.showWarningLongSnackbar(mContext,getResources().getString(R.string.internet_not_connected_text));
        }

    }

    @OnClick(R.id.btn_privacy_policy)
    public void gotoPrivacyPolicy() {
        Intent intent = new Intent(mContext, PrivacyPolicyActivity.class);
        intent.putExtra(AppConstant.PRIVACY_POLICY, privacyPolicy);
        startActivity(intent);

    }
}
