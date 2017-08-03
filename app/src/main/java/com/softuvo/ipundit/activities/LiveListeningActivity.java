package com.softuvo.ipundit.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
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
import com.softuvo.ipundit.R;
import com.softuvo.ipundit.adapters.LiveFeedsAdapter;
import com.softuvo.ipundit.api.ApiCallBack;
import com.softuvo.ipundit.config.ApiConstants;
import com.softuvo.ipundit.config.App;
import com.softuvo.ipundit.config.AppConstant;
import com.softuvo.ipundit.config.AppPreferences;
import com.softuvo.ipundit.models.BroadacstersDetailsModel;
import com.softuvo.ipundit.models.FollowCheckModel;
import com.softuvo.ipundit.models.FollowUnfollowModel;
import com.softuvo.ipundit.models.LiveBroacastersListModel;
import com.softuvo.ipundit.models.LiveFeedsModel;
import com.softuvo.ipundit.models.MatchListListnerModel;
import com.softuvo.ipundit.models.StandingListeningModel;
import com.softuvo.ipundit.models.TeamSearchSportsModel;
import com.softuvo.ipundit.models.UserDetailsAndMatchDetailsModel;
import com.softuvo.ipundit.models.UserProfileResponseModel;
import com.softuvo.ipundit.models.UserSearchLeagueModel;
import com.softuvo.ipundit.models.UserSearchSportsModel;
import com.softuvo.ipundit.receivers.ConnectivityReceivers;
import com.softuvo.ipundit.utils.SnackbarUtil;
import com.softuvo.ipundit.views.CustomBlurImage;
import com.softuvo.ipundit.views.CustomGifImageView;
import com.softuvo.ipundit.views.CustomLinearLayout;
import com.softuvo.ipundit.views.CustomRelativeLayout;
import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.softuvo.ipundit.config.AppConstant.APP_BACKGROUND;

public class LiveListeningActivity extends BaseActivity {
    private static Activity mContext;
    private LiveFeedsAdapter liveFeedsAdapter;
    public static R5Stream subscribe;
    private LiveBroacastersListModel.Channel channel;
    private R5Configuration configuration;
    private R5Connection connection;
    private Timer t;
    private static String listenerId;
    private String shareUrl, userEmail, team1Score, team2Score, matchContenstentId, followUnfoloowPath, getfollowunfollowpath, status, broadcasterId, channelId, streamName;
    private int followStatus, strMin = 0, strSec = 0, playing = 0;
    private CallbackManager callbackManager;

    @BindView(R.id.rl_live_listening_main)
    CustomRelativeLayout rlLiveListeningMain;

    @BindView(R.id.txtMatchNameTop)
    TextView txtMatchNameTop;

    @BindView(R.id.txtMatchTimeTop)
    TextView txtMatchTimeTop;

    @BindView(R.id.txt_broadcaster_name)
    TextView txtBroadcasterName;

    @BindView(R.id.txtkickoffTime)
    TextView txtkickoffTime;

    @BindView(R.id.tv_team1_score)
    TextView tvTeam1Score;

    @BindView(R.id.tv_team1_name)
    TextView tvTeam1Name;

    @BindView(R.id.tv_team2_name)
    TextView tvTeam2Name;

    @BindView(R.id.tv_team2_score)
    TextView tvTeam2Score;

    @BindView(R.id.iv_league_logo)
    ImageView ivLeagueLogo;

    @BindView(R.id.iv_switch_broacaster)
    ImageView ivSwitchBroacaster;

    @BindView(R.id.iv_play_pause)
    ImageView ivPlayPause;

    @BindView(R.id.iv_live)
    ImageView ivLive;

    @BindView(R.id.iv_fb_share)
    ImageView ivFbShare;

    @BindView(R.id.iv_twitter_share)
    ImageView ivTwitterShare;

    @BindView(R.id.rv_livefeeds_container1)
    RecyclerView rvLivefeedsContainer;

    @BindView(R.id.streaming_gif)
    CustomGifImageView streamingGif;

    @BindView(R.id.rl_blur_background)
    CustomLinearLayout rlBlurBackground;

    @BindView(R.id.civ_user_profile_picture)
    CircleImageView civUserProfilePicture;

    @BindView(R.id.tv_follower_count)
    TextView tvFollowerCount;

    @BindView(R.id.tv_hash_tags)
    TextView tvHashTags;

    @BindView(R.id.tv_user_name)
    TextView tvUserName;

    @BindView(R.id.tv_user_bio)
    TextView tvUserBio;

    @BindView(R.id.tv_no_deta)
    TextView tvNoData;

    @BindView(R.id.btn_follow_unfollow)
    Button btnFollowUnfollow;

    @BindView(R.id.btn_redirct_broadcasters_profile)
    Button btnRedirctBroadcastersProfile;

    @BindView(R.id.btn_follow_unfollow_bg)
    Button btnFollowUnfollowBg;

    @BindView(R.id.btn_redirct_broadcasters_profile_bg)
    Button btnRedirctBroadcastersProfileBg;


    @BindView(R.id.btn_show_profile)
    Button btnShowProfile;

    @BindView(R.id.progress_bar_live_listen)
    ProgressBar progressBarLiveListen;

