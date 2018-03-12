package com.softuvo.ipundit.activities;

/*
 * Created by Neha Kalia on 12/12/2017.
 */

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.softuvo.ipundit.R;
import com.softuvo.ipundit.adapters.FollowingListAdapter;
import com.softuvo.ipundit.api.ApiCallBack;
import com.softuvo.ipundit.config.App;
import com.softuvo.ipundit.config.AppConstant;
import com.softuvo.ipundit.config.AppPreferences;
import com.softuvo.ipundit.models.FollowingListModel;
import com.softuvo.ipundit.receivers.ConnectivityReceivers;
import com.softuvo.ipundit.utils.SnackbarUtil;
import com.softuvo.ipundit.views.CustomLinearLayout;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.softuvo.ipundit.config.AppConstant.APP_BACKGROUND;

public class FollowingListActivity extends BaseActivity {
    private Activity mContext;
    private FollowingListAdapter followingListAdapter;
    String userCommomgfrom;

    @BindView(R.id.ll_following_list_main)
    CustomLinearLayout llFollowingListMain;

    @BindView(R.id.rv_following_list)
    RecyclerView rvFollowingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_following_list);
        mContext=FollowingListActivity.this;
        ButterKnife.bind(mContext);
        setUpUI();
    }
    private void setUpUI() {
        if (ConnectivityReceivers.isConnected()) {
            if (AppPreferences.init(mContext).getString(APP_BACKGROUND) != null)
                Picasso.with(mContext).load(AppPreferences.init(mContext).getString(APP_BACKGROUND)).into(llFollowingListMain);
            if (getIntent().getStringExtra("usercomingfrom").equalsIgnoreCase("userProfile")) {
                userCommomgfrom=getIntent().getStringExtra("usercomingfrom");
                App.getApiHelper().getFollowingList(AppPreferences.init(mContext).getString(AppConstant.USER_ID), new ApiCallBack<FollowingListModel>() {
                    @Override
                    public void onSuccess(FollowingListModel followingListModel) {
                        rvFollowingList.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
                        List<FollowingListModel.Following> followingList = followingListModel.getFollowing();
                        followingListAdapter = new FollowingListAdapter(mContext,userCommomgfrom, followingList, new FollowingListAdapter.ItemClickListener() {
                            @Override
                            public void onClick(int position) {

                            }
                        });
                        rvFollowingList.setAdapter(followingListAdapter);
                    }

                    @Override
                    public void onFailure(String message) {

                    }
                });
            }
            else if(getIntent().getStringExtra("usercomingfrom").equalsIgnoreCase("punditsProfile")) {
                String path = getIntent().getStringExtra("id");
                userCommomgfrom=getIntent().getStringExtra("usercomingfrom");
                App.getApiHelper().getConnectFollowingList(path, new ApiCallBack<FollowingListModel>() {
                    @Override
                    public void onSuccess(FollowingListModel followingListModel) {
                        rvFollowingList.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
                        List<FollowingListModel.Following> followingList = followingListModel.getFollowing();
                        followingListAdapter = new FollowingListAdapter(mContext,userCommomgfrom, followingList, new FollowingListAdapter.ItemClickListener() {
                            @Override
                            public void onClick(int position) {

                            }
                        });
                        rvFollowingList.setAdapter(followingListAdapter);
                    }

                    @Override
                    public void onFailure(String message) {

                    }
                });
            }
        }
        else{
            SnackbarUtil.showWarningLongSnackbar(mContext,getResources().getString(R.string.internet_not_connected_text));
        }
    }
}
