package com.softuvo.ipundit.activities;

/*
 * Created by Neha Kalia on 12/08/2017.
 */

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import com.applozic.mobicomkit.uiwidgets.conversation.ConversationUIService;
import com.applozic.mobicomkit.uiwidgets.conversation.activity.ConversationActivity;
import com.bumptech.glide.Glide;
import com.google.gson.internal.LinkedTreeMap;
import com.red5pro.streaming.R5Connection;
import com.red5pro.streaming.R5Stream;
import com.red5pro.streaming.R5StreamProtocol;
import com.red5pro.streaming.config.R5Configuration;
import com.red5pro.streaming.source.R5Microphone;
import com.softuvo.ipundit.R;
import com.softuvo.ipundit.adapters.ViewPagerAdapter;
import com.softuvo.ipundit.api.ApiCallBack;
import com.softuvo.ipundit.config.ApiConstants;
import com.softuvo.ipundit.config.App;
import com.softuvo.ipundit.config.AppConstant;
import com.softuvo.ipundit.config.AppPreferences;
import com.softuvo.ipundit.fragments.LiveFeedsFragment;
import com.softuvo.ipundit.fragments.LiveFeedsPlayerFragment;
import com.softuvo.ipundit.models.BreakingNewsDatum;
import com.softuvo.ipundit.models.BreakingNewsParentModel;
import com.softuvo.ipundit.models.BroadcastMatchlistModel;
import com.softuvo.ipundit.models.ListnerCountModel;
import com.softuvo.ipundit.models.LiveBroadcstingModel;
import com.softuvo.ipundit.models.MatchStandingListModel;
import com.softuvo.ipundit.models.ServerAddressModel;
import com.softuvo.ipundit.receivers.ConnectivityReceivers;
import com.softuvo.ipundit.utils.SnackbarUtil;
import com.softuvo.ipundit.views.CustomRelativeLayout;
import com.softuvo.ipundit.views.CustomTextView;
import com.squareup.picasso.Picasso;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import static com.softuvo.ipundit.config.AppConstant.USER_ID;
import static com.softuvo.ipundit.config.AppConstant.USER_NAME;

public class LiveBroadCastingActivity extends BaseActivity {
    private Activity mContext;
    private int minutes = 0, seconds = 0, hours = 0;
    private String strName;
    private String strMatchId;
    private String strBroadcastName;
    private String strBroadcastId;
    private String strAppName;
    private String strChannelType;
    private String status;
    private String twitterStatus = "";
    private String strFollowMsg;
    private String Score_team1;
    private String Score_team2;
    private String team1_id;
    private String team2_id;
    private String serverAddress;
    private String chatChannelId;
    private static R5Stream stream;
    private static String channelId;
    private int visible = 0;
    private int isSelected = 0;
    String mUserComingFrom;
    String time;

    @BindView(R.id.rl_live_broadcasting_main)
    CustomRelativeLayout rlLiveBroadcastingMain;

   /* @BindView(R.id.tv_match_name_top)
    CustomTextView tvMatcNameTop;*/

    @BindView(R.id.tv_match_time_top)
    CustomTextView tvMatchTimeTop;

  /*  @BindView(R.id.tv_broadcsaters_name)
    CustomTextView tvBroadcsatersName;*/

    @BindView(R.id.tv_kickoff_time_top)
    CustomTextView tvKickoffTimeTop;

    @BindView(R.id.iv_league_icon)
    ImageView ivLeagueIcon;

    @BindView(R.id.tv_breaking_news)
    CustomTextView tvBreakingNews;

    @BindView(R.id.tv_team1_score)
    CustomTextView tvTeam1Score;

    @BindView(R.id.tv_team1_name)
    CustomTextView tvTeam1Name;

    @BindView(R.id.tv_team2_name)
    CustomTextView tvTeam2Name;

    @BindView(R.id.tv_team2_score)
    CustomTextView tvTeam2Score;

    /* @BindView(R.id.iv_sharefacebook)
     ImageView ivShareFacebook;

     @BindView(R.id.iv_sharetwitter)
     ImageView ivShareTwitter;
 */
    @BindView(R.id.iv_share)
    ImageView ivShare;

   /* @BindView(R.id.tv_team1name_vs_team2name)
    CustomTextView tvTeam1nameVsTeam2name;

    @BindView(R.id.tv_team1score_vs_team2score)
    CustomTextView tvTeam1scoreVsTeam2score;

    @BindView(R.id.tv_match_kickoff_time)
    CustomTextView tvMatchKickoffTime;*/

    @BindView(R.id.tv_listners_count)
    CustomTextView tvListnersCount;

    @BindView(R.id.tv_no_deta_br)
    CustomTextView tvNo_DataBr;

   /* @BindView(R.id.gif_view)
    CustomGifImageView gifView;*/

    @BindView(R.id.gif_live_view)
    ImageView gifLiveView;

    @BindView(R.id.progress_bar_live_broadcasting)
    ProgressBar progressarLiveBroadcasting;

    @BindView(R.id.rl_score_board)
    RelativeLayout rlScoreBoard;

    @BindView(R.id.tv_team1_name_editboard)
    CustomTextView tvTeam1NameEditboard;

    @BindView(R.id.tv_team2_name_editboard)
    CustomTextView tvTeam2NameEditboard;

    @BindView(R.id.tv_team1_edited_score)
    CustomTextView tvTeam1EditedScore;

    @BindView(R.id.tv_team2_edited_score)
    CustomTextView tvTeam2EditedScore;

    @BindView(R.id.np_team1_score)
    NumberPicker npTeam1Score;

    @BindView(R.id.np_team2_score)
    NumberPicker npTeam2Score;

