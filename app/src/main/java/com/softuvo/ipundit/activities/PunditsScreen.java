package com.softuvo.ipundit.activities;

/*
 * Created by Neha Kalia on 02/09/2017.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import com.softuvo.ipundit.R;
import com.softuvo.ipundit.adapters.AllUsersDetailsAdapter;
import com.softuvo.ipundit.api.ApiCallBack;
import com.softuvo.ipundit.config.App;
import com.softuvo.ipundit.config.AppConstant;
import com.softuvo.ipundit.config.AppPreferences;
import com.softuvo.ipundit.models.UserDetailsAndMatchDetailsModel;
import com.softuvo.ipundit.receivers.ConnectivityReceivers;
import com.softuvo.ipundit.utils.SnackbarUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PunditsScreen extends BaseActivity {
    private Activity mContext;
    private AllUsersDetailsAdapter allUsersDetailsAdapter;
    private SwipeRefreshLayout swipeDownRefreshUserDetails;

    @BindView(R.id.rv_user_details)
    RecyclerView rvUserDetails;

    @BindView(R.id.progress_bar_pundits)
    ProgressBar progressBarPundits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pundits_screen);
        mContext = PunditsScreen.this;
        ButterKnife.bind(mContext);
        getUserDetailsList(AppPreferences.init(mContext).getString(AppConstant.USER_ID));
        swipeDownRefreshUserDetails =  findViewById(R.id.swiperefresh_userdetails);
        swipeDownRefreshUserDetails.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeDownRefreshUserDetails.setRefreshing(false);
                        getUserDetailsList(AppPreferences.init(mContext).getString(AppConstant.USER_ID));

                    }
                }, 2000);
            }
        });
    }

    private void getUserDetailsList(String userId) {
        if (ConnectivityReceivers.isConnected()) {
            progressBarPundits.setVisibility(View.VISIBLE);
            disableUserIntraction();
            App.getApiHelper().getUserDetailsAndBroadacstDetails(userId,new ApiCallBack<UserDetailsAndMatchDetailsModel>() {
                @Override
                public void onSuccess(UserDetailsAndMatchDetailsModel userDetailsAndMatchDetailsModel) {
                    progressBarPundits.setVisibility(View.GONE);
                    enableUserIntraction();
                    rvUserDetails.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
                    if(userDetailsAndMatchDetailsModel!=null) {
                        final List<UserDetailsAndMatchDetailsModel.UsersList> userDetailsList = userDetailsAndMatchDetailsModel.getUsersList();
                        allUsersDetailsAdapter = new AllUsersDetailsAdapter(mContext, userDetailsList, new AllUsersDetailsAdapter.ItemClickListener() {
                            @Override
                            public void onClick(int position) {
                                Intent intent = new Intent(mContext, PunditsProfileActivity.class);
                                intent.putExtra("userComingFrom", "pundits");
                                intent.putExtra("mUserDatum", userDetailsList.get(position));
                                startActivity(intent);
                            }
                        });
                        rvUserDetails.setAdapter(allUsersDetailsAdapter);
                        allUsersDetailsAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(String message) {
                    progressBarPundits.setVisibility(View.GONE);
                    enableUserIntraction();
                }
            });
        }
        else{
            progressBarPundits.setVisibility(View.GONE);
            enableUserIntraction();
            SnackbarUtil.showWarningLongSnackbar(mContext,getResources().getString(R.string.internet_not_connected_text));
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserDetailsList(AppPreferences.init(mContext).getString(AppConstant.USER_ID));
    }
}
