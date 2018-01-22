package com.softuvo.ipundit.activities;

/*
 * Created by Neha Kalia on 11/12/2017.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.softuvo.ipundit.R;
import com.softuvo.ipundit.adapters.PodcastAdapter;
import com.softuvo.ipundit.api.ApiCallBack;
import com.softuvo.ipundit.config.App;
import com.softuvo.ipundit.models.PodcastDetailsModel;
import com.softuvo.ipundit.receivers.ConnectivityReceivers;
import com.softuvo.ipundit.utils.SnackbarUtil;

import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class PodcastActivity extends BaseActivity {
    private Activity mContext;
    private PodcastAdapter podcastAdapter;

    @BindView(R.id.rv_padcast_main)
    RecyclerView rvPodCastMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_podcast);
        mContext=PodcastActivity.this;
        ButterKnife.bind(mContext);
        getPodcastDetails(getIntent().getStringExtra("punditsId"));
    }

    private void getPodcastDetails(String punditsId) {
        if (ConnectivityReceivers.isConnected()) {
            App.getApiHelper().getPodcastDetalis(punditsId, new ApiCallBack<PodcastDetailsModel>() {
                @Override
                public void onSuccess(final PodcastDetailsModel podcastDetailsModel) {
                    rvPodCastMain.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
                    if (podcastDetailsModel != null) {
                        List<PodcastDetailsModel.Match> podcastList = podcastDetailsModel.getMatch();
                        podcastAdapter = new PodcastAdapter(mContext, podcastList, new PodcastAdapter.ItemClickListener() {
                            @Override
                            public void onClick(int position) {
                                Intent intent=new Intent(mContext,PodcastDetailsActivity.class);
                                intent.putExtra("mPodcastChannelDetails",podcastDetailsModel.getMatch().get(position));
                                startActivity(intent);
                            }
                        });
                        rvPodCastMain.setAdapter(podcastAdapter);
                    }
                }

                @Override
                public void onFailure(String message) {

                }
            });
        }else{
            SnackbarUtil.showWarningLongSnackbar(mContext,getResources().getString(R.string.internet_not_connected_text));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getPodcastDetails(getIntent().getStringExtra("punditsId"));
    }

}
