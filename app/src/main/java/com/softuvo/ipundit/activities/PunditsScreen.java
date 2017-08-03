package com.softuvo.ipundit.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.softuvo.ipundit.R;
import com.softuvo.ipundit.adapters.AllUsersDetailsAdapter;
import com.softuvo.ipundit.api.ApiCallBack;
import com.softuvo.ipundit.config.App;
import com.softuvo.ipundit.models.UserDetailsAndMatchDetailsModel;
import com.softuvo.ipundit.receivers.ConnectivityReceivers;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PunditsScreen extends BaseActivity {
    private Activity mContext;
    private AllUsersDetailsAdapter allUsersDetailsAdapter;
    private SwipeRefreshLayout swipeDownRefreshUserDetails;

    @BindView(R.id.rv_user_details)
    RecyclerView rvUserDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pundits_screen);
        mContext = PunditsScreen.this;
        ButterKnife.bind(mContext);
        getUSerDetailsList();
        swipeDownRefreshUserDetails = (SwipeRefreshLayout) findViewById(R.id.swiperefresh_userdetails);
        swipeDownRefreshUserDetails.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeDownRefreshUserDetails.setRefreshing(false);
                        getUSerDetailsList();

                    }
                }, 2000);
            }
        });
    }

    private void getUSerDetailsList() {
        if (ConnectivityReceivers.isConnected()) {
            App.getApiHelper().getUserDetailsAndMatchDetails(new ApiCallBack<UserDetailsAndMatchDetailsModel>() {
                @Override
                public void onSuccess(UserDetailsAndMatchDetailsModel userDetailsAndMatchDetailsModel) {
                    rvUserDetails.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
                    final List<UserDetailsAndMatchDetailsModel.UsersList> userDetailsList = userDetailsAndMatchDetailsModel.getUsersList();
                    allUsersDetailsAdapter = new AllUsersDetailsAdapter(mContext, userDetailsList, new AllUsersDetailsAdapter.ItemClickListener() {
                        @Override
                        public void onClick(int position) {
                            Intent intent =new Intent(mContext,LiveListeningActivity.class);
                            intent.putExtra("userComingFrom", "pundits");
                            intent.putExtra("mUserDatum", userDetailsList.get(position));
                            startActivity(intent);
                        }
                    });
                    rvUserDetails.setAdapter(allUsersDetailsAdapter);
                    allUsersDetailsAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(String message) {

                }
            });
        }

    }
}
