package com.softuvo.ipundit.activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.red5pro.streaming.R5Connection;
import com.red5pro.streaming.R5Stream;
import com.red5pro.streaming.R5StreamProtocol;
import com.red5pro.streaming.config.R5Configuration;
import com.red5pro.streaming.source.R5Microphone;
import com.softuvo.ipundit.R;
import com.softuvo.ipundit.adapters.LiveFeedsAdapter;
import com.softuvo.ipundit.api.ApiCallBack;
import com.softuvo.ipundit.config.ApiConstants;
import com.softuvo.ipundit.config.App;
import com.softuvo.ipundit.config.AppConstant;
import com.softuvo.ipundit.config.AppPreferences;
import com.softuvo.ipundit.models.BroadcastMatchlistModel;
import com.softuvo.ipundit.models.ListnerCountModel;
import com.softuvo.ipundit.models.LiveBroadcstingModel;
import com.softuvo.ipundit.models.LiveFeedsModel;
import com.softuvo.ipundit.models.MatchStandingListModel;
import com.softuvo.ipundit.receivers.ConnectivityReceivers;
import com.softuvo.ipundit.utils.SnackbarUtil;
import com.softuvo.ipundit.views.CustomGifImageView;
import com.softuvo.ipundit.views.CustomLinearLayout;
import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.softuvo.ipundit.config.AppConstant.APP_BACKGROUND;
import static com.softuvo.ipundit.config.AppConstant.USER_NAME;

public class LiveBroadCastingActivity extends BaseActivity {
    private static Activity mContext;
    private int minutes = 0, seconds = 0;
    private String timeStamp, strName, strMatchId, strBroadcastName, strBroadcastId, strStreamName, strStream, strAppName, strChannelType, shareUrl, team1Score, team2Score, status;
    private static R5Stream stream;
    private LiveFeedsAdapter liveFeedsAdapter;
    private static String channelId;
    private CallbackManager callbackManager;

    @BindView(R.id.ll_live_broadcasting_main)
    CustomLinearLayout llLiveBroadcastingMain;

    @BindView(R.id.tv_match_name_top)
    TextView tvMatcNameTop;

    @BindView(R.id.tv_match_time_top)
    TextView tvMatchTimeTop;

    @BindView(R.id.tv_broadcsaters_name)
    TextView tvBroadcsatersName;

    @BindView(R.id.tv_kickoff_time_top)
    TextView tvKickoffTimeTop;

    @BindView(R.id.iv_league_icon)
    ImageView ivLeagueIcon;

    @BindView(R.id.tv_team1_score)
    TextView tvTeam1Score;

    @BindView(R.id.tv_team1_name)
    TextView tvTeam1Name;

    @BindView(R.id.tv_team2_name)
    TextView tvTeam2Name;

    @BindView(R.id.tv_team2_score)
    TextView tvTeam2Score;

    @BindView(R.id.iv_sharefacebook)
    ImageView ivShareFacebook;

    @BindView(R.id.iv_sharetwitter)
    ImageView ivShareTwitter;

    @BindView(R.id.tv_team1name_vs_team2name)
    TextView tvTeam1nameVsTeam2name;

    @BindView(R.id.tv_team1score_vs_team2score)
    TextView tvTeam1scoreVsTeam2score;

    @BindView(R.id.tv_match_kickoff_time)
    TextView tvMatchKickoffTime;

    @BindView(R.id.tv_listners_count)
    TextView tvListnersCount;

    @BindView(R.id.tv_no_deta_br)
    TextView tvNo_DataBr;

    @BindView(R.id.gif_view)
    CustomGifImageView gifView;

    @BindView(R.id.progress_bar_live_broadcasting)
    ProgressBar progressarLiveBroadcasting;

