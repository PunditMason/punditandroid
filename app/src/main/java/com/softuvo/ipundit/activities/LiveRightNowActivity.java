package com.softuvo.ipundit.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.softuvo.ipundit.R;
import com.softuvo.ipundit.adapters.AllUsersDetailsAdapter;
import com.softuvo.ipundit.api.ApiCallBack;
import com.softuvo.ipundit.config.App;
import com.softuvo.ipundit.config.AppConstant;
import com.softuvo.ipundit.config.AppPreferences;
import com.softuvo.ipundit.models.UserDetailsAndMatchDetailsModel;
import com.softuvo.ipundit.receivers.ConnectivityReceivers;
import com.softuvo.ipundit.utils.SnackbarUtil;
import com.softuvo.ipundit.views.CustomRelativeLayout;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.softuvo.ipundit.config.AppConstant.APP_BACKGROUND;
import static com.softuvo.ipundit.config.AppConstant.PUNDITS_PAGE_BACKGROUND;

public class LiveRightNowActivity extends BaseActivity {
    private Activity mContext;
    private SwipeRefreshLayout swipeDownRefreshLive;
    private AllUsersDetailsAdapter allUsersDetailsAdapter;

    @BindView(R.id.rl_live_main)
    CustomRelativeLayout rlLiveMain;

    @BindView(R.id.rv_live_list)
    RecyclerView rvLiveList;

    @BindView(R.id.progress_bar_live)
    ProgressBar progressBarLive;

    @BindView(R.id.tv_no_data)
    TextView tvNoData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_right_now);
        mContext = LiveRightNowActivity.this;
        ButterKnife.bind(this);
        if (ConnectivityReceivers.isConnected()) {
            if (AppPreferences.init(mContext).getString(APP_BACKGROUND) != null)
                Picasso.with(mContext).load(AppPreferences.init(mContext).getString(PUNDITS_PAGE_BACKGROUND)).into(rlLiveMain);
        }
        else {
            SnackbarUtil.showWarningLongSnackbar(mContext, getResources().getString(R.string.internet_not_connected_text));
        }
        setData(AppPreferences.init(mContext).getString(AppConstant.USER_ID));
        swipeDownRefreshLive =  findViewById(R.id.swiperefresh_live);
        swipeDownRefreshLive.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeDownRefreshLive.setRefreshing(false);
                        setData(AppPreferences.init(mContext).getString(AppConstant.USER_ID));

                    }
                }, 2000);
            }
        });
    }

    private void setData(String userId) {
        if (ConnectivityReceivers.isConnected()) {
            progressBarLive.setVisibility(View.VISIBLE);
            disableUserIntraction();
            App.getApiHelper().getUserDetailsAndBroadacstDetails(userId,new ApiCallBack<UserDetailsAndMatchDetailsModel>() {
                @Override
                public void onSuccess(UserDetailsAndMatchDetailsModel userDetailsAndMatchDetailsModel) {
                    progressBarLive.setVisibility(View.GONE);
                    enableUserIntraction();
                    rvLiveList.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
                    if(userDetailsAndMatchDetailsModel!=null) {
                         final List<UserDetailsAndMatchDetailsModel.UsersList> userDetailsList = userDetailsAndMatchDetailsModel.getUsersList();
                        if (userDetailsList.size() > 0) {
                            tvNoData.setVisibility(View.GONE);
                            allUsersDetailsAdapter = new AllUsersDetailsAdapter(mContext, userDetailsList, new AllUsersDetailsAdapter.ItemClickListener() {
                                @Override
                                public void onClick(int position) {
                                    Intent intent = new Intent(mContext, PunditsProfileActivity.class);
                                    intent.putExtra("userComingFrom", "pundits");
                                    intent.putExtra("mUserDatum", userDetailsList.get(position));
                                    startActivity(intent);
                                }
                            });
                            rvLiveList.setAdapter(allUsersDetailsAdapter);
                            allUsersDetailsAdapter.notifyDataSetChanged();
                        }else {
                            rvLiveList.setAdapter(null);
                        }
                    }
                    else {
                        tvNoData.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onFailure(String message) {
                    progressBarLive.setVisibility(View.GONE);
                    enableUserIntraction();
                }
            });
        }
        else {
            progressBarLive.setVisibility(View.GONE);
            enableUserIntraction();
            SnackbarUtil.showWarningLongSnackbar(mContext, getResources().getString(R.string.internet_not_connected_text));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setData(AppPreferences.init(mContext).getString(AppConstant.USER_ID));
    }
}