    @BindView(R.id.btn_hide_profile)
    Button btnHideProfile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_listening);
        mContext = LiveListeningActivity.this;
        ButterKnife.bind(mContext);
        setData();
    }

    private void setData() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        t = new Timer();
        Picasso.with(mContext).load(AppPreferences.init(mContext).getString(APP_BACKGROUND)).into(rlLiveListeningMain);
        AppPreferences.init(mContext).putString(AppConstant.User_CURRENT_STATE, "2");
        streamingGif.setGifImageResource(R.drawable.listning_gif);
        if (getIntent().getStringExtra("userComingFrom").equalsIgnoreCase("matchList")) {
            if (getIntent().getSerializableExtra("mMatchDatum") != null) {
                if (AppPreferences.init(mContext).getString(AppConstant.LEAGUE_IMAGE_URL) != null)
                    Picasso.with(mContext).load(AppPreferences.init(mContext).getString(AppConstant.LEAGUE_IMAGE_URL)).into(ivLeagueLogo);
                MatchListListnerModel.ListenMatchList matchDatum = (MatchListListnerModel.ListenMatchList) getIntent().getSerializableExtra("mMatchDatum");
                channel = (LiveBroacastersListModel.Channel) getIntent().getSerializableExtra("mBrListDatum");
                matchContenstentId = matchDatum.getMatchId();
                tvTeam1Name.setText(matchDatum.getTeam1Name() + ":");
                tvTeam2Name.setText(matchDatum.getTeam2Name() + ":");
                txtMatchNameTop.setText(matchDatum.getTeam1Name() + " Vs " + matchDatum.getTeam2Name());
                txtBroadcasterName.setText("BroadCasting this Game:" + channel.getBroadcasterName());
                broadcasterId = channel.getBroadcasterId();
                channelId = channel.getId();
                streamName = channel.getStreamName();
                configRedPro(streamName);
                getLiveFeedsFromServer(matchContenstentId);

            }
        } else if (getIntent().getStringExtra("userComingFrom").equalsIgnoreCase("matchStandingListenList")) {
            if (getIntent().getSerializableExtra("mMatchDatum") != null) {
                if (AppPreferences.init(mContext).getString(AppConstant.LEAGUE_IMAGE_URL) != null)
                    Picasso.with(mContext).load(AppPreferences.init(mContext).getString(AppConstant.LEAGUE_IMAGE_URL)).into(ivLeagueLogo);
                StandingListeningModel.Datum matchDatum = (StandingListeningModel.Datum) getIntent().getSerializableExtra("mMatchDatum");
                matchContenstentId = matchDatum.getContestantId();
                channel = (LiveBroacastersListModel.Channel) getIntent().getSerializableExtra("mBrListDatum");
                txtMatchNameTop.setText(matchDatum.getContestantName());
                txtMatchTimeTop.setText("Rank:" + matchDatum.getRank());
                txtBroadcasterName.setText("BroadCasting this Game:" + channel.getBroadcasterName());
                tvTeam1Name.setText(matchDatum.getContestantClubName());
                tvTeam2Name.setText("Points: " + matchDatum.getPoints());
                tvNoData.setVisibility(View.VISIBLE);
                tvNoData.setText(R.string.team_talk);
                broadcasterId = channel.getBroadcasterId();
                channelId = channel.getId();
                streamName = channel.getStreamName();
                configRedPro(streamName);
            }
        } else if (getIntent().getStringExtra("userComingFrom").equalsIgnoreCase("pundits")) {
            if (getIntent().getSerializableExtra("mUserDatum") != null) {
                UserDetailsAndMatchDetailsModel.UsersList channell;
                UserDetailsAndMatchDetailsModel.UsersList userDatum = (UserDetailsAndMatchDetailsModel.UsersList) getIntent().getSerializableExtra("mUserDatum");
                if (userDatum.getChannelInfo().get(0).getChannel().getMarkImage() != null)
                    Picasso.with(mContext).load(ApiConstants.LEAGUE_IMAGE_BASE_URL + userDatum.getChannelInfo().get(0).getChannel().getMarkImage()).into(ivLeagueLogo);
                if (userDatum.getChannelInfo().get(0).getChannel() != null) {
                    if (userDatum.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("match")) {
                        channell = (UserDetailsAndMatchDetailsModel.UsersList) getIntent().getSerializableExtra("mUserDatum");
                        matchContenstentId = userDatum.getChannelInfo().get(0).getChannel().getMatchId();
                        tvTeam1Name.setText(userDatum.getChannelInfo().get(0).getMatchInfo().getTeam1Name() + ":");
                        tvTeam2Name.setText(userDatum.getChannelInfo().get(0).getMatchInfo().getTeam2Name() + ":");
                        txtMatchNameTop.setText(userDatum.getChannelInfo().get(0).getMatchInfo().getTeam1Name() + " Vs " + userDatum.getChannelInfo().get(0).getMatchInfo().getTeam2Name());
                        txtBroadcasterName.setText("BroadCasting this Game:" + channell.getChannelInfo().get(0).getChannel().getBroadcasterName());
                        broadcasterId = channell.getChannelInfo().get(0).getChannel().getBroadcasterId();
                        channelId = channell.getChannelInfo().get(0).getChannel().getId();
                        streamName = channell.getChannelInfo().get(0).getChannel().getStreamName();
                        configRedPro(streamName);
                        getLiveFeedsFromServer(matchContenstentId);
                    } else if (userDatum.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("team")) {
                        channell = (UserDetailsAndMatchDetailsModel.UsersList) getIntent().getSerializableExtra("mUserDatum");
                        matchContenstentId = userDatum.getChannelInfo().get(0).getChannel().getMatchId();
                        txtMatchNameTop.setText(userDatum.getChannelInfo().get(0).getTeamInfo().getContestantName());
                        txtMatchTimeTop.setText("Rank:" + userDatum.getChannelInfo().get(0).getTeamInfo().getRank());
                        txtBroadcasterName.setText("BroadCasting this Game:" + userDatum.getChannelInfo().get(0).getChannel().getBroadcasterName());
                        tvTeam1Name.setText(userDatum.getChannelInfo().get(0).getTeamInfo().getContestantClubName());
                        tvTeam2Name.setText("Points: " + userDatum.getChannelInfo().get(0).getTeamInfo().getPoints());
                        broadcasterId = channell.getChannelInfo().get(0).getChannel().getBroadcasterId();
                        channelId = channell.getChannelInfo().get(0).getChannel().getId();
                        tvNoData.setVisibility(View.VISIBLE);
                        tvNoData.setText(R.string.team_talk);
                        streamName = channell.getChannelInfo().get(0).getChannel().getStreamName();
                        configRedPro(streamName);
                    }
                }

            }
        } else if (getIntent().getStringExtra("userComingFrom").equalsIgnoreCase("sprotsUserSearch")) {
            if (getIntent().getSerializableExtra("mUserSearchDatum") != null) {
                UserSearchSportsModel.UserDatum channell;
                UserSearchSportsModel.UserDatum userDatum = (UserSearchSportsModel.UserDatum) getIntent().getSerializableExtra("mUserSearchDatum");
                if (userDatum.getChannelInfo().get(0).getChannel().getMarkImage() != null)
                    Picasso.with(mContext).load(ApiConstants.LEAGUE_IMAGE_BASE_URL + userDatum.getChannelInfo().get(0).getChannel().getMarkImage()).into(ivLeagueLogo);
                if (userDatum.getChannelInfo().get(0).getChannel() != null) {
                    if (userDatum.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("match")) {
                        channell = (UserSearchSportsModel.UserDatum) getIntent().getSerializableExtra("mUserSearchDatum");
                        matchContenstentId = userDatum.getChannelInfo().get(0).getChannel().getMatchId();
                        tvTeam1Name.setText(userDatum.getChannelInfo().get(0).getMatchInfo().getTeam1Name() + ":");
                        tvTeam2Name.setText(userDatum.getChannelInfo().get(0).getMatchInfo().getTeam2Name() + ":");
                        txtMatchNameTop.setText(userDatum.getChannelInfo().get(0).getMatchInfo().getTeam1Name() + " Vs " + userDatum.getChannelInfo().get(0).getMatchInfo().getTeam2Name());
                        txtBroadcasterName.setText("BroadCasting this Game:" + channell.getChannelInfo().get(0).getChannel().getBroadcasterName());
                        broadcasterId = channell.getChannelInfo().get(0).getChannel().getBroadcasterId();
                        channelId = channell.getChannelInfo().get(0).getChannel().getId();
                        streamName = channell.getChannelInfo().get(0).getChannel().getStreamName();
                        configRedPro(streamName);
                        getLiveFeedsFromServer(matchContenstentId);
                    } else if (userDatum.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("team")) {
                        channell = (UserSearchSportsModel.UserDatum) getIntent().getSerializableExtra("mUserSearchDatum");
                        matchContenstentId = userDatum.getChannelInfo().get(0).getChannel().getMatchId();
                        txtMatchNameTop.setText(userDatum.getChannelInfo().get(0).getTeamInfo().getContestantName());
                        txtMatchTimeTop.setText("Rank:" + userDatum.getChannelInfo().get(0).getTeamInfo().getRank());
                        txtBroadcasterName.setText("BroadCasting this Game:" + userDatum.getChannelInfo().get(0).getChannel().getBroadcasterName());
                        tvTeam1Name.setText(userDatum.getChannelInfo().get(0).getTeamInfo().getContestantClubName());
                        tvTeam2Name.setText("Points: " + userDatum.getChannelInfo().get(0).getTeamInfo().getPoints());
                        broadcasterId = channell.getChannelInfo().get(0).getChannel().getBroadcasterId();
                        channelId = channell.getChannelInfo().get(0).getChannel().getId();
                        tvNoData.setVisibility(View.VISIBLE);
                        tvNoData.setText(R.string.team_talk);
                        streamName = channell.getChannelInfo().get(0).getChannel().getStreamName();
                        configRedPro(streamName);
                    }
                }

            }
        } else if (getIntent().getStringExtra("userComingFrom").equalsIgnoreCase("sprotsTeamSearch")) {
                if (getIntent().getSerializableExtra("mTeamSearchDatum") != null) {
                TeamSearchSportsModel.Datum teamDatum = (TeamSearchSportsModel.Datum) getIntent().getSerializableExtra("mTeamSearchDatum");
                if (teamDatum.getMarkImage() != null)
                    Picasso.with(mContext).load(ApiConstants.LEAGUE_IMAGE_BASE_URL + teamDatum.getMarkImage()).into(ivLeagueLogo);
                if (teamDatum.getChannelInfo() != null) {
                    {
                        channel = (LiveBroacastersListModel.Channel) getIntent().getSerializableExtra("mBrListDatum");
                        matchContenstentId = channel.getMatchId();
                         txtMatchNameTop.setText(channel.getBroadcasterName());
                          txtMatchTimeTop.setText("Rank:" + teamDatum.getRank());
                        txtBroadcasterName.setText("BroadCasting this Game:" + channel.getBroadcasterName());
                        tvTeam1Name.setText(teamDatum.getContestantClubName());
                          tvTeam2Name.setText("Points: " + teamDatum.getPoints());
                        broadcasterId = channel.getBroadcasterId();
                        channelId = channel.getId();
                        tvNoData.setVisibility(View.VISIBLE);
                        tvNoData.setText(R.string.team_talk);
                        streamName = channel.getStreamName();
                        configRedPro(streamName);
                    }
                }

            }

        }
        else if (getIntent().getStringExtra("userComingFrom").equalsIgnoreCase("leaguesUserSearch")) {
            if (getIntent().getSerializableExtra("mUserSearchDatum") != null) {
                UserSearchLeagueModel.Datum channell;
                UserSearchLeagueModel.Datum userDatum = ( UserSearchLeagueModel.Datum) getIntent().getSerializableExtra("mUserSearchDatum");
                if (userDatum.getChannelInfo().get(0).getChannel().getMarkImage() != null)
                    Picasso.with(mContext).load(ApiConstants.LEAGUE_IMAGE_BASE_URL + userDatum.getChannelInfo().get(0).getChannel().getMarkImage()).into(ivLeagueLogo);
                if (userDatum.getChannelInfo().get(0).getChannel() != null) {
                    if (userDatum.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("match")) {
                        channell = ( UserSearchLeagueModel.Datum) getIntent().getSerializableExtra("mUserSearchDatum");
                        matchContenstentId = userDatum.getChannelInfo().get(0).getChannel().getMatchId();
                        tvTeam1Name.setText(userDatum.getChannelInfo().get(0).getMatchInfo().getTeam1Name() + ":");
                        tvTeam2Name.setText(userDatum.getChannelInfo().get(0).getMatchInfo().getTeam2Name() + ":");
                        txtMatchNameTop.setText(userDatum.getChannelInfo().get(0).getMatchInfo().getTeam1Name() + " Vs " + userDatum.getChannelInfo().get(0).getMatchInfo().getTeam2Name());
                        txtBroadcasterName.setText("BroadCasting this Game:" + channell.getChannelInfo().get(0).getChannel().getBroadcasterName());
                        broadcasterId = channell.getChannelInfo().get(0).getChannel().getBroadcasterId();
                        channelId = channell.getChannelInfo().get(0).getChannel().getId();
                        streamName = channell.getChannelInfo().get(0).getChannel().getStreamName();
                        configRedPro(streamName);
                        getLiveFeedsFromServer(matchContenstentId);
                    } else if (userDatum.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("team")) {
                        channell = ( UserSearchLeagueModel.Datum) getIntent().getSerializableExtra("mUserSearchDatum");
                        matchContenstentId = userDatum.getChannelInfo().get(0).getChannel().getMatchId();
                        txtMatchNameTop.setText(userDatum.getChannelInfo().get(0).getTeamInfo().getContestantName());
                        txtMatchTimeTop.setText("Rank:" + userDatum.getChannelInfo().get(0).getTeamInfo().getRank());
                        txtBroadcasterName.setText("BroadCasting this Game:" + userDatum.getChannelInfo().get(0).getChannel().getBroadcasterName());
                        tvTeam1Name.setText(userDatum.getChannelInfo().get(0).getTeamInfo().getContestantClubName());
                        tvTeam2Name.setText("Points: " + userDatum.getChannelInfo().get(0).getTeamInfo().getPoints());
                        broadcasterId = channell.getChannelInfo().get(0).getChannel().getBroadcasterId();
                        channelId = channell.getChannelInfo().get(0).getChannel().getId();
                        tvNoData.setVisibility(View.VISIBLE);
                        tvNoData.setText(R.string.team_talk);
                        streamName = channell.getChannelInfo().get(0).getChannel().getStreamName();
                        configRedPro(streamName);
                    }
                }

            }
        }
        followUnfoloowPath = AppPreferences.init(mContext).getString(AppConstant.USER_ID) + "/" + broadcasterId;
        getfollowunfollowpath = broadcasterId + "/" + AppPreferences.init(mContext).getString(AppConstant.USER_ID) + "/" + channelId;
        getUserFollowCount(getfollowunfollowpath);
        getBroadcastersProfile(broadcasterId);
        getBroadcastersDetails(matchContenstentId);

    }

    @OnClick(R.id.iv_switch_broacaster)
    public void switchBroacaster() {
        if (getIntent().getStringExtra("userComingFrom").equalsIgnoreCase("pundits")) {
            subscribe.stop();
            unmountListner(listenerId);
            Intent intent = new Intent(mContext, LiveBroadcastersListActivity.class);
            intent.putExtra("userComingFrom", "pundits");
            intent.putExtra("matchidcontestentid", matchContenstentId);
            intent.putExtra("mUserDatum", getIntent().getSerializableExtra("mUserDatum"));
            startActivity(intent);
            finish();
        } else if (getIntent().getStringExtra("userComingFrom").equalsIgnoreCase("sprotsUserSearch")) {
            subscribe.stop();
            unmountListner(listenerId);
            Intent intent = new Intent(mContext, LiveBroadcastersListActivity.class);
            intent.putExtra("userComingFrom", "sprotsUserSearch");
            intent.putExtra("matchidcontestentid", matchContenstentId);
            intent.putExtra("mUserSearchDatum", getIntent().getSerializableExtra("mUserSearchDatum"));
            startActivity(intent);
            finish();
        }
        else if (getIntent().getStringExtra("userComingFrom").equalsIgnoreCase("leaguesUserSearch")) {
            subscribe.stop();
            unmountListner(listenerId);
            Intent intent = new Intent(mContext, LiveBroadcastersListActivity.class);
            intent.putExtra("userComingFrom", "leaguesUserSearch");
            intent.putExtra("matchidcontestentid", matchContenstentId);
            intent.putExtra("mUserSearchDatum", getIntent().getSerializableExtra("mUserSearchDatum"));
            startActivity(intent);
            finish();
        }else {
            subscribe.stop();
            unmountListner(listenerId);
            finish();
        }

    }

    @OnClick(R.id.iv_play_pause)
    public void plyPause() {
        if (playing == 0) {
            enableDisableUserIntracton();
            playing = 1;
            ivPlayPause.setImageResource(R.drawable.ic_listen_play);
            subscribe.stop();

        } else if (playing == 1) {
            enableDisableUserIntracton();
            playing = 0;
            ivPlayPause.setImageResource(R.drawable.pause);
            subscribe.play(streamName);
        }
    }

    @OnClick(R.id.btn_show_profile)
    public void openUserProfile() {
        rlBlurBackground.setVisibility(View.VISIBLE);
        rlBlurBackground.animate()
                .translationYBy(rlBlurBackground.getHeight())
                .translationY(0)
                .setDuration(10000)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        rlBlurBackground.animate().setListener(null);
                    }
                });

        btnShowProfile.setVisibility(View.GONE);
        btnFollowUnfollowBg.setVisibility(View.GONE);
        btnRedirctBroadcastersProfileBg.setVisibility(View.GONE);
        btnHideProfile.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.btn_hide_profile)
    public void hideUserProfileLayout() {
        rlBlurBackground.setVisibility(View.GONE);
        rlBlurBackground.animate()
                .translationYBy(rlBlurBackground.getHeight())
                .translationY(0)
                .setDuration(10000)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        rlBlurBackground.animate().setListener(null);
                    }
                });

        btnShowProfile.setVisibility(View.VISIBLE);
        btnFollowUnfollowBg.setVisibility(View.VISIBLE);
        btnRedirctBroadcastersProfileBg.setVisibility(View.VISIBLE);
        btnHideProfile.setVisibility(View.GONE);
    }


    @OnClick(R.id.rl_live_listening_main)
    public void hideUserProfile() {
        rlBlurBackground.setVisibility(View.GONE);
        rlBlurBackground.animate()
                .translationYBy(rlBlurBackground.getHeight())
                .translationY(0)
                .setDuration(10000)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        rlBlurBackground.animate().setListener(null);
                    }
                });

        btnShowProfile.setVisibility(View.VISIBLE);
        btnFollowUnfollowBg.setVisibility(View.VISIBLE);
        btnRedirctBroadcastersProfileBg.setVisibility(View.VISIBLE);
        btnHideProfile.setVisibility(View.GONE);

    }

    @OnClick(R.id.iv_fb_share)
    public void shareOnFacebook() {
        shareUrl = ApiConstants.SHARE_BASE_URL + streamName;
        if (getIntent().getStringExtra("userComingFrom") != null) {
            if (getIntent().getStringExtra("userComingFrom").equalsIgnoreCase("matchList")) {
                if (getIntent().getSerializableExtra("mMatchDatum") != null) {
                    MatchListListnerModel.ListenMatchList matchDatum = (MatchListListnerModel.ListenMatchList) getIntent().getSerializableExtra("mMatchDatum");
                    status = "I'm live on Pundit now listening " + matchDatum.getTeam1Name() + " Vs " + matchDatum.getTeam2Name() + ", come join me.";
                    facebookShare(shareUrl, status);
                }
            } else if (getIntent().getStringExtra("userComingFrom").equalsIgnoreCase("matchStandingListenList")) {
                if (getIntent().getSerializableExtra("mMatchDatum") != null) {
                    StandingListeningModel.Datum matchDatum = (StandingListeningModel.Datum) getIntent().getSerializableExtra("mMatchDatum");
                    status = "I'm live on Pundit now listening " + matchDatum.getContestantClubName() + ", come join me.";
                    facebookShare(shareUrl, status);
                }
            } else if (getIntent().getStringExtra("userComingFrom").equalsIgnoreCase("pundits")) {
                if (getIntent().getSerializableExtra("mUserDatum") != null) {
                    UserDetailsAndMatchDetailsModel.UsersList userDatum = (UserDetailsAndMatchDetailsModel.UsersList) getIntent().getSerializableExtra("mUserDatum");
                    if (userDatum.getChannelInfo().get(0).getChannel() != null) {
                        if (userDatum.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("match")) {
                            status = "I'm live on Pundit now listening " + userDatum.getChannelInfo().get(0).getMatchInfo().getTeam1Name() + " Vs " + userDatum.getChannelInfo().get(0).getMatchInfo().getTeam2Name() + ", come join me.";
                            facebookShare(shareUrl, status);
                        } else if (userDatum.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("team")) {
                            status = "I'm live on Pundit now listening " + userDatum.getChannelInfo().get(0).getTeamInfo().getContestantClubName() + ", come join me.";
                            facebookShare(shareUrl, status);
                        }
                    }
                }
            } else if (getIntent().getStringExtra("userComingFrom").equalsIgnoreCase("sprotsUserSearch")) {
                if (getIntent().getSerializableExtra("mUserSearchDatum") != null) {
                    UserSearchSportsModel.UserDatum userDatum = (UserSearchSportsModel.UserDatum) getIntent().getSerializableExtra("mUserSearchDatum");
                    if (userDatum.getChannelInfo().get(0).getChannel() != null) {
                        if (userDatum.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("match")) {
                            status = "I'm live on Pundit now listening " + userDatum.getChannelInfo().get(0).getMatchInfo().getTeam1Name() + " Vs " + userDatum.getChannelInfo().get(0).getMatchInfo().getTeam2Name() + ", come join me.";
                            facebookShare(shareUrl, status);
                        } else if (userDatum.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("team")) {
                            status = "I'm live on Pundit now listening " + userDatum.getChannelInfo().get(0).getTeamInfo().getContestantClubName() + ", come join me.";
                            facebookShare(shareUrl, status);
                        }
                    }
                }
            }else if (getIntent().getStringExtra("userComingFrom").equalsIgnoreCase("sprotsUserSearch")) {
                if (getIntent().getSerializableExtra("mUserSearchDatum") != null) {
                    UserSearchSportsModel.UserDatum userDatum = (UserSearchSportsModel.UserDatum) getIntent().getSerializableExtra("mUserSearchDatum");
                    if (userDatum.getChannelInfo().get(0).getChannel() != null) {
                        if (userDatum.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("match")) {
                            status = "I'm live on Pundit now listening " + userDatum.getChannelInfo().get(0).getMatchInfo().getTeam1Name() + " Vs " + userDatum.getChannelInfo().get(0).getMatchInfo().getTeam2Name() + ", come join me.";
                            facebookShare(shareUrl, status);
                        } else if (userDatum.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("team")) {
                            status = "I'm live on Pundit now listening " + userDatum.getChannelInfo().get(0).getTeamInfo().getContestantClubName() + ", come join me.";
                            facebookShare(shareUrl, status);
                        }
                    }
                }
            }
            else if (getIntent().getStringExtra("userComingFrom").equalsIgnoreCase("leaguesUserSearch")) {
                if (getIntent().getSerializableExtra("mUserSearchDatum") != null) {
                    UserSearchLeagueModel.Datum userDatum = ( UserSearchLeagueModel.Datum) getIntent().getSerializableExtra("mUserSearchDatum");
                    if (userDatum.getChannelInfo().get(0).getChannel() != null) {
                        if (userDatum.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("match")) {
                            status = "I'm live on Pundit now listening " + userDatum.getChannelInfo().get(0).getMatchInfo().getTeam1Name() + " Vs " + userDatum.getChannelInfo().get(0).getMatchInfo().getTeam2Name() + ", come join me.";
                            facebookShare(shareUrl, status);
                        } else if (userDatum.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("team")) {
                            status = "I'm live on Pundit now listening " + userDatum.getChannelInfo().get(0).getTeamInfo().getContestantClubName() + ", come join me.";
                            facebookShare(shareUrl, status);
                        }
                    }
                }
            }
            else if (getIntent().getStringExtra("userComingFrom").equalsIgnoreCase("sprotsTeamSearch")) {
                if(getIntent().getSerializableExtra("mTeamSearchDatum")!=null){
                    TeamSearchSportsModel.Datum teamDatum=(  TeamSearchSportsModel.Datum) getIntent().getSerializableExtra("mTeamSearchDatum");
                    status = "I'm live on Pundit now listening " + teamDatum.getContestantClubName() + ", come join me.";
                    facebookShare(shareUrl, status);
                }
            }
        }
    }

    public void facebookShare(String shareUrl, String status) {
        callbackManager = CallbackManager.Factory.create();
        ShareDialog shareDialog;
        shareDialog = new ShareDialog(mContext);
        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {
                Toast.makeText(mContext, "Posted", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @OnClick(R.id.iv_twitter_share)
    public void shareOnTwitter() {
        shareUrl = ApiConstants.SHARE_BASE_URL + streamName;
        AppPreferences.init(mContext).putString(AppConstant.User_CURRENT_STATE, "3");
        if (getIntent().getStringExtra("userComingFrom") != null) {
            if ((getIntent().getStringExtra("userComingFrom")).equalsIgnoreCase("matchList")) {
                if (getIntent().getSerializableExtra("mMatchDatum") != null) {
                    MatchListListnerModel.ListenMatchList matchDatum = (MatchListListnerModel.ListenMatchList) getIntent().getSerializableExtra("mMatchDatum");
                    status = "I'm live on Pundit now listening the game between " + matchDatum.getTeam1Name() + " Vs " + matchDatum.getTeam2Name() + ", come join me.";
                    twitterShare(shareUrl, status);
                }
            } else if ((getIntent().getStringExtra("userComingFrom")).equalsIgnoreCase("matchStandingListenList")) {
                if (getIntent().getSerializableExtra("mMatchDatum") != null) {
                    StandingListeningModel.Datum matchDatum = (StandingListeningModel.Datum) getIntent().getSerializableExtra("mMatchDatum");
                    status = "I'm live on Pundit now listening the game between " + matchDatum.getContestantClubName() + ", come join me.";
                    twitterShare(shareUrl, status);
                }
            } else if (getIntent().getStringExtra("userComingFrom").equalsIgnoreCase("pundits")) {
                if (getIntent().getSerializableExtra("mUserDatum") != null) {
                    UserDetailsAndMatchDetailsModel.UsersList userDatum = (UserDetailsAndMatchDetailsModel.UsersList) getIntent().getSerializableExtra("mUserDatum");
                    if (userDatum.getChannelInfo().get(0).getChannel() != null) {
                        if (userDatum.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("match")) {
                            status = "I'm live on Pundit now listening " + userDatum.getChannelInfo().get(0).getMatchInfo().getTeam1Name() + " Vs " + userDatum.getChannelInfo().get(0).getMatchInfo().getTeam2Name() + ", come join me.";
                            twitterShare(shareUrl, status);
                        } else if (userDatum.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("team")) {
                            status = "I'm live on Pundit now listening " + userDatum.getChannelInfo().get(0).getTeamInfo().getContestantClubName() + ", come join me.";
                            twitterShare(shareUrl, status);
                        }
                    }
                }
            } else if (getIntent().getStringExtra("userComingFrom").equalsIgnoreCase("sprotsUserSearch")) {
                if (getIntent().getSerializableExtra("mUserSearchDatum") != null) {
                    UserSearchSportsModel.UserDatum userDatum = (UserSearchSportsModel.UserDatum) getIntent().getSerializableExtra("mUserSearchDatum");
                    if (userDatum.getChannelInfo().get(0).getChannel() != null) {
                        if (userDatum.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("match")) {
                            status = "I'm live on Pundit now listening " + userDatum.getChannelInfo().get(0).getMatchInfo().getTeam1Name() + " Vs " + userDatum.getChannelInfo().get(0).getMatchInfo().getTeam2Name() + ", come join me.";
                            twitterShare(shareUrl, status);
                        } else if (userDatum.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("team")) {
                            status = "I'm live on Pundit now listening " + userDatum.getChannelInfo().get(0).getTeamInfo().getContestantClubName() + ", come join me.";
                            twitterShare(shareUrl, status);
                        }
                    }
                }
            }
            else if (getIntent().getStringExtra("userComingFrom").equalsIgnoreCase("leaguesUserSearch")) {
                if (getIntent().getSerializableExtra("mUserSearchDatum") != null) {
                    UserSearchLeagueModel.Datum userDatum = ( UserSearchLeagueModel.Datum) getIntent().getSerializableExtra("mUserSearchDatum");
                    if (userDatum.getChannelInfo().get(0).getChannel() != null) {
                        if (userDatum.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("match")) {
                            status = "I'm live on Pundit now listening " + userDatum.getChannelInfo().get(0).getMatchInfo().getTeam1Name() + " Vs " + userDatum.getChannelInfo().get(0).getMatchInfo().getTeam2Name() + ", come join me.";
                            twitterShare(shareUrl, status);
                        } else if (userDatum.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("team")) {
                            status = "I'm live on Pundit now listening " + userDatum.getChannelInfo().get(0).getTeamInfo().getContestantClubName() + ", come join me.";
                            twitterShare(shareUrl, status);
                        }
                    }
                }
            }
            else if (getIntent().getStringExtra("userComingFrom").equalsIgnoreCase("sprotsTeamSearch")) {
                if(getIntent().getSerializableExtra("mTeamSearchDatum")!=null){
                    TeamSearchSportsModel.Datum teamDatum=(  TeamSearchSportsModel.Datum) getIntent().getSerializableExtra("mTeamSearchDatum");
                    status = "I'm live on Pundit now listening " + teamDatum.getContestantClubName() + ", come join me.";
                    facebookShare(shareUrl, status);
                }
            }
        }
    }

    public void twitterShare(String shareUrl, String status) {
        TweetComposer.Builder builder = null;
        try {
            builder = new TweetComposer.Builder(this)
                    .text(status).url(new URL(shareUrl));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        builder.show();
    }

    @OnClick(R.id.btn_redirct_broadcasters_profile)
    public void redirectToBroadcasterProfile() {
        AppPreferences.init(mContext).putString(AppConstant.User_CURRENT_STATE, "3");
        Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse("http://www.facebook.com/" + userEmail));
        startActivity(viewIntent);
    }

    @OnClick(R.id.btn_redirct_broadcasters_profile_bg)
    public void redirectToBroadcasterProfileBg() {
        AppPreferences.init(mContext).putString(AppConstant.User_CURRENT_STATE, "3");
        Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse("http://www.facebook.com/" + userEmail));
        startActivity(viewIntent);
    }

    @OnClick(R.id.btn_follow_unfollow)
    public void buttonFollowUnfollow() {
        if (followStatus == 0) {
            btnFollowUnfollow.setText(R.string.follow);
            btnFollowUnfollowBg.setText(R.string.follow);
            followUnfoloowPath = broadcasterId + "/" + AppPreferences.init(mContext).getString(AppConstant.USER_ID);
            followunfollowUser(followUnfoloowPath);
        } else if (followStatus == 1) {
            btnFollowUnfollow.setText(R.string.unfollow);
            btnFollowUnfollowBg.setText(R.string.unfollow);
            followUnfoloowPath = broadcasterId + "/" + AppPreferences.init(mContext).getString(AppConstant.USER_ID);
            followunfollowUser(followUnfoloowPath);
        }
    }

    @OnClick(R.id.btn_follow_unfollow_bg)
    public void buttonFollowUnfollowBg() {
        if (followStatus == 0) {
            btnFollowUnfollowBg.setText(R.string.follow);
            btnFollowUnfollow.setText(R.string.follow);
            followUnfoloowPath = broadcasterId + "/" + AppPreferences.init(mContext).getString(AppConstant.USER_ID);
            followunfollowUser(followUnfoloowPath);
        } else if (followStatus == 1) {
            btnFollowUnfollowBg.setText(R.string.unfollow);
            btnFollowUnfollow.setText(R.string.unfollow);
            followUnfoloowPath = broadcasterId + "/" + AppPreferences.init(mContext).getString(AppConstant.USER_ID);
            followunfollowUser(followUnfoloowPath);
        }

    }

    @Override
    public void onBackPressed() {
        openCancelDialog();
    }

    @Override
    public void onStop() {
        super.onStop();
        super.onStop();
        if (AppPreferences.init(mContext).getString(AppConstant.User_CURRENT_STATE).equalsIgnoreCase("3")) {
//            Do Nothing
        } else {
            subscribe.stop();
            unmountListner(listenerId);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        AppPreferences.init(mContext).putString(AppConstant.User_CURRENT_STATE, "2");
    }

    private void getLiveFeedsFromServer(String matchId) {
        progressBarLiveListen.setVisibility(View.VISIBLE);
        disableUserIntraction();
        if (ConnectivityReceivers.isConnected()) {
            App.getApiHelper().getLiveFeeds(matchId, new ApiCallBack<LiveFeedsModel>() {
                @Override
                public void onSuccess(LiveFeedsModel liveFeedsModel) {
                    progressBarLiveListen.setVisibility(View.GONE);
                    enableUserIntraction();
                    if (liveFeedsModel != null) {
                        List<LiveFeedsModel.Feed> liveFeedsList;
                        liveFeedsList = liveFeedsModel.getFeeds();
                        liveFeedsAdapter = new LiveFeedsAdapter(mContext, liveFeedsList);
                        GridLayoutManager glm = new GridLayoutManager(getApplicationContext(), 1);
                        rvLivefeedsContainer.setLayoutManager(glm);
                        rvLivefeedsContainer.setAdapter(liveFeedsAdapter);
                        liveFeedsAdapter.notifyDataSetChanged();
                        if (liveFeedsModel.getMatchinfo().getMatchStatus() != null) {
                            if (liveFeedsModel.getMatchinfo().getMatchStatus().equalsIgnoreCase("Fixture")) {
                                txtMatchTimeTop.setText(liveFeedsModel.getMatchinfo().getMatchStatus());
                            } else if (liveFeedsModel.getMatchinfo().getMatchStatus().equalsIgnoreCase("Played")) {
                                txtMatchTimeTop.setText(getString(R.string.ft_string));
                            } else if (liveFeedsModel.getMatchinfo().getMatchStatus().equalsIgnoreCase("Playing")) {
                                txtMatchTimeTop.setText(liveFeedsModel.getMatchinfo().getMatchLengthMin() + ":" + liveFeedsModel.getMatchinfo().getMatchLengthSec());
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
                            if (liveFeedsList.size() > 0) {
                                tvNoData.setVisibility(View.GONE);
                                liveFeedsAdapter.notifyDataSetChanged();
                            } else
                                tvNoData.setVisibility(View.VISIBLE);
                            tvNoData.setText(R.string.no_live_feeds);
                        }
                    }
                }

                @Override
                public void onFailure(String message) {
                    progressBarLiveListen.setVisibility(View.GONE);
                    enableUserIntraction();
//                    SnackbarUtil.showErrorLongSnackbar(mContext, message);
                }
            });
        } else {
            progressBarLiveListen.setVisibility(View.GONE);
            enableUserIntraction();
            SnackbarUtil.showWarningLongSnackbar(mContext, getResources().getString(R.string.internet_not_connected_text));

        }
    }

    private void configRedPro(String media) {
        configuration = new R5Configuration(R5StreamProtocol.RTSP, AppConstant.RED5PRO_SERVER_IP, AppConstant.RED5PRO_SERVER_PORT, AppConstant.RED5PRO_SERVER_APP_NAME, AppConstant.RED5PRO_SERVER_CASHE);
        configuration.setLicenseKey(AppConstant.RED5PRO_LICENSE_KEY);
        configuration.setBundleID(mContext.getPackageName());
        connection = new R5Connection(configuration);
        subscribe = new R5Stream(connection);
        subscribe.play(media);
        setkickofftime();
    }

    private void getBroadcastersProfile(String broadcastersId) {
        progressBarLiveListen.setVisibility(View.VISIBLE);
        disableUserIntraction();
        Map<String, String> profileMap = new HashMap<>();
        profileMap.put(ApiConstants.USER_ID, broadcastersId);
        App.getApiHelper().getUserProfile(profileMap, new ApiCallBack<UserProfileResponseModel>() {
            @Override
            public void onSuccess(final UserProfileResponseModel userProfileResponseModel) {
                progressBarLiveListen.setVisibility(View.GONE);
                enableUserIntraction();
                if (userProfileResponseModel != null) {
                    tvUserName.setText(userProfileResponseModel.getMessage().getFirstName());
                    tvUserBio.setText(userProfileResponseModel.getMessage().getUserBio());
                    userEmail = userProfileResponseModel.getMessage().getEmail();
                    Picasso.with(mContext).load(ApiConstants.PROFILE_IMAGE_BASE_URL + userProfileResponseModel.getMessage().getAvatar()).into(civUserProfilePicture);
                  /* if(userProfileResponseModel.getTags()!= null) {
                       if (userProfileResponseModel.getTags().getTags().length() > 0) {
                           String hashTags = userProfileResponseModel.getTags().getTags().substring(1, userProfileResponseModel.getTags().getTags().length() - 1);
                           tvHashTags.setText(hashTags.replaceAll(", ", "\n"));
                       }
                   }*/
                    new AsyncTask<Void, Void, Bitmap>() {
                        @Override
                        protected void onPostExecute(Bitmap image) {
                            super.onPostExecute(image);
                            if (image != null) {
                                enableUserIntraction();
                                Bitmap resultedBitmap = CustomBlurImage.blur(mContext, image);
                                rlBlurBackground.setBackground(new BitmapDrawable(mContext.getResources(), resultedBitmap));
                            }
                        }

                        @Override
                        protected Bitmap doInBackground(Void... voids) {
                            Bitmap bitmap = null;
                            try {
                                bitmap = Picasso.with(mContext).load(ApiConstants.PROFILE_IMAGE_BASE_URL + userProfileResponseModel.getMessage().getAvatar()).get();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            return bitmap;
                        }
                    }.execute();
                }
            }

            @Override
            public void onFailure(String message) {
                progressBarLiveListen.setVisibility(View.GONE);
                enableUserIntraction();
            }
        });
    }

    private void setkickofftime() {
        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        txtkickoffTime.setText(String.valueOf(strMin) + ":" + String.valueOf(strSec));
                        strSec += 1;
                        if (strSec == 60) {
                            txtkickoffTime.setText(String.valueOf(strMin) + ":" + String.valueOf(strSec));
                            strSec = 0;
                            strMin = strMin + 1;
                        }
                    }
                });
            }
        }, 0, 1000);
    }

    private void openCancelDialog() {
        if (!isFinishing()) {
            new AlertDialog.Builder(mContext)
                    .setTitle(getString(R.string.cancel_listen_title))
                    .setMessage(getString(R.string.cancel_listen_message))
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            subscribe.stop();
                            unmountListner(listenerId);
                            if (getIntent().getStringExtra("userComingFrom").equalsIgnoreCase("pundits")) {
                                Intent intent = new Intent(mContext, PunditsScreen.class);
                                startActivity(intent);

                            } else if (getIntent().getStringExtra("userComingFrom").equalsIgnoreCase("sprotsUserSearch")) {
                                AppPreferences.init(mContext).putString(AppConstant.USER_SELECTION, AppConstant.SELECTED_LISTNER);
                                Intent intent = new Intent(mContext, BroadcastListenerMainActivity.class);
                                startActivity(intent);
                            } else {
                                //do nothing
                            }

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

    }

    private void followunfollowUser(final String stringPath) {
        progressBarLiveListen.setVisibility(View.VISIBLE);
        disableUserIntraction();
        if (ConnectivityReceivers.isConnected()) {
            App.getApiHelper().followunfollow(stringPath, new ApiCallBack<FollowUnfollowModel>() {
                @Override
                public void onSuccess(FollowUnfollowModel followUnfollowModel) {
                    if (followUnfollowModel != null) {
                        progressBarLiveListen.setVisibility(View.GONE);
                        enableUserIntraction();
                        followStatus = followUnfollowModel.getData().getResult();
                        if (followStatus == 0) {
                            btnFollowUnfollow.setText(R.string.follow);
                            btnFollowUnfollowBg.setText(R.string.follow);
                            getUserFollowCount(getfollowunfollowpath);
                        } else if (followStatus == 1) {
                            btnFollowUnfollow.setText(R.string.unfollow);
                            btnFollowUnfollowBg.setText(R.string.unfollow);
                            getUserFollowCount(getfollowunfollowpath);
                        }
                    }
                }

                @Override
                public void onFailure(String message) {
                    progressBarLiveListen.setVisibility(View.GONE);
                    enableUserIntraction();
//                    SnackbarUtil.showErrorLongSnackbar(mContext, message);
                }
            });
        } else {
            progressBarLiveListen.setVisibility(View.GONE);
            enableUserIntraction();
            SnackbarUtil.showWarningLongSnackbar(mContext, getResources().getString(R.string.internet_not_connected_text));
        }
    }

    private void getUserFollowCount(String stringPath) {
        progressBarLiveListen.setVisibility(View.VISIBLE);
        disableUserIntraction();
        if (ConnectivityReceivers.isConnected()) {
            App.getApiHelper().getFollowCount(stringPath, new ApiCallBack<FollowCheckModel>() {
                @Override
                public void onSuccess(FollowCheckModel followCheckModel) {
                    progressBarLiveListen.setVisibility(View.GONE);
                    enableUserIntraction();
                    if (followCheckModel != null) {
                        followStatus = followCheckModel.getInfo().getFollow();
                        if (followStatus == 0) {
                            btnFollowUnfollow.setText(R.string.follow);
                            btnFollowUnfollowBg.setText(R.string.follow);
                        } else if (followStatus == 1) {
                            btnFollowUnfollow.setText(R.string.unfollow);
                            btnFollowUnfollowBg.setText(R.string.unfollow);
                        }
                        if (followCheckModel.getListenerId() != null) {
                            listenerId = followCheckModel.getListenerId().toString();
                            tvFollowerCount.setText(followCheckModel.getInfo().getFollowerCount().toString());
                        }
                    }
                }

                @Override
                public void onFailure(String message) {
                    progressBarLiveListen.setVisibility(View.GONE);
                    enableUserIntraction();
//                    SnackbarUtil.showErrorLongSnackbar(mContext, message);
                }
            });
        } else {
            progressBarLiveListen.setVisibility(View.GONE);
            enableUserIntraction();
            SnackbarUtil.showWarningLongSnackbar(mContext, getResources().getString(R.string.internet_not_connected_text));
        }
    }

    private void getBroadcastersDetails(final String matchcontestentId) {
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (ConnectivityReceivers.isConnected()) {
                            App.getApiHelper().getBroadcastersDetails(matchcontestentId, new ApiCallBack<BroadacstersDetailsModel>() {
                                @Override
                                public void onSuccess(BroadacstersDetailsModel broadacstersDetailsModel) {
                                    if (broadacstersDetailsModel.getChannel().size() == 0) {
                                        t.cancel();
                                        ivPlayPause.setImageResource(R.drawable.ic_listen_play);
                                        broadcasterLeaveDialog();
                                    } else {
                                        boolean streamlive = false;
                                        for (int i = 0; i < broadacstersDetailsModel.getChannel().size(); i++) {
                                            if ((broadacstersDetailsModel.getChannel().get(i).getStreamName()).equalsIgnoreCase(streamName)) {
                                                streamlive = true;
                                                return;
                                            }
                                        }
                                        if (!streamlive) {
                                            t.cancel();
                                            ivPlayPause.setImageResource(R.drawable.ic_listen_play);
                                            broadcasterLeaveDialog();
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(String message) {
//                                    SnackbarUtil.showErrorLongSnackbar(mContext, message);

                                }
                            });
                        }
                    }
                });
            }
        }, 0, 5000);
    }

    private void unmountListner(String listenerId) {
        if (ConnectivityReceivers.isConnected()) {
            App.getApiHelper().unmountListenerOnServer(listenerId, new ApiCallBack<Map>() {
                @Override
                public void onSuccess(Map map) {
                    if (map != null) {
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
    }

    private void enableDisableUserIntracton() {
        Thread pBarThread = new Thread() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBarLiveListen.setVisibility(View.VISIBLE);
                        disableUserIntraction();
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                progressBarLiveListen.setVisibility(View.GONE);
                                enableUserIntraction();
                            }
                        }, 3000);
                    }
                });
            }
        };
        pBarThread.start();
    }

    private void broadcasterLeaveDialog() {
        if (!isFinishing()) {
            final Dialog dialog = new Dialog(mContext);
            dialog.setContentView(R.layout.custom_alertdialog_for_listner);
            dialog.setCancelable(false);
            Button ok = (Button) dialog.findViewById(R.id.btn_ok_custom);
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    t.cancel();
                    finish();
                }
            });
            dialog.show();
        }
    }

    public static void stopListenAppBackground() {
        if (subscribe != null) {
            subscribe.stop();
            new LiveListeningActivity().unmountListner(listenerId);
            if (!mContext.isFinishing())
                mContext.finish();
        }
    }

    private void onIncommingCallListen() {
        PhoneStateListener phoneStateListener1 = new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                if (state == TelephonyManager.CALL_STATE_RINGING) {

                }
                if (state == TelephonyManager.CALL_STATE_IDLE) {
                }
                if (state == TelephonyManager.CALL_STATE_OFFHOOK) {

                }
                super.onCallStateChanged(state, incomingNumber);
            }
        };
        TelephonyManager mgr1 = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        if (mgr1 != null) {
            mgr1.listen(phoneStateListener1, PhoneStateListener.LISTEN_CALL_STATE);
        }
    }
}
