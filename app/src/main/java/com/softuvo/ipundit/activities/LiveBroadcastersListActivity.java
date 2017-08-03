package com.softuvo.ipundit.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.softuvo.ipundit.R;
import com.softuvo.ipundit.adapters.LiveBroadcastersListAdapter;
import com.softuvo.ipundit.api.ApiCallBack;
import com.softuvo.ipundit.config.App;
import com.softuvo.ipundit.config.AppPreferences;
import com.softuvo.ipundit.models.LiveBroacastersListModel;
import com.softuvo.ipundit.models.MatchListListnerModel;
import com.softuvo.ipundit.models.StandingListeningModel;
import com.softuvo.ipundit.models.TeamSearchSportsModel;
import com.softuvo.ipundit.receivers.ConnectivityReceivers;
import com.softuvo.ipundit.utils.SnackbarUtil;
import com.softuvo.ipundit.views.CustomRelativeLayout;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.softuvo.ipundit.config.AppConstant.APP_BACKGROUND;

public class LiveBroadcastersListActivity extends AppCompatActivity {
    private Activity mContext;
    LiveBroadcastersListAdapter liveBroadcastersListAdapter;
    static String matchOrcontestentId, userselection;

    @BindView(R.id.rv_broadcasters_list)
    RecyclerView rvBroadcastersList;

    @BindView(R.id.rl_live_broadcasters)
    CustomRelativeLayout rlLiveBroadcasters;

