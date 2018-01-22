package com.softuvo.ipundit.activities;

/*
 * Created by Neha Kalia on 12/08/2017.
 */

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.applozic.mobicomkit.api.people.ChannelInfo;
import com.applozic.mobicomkit.channel.service.ChannelService;
import com.applozic.mobicomkit.uiwidgets.async.ApplozicChannelAddMemberTask;
import com.applozic.mobicommons.people.channel.Channel;
import com.applozic.mobicommons.people.channel.ChannelMetadata;
import com.softuvo.ipundit.R;
import com.softuvo.ipundit.adapters.MatchSatadingAdapter;
import com.softuvo.ipundit.adapters.TeamStandingAdapter;
import com.softuvo.ipundit.api.ApiCallBack;
import com.softuvo.ipundit.config.App;
import com.softuvo.ipundit.config.AppConstant;
import com.softuvo.ipundit.config.AppPreferences;
import com.softuvo.ipundit.models.MatchStandingListModel;
import com.softuvo.ipundit.models.StandingListeningModel;
import com.softuvo.ipundit.receivers.ConnectivityReceivers;
import com.softuvo.ipundit.utils.SnackbarUtil;
import com.softuvo.ipundit.views.CustomLinearLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.softuvo.ipundit.config.AppConstant.APP_BACKGROUND;
import static com.softuvo.ipundit.config.AppConstant.FB_ID;

public class MatchStandingActivity extends BaseActivity {
    private Activity mContext;
    private MatchSatadingAdapter matchSatadingAdapter;
    private TeamStandingAdapter teamStandingAdapter;
    private String sportsLeagueId;
    String chatChannelId, chatChannelName, matchid;

    @BindView(R.id.ll_match_stansing_main)
    CustomLinearLayout tvAboutUsContent;

    @BindView(R.id.rv_match_standing_list)
    RecyclerView rvMatchStandingList;

    @BindView(R.id.tv_no_data)
    TextView tvNoData;

    @BindView(R.id.tv_league_name)
    TextView tvLeagueName;

    @BindView(R.id.tv_tap_club_match_standing)
    TextView tvTapClubMatchStanding;