    @BindView(R.id.tv_done)
    CustomTextView tvDone;

    @BindView(R.id.iv_edit_score)
    ImageView ivEditScore;

    @BindView(R.id.iv_play_pause)
    ImageView ivPlayPause;

    @BindView(R.id.tabs)
    TabLayout mTabLayout;

    @BindView(R.id.viewpager)
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_broad_casting);
        mContext = LiveBroadCastingActivity.this;
        ButterKnife.bind(mContext);
        Date date = new Date();  // to get the date
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd"); // getting date in this format
        String formattedDate = df.format(date.getTime());
        Log.e("cureent date:",formattedDate);
        getNewsFromServer(formattedDate);
        setData();
//        onIncommingCallBroadcast();
    }

    private void setData() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        if (isSelected == 0)
            ivPlayPause.setImageResource(R.drawable.break_new);
        else if (isSelected == 1)
            ivPlayPause.setImageResource(R.drawable.live_new);
        rlLiveBroadcastingMain.setBackground(getResources().getDrawable(R.drawable.screen_image));
        /*if (AppPreferences.init(mContext).getString(APP_BACKGROUND) != null)
            Picasso.with(mContext).load(AppPreferences.init(mContext).getString(APP_BACKGROUND)).into(rlLiveBroadcastingMain);*/