    SwipeRefreshLayout swiperefreshLiveBroadcasterList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_broadcasters_list);
        mContext = LiveBroadcastersListActivity.this;
        ButterKnife.bind(mContext);
        checkConnection();
        swiperefreshLiveBroadcasterList = (SwipeRefreshLayout) findViewById(R.id.swiperefresh_live_broadcaster_list);
        swiperefreshLiveBroadcasterList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swiperefreshLiveBroadcasterList.setRefreshing(false);
                        checkConnection();

                    }
                }, 2000);
            }
        });
    }

    private void checkConnection() {
        if (ConnectivityReceivers.isConnected()) {
            if (getIntent().getStringExtra("userComingFrom") != null) {
                if ((getIntent().getStringExtra("userComingFrom")).equalsIgnoreCase("matchList")) {
                    userselection = (getIntent().getStringExtra("userComingFrom"));
                    if (getIntent().getSerializableExtra("mMatchDatum") != null) {
                        MatchListListnerModel.ListenMatchList mMatchDatum = (MatchListListnerModel.ListenMatchList) getIntent().getSerializableExtra("mMatchDatum");
                        matchOrcontestentId = mMatchDatum.getMatchId();
                    }
                } else if ((getIntent().getStringExtra("userComingFrom")).equalsIgnoreCase("matchStandingListenList")) {
                    userselection = (getIntent().getStringExtra("userComingFrom"));
                    if (getIntent().getSerializableExtra("mMatchDatum") != null) {
                        StandingListeningModel.Datum mMatchDatum = (StandingListeningModel.Datum) getIntent().getSerializableExtra("mMatchDatum");
                        matchOrcontestentId = mMatchDatum.getContestantId();
                    }
                } else if(getIntent().getStringExtra("userComingFrom").equalsIgnoreCase("pundits")){
                    userselection = (getIntent().getStringExtra("userComingFrom"));
                    matchOrcontestentId=getIntent().getStringExtra("matchidcontestentid");
                }
                else if(getIntent().getStringExtra("userComingFrom").equalsIgnoreCase("sprotsUserSearch")){
                    userselection = (getIntent().getStringExtra("userComingFrom"));
                    matchOrcontestentId=getIntent().getStringExtra("matchidcontestentid");
                }
                else if(getIntent().getStringExtra("userComingFrom").equalsIgnoreCase("leaguesUserSearch")){
                    userselection = (getIntent().getStringExtra("userComingFrom"));
                    matchOrcontestentId=getIntent().getStringExtra("matchidcontestentid");
                }
                else if(getIntent().getStringExtra("userComingFrom").equalsIgnoreCase("sprotsTeamSearch")){
                    userselection = (getIntent().getStringExtra("userComingFrom"));
                    if (getIntent().getSerializableExtra("mTeamSearchDatum") != null) {
                        TeamSearchSportsModel.Datum mMatchDatum = (TeamSearchSportsModel.Datum) getIntent().getSerializableExtra("mTeamSearchDatum");
                        matchOrcontestentId = mMatchDatum.getChannelInfo().get(0).getMatchId();
                    }
                }
                getLiveBroadcastersListData(matchOrcontestentId);
            } else
                getLiveBroadcastersListData(matchOrcontestentId);
        } else {
            SnackbarUtil.showWarningLongSnackbar(mContext, getResources().getString(R.string.internet_not_connected_text));
        }
    }

    private void getLiveBroadcastersListData(String stringPath) {
        if (ConnectivityReceivers.isConnected()) {
            Picasso.with(mContext).load(AppPreferences.init(mContext).getString(APP_BACKGROUND)).into(rlLiveBroadcasters);
            App.getApiHelper().getLiveBroadcastersList(stringPath, new ApiCallBack<LiveBroacastersListModel>() {

                @Override
                public void onSuccess(LiveBroacastersListModel liveBroacastersListModel) {
                    rvBroadcastersList.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
                    final List<LiveBroacastersListModel.Channel> liveBroadcastersList = liveBroacastersListModel.getChannel();
                    if (liveBroadcastersList.size() == 0)
                        finish();
                    else {
                        liveBroadcastersListAdapter = new LiveBroadcastersListAdapter(mContext, liveBroadcastersList, new LiveBroadcastersListAdapter.ItemClickListener() {
                            @Override
                            public void onClick(int position) {
                                if (userselection != null) {
                                    if (userselection.equalsIgnoreCase("matchList")) {
                                        Intent intent = new Intent(mContext, LiveListeningActivity.class);
                                        intent.putExtra("userComingFrom", "matchList");
                                        intent.putExtra("mMatchDatum", getIntent().getSerializableExtra("mMatchDatum"));
                                        intent.putExtra("mBrListDatum", liveBroadcastersList.get(position));
                                        startActivity(intent);
                                    } else if (userselection.equalsIgnoreCase("matchStandingListenList")) {
                                        Intent intent = new Intent(mContext, LiveListeningActivity.class);
                                        intent.putExtra("userComingFrom", "matchStandingListenList");
                                        intent.putExtra("mMatchDatum", getIntent().getSerializableExtra("mMatchDatum"));
                                        intent.putExtra("mBrListDatum", liveBroadcastersList.get(position));
                                        startActivity(intent);
                                    } else if (userselection.equalsIgnoreCase("pundits")) {
                                        Intent intent = new Intent(mContext, LiveListeningActivity.class);
                                        intent.putExtra("userComingFrom", "pundits");
                                        intent.putExtra("mUserDatum", getIntent().getSerializableExtra("mUserDatum"));
                                        intent.putExtra("mBrListDatum", liveBroadcastersList.get(position));
                                        startActivity(intent);
                                    }
                                    else if (userselection.equalsIgnoreCase("sprotsUserSearch")) {
                                        Intent intent = new Intent(mContext, LiveListeningActivity.class);
                                        intent.putExtra("userComingFrom", "sprotsUserSearch");
                                        intent.putExtra("mUserSearchDatum", getIntent().getSerializableExtra("mUserSearchDatum"));
                                        intent.putExtra("mBrListDatum", liveBroadcastersList.get(position));
                                        startActivity(intent);
                                    }
                                    else if (userselection.equalsIgnoreCase("leaguesUserSearch")) {
                                        Intent intent = new Intent(mContext, LiveListeningActivity.class);
                                        intent.putExtra("userComingFrom", "leaguesUserSearch");
                                        intent.putExtra("mTeamSearchDatum", getIntent().getSerializableExtra("mTeamSearchDatum"));
                                        intent.putExtra("mBrListDatum", liveBroadcastersList.get(position));
                                        startActivity(intent);
                                    }
                                }
                            }
                        });
                        rvBroadcastersList.setAdapter(liveBroadcastersListAdapter);
                    }
                }

                @Override
                public void onFailure(String message) {

                }
            });


        }
    }

    @Override
    public void onResume() {
        super.onResume();
        checkConnection();
    }
}