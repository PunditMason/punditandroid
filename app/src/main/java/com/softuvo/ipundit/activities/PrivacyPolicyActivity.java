package com.softuvo.ipundit.activities;

/*
 * Created by Neha Kalia on 12/07/2017.
 */

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import com.softuvo.ipundit.R;
import com.softuvo.ipundit.config.AppConstant;
import com.softuvo.ipundit.config.AppPreferences;
import com.softuvo.ipundit.views.CustomRelativeLayout;
import com.squareup.picasso.Picasso;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.softuvo.ipundit.config.AppConstant.APP_BACKGROUND;

public class PrivacyPolicyActivity extends BaseActivity {

    @BindView(R.id.tv_privacypolicy_content)
    TextView tvPrivacyPolicyContent;

    @BindView(R.id.rl_privacy_policy_main_background)
    CustomRelativeLayout rlPrivacyPolicyMainBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);
        Activity mContext = PrivacyPolicyActivity.this;
        ButterKnife.bind(mContext);
       /* if(AppPreferences.init(mContext).getString(APP_BACKGROUND)!=null)
        Picasso.with(mContext).load(AppPreferences.init(mContext).getString(APP_BACKGROUND)).into(rlPrivacyPolicyMainBackground);*/
        if(getIntent().getStringExtra(AppConstant.PRIVACY_POLICY)!=null) {
            tvPrivacyPolicyContent.setText(getIntent().getStringExtra(AppConstant.PRIVACY_POLICY));
        }

    }
}