    @BindView(R.id.rv_live_feeds)
    RecyclerView rvLiveFeeds;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_broad_casting);
        mContext = LiveBroadCastingActivity.this;
        ButterKnife.bind(mContext);
        setData();
        onIncommingCallBroadcast();
    }

    private void setData() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        timeStamp = new Random().nextLong() + "";
        if (timeStamp.contains("-")) {
            String timeCheck[] = timeStamp.split("-");
            timeStamp = timeCheck[0];
        }
        if(AppPreferences.init(mContext).getString(APP_BACKGROUND)!=null)
        Picasso.with(mContext).load(AppPreferences.init(mContext).getString(APP_BACKGROUND)).into(llLiveBroadcastingMain);
        AppPreferences.init(mContext).putString(AppConstant.User_CURRENT_STATE, "1");
        if (getIntent().getStringExtra("userComingFrom") != null) {
            String mUserComingFrom;
            if ((getIntent().getStringExtra("userComingFrom")).equalsIgnoreCase("matchList")) {
                mUserComingFrom = "match";
                if (getIntent().getSerializableExtra("mBrDatum") != null) {
                    BroadcastMatchlistModel.Datum mBrDatum = (BroadcastMatchlistModel.Datum) getIntent().getSerializableExtra("mBrDatum");
                    tvMatcNameTop.setText(mBrDatum.getTeam1Name() + " Vs " + mBrDatum.getTeam2Name());
                    tvTeam1nameVsTeam2name.setText(mBrDatum.getTeam1Name() + " Vs " + mBrDatum.getTeam2Name());
                    tvTeam1Name.setText(mBrDatum.getTeam1Name() + ":");
                    tvTeam2Name.setText(mBrDatum.getTeam2Name() + ":");
//                    strStation = "broadcast-" + AppPreferences.init(mContext).getString(AppConstant.USER_ID) + "-" + mBrDatum.getMatchId();
                    strName = mBrDatum.getTeam1Name() + " Vs " + mBrDatum.getTeam2Name() + "-" + AppPreferences.init(mContext).getString(AppConstant.USER_NAME);
                    strMatchId = mBrDatum.getMatchId();
                    strBroadcastName = AppPreferences.init(mContext).getString(USER_NAME);
                    strBroadcastId = AppPreferences.init(mContext).getString(AppConstant.USER_ID);
                    strAppName = "live";
                    strChannelType = mUserComingFrom;
                    strStreamName = AppPreferences.init(mContext).getString(AppConstant.USER_ID) + "-" + mBrDatum.getMatchId() + "-" + timeStamp;
                }
                getLiveFeedsFromServer(strMatchId);
            } else if ((getIntent().getStringExtra("userComingFrom")).equalsIgnoreCase("matchStandingList")) {
                mUserComingFrom = "team";
                if (getIntent().getSerializableExtra("mBrDatum") != null) {
                    MatchStandingListModel.Datum mBrDatum = (MatchStandingListModel.Datum) getIntent().getSerializableExtra("mBrDatum");
                    tvMatcNameTop.setText(mBrDatum.getContestantName());
                    tvMatchTimeTop.setText("Rank:" + mBrDatum.getRank());
                    tvTeam1nameVsTeam2name.setText(mBrDatum.getContestantName());
                    tvTeam1Name.setText(mBrDatum.getContestantClubName());
                    tvTeam2Name.setText("Points : " + mBrDatum.getPoints());
                    tvTeam1scoreVsTeam2score.setText("");
//                    strStation = "broadcast-" + AppPreferences.init(mContext).getString(AppConstant.USER_ID) + "-" + mBrDatum.getContestantId();
                    strName = mBrDatum.getContestantName() + "-" + AppPreferences.init(mContext).getString(AppConstant.USER_NAME);
                    strMatchId = mBrDatum.getContestantId();
                    strBroadcastName = AppPreferences.init(mContext).getString(USER_NAME);
                    strBroadcastId = AppPreferences.init(mContext).getString(AppConstant.USER_ID);
                    strAppName = "live";
                    strChannelType = mUserComingFrom;
                    strStreamName = AppPreferences.init(mContext).getString(AppConstant.USER_ID) + "-" + mBrDatum.getContestantId() + "-" + timeStamp;
                    tvNo_DataBr.setVisibility(View.VISIBLE);
                    tvNo_DataBr.setText(R.string.team_talk);
            }
            }
            if (AppPreferences.init(mContext).getString(AppConstant.LEAGUE_IMAGE_URL) != null && !AppPreferences.init(mContext).getString(AppConstant.LEAGUE_IMAGE_URL).equalsIgnoreCase(""))
                Picasso.with(mContext).load(AppPreferences.init(mContext).getString(AppConstant.LEAGUE_IMAGE_URL)).into(ivLeagueIcon);
            String broadcasterName = "Logged in as " + AppPreferences.init(mContext).getString(USER_NAME);
            gifView.setGifImageResource(R.drawable.broadcast_gif);
            tvBroadcsatersName.setText(broadcasterName);
            publish();
        }
    }

    private void setkickofftime() {
        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvKickoffTimeTop.setText(String.valueOf(minutes) + ":" + String.valueOf(seconds));
                        tvMatchKickoffTime.setText(String.valueOf(minutes) + ":" + String.valueOf(seconds));
                        seconds += 1;
                        if (seconds == 60) {
                            tvKickoffTimeTop.setText(String.valueOf(minutes) + ":" + String.valueOf(seconds));
                            tvMatchKickoffTime.setText(String.valueOf(minutes) + ":" + String.valueOf(seconds));
                            seconds = 0;
                            minutes = minutes + 1;
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
                        configRedPro();
                        SnackbarUtil.showSuccessLongSnackbar(mContext, getString(R.string.start_broadcast_success_msg));
                        setkickofftime();
                        channelId = map.getChannelid().toString();
                        stream.publish(map.getData().getStreamName(), R5Stream.RecordType.Live);
                        strStream = map.getData().getStreamName();
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

    @Override
    public void onBackPressed() {
        openCancelDialog();
    }

    @Override
    public void onStop() {
        super.onStop();
        if(AppPreferences.init(mContext).getString(AppConstant.User_CURRENT_STATE).equalsIgnoreCase("3")){
//            Do Nothing
        }
        else{
            unmountUser();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        AppPreferences.init(mContext).putString(AppConstant.User_CURRENT_STATE, "1");
    }

    private void unmountUser() {
        if (ConnectivityReceivers.isConnected()) {
            App.getApiHelper().unmountOnServer(channelId, new ApiCallBack<Map>() {
                @Override
                public void onSuccess(Map map) {
                    if (map != null) {
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
//            SnackbarUtil.showWarningLongSnackbar(mContext, getResources().getString(R.string.internet_not_connected_text));
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
                                if(listnerCountModel!= null)
                                    tvListnersCount.setText("LISTENERS :-" + listnerCountModel.getCount());
                            }
                            @Override
                            public void onFailure(String message) {

                            }
                        });
                    }
                });
            }
        }, 0, 5000);
    }

    private void getLiveFeedsFromServer(final String matchId) {
        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (ConnectivityReceivers.isConnected()) {
                            App.getApiHelper().getLiveFeeds(matchId, new ApiCallBack<LiveFeedsModel>() {
                                @Override
                                public void onSuccess(LiveFeedsModel liveFeedsModel) {
                                    if (liveFeedsModel != null) {
                                        if (liveFeedsModel.getMatchinfo().getMatchStatus() != null) {
                                            if (liveFeedsModel.getMatchinfo().getMatchStatus().equalsIgnoreCase("Fixture")) {
                                                tvMatchTimeTop.setText(liveFeedsModel.getMatchinfo().getMatchStatus());
                                            } else if (liveFeedsModel.getMatchinfo().getMatchStatus().equalsIgnoreCase("Played")) {
                                                tvMatchTimeTop.setText(getString(R.string.ft_string));
                                            } else if (liveFeedsModel.getMatchinfo().getMatchStatus().equalsIgnoreCase("Playing")) {
                                                tvMatchTimeTop.setText(liveFeedsModel.getMatchinfo().getMatchLengthMin() + ":" + liveFeedsModel.getMatchinfo().getMatchLengthSec());
                                            }
                                            if (liveFeedsModel.getMatchinfo().getTeam1Score() == null || liveFeedsModel.getMatchinfo().getTeam1Score().equalsIgnoreCase(""))
                                                team1Score = "N/A";
                                            else
                                                team1Score = liveFeedsModel.getMatchinfo().getTeam1Score();
                                            tvTeam1Score.setText(team1Score);
                                            if (liveFeedsModel.getMatchinfo().getTeam2Score() == null || liveFeedsModel.getMatchinfo().getTeam2Score().equalsIgnoreCase(""))
                                                team2Score = "N/A";
                                            else
                                                team2Score = liveFeedsModel.getMatchinfo().getTeam2Score();
                                            tvTeam2Score.setText(team2Score);
                                            tvTeam1scoreVsTeam2score.setText(team1Score + ":" + team2Score);
                                            List<LiveFeedsModel.Feed> liveFeedsList = liveFeedsModel.getFeeds();
                                            if (liveFeedsList.size() > 0) {
                                                tvNo_DataBr.setVisibility(View.GONE);
                                                liveFeedsAdapter = new LiveFeedsAdapter(mContext, liveFeedsList);
                                                rvLiveFeeds.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
                                                rvLiveFeeds.setAdapter(liveFeedsAdapter);
                                            } else {
                                                tvNo_DataBr.setVisibility(View.GONE);
                                                tvNo_DataBr.setText(R.string.team_talk);
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
        }, 0, 10000);
    }

    private void configRedPro() {
        R5Configuration configuration = new R5Configuration(R5StreamProtocol.RTSP, AppConstant.RED5PRO_SERVER_IP, AppConstant.RED5PRO_SERVER_PORT, AppConstant.RED5PRO_SERVER_APP_NAME, AppConstant.RED5PRO_SERVER_CASHE);
        configuration.setLicenseKey(AppConstant.RED5PRO_LICENSE_KEY);
        configuration.setBundleID(mContext.getPackageName());
        stream = new R5Stream(new R5Connection(configuration));
        R5Microphone r5Microphone = new R5Microphone();
        stream.attachMic(r5Microphone);
    }

    protected void publish() {
        Map<String, String> mountMap = new HashMap<>();
        mountMap.put("name", strName);
        mountMap.put("match_id", strMatchId);
        mountMap.put("broadcaster_name", strBroadcastName);
        mountMap.put("broadcaster_id", strBroadcastId);
//        mountMap.put("station", strStation);
        mountMap.put("appName", strAppName);
        mountMap.put("channel_type", strChannelType);
        mountMap.put("sport_id",AppPreferences.init(mContext).getString(AppConstant.SPORTS_ID));
        mountMap.put("league_id",AppPreferences.init(mContext).getString(AppConstant.LEAGUE_ID));
        postMountOnServer(mountMap);
    }


    @OnClick(R.id.iv_sharefacebook)
    public void shareOnFacebook() {
        shareUrl = ApiConstants.SHARE_BASE_URL + strStream;
//        AppPreferences.init(mContext).putString(AppConstant.User_CURRENT_STATE, "3");
        if (getIntent().getStringExtra("userComingFrom") != null) {
            if ((getIntent().getStringExtra("userComingFrom")).equalsIgnoreCase("matchList")) {
                if (getIntent().getSerializableExtra("mBrDatum") != null) {
                    BroadcastMatchlistModel.Datum mBrDatum = (BroadcastMatchlistModel.Datum) getIntent().getSerializableExtra("mBrDatum");
                    status = "I'm live on Pundit now discussing the game between " + mBrDatum.getTeam1Name() + " Vs " + mBrDatum.getTeam2Name() + ", come join me.";
                    callbackManager = CallbackManager.Factory.create();
                    ShareDialog shareDialog;
                    shareDialog = new ShareDialog(mContext);
                    shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>(){

                        @Override
                        public void onSuccess(Sharer.Result result) {
                            Toast.makeText(mContext,"Posted",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancel() {

                        }

                        @Override
                        public void onError(FacebookException error) {

                        }
                    });

                    if (ShareDialog.canShow(ShareLinkContent.class)) {
                        ShareLinkContent content = new ShareLinkContent.Builder()
                                .setContentUrl(Uri.parse(shareUrl))
                                .setQuote(status)
                                .build();
                        shareDialog.show(content);
                    }

                }
            } else if ((getIntent().getStringExtra("userComingFrom")).equalsIgnoreCase("matchStandingList")) {
                if (getIntent().getSerializableExtra("mBrDatum") != null) {
                    MatchStandingListModel.Datum mBrDatum = (MatchStandingListModel.Datum) getIntent().getSerializableExtra("mBrDatum");
                    status = "I'm live on Pundit now discussing " + mBrDatum.getContestantClubName() + ", come join me.";
                    callbackManager = CallbackManager.Factory.create();
                    ShareDialog shareDialog;
                    shareDialog = new ShareDialog(mContext);
                    shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>(){

                        @Override
                        public void onSuccess(Sharer.Result result) {
                            Toast.makeText(mContext,"Posted",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancel() {

                        }

                        @Override
                        public void onError(FacebookException error) {

                        }
                    });

                    if (ShareDialog.canShow(ShareLinkContent.class)) {
                        ShareLinkContent content = new ShareLinkContent.Builder()
                                .setContentUrl(Uri.parse(shareUrl))
                                .setQuote(status)
                                .build();
                        shareDialog.show(content);
                    }
                }
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @OnClick(R.id.iv_sharetwitter)
    public void shareOnTwitter() {
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
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    builder.show();
                }
            } else if ((getIntent().getStringExtra("userComingFrom")).equalsIgnoreCase("matchStandingList")) {
                if (getIntent().getSerializableExtra("mBrDatum") != null) {
                    MatchStandingListModel.Datum mBrDatum = (MatchStandingListModel.Datum) getIntent().getSerializableExtra("mBrDatum");
                    status = "I'm live on Pundit now discussing " + mBrDatum.getContestantClubName() + ", come join me.";
                    TweetComposer.Builder builder = null;
                    try {
                        builder = new TweetComposer.Builder(this)
                                .text(status).url(new URL(shareUrl));
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    builder.show();
                }
            }
        }
    }

    public static void stopBroadcastAppBackground() {
        if (stream != null) {
//            stream.stop();
            if (mContext != null) {
                new LiveBroadCastingActivity().unmountUser();
                if (!mContext.isFinishing())
                    mContext.finish();
            }
        }
    }

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
        if(mgr != null) {
            mgr.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
        }
    }
}