//        AppPreferences.init(mContext).putString(AppConstant.User_CURRENT_STATE, "1");
        if (getIntent().getStringExtra("userComingFrom") != null) {

            if ((getIntent().getStringExtra("userComingFrom")).equalsIgnoreCase("matchList")) {
                mUserComingFrom = "match";
                if (getIntent().getSerializableExtra("mBrDatum") != null) {
                    BroadcastMatchlistModel.Datum mBrDatum = (BroadcastMatchlistModel.Datum) getIntent().getSerializableExtra("mBrDatum");
//                    tvMatcNameTop.setText(mBrDatum.getTeam1Name() + " Vs " + mBrDatum.getTeam2Name());
                   // tvTeam1nameVsTeam2name.setText(mBrDatum.getTeam1Name() + " Vs " + mBrDatum.getTeam2Name());
                    tvTeam1Name.setText(mBrDatum.getTeam1Name() + ":");
                    tvTeam2Name.setText(mBrDatum.getTeam2Name() + ":");
                    team1_id = mBrDatum.getTeam1Id();
                    team2_id = mBrDatum.getTeam2Id();
                    tvTeam1NameEditboard.setText(mBrDatum.getTeam1Name());
                    tvTeam2NameEditboard.setText(mBrDatum.getTeam2Name());
                    strName = mBrDatum.getTeam1Name() + " Vs " + mBrDatum.getTeam2Name() + "-" + AppPreferences.init(mContext).getString(AppConstant.USER_NAME);
                    strMatchId = mBrDatum.getMatchId();
                    strBroadcastName = AppPreferences.init(mContext).getString(USER_NAME);
                    strBroadcastId = AppPreferences.init(mContext).getString(AppConstant.USER_ID);
                    strAppName = "live";
                    strChannelType = mUserComingFrom;
                    strFollowMsg = AppPreferences.init(mContext).getString(USER_NAME) + "is now the live pundit on " + mBrDatum.getTeam1Name() + " Vs " + mBrDatum.getTeam2Name() + ", " + "Listen now";
                }
//                getLiveFeedsFromServer(strMatchId);
                setupViewPager(mViewPager, strMatchId);
                mViewPager.setOffscreenPageLimit(0);
                mTabLayout.setVisibility(View.VISIBLE);
                mTabLayout.setupWithViewPager(mViewPager);
            } else if ((getIntent().getStringExtra("userComingFrom")).equalsIgnoreCase("matchStandingList")) {
                mUserComingFrom = "team";
                if (getIntent().getSerializableExtra("mBrDatum") != null) {
                    ivEditScore.setVisibility(View.GONE);
                    MatchStandingListModel.Datum mBrDatum = (MatchStandingListModel.Datum) getIntent().getSerializableExtra("mBrDatum");
//                    tvMatcNameTop.setText(mBrDatum.getContestantName());
                    tvMatchTimeTop.setText("Rank:" + mBrDatum.getRank());
                   // tvTeam1nameVsTeam2name.setText(mBrDatum.getContestantName());
                    tvTeam1Name.setText(mBrDatum.getContestantClubName());
                    tvTeam2Name.setText("Points : " + mBrDatum.getPoints());
                   // tvTeam1scoreVsTeam2score.setText("");
                    strName = mBrDatum.getContestantName() + "-" + AppPreferences.init(mContext).getString(AppConstant.USER_NAME);
                    strMatchId = mBrDatum.getContestantId();
                    strBroadcastName = AppPreferences.init(mContext).getString(USER_NAME);
                    strBroadcastId = AppPreferences.init(mContext).getString(AppConstant.USER_ID);
                    strAppName = "live";
                    strChannelType = mUserComingFrom;
                    tvNo_DataBr.setVisibility(View.VISIBLE);
                    tvNo_DataBr.setText(R.string.team_talk);
                    strFollowMsg = AppPreferences.init(mContext).getString(USER_NAME) + "is now the live pundit on " + mBrDatum.getContestantName() + ", " + "Listen now";
                }
                mTabLayout.setVisibility(View.GONE);
            }
            if (AppPreferences.init(mContext).getString(AppConstant.LEAGUE_IMAGE_URL) != null && !AppPreferences.init(mContext).getString(AppConstant.LEAGUE_IMAGE_URL).equalsIgnoreCase(""))
                Picasso.with(mContext).load(AppPreferences.init(mContext).getString(AppConstant.LEAGUE_IMAGE_URL)).into(ivLeagueIcon);
            String broadcasterName = "Logged in as " + AppPreferences.init(mContext).getString(USER_NAME);
            chatChannelId = getIntent().getStringExtra("chatChannelKey");
            //gifView.setGifImageResource(R.drawable.broadcast_gif);

            Glide.with(mContext).load(R.drawable.live_gif).into(gifLiveView);
//            gifLiveView.setGifImageResource(R.drawable.live_gif);
//            tvBroadcsatersName.setText(broadcasterName);
            publish();
            createMap();
        }
    }

    private void getNewsFromServer(final String date) {
        if (ConnectivityReceivers.isConnected()) {
            int apiHitTimeInterval = 40000;
            Timer t = new Timer();
            t.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                           /* App.getApiHelper().getBreakingNews(new ApiCallBack<BreakingNewsParentModel>() {
                                @Override
                                public void onSuccess(BreakingNewsParentModel breakingNewsParentModel) {
                                    if (breakingNewsParentModel != null) {
                                        ArrayList<BreakingNewsDatum> breakingNewsResponse = (ArrayList<BreakingNewsDatum>) breakingNewsParentModel.getData();
                                        List<String> breakingNews = new ArrayList<>();
                                        for (int i = 0; i < breakingNewsResponse.size(); i++) {
                                            if (breakingNewsResponse.get(i).getTitle() != null)
                                                breakingNews.add(breakingNewsResponse.get(i).getTitle());
                                        }
                                        String SubTitle = (breakingNews.toString().replace("[", "").replace("]", "").trim()).replaceAll(",", ". ||   ");
                                        tvBreakingNews.setText(SubTitle);
                                        tvBreakingNews.setSelected(true);
                                    }
                                }

                                @Override
                                public void onFailure(String message) {
                                    SnackbarUtil.showErrorLongSnackbar(mContext, message);
                                }
                            });*/
                            App.getApiHelper().getBreakingNewsList("null/"+date, new ApiCallBack<BreakingNewsParentModel>() {
                                @Override
                                public void onSuccess(BreakingNewsParentModel breakingNewsParentModel) {
                                    if (breakingNewsParentModel != null) {
                                        ArrayList<BreakingNewsDatum> breakingNewsResponse = (ArrayList<BreakingNewsDatum>) breakingNewsParentModel.getData();
                                        List<String> breakingNews = new ArrayList<>();
                                        for (int i = 0; i < breakingNewsResponse.size(); i++) {
                                            if (breakingNewsResponse.get(i).getTitle() != null)
                                                breakingNews.add(breakingNewsResponse.get(i).getTitle());
                                        }
                                        String SubTitle = (breakingNews.toString().replace("[", "").replace("]", "").trim()).replaceAll(",", ". ||   ");
                                        tvBreakingNews.setText(SubTitle);
                                        tvBreakingNews.setSelected(true);
                                    }
                                }

                                @Override
                                public void onFailure(String message) {

                                }
                            });
                        }
                    });
                }
            }, 0, apiHitTimeInterval);
        } else {
            SnackbarUtil.showWarningLongSnackbar(mContext, getResources().getString(R.string.internet_not_connected_text));
        }
    }

    @OnClick(R.id.iv_play_pause)
    public void onClickPlaypause() {
        if (isSelected == 0) {
            isSelected = 1;
            ivPlayPause.setImageResource(R.drawable.live_new);

        } else if (isSelected == 1) {
            isSelected = 0;
            ivPlayPause.setImageResource(R.drawable.break_new);
        }
        String path = channelId + "/" + isSelected+"/"+time;
        pauseStream(path);
        Log.e("pathtime",path);
    }

    private void pauseStream(String path) {
        if (ConnectivityReceivers.isConnected()) {
            App.getApiHelper().pauseStream(path, new ApiCallBack<Map>() {
                @Override
                public void onSuccess(Map map) {
                    Log.e("Api", "Api hit successfully");
                    if (isSelected == 0) {
                        stream.restrainAudio(false);
                    }
                    if (isSelected == 1) {
                        stream.restrainAudio(true);
                    }
                }

                @Override
                public void onFailure(String message) {
                    Log.e("Api", "Not successfully");
                }
            });
        } else {
            SnackbarUtil.showWarningLongSnackbar(mContext, getResources().getString(R.string.internet_not_connected_text));
        }
    }

    private void setupViewPager(ViewPager viewPager, String strMatchId) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(LiveFeedsFragment.newInstance("Broadcasting", strMatchId), "Overview");
        adapter.addFragment(LiveFeedsPlayerFragment.newInstance("Broadcasting", strMatchId), "Lineups");
        viewPager.setAdapter(adapter);
    }

    private void setkickofftime() {
        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String newMinute,newSeconds,newHour;
                        seconds += 1;
                        time=String.valueOf(hours)+":"+String.valueOf(minutes) + ":" + String.valueOf(seconds);
                        tvKickoffTimeTop.setText(time);
                        if (seconds == 60) {
                            time=String.valueOf(hours)+":"+String.valueOf(minutes) + ":" + String.valueOf(seconds);
                            tvKickoffTimeTop.setText(time);
                            //tvMatchKickoffTime.setText(String.valueOf(minutes) + ":" + String.valueOf(seconds));
                            seconds = 0;
                            minutes = minutes + 1;
                        }
                        if(minutes==60){
                            time=String.valueOf(hours)+":"+String.valueOf(minutes) + ":" + String.valueOf(seconds);
                            tvKickoffTimeTop.setText(time);
                            minutes=0;
                            hours=hours+1;


                        }
                    }
                });
            }
        }, 0, 1000);
    }

    private void postMountOnServer(Map<String, String> mountMap) {
        progressarLiveBroadcasting.setVisibility(View.VISIBLE);
        disableUserIntraction();
        if (ConnectivityReceivers.isConnected()) {
            App.getApiHelper().mountOnServer(mountMap, new ApiCallBack<LiveBroadcstingModel>() {
                @Override
                public void onSuccess(LiveBroadcstingModel map) {
                    progressarLiveBroadcasting.setVisibility(View.GONE);
                    enableUserIntraction();
                    if (map != null) {
                        // configRedPro();
                        getServerAddressData(map.getData().getStreamName());
                        SnackbarUtil.showSuccessLongSnackbar(mContext, getString(R.string.start_broadcast_success_msg));
                        setkickofftime();
                        channelId = map.getChannelid().toString();
                        getListnerCountData(channelId);
                    } else {
                        progressarLiveBroadcasting.setVisibility(View.GONE);
                        enableUserIntraction();
                    }
                }

                @Override
                public void onFailure(String message) {
                    progressarLiveBroadcasting.setVisibility(View.GONE);
                    enableUserIntraction();
                    SnackbarUtil.showErrorLongSnackbar(mContext, message);
                }
            });
        } else
            SnackbarUtil.showWarningLongSnackbar(mContext, getResources().getString(R.string.internet_not_connected_text));
    }

    private void getServerAddressData(String StreamName) {
        if (ConnectivityReceivers.isConnected()) {
            App.getApiHelper().getServerAddress(StreamName, new ApiCallBack<ServerAddressModel>() {
                @Override
                public void onSuccess(ServerAddressModel serverAddressModel) {
                    if (serverAddressModel != null) {
                        serverAddress = serverAddressModel.getServerAddress();
                        configRedPro(serverAddress);
                        stream.publish(serverAddressModel.getName(), R5Stream.RecordType.Record);
                    }
                }

                @Override
                public void onFailure(String message) {

                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        openCancelDialog();
    }

    @Override
    public void onStop() {
        super.onStop();
      /*  if (AppPreferences.init(mContext).getString(AppConstant.User_CURRENT_STATE).equalsIgnoreCase("3")) {
//            Do Nothing
        } else {
            unmountUser();
        }*/
//        unmountUser();

    }

    @Override
    protected void onResume() {
        super.onResume();
//        AppPreferences.init(mContext).putString(AppConstant.User_CURRENT_STATE, "1");
    }

    private void unmountUser() {
        try {
            if (ConnectivityReceivers.isConnected()) {
                App.getApiHelper().unmountOnServer(channelId, new ApiCallBack<Map>() {
                    @Override
                    public void onSuccess(Map map) {
                        if (map != null) {
                            if (stream != null)
                                stream.stop();
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(String message) {
//                    SnackbarUtil.showErrorLongSnackbar(mContext, message);
                    }
                });
            } else {
                SnackbarUtil.showWarningLongSnackbar(mContext, getResources().getString(R.string.internet_not_connected_text));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openCancelDialog() {
        new AlertDialog.Builder(mContext)
                .setTitle(getString(R.string.cancel_broadcast_title))
                .setMessage(getString(R.string.cancel_broadcast_message))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        unmountUser();
                        finish();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void getListnerCountData(final String Channel_id) {
        if (ConnectivityReceivers.isConnected()) {
            Timer t = new Timer();
            t.scheduleAtFixedRate(new TimerTask() {

                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            App.getApiHelper().getListnerCount(Channel_id, new ApiCallBack<ListnerCountModel>() {
                                @Override
                                public void onSuccess(ListnerCountModel listnerCountModel) {
                                    if (listnerCountModel != null)
                                        tvListnersCount.setText(listnerCountModel.getCount() + "");
                                }

                                @Override
                                public void onFailure(String message) {

                                }
                            });
                        }
                    });
                }
            }, 0, 5000);
        } else {
            SnackbarUtil.showWarningLongSnackbar(mContext, getResources().getString(R.string.internet_not_connected_text));
        }
    }

    /*private void getLiveFeedsFromServer(final String matchId) {
        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (ConnectivityReceivers.isConnected()) {
                            App.getApiHelper().getLiveFeedsData(matchId, new ApiCallBack<LiveFeedsNewModel>() {
                                @Override
                                public void onSuccess(LiveFeedsNewModel liveFeedsNewModel) {
                                    if (liveFeedsNewModel != null) {
                                        if (liveFeedsNewModel.getMatch() != null) {
                                           *//* if (liveFeedsModel.getMatchinfo().getMatchStatus().equalsIgnoreCase("Fixture")) {
                                                tvMatchTimeTop.setText(liveFeedsModel.getMatchinfo().getMatchStatus());
                                            } else if (liveFeedsModel.getMatchinfo().getMatchStatus().equalsIgnoreCase("Played")) {
                                                tvMatchTimeTop.setText(getString(R.string.ft_string));
                                            } else if (liveFeedsModel.getMatchinfo().getMatchStatus().equalsIgnoreCase("Playing")) {
                                                tvMatchTimeTop.setText(liveFeedsModel.getMatchinfo().getMatchLengthMin() + ":" + liveFeedsModel.getMatchinfo().getMatchLengthSec());
                                            }*//*
                                           *//* if (liveFeedsNewModel.getMatchinfo().getTeam1Score() == null || liveFeedsModel.getMatchinfo().getTeam1Score().equalsIgnoreCase(""))
                                                team1Score = "N/A";
                                            else
                                                team1Score = liveFeedsModel.getMatchinfo().getTeam1Score();
                                            tvTeam1Score.setText(team1Score);
                                            if (liveFeedsModel.getMatchinfo().getTeam2Score() == null || liveFeedsModel.getMatchinfo().getTeam2Score().equalsIgnoreCase(""))
                                                team2Score = "N/A";
                                            else
                                                team2Score = liveFeedsModel.getMatchinfo().getTeam2Score();
                                            tvTeam2Score.setText(team2Score);
                                            tvTeam1scoreVsTeam2score.setText(team1Score + ":" + team2Score);*//*
                                            if (liveFeedsNewModel.getMatch().getStatus().contains("Kick off")) {
                                                tvMatchTimeTop.setText(getString(R.string.fixture_string));
                                            } else if (liveFeedsNewModel.getMatch().getStatus().equalsIgnoreCase("Full Time")) {
                                                tvMatchTimeTop.setText(getString(R.string.ft_string));
                                            } else if (liveFeedsNewModel.getMatch().getStatus().contains("First Half") || liveFeedsNewModel.getMatch().getStatus().contains("Second Half")) {
                                                tvMatchTimeTop.setText(getString(R.string.playing_String));
                                            } else {
                                                tvMatchTimeTop.setText("-");
                                            }
                                            if (liveFeedsNewModel.getMatch().getTeams().get(0).getScore() == null)
                                                team1Score = "N/A";
                                            else
                                                team1Score = liveFeedsNewModel.getMatch().getTeams().get(0).getScore();
                                            tvTeam1Score.setText(team1Score);
                                            if (liveFeedsNewModel.getMatch().getTeams().get(1).getScore() == null)
                                                team2Score = "N/A";
                                            else
                                                team2Score = liveFeedsNewModel.getMatch().getTeams().get(1).getScore();
                                            tvTeam2Score.setText(team2Score);

                                            List<PlayerDataModel> playerDataModelsList = new ArrayList<PlayerDataModel>();
                                            List<LiveFeedsNewModel.Match.Team> liveFeedsList = liveFeedsNewModel.getMatch().getTeams();

                                            for (int i = 0; i < liveFeedsList.size(); i++) {
                                                if (liveFeedsList.get(i).getGoals() != null) {
                                                    if (liveFeedsList.get(i).getGoals().size() > 0) {
                                                        for (int j = 0; j < liveFeedsList.get(i).getGoals().size(); j++) {
                                                            PlayerDataModel mPlyerListData = new PlayerDataModel();
                                                            mPlyerListData.setGoal(liveFeedsList.get(i).getGoals().get(j).getGoal());
                                                            mPlyerListData.setType("Goal");
                                                            playerDataModelsList.add(mPlyerListData);
                                                        }
                                                    }
                                                }
                                                if (liveFeedsList.get(i).getPlayers() != null) {
                                                    if (liveFeedsList.get(i).getPlayers().size() > 0) {
                                                        for (int j = 0; j < liveFeedsList.get(i).getPlayers().size(); j++) {
                                                            if (liveFeedsList.get(i).getPlayers().get(j).getSubstitution() != null) {
                                                                PlayerDataModel mPlyerListData = new PlayerDataModel();
                                                                mPlyerListData.setName(liveFeedsList.get(i).getPlayers().get(j).getName());
                                                                mPlyerListData.setType("Substitute");
                                                                mPlyerListData.setReplacedby(liveFeedsList.get(i).getPlayers().get(j).getSubstitution().getReplacedBy());
                                                                mPlyerListData.setMinute(liveFeedsList.get(i).getPlayers().get(j).getSubstitution().getMinute());
                                                                playerDataModelsList.add(mPlyerListData);
                                                            }
                                                        }
                                                    }
                                                }

                                            }

                                            if (playerDataModelsList.size() > 0) {
                                                tvNo_DataBr.setVisibility(View.GONE);
                                                liveFeedsAdapter = new LiveFeedsAdapter(mContext, playerDataModelsList);
                                                rvLiveFeeds.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
                                                rvLiveFeeds.setAdapter(liveFeedsAdapter);
                                            } else {
                                                tvNo_DataBr.setVisibility(View.VISIBLE);
                                                tvNo_DataBr.setText(R.string.no_live_feeds);
                                            }
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(String message) {
                                    progressarLiveBroadcasting.setVisibility(View.GONE);
                                    enableUserIntraction();
                                    SnackbarUtil.showErrorLongSnackbar(mContext, message);
                                }
                            });
                        } else {
                            progressarLiveBroadcasting.setVisibility(View.GONE);
                            enableUserIntraction();
                            SnackbarUtil.showWarningLongSnackbar(mContext, getResources().getString(R.string.internet_not_connected_text));
                        }
                    }
                });
            }
        }, 0, 20000);
    }*/

    private void configRedPro(String serverIP) {
        R5Configuration configuration = new R5Configuration(R5StreamProtocol.RTSP, serverIP, AppConstant.RED5PRO_SERVER_PORT, AppConstant.RED5PRO_SERVER_APP_NAME, AppConstant.RED5PRO_SERVER_CASHE);
        configuration.setLicenseKey(AppConstant.RED5PRO_LICENSE_KEY);
        configuration.setBundleID(mContext.getPackageName());
        stream = new R5Stream(new R5Connection(configuration));
        R5Microphone r5Microphone = new R5Microphone();
//        r5Microphone.setBitRate(AppConstant.RED5PRO_BIT_RATE);
        stream.audioController.sampleRate =  44100;
        stream.attachMic(r5Microphone);

    }


    protected void publish() {
        Map<String, String> mountMap = new HashMap<>();
        mountMap.put("name", strName);
        mountMap.put("match_id", strMatchId);
        mountMap.put("broadcaster_name", strBroadcastName);
        mountMap.put("broadcaster_id", strBroadcastId);
        mountMap.put("appName", strAppName);
        mountMap.put("channel_type", strChannelType);
        mountMap.put("sport_id", AppPreferences.init(mContext).getString(AppConstant.SPORTS_ID));
        mountMap.put("league_id", AppPreferences.init(mContext).getString(AppConstant.LEAGUE_ID));
        postMountOnServer(mountMap);
    }

    protected void createMap() {
        Map<String, String> mountMap = new HashMap<>();
        mountMap.put("id", strBroadcastId);
        mountMap.put("msg", strFollowMsg);
        followNotificationMessage(mountMap);

    }

    private void followNotificationMessage(Map<String, String> mountMap) {
        if (ConnectivityReceivers.isConnected()) {
            App.getApiHelper().followNotification(mountMap, new ApiCallBack<Map>() {
                @Override
                public void onSuccess(Map map) {

                }

                @Override
                public void onFailure(String message) {

                }
            });
        }

    }


   /* @OnClick(R.id.iv_sharefacebook)
    public void shareOnFacebook() {
        if (ConnectivityReceivers.isConnected()) {
            shareUrl = ApiConstants.SHARE_BASE_URL + strStream;
//        AppPreferences.init(mContext).putString(AppConstant.User_CURRENT_STATE, "3");
            if (getIntent().getStringExtra("userComingFrom") != null) {
                if ((getIntent().getStringExtra("userComingFrom")).equalsIgnoreCase("matchList")) {
                    if (getIntent().getSerializableExtra("mBrDatum") != null) {
                        BroadcastMatchlistModel.Datum mBrDatum = (BroadcastMatchlistModel.Datum) getIntent().getSerializableExtra("mBrDatum");
                        status = "I'm live on Pundit now discussing the game between " + mBrDatum.getTeam1Name() + " Vs " + mBrDatum.getTeam2Name() + ", come join me.";
                        ShareDialog shareDialog;
                        shareDialog = new ShareDialog(mContext);
                        ShareLinkContent content = new ShareLinkContent.Builder()
                                .setContentUrl(Uri.parse(shareUrl))
                                .setQuote(status)
                                .build();

                        shareDialog.show(content);
                    }
                } else if ((getIntent().getStringExtra("userComingFrom")).equalsIgnoreCase("matchStandingList")) {
                    if (getIntent().getSerializableExtra("mBrDatum") != null) {
                        MatchStandingListModel.Datum mBrDatum = (MatchStandingListModel.Datum) getIntent().getSerializableExtra("mBrDatum");
                        status = "I'm live on Pundit now discussing " + mBrDatum.getContestantClubName() + ", come join me.";
                        ShareDialog shareDialog;
                        shareDialog = new ShareDialog(mContext);
                        ShareLinkContent content = new ShareLinkContent.Builder()
                                .setContentUrl(Uri.parse(shareUrl))
                                .setQuote(status)
                                .build();

                        shareDialog.show(content);
                    }
                }
            }
        }
    }*/

    @OnClick(R.id.rl_share_tile)
    public void onShare() {
        if (ConnectivityReceivers.isConnected()) {
            String encodedUserId = Base64.encodeToString(AppPreferences.init(mContext).getString(USER_ID).getBytes(), Base64.NO_WRAP);
            String username = AppPreferences.init(mContext).getString(USER_NAME);
            username = username.replace(" ", "");
            String shareUrl = ApiConstants.SHARE_BASE_URL + username + "-" + encodedUserId;
//            AppPreferences.init(mContext).putString(AppConstant.User_CURRENT_STATE, "3");
            if (getIntent().getStringExtra("userComingFrom") != null) {
                if ((getIntent().getStringExtra("userComingFrom")).equalsIgnoreCase("matchList")) {
                    if (getIntent().getSerializableExtra("mBrDatum") != null) {
                        BroadcastMatchlistModel.Datum mBrDatum = (BroadcastMatchlistModel.Datum) getIntent().getSerializableExtra("mBrDatum");
                        status = "I'm live on Pundit now discussing the game between " + mBrDatum.getTeam1Name() + " Vs " + mBrDatum.getTeam2Name() + ", come join me.";
                        if (!mBrDatum.getTeam1_twitter_id().equalsIgnoreCase("") && !mBrDatum.getTeam2__twitter_id().equalsIgnoreCase("")) {
                            twitterStatus = "Twitter: @" + mBrDatum.getTeam1_twitter_id() + " @" + mBrDatum.getTeam2__twitter_id();
                        } else
                            twitterStatus = "";
                    }
                } else if ((getIntent().getStringExtra("userComingFrom")).equalsIgnoreCase("matchStandingList")) {
                    if (getIntent().getSerializableExtra("mBrDatum") != null) {
                        MatchStandingListModel.Datum mBrDatum = (MatchStandingListModel.Datum) getIntent().getSerializableExtra("mBrDatum");
                        status = "I'm live on Pundit now discussing " + mBrDatum.getContestantClubName() + ", come join me.";
                        if (!mBrDatum.getTwitter_id().equalsIgnoreCase("")) {
                            twitterStatus = "Twitter: @" + mBrDatum.getTwitter_id();
                        } else {
                            twitterStatus = "";
                        }
                    }
                }
            }


            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, "Pundit");
            if (twitterStatus.equalsIgnoreCase(""))
                i.putExtra(Intent.EXTRA_TEXT, status + "\n" + shareUrl);
            else
                i.putExtra(Intent.EXTRA_TEXT, twitterStatus+"\n"+status + "\n" + shareUrl);
            startActivity(Intent.createChooser(i, "Share Via...."));
        }


            /*
            List<ResolveInfo> resInfo = getPackageManager()
                    .queryIntentActivities(i, 0);
            if (!resInfo.isEmpty()) {
                for (ResolveInfo info : resInfo) {
                    if (info.activityInfo.packageName.toLowerCase().contains("twitter")) {
                        *//*ShareDialog shareDialog;
                        shareDialog = new ShareDialog(mContext);
                        ShareLinkContent content = new ShareLinkContent.Builder()
                                .setContentUrl(Uri.parse(shareUrl))
                                .setQuote(status)
                                .build();
                        shareDialog.show(content);*//*
                        i.putExtra(Intent.EXTRA_TEXT, shareUrl + "\n" + status);
                        targetedShareIntents.add(i);
                    }
                }
                Intent chooserIntent = Intent.createChooser(
                        targetedShareIntents.remove(0), "Share via....");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS,
                        targetedShareIntents.toArray(new Parcelable[]{}));
                i.putExtra(Intent.EXTRA_TEXT, shareUrl + "\n" + status);
                startActivity(chooserIntent);

            }*/

            /*Intent emailIntent = new Intent();
            emailIntent.setAction(Intent.ACTION_SEND);
            // Native email client doesn't currently support HTML, but it doesn't hurt to try in case they fix it
            emailIntent.putExtra(Intent.EXTRA_TEXT, shareUrl + "\n" + status);
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Pundit");
            emailIntent.setType("message/rfc822");

            PackageManager pm = getPackageManager();
            Intent sendIntent = new Intent(Intent.ACTION_SEND);
            sendIntent.setType("text/plain");
            Intent openInChooser = Intent.createChooser(emailIntent,"Share via....");

            List<ResolveInfo> resInfo = pm.queryIntentActivities(sendIntent, 0);
            List<LabeledIntent> intentList = new ArrayList<LabeledIntent>();
            for (int i = 0; i < resInfo.size(); i++) {
                // Extract the label, append it, and repackage it in a LabeledIntent
                ResolveInfo ri = resInfo.get(i);
                String packageName = ri.activityInfo.packageName;
                if (packageName.contains("android.email")) {
                    emailIntent.setPackage(packageName);
                } else if (packageName.contains("twitter") || packageName.contains("facebook") || packageName.contains("mms") || packageName.contains("android.gm")) {
                    Intent intent = new Intent();
                    intent.setComponent(new ComponentName(packageName, ri.activityInfo.name));
                    intent.setAction(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    if (packageName.contains("twitter")) {
                        intent.putExtra(Intent.EXTRA_TEXT, shareUrl + "\n" + twitterStatus);
                    } else if (packageName.contains("facebook")) {
                       *//* ShareDialog shareDialog;
                        shareDialog = new ShareDialog(mContext);
                        ShareLinkContent content = new ShareLinkContent.Builder()
                                .setContentUrl(Uri.parse(shareUrl))
                                .setQuote(status)
                                .build();
                        shareDialog.show(content);*//*
                        intent.putExtra(Intent.EXTRA_TEXT, shareUrl + "\n" + status);
                    } else if (packageName.contains("mms")) {
                        intent.putExtra(Intent.EXTRA_TEXT, shareUrl + "\n" + status);
                    } else if (packageName.contains("android.gm")) { // If Gmail shows up twice, try removing this else-if clause and the reference to "android.gm" above
                        intent.putExtra(Intent.EXTRA_TEXT, shareUrl + "\n" + status);
                        intent.putExtra(Intent.EXTRA_SUBJECT, "Pundit");

                        intent.setType("message/rfc822");
                    }

                    intentList.add(new LabeledIntent(intent, packageName, ri.loadLabel(pm), ri.icon));
                }
            }
            // convert intentList to array
            LabeledIntent[] extraIntents = intentList.toArray(new LabeledIntent[intentList.size()]);
            openInChooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, extraIntents);
            startActivity(openInChooser);
        }*/
    }

    /*@OnClick(R.id.iv_sharetwitter)
    public void shareOnTwitter() {
        if (ConnectivityReceivers.isConnected()) {
            shareUrl = ApiConstants.SHARE_BASE_URL + strStream;
            AppPreferences.init(mContext).putString(AppConstant.User_CURRENT_STATE, "3");
            if (getIntent().getStringExtra("userComingFrom") != null) {
                if ((getIntent().getStringExtra("userComingFrom")).equalsIgnoreCase("matchList")) {
                    if (getIntent().getSerializableExtra("mBrDatum") != null) {
                        BroadcastMatchlistModel.Datum mBrDatum = (BroadcastMatchlistModel.Datum) getIntent().getSerializableExtra("mBrDatum");
                        status = "I'm live on Pundit now discussing the game between " + mBrDatum.getTeam1Name() + " Vs " + mBrDatum.getTeam2Name() + ", come join me.";
                        TweetComposer.Builder builder = null;
                        try {
                            builder = new TweetComposer.Builder(this)
                                    .text(status).url(new URL(shareUrl));
                            builder.show();
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }

                    }
                } else if ((getIntent().getStringExtra("userComingFrom")).equalsIgnoreCase("matchStandingList")) {
                    if (getIntent().getSerializableExtra("mBrDatum") != null) {
                        MatchStandingListModel.Datum mBrDatum = (MatchStandingListModel.Datum) getIntent().getSerializableExtra("mBrDatum");
                        status = "I'm live on Pundit now discussing " + mBrDatum.getContestantClubName() + ", come join me.";
                        TweetComposer.Builder builder = null;
                        try {
                            builder = new TweetComposer.Builder(this)
                                    .text(status).url(new URL(shareUrl));
                            builder.show();
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
        }
    }*/

  /*  public static void stopBroadcastAppBackground() {
        if (stream != null) {
            if (mContext != null) {
                new LiveBroadCastingActivity().unmountUser();
                if (!mContext.isFinishing())
                    mContext.finish();
            }
        }
    }*/

    private void onIncommingCallBroadcast() {
        PhoneStateListener phoneStateListener = new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                if (state == TelephonyManager.CALL_STATE_RINGING) {
                    if (stream != null) {
                        if (mContext != null) {
                            new LiveBroadCastingActivity().unmountUser();
                            if (!mContext.isFinishing())
                                mContext.finish();
                        }
                    }
                }
                if (state == TelephonyManager.CALL_STATE_IDLE) {
                }
                if (state == TelephonyManager.CALL_STATE_OFFHOOK) {
                    if (stream != null) {
                        if (mContext != null) {
                            new LiveBroadCastingActivity().unmountUser();
                            if (!mContext.isFinishing())
                                mContext.finish();
                        }
                    }
                }
                super.onCallStateChanged(state, incomingNumber);
            }
        };
        TelephonyManager mgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        if (mgr != null) {
            mgr.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
        }
    }

    @OnClick(R.id.iv_edit_score)
    public void showScoreBoard() {
        if (visible == 0) {
            visible = 1;
            String[] data = new String[20];
            for (int i = 0; i < data.length; i++) {
                data[i] = String.valueOf(i);
            }
            npTeam1Score.setMinValue(0);
            npTeam2Score.setMinValue(0);
            npTeam1Score.setMaxValue(data.length - 1);
            npTeam2Score.setMaxValue(data.length - 1);
            npTeam1Score.setDisplayedValues(data);
            npTeam2Score.setDisplayedValues(data);
            rlScoreBoard.setVisibility(View.VISIBLE);
            rlLiveBroadcastingMain.setFocusableInTouchMode(false);
            rlScoreBoard.animate()
                    .translationYBy(rlScoreBoard.getHeight())
                    .translationY(0)
                    .setDuration(10000)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            rlScoreBoard.animate().setListener(null);
                        }
                    });
            tvTeam1EditedScore.setText(npTeam1Score.getValue() + "");
            tvTeam2EditedScore.setText(npTeam2Score.getValue() + "");
            npTeam1Score.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    Score_team1 = "" + newVal;
                    tvTeam1EditedScore.setText(Score_team1);
                }
            });
            npTeam2Score.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    Score_team2 = "" + newVal;
                    tvTeam2EditedScore.setText(Score_team2);
                }
            });
            Score_team1 = npTeam1Score.getValue() + "";
            Score_team2 = npTeam2Score.getValue() + "";
        } else if (visible == 1) {
            visible = 0;
            rlLiveBroadcastingMain.setFocusableInTouchMode(true);
            rlScoreBoard.setVisibility(View.GONE);
            rlScoreBoard.animate()
                    .translationYBy(rlScoreBoard.getHeight())
                    .translationY(0)
                    .setDuration(10000)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            rlScoreBoard.animate().setListener(null);
                        }
                    });
        }
    }

    @OnClick(R.id.rl_live_broadcasting_main)
    public void hideScore() {
        if (visible == 1) {
            visible = 0;
            rlLiveBroadcastingMain.setFocusableInTouchMode(true);
            rlScoreBoard.setVisibility(View.GONE);
            rlScoreBoard.animate()
                    .translationYBy(rlScoreBoard.getHeight())
                    .translationY(0)
                    .setDuration(10000)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            rlScoreBoard.animate().setListener(null);
                        }
                    });
        }
    }


    @OnClick(R.id.tv_done)
    public void hideScoreBoard() {
        updateTeamsScore();
        visible = 0;
        rlScoreBoard.setVisibility(View.GONE);
        rlScoreBoard.animate()
                .translationYBy(rlScoreBoard.getHeight())
                .translationY(0)
                .setDuration(10000)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        rlScoreBoard.animate().setListener(null);
                    }
                });

    }

    private void updateTeamsScore() {
        Map<String, String> updateScoreMap = new HashMap<>();
        updateScoreMap.put("match_id", strMatchId);
        updateScoreMap.put("team1_id", team1_id);
        updateScoreMap.put("team2_id", team2_id);
        updateScoreMap.put("team1_score", Score_team1);
        updateScoreMap.put("team2_score", Score_team2);
        App.getApiHelper().updateScore(updateScoreMap, new ApiCallBack<Map>() {
            @Override
            public void onSuccess(Map map) {
                if (map != null) {
                    if ((map.get("Result")) != null) {
                        tvTeam1Score.setText(((LinkedTreeMap) (map.get("Result"))).get("team1_score").toString());
                        tvTeam2Score.setText(((LinkedTreeMap) (map.get("Result"))).get("team2_score").toString());
                        SnackbarUtil.showSuccessLongSnackbar(mContext, getString(R.string.update_success_scores));
                    }

                }
            }

            @Override
            public void onFailure(String message) {
                SnackbarUtil.showErrorLongSnackbar(mContext, getString(R.string.fb_error_message));
            }
        });


    }

    @OnClick(R.id.rl_chat_tile)
    public void onClickChatBroadcast() {
        Intent intent = new Intent(mContext, ConversationActivity.class);
        intent.putExtra(ConversationUIService.GROUP_ID, Integer.parseInt(chatChannelId));
        intent.putExtra(ConversationUIService.TAKE_ORDER,true);
        startActivity(intent);
    }
}