    @BindView(R.id.progress_bar_match_standings)
    ProgressBar progressBarMatchStandings;
    private SwipeRefreshLayout swipeDownRefresh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_standing);
        mContext = MatchStandingActivity.this;
        ButterKnife.bind(mContext);
        setData();

    }

    private void setData() {
        progressBarMatchStandings.setVisibility(View.VISIBLE);
        if (AppPreferences.init(mContext).getString(APP_BACKGROUND) != null)
            Picasso.with(mContext).load(AppPreferences.init(mContext).getString(APP_BACKGROUND)).into(tvAboutUsContent);
        rvMatchStandingList.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));

    }

    // getMatchStandingListing from server
    private void getMatchData(String stringPath) {
        if (ConnectivityReceivers.isConnected()) {
            App.getApiHelper().getMatchStandingsList(stringPath, new ApiCallBack<MatchStandingListModel>() {
                @Override
                public void onSuccess(MatchStandingListModel matchStandingListModel) {
                    if (matchStandingListModel != null) {
                        progressBarMatchStandings.setVisibility(View.GONE);
                        enableUserIntraction();
                        final List<MatchStandingListModel.Datum> matchStandingList = matchStandingListModel.getData();
                        if (matchStandingList.size() > 0)
                            tvNoData.setVisibility(View.GONE);
                        else
                            tvNoData.setVisibility(View.VISIBLE);
                        if (AppPreferences.init(mContext).getString(AppConstant.USER_SELECTION).equalsIgnoreCase(AppConstant.SELECTED_BROADCAST)) {
                            matchSatadingAdapter = new MatchSatadingAdapter(mContext, matchStandingList, new MatchSatadingAdapter.ItemClickListener() {
                                @Override
                                public void onClick(int position) {
                                    chatChannelId = matchStandingList.get(position).getChatChannelid();
                                    chatChannelName = matchStandingList.get(position).getContestantName();
                                    matchid = matchStandingList.get(position).getContestantId();
                                    if (chatChannelId.equalsIgnoreCase("0")) {
                                        getChannelId();
                                    } else {
                                        ApplozicChannelAddMemberTask.ChannelAddMemberListener channelAddMemberListener = new ApplozicChannelAddMemberTask.ChannelAddMemberListener() {
                                            @Override
                                            public void onSuccess(String response, Context context) {
                                                Log.i("ApplozicChannelMember", "Add Response:" + response);
                                            }

                                            @Override
                                            public void onFailure(String response, Exception e, Context context) {

                                            }
                                        };
                                        ApplozicChannelAddMemberTask applozicChannelAddMemberTask = new ApplozicChannelAddMemberTask(mContext, Integer.parseInt(chatChannelId), AppPreferences.init(mContext).getString(FB_ID), channelAddMemberListener);//pass channel key and userId whom you want to add to channel
                                        applozicChannelAddMemberTask.execute((Void) null);
                                    }
                                    openBottomSheet(matchStandingList.get(position));
                                }
                            });
                            rvMatchStandingList.setAdapter(matchSatadingAdapter);
                            matchSatadingAdapter.notifyDataSetChanged();
                        }

                    }
                }

                @Override
                public void onFailure(String message) {
                    progressBarMatchStandings.setVisibility(View.GONE);
                    enableUserIntraction();
                    SnackbarUtil.showErrorLongSnackbar(mContext, message);
                }
            });
        } else {
            progressBarMatchStandings.setVisibility(View.GONE);
            enableUserIntraction();
            SnackbarUtil.showWarningLongSnackbar(mContext, getResources().getString(R.string.internet_not_connected_text));
        }
    }


    // getMatchStandingListing from server
    private void getTeamData(String stringPath) {
        progressBarMatchStandings.setVisibility(View.VISIBLE);
        disableUserIntraction();
        if (ConnectivityReceivers.isConnected()) {
            App.getApiHelper().getTeamStandingsList(stringPath, new ApiCallBack<StandingListeningModel>() {
                @Override
                public void onSuccess(StandingListeningModel teamStandingListModel) {
                    if (teamStandingListModel != null) {
                        progressBarMatchStandings.setVisibility(View.GONE);
                        enableUserIntraction();
                        final List<StandingListeningModel.Datum> teamStandingList = teamStandingListModel.getData();
                        if (teamStandingList.size() > 0)
                            tvNoData.setVisibility(View.GONE);
                        else
                            tvNoData.setVisibility(View.VISIBLE);
                        teamStandingAdapter = new TeamStandingAdapter(mContext, teamStandingList, new TeamStandingAdapter.ItemClickListener() {
                            @Override
                            public void onClick(int position) {
                                chatChannelId = teamStandingList.get(position).getChannel().get(0).getChatChannelid();
                                chatChannelName = teamStandingList.get(position).getContestantName();
                                matchid = teamStandingList.get(position).getContestantId();
                                if (chatChannelId.equalsIgnoreCase("0")) {
                                    getChannelId();
                                } else {
                                    ApplozicChannelAddMemberTask.ChannelAddMemberListener channelAddMemberListener = new ApplozicChannelAddMemberTask.ChannelAddMemberListener() {
                                        @Override
                                        public void onSuccess(String response, Context context) {
                                            Log.i("ApplozicChannelMember", "Add Response:" + response);
                                        }

                                        @Override
                                        public void onFailure(String response, Exception e, Context context) {

                                        }
                                    };
                                    ApplozicChannelAddMemberTask applozicChannelAddMemberTask = new ApplozicChannelAddMemberTask(mContext, Integer.parseInt(chatChannelId), AppPreferences.init(mContext).getString(FB_ID), channelAddMemberListener);//pass channel key and userId whom you want to add to channel
                                    applozicChannelAddMemberTask.execute((Void) null);
                                }
                                Intent intent = new Intent(mContext, LiveBroadcastersListActivity.class);
                                intent.putExtra("userComingFrom", "matchStandingListenList");
                                intent.putExtra("mMatchDatum", teamStandingList.get(position));
                                intent.putExtra("chatChannelKey", chatChannelId);
                                startActivity(intent);

                            }
                        });
                    }
                    rvMatchStandingList.setAdapter(teamStandingAdapter);
                    teamStandingAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(String message) {
                    progressBarMatchStandings.setVisibility(View.GONE);
                    enableUserIntraction();
                    SnackbarUtil.showErrorLongSnackbar(mContext, message);
                }
            });
        } else {
            progressBarMatchStandings.setVisibility(View.GONE);
            enableUserIntraction();
            SnackbarUtil.showWarningLongSnackbar(mContext, getResources().getString(R.string.internet_not_connected_text));
        }
    }

    private void getChannelId() {
        try{
            new Thread(new Runnable() {
                @Override
                public void run() {
                    List<String> channelMembersList = new ArrayList<>();
                    channelMembersList.add(AppPreferences.init(mContext).getString(FB_ID));
                    ChannelInfo channelInfo = new ChannelInfo(chatChannelName, channelMembersList);
                    channelInfo.setType(Channel.GroupType.PUBLIC.getValue().intValue());
                    ChannelService service = ChannelService.getInstance(mContext);
                    Channel channel = service.createChannel(channelInfo);
                    Log.i("Channel", "Channel respone is:" + channel);
                    if (channel!=null && channel.getKey() != null) {
                        chatChannelId = String.valueOf(channel.getKey());
                        updateChatChannelId();
                    }
                }
            }).start();
        }catch (Exception e){e.printStackTrace();}
    }

    private void updateChatChannelId() {
        Map<String, String> mountMap = new HashMap<>();
        mountMap.put("match_id", matchid);
        mountMap.put("channeltype", "team");
        mountMap.put("chatChannelid", chatChannelId);
        App.getApiHelper().updateChatId(mountMap, new ApiCallBack<Map>() {
            @Override
            public void onSuccess(Map map) {
                Log.e("chat", map.toString());
            }

            @Override
            public void onFailure(String message) {

            }
        });
    }

    private class createChannel extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
//            ChannelMetadata channelMetadata = new ChannelMetadata();
//            channelMetadata.setCreateGroupMessage(ChannelMetadata.ADMIN_NAME + " created " + chatChannelName);
//            channelMetadata.setAddMemberMessage(ChannelMetadata.ADMIN_NAME + " added " + ChannelMetadata.USER_NAME);
//            channelMetadata.setRemoveMemberMessage(ChannelMetadata.ADMIN_NAME + " removed " + ChannelMetadata.USER_NAME);
//            channelMetadata.setGroupNameChangeMessage(ChannelMetadata.USER_NAME + " changed group name " + ChannelMetadata.GROUP_NAME);
//            channelMetadata.setJoinMemberMessage(ChannelMetadata.USER_NAME + " joined");
//            channelMetadata.setGroupLeftMessage(ChannelMetadata.USER_NAME + " left group " + ChannelMetadata.GROUP_NAME);
//            channelMetadata.setGroupIconChangeMessage(ChannelMetadata.USER_NAME + " changed icon");
//            channelMetadata.setDeletedGroupMessage(ChannelMetadata.ADMIN_NAME + " deleted group " + ChannelMetadata.GROUP_NAME);
            List<String> channelMembersList = new ArrayList<>();
            channelMembersList.add(AppPreferences.init(mContext).getString(FB_ID));
            ChannelInfo channelInfo = new ChannelInfo(chatChannelName, channelMembersList);
            channelInfo.setType(Channel.GroupType.PUBLIC.getValue().intValue());
            ChannelService service = ChannelService.getInstance(mContext);
            Channel channel = service.createChannel(channelInfo);
            Log.i("Channel", "Channel respone is:" + channel);
            chatChannelId = String.valueOf(channel.getKey());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            updateChatChannelId();
        }

    }


    public void openBottomSheet(final MatchStandingListModel.Datum mBrDatum) {
        @SuppressLint("InflateParams")
        View view = getLayoutInflater().inflate(R.layout.layout_bottom_sheet_fragment, null);
        final Dialog mBottomSheetDialog = new Dialog(mContext, R.style.MaterialDialogSheet);
        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.setCancelable(true);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
        mBottomSheetDialog.show();
        view.findViewById(R.id.rl_bottom_sheet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean result = checkPermission();
                if (result) {
                    mBottomSheetDialog.dismiss();
                    Intent intent = new Intent(mContext, LiveBroadCastingActivity.class);
                    intent.putExtra("userComingFrom", "matchStandingList");
                    intent.putExtra("mBrDatum", mBrDatum);
                    intent.putExtra("chatChannelKey", chatChannelId);
                    startActivity(intent);
                }
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public boolean checkPermission() {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(mContext, Manifest.permission.RECORD_AUDIO)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(mContext);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("RECORD AUDIO Permission is necessary to Broadacst");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(mContext, new String[]{Manifest.permission.RECORD_AUDIO}, AppConstant.MY_PERMISSIONS_REQUEST_MIC);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();
                } else {
                    ActivityCompat.requestPermissions(mContext, new String[]{Manifest.permission.RECORD_AUDIO}, AppConstant.MY_PERMISSIONS_REQUEST_MIC);
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case AppConstant.MY_PERMISSIONS_REQUEST_MIC:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("permission check", "Granted");
                } else {
                    Log.e("permission check", "Denied");
                }
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getIntent().getStringExtra("sportsLeagueId") != null)
            sportsLeagueId = getIntent().getStringExtra("sportsLeagueId");
        if (getIntent().getStringExtra("sportsLeagueName") != null) {
            final String sportsLeagueName = getIntent().getStringExtra("sportsLeagueName");
            if (AppPreferences.init(mContext).getString(AppConstant.USER_SELECTION).equalsIgnoreCase(AppConstant.SELECTED_BROADCAST)) {
                if (sportsLeagueId != null) {
                    getMatchData(sportsLeagueId);
                    tvLeagueName.setText(sportsLeagueName);
                }
            } else if (AppPreferences.init(mContext).getString(AppConstant.USER_SELECTION).equalsIgnoreCase(AppConstant.SELECTED_LISTNER)) {
                if (sportsLeagueId != null) {
                    getTeamData(sportsLeagueId);
                    tvLeagueName.setText(sportsLeagueName);
                    tvTapClubMatchStanding.setText(getString(R.string.tap_club_listen));
                }
            }
            swipeDownRefresh = findViewById(R.id.swiperefresh_standings);
            swipeDownRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    (new Handler()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            swipeDownRefresh.setRefreshing(false);
                            if (AppPreferences.init(mContext).getString(AppConstant.USER_SELECTION).equalsIgnoreCase(AppConstant.SELECTED_BROADCAST)) {
                                if (sportsLeagueId != null) {
                                    getMatchData(sportsLeagueId);
                                    tvLeagueName.setText(sportsLeagueName);
                                }
                            } else if (AppPreferences.init(mContext).getString(AppConstant.USER_SELECTION).equalsIgnoreCase(AppConstant.SELECTED_LISTNER)) {
                                if (sportsLeagueId != null) {
                                    getTeamData(sportsLeagueId);
                                    tvLeagueName.setText(sportsLeagueName);
                                    tvTapClubMatchStanding.setText(getString(R.string.tap_club_listen));
                                }
                            }
                        }
                    }, 2000);
                }
            });
        }
    }

}

