package com.softuvo.ipundit.activities;

/*
 * Created by Neha Kalia on 12/12/2017.
 */

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.softuvo.ipundit.R;
import com.softuvo.ipundit.adapters.FollowerListAdapter;
import com.softuvo.ipundit.api.ApiCallBack;
import com.softuvo.ipundit.config.App;
import com.softuvo.ipundit.config.AppConstant;
import com.softuvo.ipundit.config.AppPreferences;
import com.softuvo.ipundit.models.FollowerListModel;
import com.softuvo.ipundit.receivers.ConnectivityReceivers;
import com.softuvo.ipundit.utils.SnackbarUtil;
import com.softuvo.ipundit.views.CustomLinearLayout;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.softuvo.ipundit.config.AppConstant.APP_BACKGROUND;

public class FollowersListActivity extends BaseActivity {
    private Activity mContext;
    private FollowerListAdapter followerListAdapter;

    @BindView(R.id.ll_followers_list_main)
    CustomLinearLayout llFollowersListMain;

    @BindView(R.id.rv_followers_list)
    RecyclerView rvFollowerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followers_list);
        mContext = FollowersListActivity.this;
        ButterKnife.bind(mContext);
        setUpUI();
    }

    private void setUpUI() {
        if (ConnectivityReceivers.isConnected()) {
            if (AppPreferences.init(mContext).getString(APP_BACKGROUND) != null)
                Picasso.with(mContext).load(AppPreferences.init(mContext).getString(APP_BACKGROUND)).into(llFollowersListMain);
            if (getIntent().getStringExtra("usercomingfrom").equalsIgnoreCase("userProfile")) {
                App.getApiHelper().getFollowersList(AppPreferences.init(mContext).getString(AppConstant.USER_ID), new ApiCallBack<FollowerListModel>() {
                    @Override
                    public void onSuccess(FollowerListModel followerListModel) {
                        rvFollowerList.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
                        List<FollowerListModel.Follwer> followerList = followerListModel.getFollwers();
                        followerListAdapter = new FollowerListAdapter(mContext, followerList, new FollowerListAdapter.ItemClickListener() {
                            @Override
                            public void onClick(int position) {

                            }
                        });
                        rvFollowerList.setAdapter(followerListAdapter);
                    }

                    @Override
                    public void onFailure(String message) {

                    }
                });
            }
            else if(getIntent().getStringExtra("usercomingfrom").equalsIgnoreCase("punditsProfile")){
                String path=getIntent().getStringExtra("id");
                App.getApiHelper().getConnectFollowerList(path, new ApiCallBack<FollowerListModel>() {
                    @Override
                    public void onSuccess(FollowerListModel followerListModel) {
                        rvFollowerList.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
                        List<FollowerListModel.Follwer> followerList = followerListModel.getFollwers();
                        followerListAdapter = new FollowerListAdapter(mContext, followerList, new FollowerListAdapter.ItemClickListener() {
                            @Override
                            public void onClick(int position) {

                            }
                        });
                        rvFollowerList.setAdapter(followerListAdapter);
                    }

                    @Override
                    public void onFailure(String message) {

                    }
                });

            }
        } else {
            SnackbarUtil.showWarningLongSnackbar(mContext, getResources().getString(R.string.internet_not_connected_text));
        }
    }
}
