package com.softuvo.ipundit.activities;

/*
 * Created by Neha Kalia on 11/12/2017.
 */

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.softuvo.ipundit.R;
import com.softuvo.ipundit.adapters.PodcastAdapter;
import com.softuvo.ipundit.adapters.PodcastDetailsAdapter;
import com.softuvo.ipundit.models.PodcastDetailsModel;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class PodcastDetailsActivity extends BaseActivity {
    private Activity mContext;
    private List<PodcastDetailsModel.Match.Channel> podacstchannelList;
    PodcastDetailsAdapter podcastDetailsAdapter;
    @BindView(R.id.rv_podcast_details)
    RecyclerView rvPodcastDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_podcast_details);
        mContext=PodcastDetailsActivity.this;
        ButterKnife.bind(mContext);
        getPodcastDetails();
    }


    private void getPodcastDetails() {
        rvPodcastDetails.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        final PodcastDetailsModel.Match podcastDetailsList=(PodcastDetailsModel.Match)mContext.getIntent().getSerializableExtra("mPodcastChannelDetails");
        podacstchannelList=podcastDetailsList.getChannel();
        podcastDetailsAdapter = new PodcastDetailsAdapter(mContext, podacstchannelList);
        rvPodcastDetails.setAdapter(podcastDetailsAdapter);
    }
}
