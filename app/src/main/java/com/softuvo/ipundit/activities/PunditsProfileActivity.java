package com.softuvo.ipundit.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.softuvo.ipundit.R;
import com.softuvo.ipundit.api.ApiCallBack;
import com.softuvo.ipundit.config.ApiConstants;
import com.softuvo.ipundit.config.App;
import com.softuvo.ipundit.config.AppConstant;
import com.softuvo.ipundit.config.AppPreferences;
import com.softuvo.ipundit.models.FollowUnfollowModel;
import com.softuvo.ipundit.models.UserDetailsAndMatchDetailsModel;
import com.softuvo.ipundit.models.UserSearchLeagueModel;
import com.softuvo.ipundit.models.UserSearchSportsModel;
import com.softuvo.ipundit.receivers.ConnectivityReceivers;
import com.softuvo.ipundit.utils.SnackbarUtil;
import com.softuvo.ipundit.views.CustomTextView;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PunditsProfileActivity extends BaseActivity {
    private Activity mContext;
    private String punditsName;
    private String profilepic;
    private String followingCount;
    private String followerCount;
    private String punditsBio;
    private String live;
    private String punditsId;
    private int followStatus;

    @BindView(R.id.tv_pundits_name)
    CustomTextView tvPunditsName;

    @BindView(R.id.iv_pundits_profile_pic)
    ImageView ivPunditsProfilePic;

    @BindView(R.id.iv_follow_me)
    ImageView ivFollowMe;

    @BindView(R.id.iv_listen_live)
    ImageView ivListenLive;

    @BindView(R.id.tv_pundits_follower_count)
    CustomTextView tvPunditsFollowerCount;

    @BindView(R.id.tv_pundits_following_count)
    CustomTextView tvPunditsFollowingCount;

    @BindView(R.id.tv_pundits_bio)
    CustomTextView tvPunditsBio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pundits_profile);
        mContext = PunditsProfileActivity.this;
        ButterKnife.bind(this);
        setUIBackGrounds();
    }

    private void setUIBackGrounds() {
        if (getIntent().getStringExtra("userComingFrom").equalsIgnoreCase("pundits")) {
            if (getIntent().getSerializableExtra("mUserDatum") != null) {
                UserDetailsAndMatchDetailsModel.UsersList userDatum = (UserDetailsAndMatchDetailsModel.UsersList) getIntent().getSerializableExtra("mUserDatum");
                punditsName = userDatum.getFirstName();
                profilepic = userDatum.getAvatar();
                punditsBio = userDatum.getUserBio();
                followingCount = userDatum.getFollowingCount().toString();
                followerCount = userDatum.getFollowCount().toString();
                live = userDatum.getLive().toString();
                punditsId=userDatum.getId();
                if(userDatum.getFollowCheck().equalsIgnoreCase("TRUE"))
                    followStatus=1;
                else if(userDatum.getFollowCheck().equalsIgnoreCase("FALSE"))
                    followStatus=0;
            }
        } else if (getIntent().getStringExtra("userComingFrom").equalsIgnoreCase("sprotsUserSearch")) {
            if (getIntent().getSerializableExtra("mUserDatum") != null) {
                UserSearchSportsModel.UserDatum userSearchSportsDatum = (UserSearchSportsModel.UserDatum) getIntent().getSerializableExtra("mUserDatum");
                punditsName = userSearchSportsDatum.getFirstName();
                profilepic = userSearchSportsDatum.getAvatar();
                punditsBio = userSearchSportsDatum.getUserBio();
                followingCount = userSearchSportsDatum.getFollowingCount().toString();
                followerCount = userSearchSportsDatum.getFollowCount().toString();
                live = userSearchSportsDatum.getLive().toString();
                punditsId=userSearchSportsDatum.getId();
                if(userSearchSportsDatum.getFollowCheck().equalsIgnoreCase("TRUE"))
                followStatus=1;
                else if(userSearchSportsDatum.getFollowCheck().equalsIgnoreCase("FALSE"))
                    followStatus=0;

            }
        } else if (getIntent().getStringExtra("userComingFrom").equalsIgnoreCase("leaguesUserSearch")) {
            if (getIntent().getSerializableExtra("mUserDatum") != null) {
                UserSearchLeagueModel.Datum userSearchLeagueDatum = (UserSearchLeagueModel.Datum) getIntent().getSerializableExtra("mUserDatum");
                punditsName = userSearchLeagueDatum.getFirstName();
                profilepic = userSearchLeagueDatum.getAvatar();
                punditsBio = userSearchLeagueDatum.getUserBio();
                followingCount = userSearchLeagueDatum.getFollowingCount().toString();
                followerCount = userSearchLeagueDatum.getFollowCount().toString();
                live = userSearchLeagueDatum.getLive().toString();
                punditsId=userSearchLeagueDatum.getId();
                if(userSearchLeagueDatum.getFollowCheck().equalsIgnoreCase("TRUE"))
                followStatus=1;
                else if(userSearchLeagueDatum.getFollowCheck().equalsIgnoreCase("FALSE"))
                    followStatus=0;

            }
        }
        tvPunditsName.setText(punditsName);
        Picasso.with(mContext).load(ApiConstants.PROFILE_IMAGE_BASE_URL + profilepic).into(ivPunditsProfilePic);
        tvPunditsFollowerCount.setText(followingCount);
        tvPunditsFollowingCount.setText(followerCount);
        tvPunditsBio.setText(punditsBio);
        if (live.equals("1")) {
            ivListenLive.setImageResource(R.drawable.listen_live_red);
            ivListenLive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, LiveListeningActivity.class);
                    intent.putExtra("userComingFrom", getIntent().getStringExtra("userComingFrom"));
                    intent.putExtra("mUserDatum", getIntent().getSerializableExtra("mUserDatum"));
                    startActivity(intent);
                    finish();
                }
            });
        } else {
            ivListenLive.setImageResource(R.drawable.listen_live_gray);
            ivListenLive.setClickable(false);
        }
        if(followStatus==0)
            ivFollowMe.setImageResource(R.drawable.follow_me);
        else if(followStatus==1){
            ivFollowMe.setImageResource(R.drawable.unfollow);
        }
    }

    @OnClick(R.id.iv_follow_me)
    public void buttonFollowUnfollow() {
        if(AppPreferences.init(mContext).getString(AppConstant.USER_ID).equalsIgnoreCase(punditsId)){
            SnackbarUtil.showWarningShortSnackbar(mContext,getString(R.string.self_follow_text));
            ivFollowMe.setClickable(false);
        } else {
            String followUnfoloowPath;
            if (followStatus == 0) {
                ivFollowMe.setImageResource(R.drawable.follow_me);
                followUnfoloowPath = AppPreferences.init(mContext).getString(AppConstant.USER_ID) + "/" + punditsId;
                followunfollowUser(followUnfoloowPath);

            } else if (followStatus == 1) {
                ivFollowMe.setImageResource(R.drawable.unfollow);
                followUnfoloowPath = AppPreferences.init(mContext).getString(AppConstant.USER_ID) + "/" + punditsId;
                followunfollowUser(followUnfoloowPath);
            }
        }
    }
    private void followunfollowUser(final String stringPath) {
        if (ConnectivityReceivers.isConnected()) {
            App.getApiHelper().followunfollowlist(stringPath, new ApiCallBack<FollowUnfollowModel>() {
                @Override
                public void onSuccess(FollowUnfollowModel followUnfollowModel) {
                    if (followUnfollowModel != null) {
                        followStatus = followUnfollowModel.getData().getResult();
                        if (followStatus == 0) {
                            ivFollowMe.setImageResource(R.drawable.follow_me);
                            tvPunditsFollowerCount.setText(followUnfollowModel.getData().getCount().toString());
                        } else if (followStatus == 1) {
                            ivFollowMe.setImageResource(R.drawable.unfollow);
                            tvPunditsFollowerCount.setText(followUnfollowModel.getData().getCount().toString());
                        }
                    }
                }

                @Override
                public void onFailure(String message) {

//
                }
            });
        } else {
            enableUserIntraction();
            SnackbarUtil.showWarningLongSnackbar(mContext, getResources().getString(R.string.internet_not_connected_text));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUIBackGrounds();
    }
}
