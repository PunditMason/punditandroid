package com.softuvo.ipundit.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.softuvo.ipundit.R;
import com.softuvo.ipundit.adapters.PodcastAdapter;
import com.softuvo.ipundit.adapters.TeamMatchsAdapter;
import com.softuvo.ipundit.api.ApiCallBack;
import com.softuvo.ipundit.config.App;
import com.softuvo.ipundit.config.AppPreferences;
import com.softuvo.ipundit.models.PodcastDetailsModel;
import com.softuvo.ipundit.receivers.ConnectivityReceivers;
import com.softuvo.ipundit.utils.SnackbarUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.softuvo.ipundit.config.AppConstant.USER_ID;

public class TeamMatchListActivity extends AppCompatActivity {
    private Activity mContext;

    private TeamMatchsAdapter teamMatchsAdapter;

    @BindView(R.id.rv_padcast_main)
    RecyclerView rvPodCastMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_match_list);
        mContext=TeamMatchListActivity.this;
        ButterKnife.bind(mContext);
        getMatchDetails(getIntent().getStringExtra("mTeamId"));
    }

    private void getMatchDetails(String mTeamId) {
        if (ConnectivityReceivers.isConnected()) {
            Map<String, String> mountMap = new HashMap<>();
            mountMap.put("team_id", mTeamId);
            App.getApiHelper().getMatchTeamsAndPodcasts(mountMap, new ApiCallBack<PodcastDetailsModel>() {
                @Override
                public void onSuccess(final PodcastDetailsModel podcastDetailsModel) {
                    rvPodCastMain.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
                    if (podcastDetailsModel != null) {
                        List<PodcastDetailsModel.Match> podcastList = podcastDetailsModel.getMatch();
                        teamMatchsAdapter = new TeamMatchsAdapter(mContext, podcastList, new TeamMatchsAdapter.ItemClickListener() {
                            @Override
                            public void onClick(int position) {
                                Intent intent=new Intent(mContext,PodcastDetailsActivity.class);
                                intent.putExtra("mPodcastChannelDetails",podcastDetailsModel.getMatch().get(position));
                                intent.putExtra("baseImageUrl",podcastDetailsModel.getMatch().get(position).getLeagueIcon());
                                startActivity(intent);
                            }
                        });
                        rvPodCastMain.setAdapter(teamMatchsAdapter);
                    }
                }

                @Override
                public void onFailure(String message) {
                    Log.e("===",message);
                }
            });

        }
        else{
            SnackbarUtil.showWarningLongSnackbar(mContext, getResources().getString(R.string.internet_not_connected_text));
        }
    }
}
