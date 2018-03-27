package com.softuvo.ipundit.activities;

/*
 * Created by Neha Kalia on 12/08/2017.
 */

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.app.Activity;;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import com.applozic.mobicomkit.uiwidgets.conversation.ConversationUIService;
import com.applozic.mobicomkit.uiwidgets.conversation.activity.ConversationActivity;
import com.red5pro.streaming.R5Connection;
import com.red5pro.streaming.R5Stream;
import com.red5pro.streaming.R5StreamProtocol;
import com.red5pro.streaming.config.R5Configuration;
import com.softuvo.ipundit.R;
import com.softuvo.ipundit.adapters.ViewPagerAdapter;
import com.softuvo.ipundit.api.ApiCallBack;
import com.softuvo.ipundit.config.ApiConstants;
import com.softuvo.ipundit.config.App;
import com.softuvo.ipundit.config.AppConstant;
import com.softuvo.ipundit.config.AppPreferences;
import com.softuvo.ipundit.fragments.LiveFeedsFragment;
import com.softuvo.ipundit.fragments.LiveFeedsPlayerFragment;
import com.softuvo.ipundit.models.AddsModel;
import com.softuvo.ipundit.models.BreakingNewsDatum;
import com.softuvo.ipundit.models.BreakingNewsParentModel;
import com.softuvo.ipundit.models.BroadacstersDetailsModel;
import com.softuvo.ipundit.models.FollowCheckModel;
import com.softuvo.ipundit.models.FollowUnfollowModel;
import com.softuvo.ipundit.models.FollowerListModel;
import com.softuvo.ipundit.models.FollowingListModel;
import com.softuvo.ipundit.models.ListnerCountModel;
import com.softuvo.ipundit.models.LiveBroacastersListModel;
import com.softuvo.ipundit.models.MatchListListnerModel;
import com.softuvo.ipundit.models.ReconnectModel;
import com.softuvo.ipundit.models.RedFiveProGroupIdModel;
import com.softuvo.ipundit.models.ServerListenerAddressModel;
import com.softuvo.ipundit.models.StandingListeningModel;
import com.softuvo.ipundit.models.TeamSearchSportsModel;
import com.softuvo.ipundit.models.UserDetailsAndMatchDetailsModel;
import com.softuvo.ipundit.models.UserProfileResponseModel;
import com.softuvo.ipundit.models.UserSearchLeagueModel;
import com.softuvo.ipundit.models.UserSearchSportsModel;
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

public class LiveListeningActivity extends BaseActivity {
    private Activity mContext;
    public static R5Stream subscribe;
    private Timer t;
    Timer timer, tm;
    MediaPlayer mp;
    int i = 0;
    int mediatime = 0;
    private boolean mPlayPauseCheckbool = false;
    private static String listenerId;
    List<AddsModel.AdsDetail.Playlist> playlists;
    private String matchContenstentId;
    private String status;
    private String broadcasterId;
    private String broadcasterName;
    private String channelId;
    private String streamName;
    private String serverAddress;
    private String groupID;
    private String twitterStatus;
    private String chatChannelId;
    private int followStatus,strHours, strMin = 0, strSec = 0, visible = 0;
    private String newtime;


    @BindView(R.id.rl_live_listening_main)
    CustomRelativeLayout rlLiveListeningMain;

    @BindView(R.id.tv_reconnecting)
    CustomTextView tvReconnecting;


    @BindView(R.id.txt_match_name_top)
    CustomTextView txtMatchNameTop;

    @BindView(R.id.txt_match_time_top)
    CustomTextView txtMatchTimeTop;

    @BindView(R.id.txt_broadcaster_name)
    CustomTextView txtBroadcasterName;

    @BindView(R.id.txt_kick_off_Time)
    CustomTextView txtkickoffTime;

    @BindView(R.id.tv_team1_score)
    CustomTextView tvTeam1Score;

    @BindView(R.id.tv_team1_name)
    CustomTextView tvTeam1Name;

    @BindView(R.id.tv_team2_name)
    CustomTextView tvTeam2Name;

    @BindView(R.id.tv_team2_score)
    CustomTextView tvTeam2Score;

    @BindView(R.id.iv_league_logo)
    ImageView ivLeagueLogo;

    @BindView(R.id.rl_switch_tile)
    RelativeLayout rlSwitchTile;

    @BindView(R.id.rl_share_tile)
    RelativeLayout rlShareTile;

    @BindView(R.id.rl_pundit_tile)
    RelativeLayout rlPunditTile;

    /* @BindView(R.id.rv_livefeeds_container1)
     RecyclerView rvLivefeedsContainer;
 */
    @BindView(R.id.iv_profile_img)
    ImageView ivProfileImg;

    @BindView(R.id.tv_pundits_follower_count)
    CustomTextView tvPunditsFollowerCount;

    @BindView(R.id.tv_pundits_following_count)
    CustomTextView tvPunditsFollowingCount;

    @BindView(R.id.tv_broadcasters_name)
    CustomTextView tvBroadcastersName;

    @BindView(R.id.tv_no_deta)
    CustomTextView tvNoData;

    @BindView(R.id.tv_listners_count)
    CustomTextView tvListnersCount;

    @BindView(R.id.tv_breaking_news)
    CustomTextView tvBreakingNews;

    @BindView(R.id.btn_follow_unfollow)
    Button btnFollowUnfollow;

    @BindView(R.id.ll_broadcasters_profile)
    LinearLayout llBroadcastersProfile;

    @BindView(R.id.progress_bar_live_listen)
    ProgressBar progressBarLiveListen;

    @BindView(R.id.tabs)
    TabLayout mTabLayout;

    @BindView(R.id.viewpager)
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_listening);
        mContext = LiveListeningActivity.this;
        ButterKnife.bind(mContext);
        Date date = new Date();  // to get the date
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd"); // getting date in this format
        String formattedDate = df.format(date.getTime());
        Log.e("cureent date:",formattedDate);
        getNewsFromServer(formattedDate);
        setData();
    }

    private void setData() {
        // getApplication();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        t = new Timer();
        rlLiveListeningMain.setBackground(getResources().getDrawable(R.drawable.screen_image));
//        Picasso.with(mContext).load(AppPreferences.init(mContext).getString(APP_BACKGROUND)).into(rlLiveListeningMain);
//        AppPreferences.init(mContext).putString(AppConstant.User_CURRENT_STATE, "2");
        chatChannelId = getIntent().getStringExtra("chatChannelKey");
//        streamingGif.setGifImageResource(R.drawable.listning_gif);
        LiveBroacastersListModel.Channel channel;
        if (ConnectivityReceivers.isConnected()) {
            if (getIntent().getStringExtra("userComingFrom").equalsIgnoreCase("matchList")) {
                if (getIntent().getSerializableExtra("mMatchDatum") != null) {
                    if (AppPreferences.init(mContext).getString(AppConstant.LEAGUE_IMAGE_URL) != null&& !AppPreferences.init(mContext).getString(AppConstant.LEAGUE_IMAGE_URL).equalsIgnoreCase(""))
                        Picasso.with(mContext).load(AppPreferences.init(mContext).getString(AppConstant.LEAGUE_IMAGE_URL)).into(ivLeagueLogo);
                    MatchListListnerModel.ListenMatchList matchDatum = (MatchListListnerModel.ListenMatchList) getIntent().getSerializableExtra("mMatchDatum");
                    channel = (LiveBroacastersListModel.Channel) getIntent().getSerializableExtra("mBrListDatum");
                    matchContenstentId = matchDatum.getMatchId();
                    tvTeam1Name.setText(matchDatum.getTeam1Name() + ":");
                    tvTeam2Name.setText(matchDatum.getTeam2Name() + ":");
                    txtMatchNameTop.setText(matchDatum.getTeam1Name() + " Vs " + matchDatum.getTeam2Name());
                    broadcasterName = channel.getBroadcasterName();
                    txtBroadcasterName.setText(broadcasterName);
                    broadcasterId = channel.getBroadcasterId();
                    channelId = channel.getId();
                    streamName = channel.getStreamName();
                    getRed5ProGroupId();
//                    configRedPro(streamName);
//                    getLiveFeedsFromServer(matchContenstentId);
                    setupViewPager(mViewPager, matchContenstentId);
                    mViewPager.setOffscreenPageLimit(0);
                    mTabLayout.setVisibility(View.VISIBLE);
                    mTabLayout.setupWithViewPager(mViewPager);
                }
            } else if (getIntent().getStringExtra("userComingFrom").equalsIgnoreCase("matchStandingListenList")) {
                if (getIntent().getSerializableExtra("mMatchDatum") != null) {
                    if (AppPreferences.init(mContext).getString(AppConstant.LEAGUE_IMAGE_URL) != null&& !AppPreferences.init(mContext).getString(AppConstant.LEAGUE_IMAGE_URL).equalsIgnoreCase(""))
                        Picasso.with(mContext).load(AppPreferences.init(mContext).getString(AppConstant.LEAGUE_IMAGE_URL)).into(ivLeagueLogo);
                    StandingListeningModel.Datum matchDatum = (StandingListeningModel.Datum) getIntent().getSerializableExtra("mMatchDatum");
                    matchContenstentId = matchDatum.getContestantId();
                    channel = (LiveBroacastersListModel.Channel) getIntent().getSerializableExtra("mBrListDatum");
                    txtMatchNameTop.setText(matchDatum.getContestantName());
                    txtMatchTimeTop.setText("Rank:" + matchDatum.getRank());
                    broadcasterName = channel.getBroadcasterName();
                    txtBroadcasterName.setText(broadcasterName);
                    tvTeam1Name.setText(matchDatum.getContestantClubName());
                    tvTeam2Name.setText("Points: " + matchDatum.getPoints());
                    tvNoData.setVisibility(View.VISIBLE);
                    tvNoData.setText(R.string.team_talk);
                    broadcasterId = channel.getBroadcasterId();
                    channelId = channel.getId();
                    streamName = channel.getStreamName();
                    mTabLayout.setVisibility(View.GONE);
                    getRed5ProGroupId();
//                    configRedPro(streamName);
                }
            } else if (getIntent().getStringExtra("userComingFrom").equalsIgnoreCase("pundits")) {
                if (getIntent().getSerializableExtra("mUserDatum") != null) {
                    UserDetailsAndMatchDetailsModel.UsersList channell;
                    UserDetailsAndMatchDetailsModel.UsersList userDatum = (UserDetailsAndMatchDetailsModel.UsersList) getIntent().getSerializableExtra("mUserDatum");
                    if (userDatum.getChannelInfo().get(0).getChannel().getMarkImage() != null && !userDatum.getChannelInfo().get(0).getChannel().getMarkImage().equalsIgnoreCase(""))
                        Picasso.with(mContext).load(userDatum.getChannelInfo().get(0).getChannel().getMarkImage()).into(ivLeagueLogo);
                    if (userDatum.getChannelInfo().get(0).getChannel() != null) {
                        if (userDatum.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("match")) {
                            channell = (UserDetailsAndMatchDetailsModel.UsersList) getIntent().getSerializableExtra("mUserDatum");
                            matchContenstentId = userDatum.getChannelInfo().get(0).getChannel().getMatchId();
                            tvTeam1Name.setText(userDatum.getChannelInfo().get(0).getMatchInfo().getTeam1Name() + ":");
                            tvTeam2Name.setText(userDatum.getChannelInfo().get(0).getMatchInfo().getTeam2Name() + ":");
                            txtMatchNameTop.setText(userDatum.getChannelInfo().get(0).getMatchInfo().getTeam1Name() + " Vs " + userDatum.getChannelInfo().get(0).getMatchInfo().getTeam2Name());
                            broadcasterName = channell.getChannelInfo().get(0).getChannel().getBroadcasterName();
                            txtBroadcasterName.setText(broadcasterName);
                            broadcasterId = channell.getChannelInfo().get(0).getChannel().getBroadcasterId();
                            channelId = channell.getChannelInfo().get(0).getChannel().getId();
                            streamName = channell.getChannelInfo().get(0).getChannel().getStreamName();
                            getRed5ProGroupId();
//                            configRedPro(streamName);
//                            getLiveFeedsFromServer(matchContenstentId);
                            setupViewPager(mViewPager, matchContenstentId);
                            mViewPager.setOffscreenPageLimit(0);
                            mTabLayout.setVisibility(View.VISIBLE);
                            mTabLayout.setupWithViewPager(mViewPager);

                        } else if (userDatum.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("team")) {
                            channell = (UserDetailsAndMatchDetailsModel.UsersList) getIntent().getSerializableExtra("mUserDatum");
                            matchContenstentId = userDatum.getChannelInfo().get(0).getChannel().getMatchId();
                            txtMatchNameTop.setText(userDatum.getChannelInfo().get(0).getTeamInfo().getContestantName());
                            txtMatchTimeTop.setText("Rank:" + userDatum.getChannelInfo().get(0).getTeamInfo().getRank());
                            broadcasterName = userDatum.getChannelInfo().get(0).getChannel().getBroadcasterName();
                            txtBroadcasterName.setText(broadcasterName);
                            tvTeam1Name.setText(userDatum.getChannelInfo().get(0).getTeamInfo().getContestantClubName());
                            tvTeam2Name.setText("Points: " + userDatum.getChannelInfo().get(0).getTeamInfo().getPoints());
                            broadcasterId = channell.getChannelInfo().get(0).getChannel().getBroadcasterId();
                            channelId = channell.getChannelInfo().get(0).getChannel().getId();
                            tvNoData.setVisibility(View.VISIBLE);
                            tvNoData.setText(R.string.team_talk);
                            streamName = channell.getChannelInfo().get(0).getChannel().getStreamName();
                            mTabLayout.setVisibility(View.GONE);
                            getRed5ProGroupId();
//                            configRedPro(streamName);
                        }
                    }

                }
            } else if (getIntent().getStringExtra("userComingFrom").equalsIgnoreCase("punditsSwitch")) {
                if (getIntent().getSerializableExtra("mUserDatum") != null) {
                    UserDetailsAndMatchDetailsModel.UsersList userDatum = (UserDetailsAndMatchDetailsModel.UsersList) getIntent().getSerializableExtra("mUserDatum");
                    if (userDatum.getChannelInfo().get(0).getChannel().getMarkImage() != null && !userDatum.getChannelInfo().get(0).getChannel().getMarkImage().equalsIgnoreCase(""))
                        Picasso.with(mContext).load(userDatum.getChannelInfo().get(0).getChannel().getMarkImage()).into(ivLeagueLogo);
                    if (userDatum.getChannelInfo().get(0).getChannel() != null) {
                        if (userDatum.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("match")) {
                            channel = (LiveBroacastersListModel.Channel) getIntent().getSerializableExtra("mBrListDatum");
                            matchContenstentId = userDatum.getChannelInfo().get(0).getChannel().getMatchId();
                            tvTeam1Name.setText(userDatum.getChannelInfo().get(0).getMatchInfo().getTeam1Name() + ":");
                            tvTeam2Name.setText(userDatum.getChannelInfo().get(0).getMatchInfo().getTeam2Name() + ":");
                            txtMatchNameTop.setText(userDatum.getChannelInfo().get(0).getMatchInfo().getTeam1Name() + " Vs " + userDatum.getChannelInfo().get(0).getMatchInfo().getTeam2Name());
                            broadcasterName = channel.getBroadcasterName();
                            txtBroadcasterName.setText(broadcasterName);
                            broadcasterId = channel.getBroadcasterId();
                            channelId = channel.getId();
                            streamName = channel.getStreamName();
                            getRed5ProGroupId();
//                            configRedPro(streamName);
//                            getLiveFeedsFromServer(matchContenstentId);
                            setupViewPager(mViewPager, matchContenstentId);
                            mViewPager.setOffscreenPageLimit(0);
                            mTabLayout.setVisibility(View.VISIBLE);
                            mTabLayout.setupWithViewPager(mViewPager);
                        } else if (userDatum.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("team")) {
                            channel = (LiveBroacastersListModel.Channel) getIntent().getSerializableExtra("mBrListDatum");
                            matchContenstentId = userDatum.getChannelInfo().get(0).getChannel().getMatchId();
                            txtMatchNameTop.setText(userDatum.getChannelInfo().get(0).getTeamInfo().getContestantName());
                            txtMatchTimeTop.setText("Rank:" + userDatum.getChannelInfo().get(0).getTeamInfo().getRank());
                            broadcasterName = channel.getBroadcasterName();
                            txtBroadcasterName.setText(broadcasterName);
                            tvTeam1Name.setText(userDatum.getChannelInfo().get(0).getTeamInfo().getContestantClubName());
                            tvTeam2Name.setText("Points: " + userDatum.getChannelInfo().get(0).getTeamInfo().getPoints());
                            broadcasterId = channel.getBroadcasterId();
                            channelId = channel.getId();
                            tvNoData.setVisibility(View.VISIBLE);
                            tvNoData.setText(R.string.team_talk);
                            streamName = channel.getStreamName();
                            mTabLayout.setVisibility(View.GONE);
                            getRed5ProGroupId();
//                            configRedPro(streamName);
                        }
                    }
                }
            } else if (getIntent().getStringExtra("userComingFrom").equalsIgnoreCase("sprotsUserSearch")) {
                if (getIntent().getSerializableExtra("mUserDatum") != null) {
                    UserSearchSportsModel.UserDatum channell;
                    UserSearchSportsModel.UserDatum userDatum = (UserSearchSportsModel.UserDatum) getIntent().getSerializableExtra("mUserDatum");
                    if (userDatum.getChannelInfo().get(0).getChannel().getMarkImage() != null && !userDatum.getChannelInfo().get(0).getChannel().getMarkImage().equalsIgnoreCase(""))
                        Picasso.with(mContext).load(userDatum.getChannelInfo().get(0).getChannel().getMarkImage()).into(ivLeagueLogo);
                    if (userDatum.getChannelInfo().get(0).getChannel() != null) {
                        if (userDatum.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("match")) {
                            channell = (UserSearchSportsModel.UserDatum) getIntent().getSerializableExtra("mUserDatum");
                            matchContenstentId = userDatum.getChannelInfo().get(0).getChannel().getMatchId();
                            tvTeam1Name.setText(userDatum.getChannelInfo().get(0).getMatchInfo().getTeam1Name() + ":");
                            tvTeam2Name.setText(userDatum.getChannelInfo().get(0).getMatchInfo().getTeam2Name() + ":");
                            txtMatchNameTop.setText(userDatum.getChannelInfo().get(0).getMatchInfo().getTeam1Name() + " Vs " + userDatum.getChannelInfo().get(0).getMatchInfo().getTeam2Name());
                            broadcasterName = channell.getChannelInfo().get(0).getChannel().getBroadcasterName();
                            txtBroadcasterName.setText(broadcasterName);
                            broadcasterId = channell.getChannelInfo().get(0).getChannel().getBroadcasterId();
                            channelId = channell.getChannelInfo().get(0).getChannel().getId();
                            streamName = channell.getChannelInfo().get(0).getChannel().getStreamName();
                            getRed5ProGroupId();
//                            configRedPro(streamName);
//                            getLiveFeedsFromServer(matchContenstentId);
                            setupViewPager(mViewPager, matchContenstentId);
                            mViewPager.setOffscreenPageLimit(0);
                            mTabLayout.setVisibility(View.VISIBLE);
                            mTabLayout.setupWithViewPager(mViewPager);
                        } else if (userDatum.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("team")) {
                            channell = (UserSearchSportsModel.UserDatum) getIntent().getSerializableExtra("mUserDatum");
                            matchContenstentId = userDatum.getChannelInfo().get(0).getChannel().getMatchId();
                            txtMatchNameTop.setText(userDatum.getChannelInfo().get(0).getTeamInfo().getContestantName());
                            txtMatchTimeTop.setText("Rank:" + userDatum.getChannelInfo().get(0).getTeamInfo().getRank());
                            broadcasterName = userDatum.getChannelInfo().get(0).getChannel().getBroadcasterName();
                            txtBroadcasterName.setText(broadcasterName);
                            tvTeam1Name.setText(userDatum.getChannelInfo().get(0).getTeamInfo().getContestantClubName());
                            tvTeam2Name.setText("Points: " + userDatum.getChannelInfo().get(0).getTeamInfo().getPoints());
                            broadcasterId = channell.getChannelInfo().get(0).getChannel().getBroadcasterId();
                            channelId = channell.getChannelInfo().get(0).getChannel().getId();
                            tvNoData.setVisibility(View.VISIBLE);
                            tvNoData.setText(R.string.team_talk);
                            streamName = channell.getChannelInfo().get(0).getChannel().getStreamName();
                            mTabLayout.setVisibility(View.GONE);
                            getRed5ProGroupId();

//                            configRedPro(streamName);
                        }
                    }
                }
            } else if (getIntent().getStringExtra("userComingFrom").equalsIgnoreCase("sprotsUserSearchSwitch")) {
                if (getIntent().getSerializableExtra("mUserDatum") != null) {
                    UserSearchSportsModel.UserDatum userDatum = (UserSearchSportsModel.UserDatum) getIntent().getSerializableExtra("mUserDatum");
                    if (userDatum.getChannelInfo().get(0).getChannel().getMarkImage() != null && !userDatum.getChannelInfo().get(0).getChannel().getMarkImage().equalsIgnoreCase(""))
                        Picasso.with(mContext).load(userDatum.getChannelInfo().get(0).getChannel().getMarkImage()).into(ivLeagueLogo);
                    if (userDatum.getChannelInfo().get(0).getChannel() != null) {
                        if (userDatum.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("match")) {
                            channel = (LiveBroacastersListModel.Channel) getIntent().getSerializableExtra("mBrListDatum");
                            matchContenstentId = userDatum.getChannelInfo().get(0).getChannel().getMatchId();
                            tvTeam1Name.setText(userDatum.getChannelInfo().get(0).getMatchInfo().getTeam1Name() + ":");
                            tvTeam2Name.setText(userDatum.getChannelInfo().get(0).getMatchInfo().getTeam2Name() + ":");
                            txtMatchNameTop.setText(userDatum.getChannelInfo().get(0).getMatchInfo().getTeam1Name() + " Vs " + userDatum.getChannelInfo().get(0).getMatchInfo().getTeam2Name());
                            broadcasterName = channel.getBroadcasterName();
                            txtBroadcasterName.setText(broadcasterName);
                            broadcasterId = channel.getBroadcasterId();
                            channelId = channel.getId();
                            streamName = channel.getStreamName();
                            getRed5ProGroupId();
//                            configRedPro(streamName);
//                            getLiveFeedsFromServer(matchContenstentId);
                            setupViewPager(mViewPager, matchContenstentId);
                            mViewPager.setOffscreenPageLimit(0);
                            mTabLayout.setVisibility(View.VISIBLE);
                            mTabLayout.setupWithViewPager(mViewPager);
                        } else if (userDatum.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("team")) {
                            channel = (LiveBroacastersListModel.Channel) getIntent().getSerializableExtra("mBrListDatum");
                            matchContenstentId = userDatum.getChannelInfo().get(0).getChannel().getMatchId();
                            txtMatchNameTop.setText(userDatum.getChannelInfo().get(0).getTeamInfo().getContestantName());
                            txtMatchTimeTop.setText("Rank:" + userDatum.getChannelInfo().get(0).getTeamInfo().getRank());
                            broadcasterName = channel.getBroadcasterName();
                            txtBroadcasterName.setText(broadcasterName);
                            tvTeam1Name.setText(userDatum.getChannelInfo().get(0).getTeamInfo().getContestantClubName());
                            tvTeam2Name.setText("Points: " + userDatum.getChannelInfo().get(0).getTeamInfo().getPoints());
                            broadcasterId = channel.getBroadcasterId();
                            channelId = channel.getId();
                            tvNoData.setVisibility(View.VISIBLE);
                            tvNoData.setText(R.string.team_talk);
                            streamName = channel.getStreamName();
                            getRed5ProGroupId();
                            mTabLayout.setVisibility(View.GONE);
//                            configRedPro(streamName);
                        }
                    }
                }
            } else if (getIntent().getStringExtra("userComingFrom").equalsIgnoreCase("sprotsTeamSearch")) {
                if (getIntent().getSerializableExtra("mTeamSearchDatum") != null) {
                    TeamSearchSportsModel.Datum teamDatum = (TeamSearchSportsModel.Datum) getIntent().getSerializableExtra("mTeamSearchDatum");
                    if (teamDatum.getMarkImage() != null && !teamDatum.getMarkImage().equalsIgnoreCase(""))
                        Picasso.with(mContext).load(teamDatum.getMarkImage()).into(ivLeagueLogo);
                    if (teamDatum.getChannelInfo() != null) {
                        {
                            channel = (LiveBroacastersListModel.Channel) getIntent().getSerializableExtra("mBrListDatum");
                            matchContenstentId = channel.getMatchId();
                            txtMatchNameTop.setText(channel.getBroadcasterName());
                            txtMatchTimeTop.setText("Rank:" + teamDatum.getRank());
                            broadcasterName = channel.getBroadcasterName();
                            txtBroadcasterName.setText(broadcasterName);
                            tvTeam1Name.setText(teamDatum.getContestantClubName());
                            tvTeam2Name.setText("Points: " + teamDatum.getPoints());
                            broadcasterId = channel.getBroadcasterId();
                            channelId = channel.getId();
                            tvNoData.setVisibility(View.VISIBLE);
                            tvNoData.setText(R.string.team_talk);
                            streamName = channel.getStreamName();
                            getRed5ProGroupId();
                            mTabLayout.setVisibility(View.GONE);
//                            configRedPro(streamName);
                        }
                    }
                }
            } else if (getIntent().getStringExtra("userComingFrom").equalsIgnoreCase("leaguesUserSearch")) {
                if (getIntent().getSerializableExtra("mUserDatum") != null) {
                    UserSearchLeagueModel.Datum channell;
                    UserSearchLeagueModel.Datum userDatum = (UserSearchLeagueModel.Datum) getIntent().getSerializableExtra("mUserDatum");
                    if (userDatum.getChannelInfo().get(0).getChannel().getMarkImage() != null && !userDatum.getChannelInfo().get(0).getChannel().getMarkImage().equalsIgnoreCase(""))
                        Picasso.with(mContext).load(userDatum.getChannelInfo().get(0).getChannel().getMarkImage()).into(ivLeagueLogo);
                    if (userDatum.getChannelInfo().get(0).getChannel() != null) {
                        if (userDatum.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("match")) {
                            channell = (UserSearchLeagueModel.Datum) getIntent().getSerializableExtra("mUserDatum");
                            matchContenstentId = userDatum.getChannelInfo().get(0).getChannel().getMatchId();
                            tvTeam1Name.setText(userDatum.getChannelInfo().get(0).getMatchInfo().getTeam1Name() + ":");
                            tvTeam2Name.setText(userDatum.getChannelInfo().get(0).getMatchInfo().getTeam2Name() + ":");
                            txtMatchNameTop.setText(userDatum.getChannelInfo().get(0).getMatchInfo().getTeam1Name() + " Vs " + userDatum.getChannelInfo().get(0).getMatchInfo().getTeam2Name());
                            broadcasterName = channell.getChannelInfo().get(0).getChannel().getBroadcasterName();
                            txtBroadcasterName.setText(broadcasterName);
                            broadcasterId = channell.getChannelInfo().get(0).getChannel().getBroadcasterId();
                            channelId = channell.getChannelInfo().get(0).getChannel().getId();
                            streamName = channell.getChannelInfo().get(0).getChannel().getStreamName();
                            getRed5ProGroupId();
//                            configRedPro(streamName);
//                            getLiveFeedsFromServer(matchContenstentId);
                            setupViewPager(mViewPager, matchContenstentId);
                            mViewPager.setOffscreenPageLimit(0);
                            mTabLayout.setVisibility(View.VISIBLE);
                            mTabLayout.setupWithViewPager(mViewPager);
                        } else if (userDatum.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("team")) {
                            channell = (UserSearchLeagueModel.Datum) getIntent().getSerializableExtra("mUserDatum");
                            matchContenstentId = userDatum.getChannelInfo().get(0).getChannel().getMatchId();
                            txtMatchNameTop.setText(userDatum.getChannelInfo().get(0).getTeamInfo().getContestantName());
                            txtMatchTimeTop.setText("Rank:" + userDatum.getChannelInfo().get(0).getTeamInfo().getRank());
                            broadcasterName = userDatum.getChannelInfo().get(0).getChannel().getBroadcasterName();
                            txtBroadcasterName.setText(broadcasterName);
                            tvTeam1Name.setText(userDatum.getChannelInfo().get(0).getTeamInfo().getContestantClubName());
                            tvTeam2Name.setText("Points: " + userDatum.getChannelInfo().get(0).getTeamInfo().getPoints());
                            broadcasterId = channell.getChannelInfo().get(0).getChannel().getBroadcasterId();
                            channelId = channell.getChannelInfo().get(0).getChannel().getId();
                            tvNoData.setVisibility(View.VISIBLE);
                            tvNoData.setText(R.string.team_talk);
                            streamName = channell.getChannelInfo().get(0).getChannel().getStreamName();
                            getRed5ProGroupId();
                            mTabLayout.setVisibility(View.GONE);
//                            configRedPro(streamName);
                        }
                    }
                }
            } else if (getIntent().getStringExtra("userComingFrom").equalsIgnoreCase("followerList")) {
                if (getIntent().getSerializableExtra("mUserDatum") != null) {
                    FollowerListModel.Follwer channell;
                    FollowerListModel.Follwer userDatum = (FollowerListModel.Follwer) getIntent().getSerializableExtra("mUserDatum");
                    if (userDatum.getChannelInfo().get(0).getChannel().getMarkImage() != null && !userDatum.getChannelInfo().get(0).getChannel().getMarkImage().equalsIgnoreCase(""))
                        Picasso.with(mContext).load(userDatum.getChannelInfo().get(0).getChannel().getMarkImage()).into(ivLeagueLogo);
                    if (userDatum.getChannelInfo().get(0).getChannel() != null) {
                        if (userDatum.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("match")) {
                            channell = (FollowerListModel.Follwer) getIntent().getSerializableExtra("mUserDatum");
                            matchContenstentId = userDatum.getChannelInfo().get(0).getChannel().getMatchId();
                            tvTeam1Name.setText(userDatum.getChannelInfo().get(0).getMatchInfo().getTeam1Name() + ":");
                            tvTeam2Name.setText(userDatum.getChannelInfo().get(0).getMatchInfo().getTeam2Name() + ":");
                            txtMatchNameTop.setText(userDatum.getChannelInfo().get(0).getMatchInfo().getTeam1Name() + " Vs " + userDatum.getChannelInfo().get(0).getMatchInfo().getTeam2Name());
                            broadcasterName = channell.getChannelInfo().get(0).getChannel().getBroadcasterName();
                            txtBroadcasterName.setText(broadcasterName);
                            broadcasterId = channell.getChannelInfo().get(0).getChannel().getBroadcasterId();
                            channelId = channell.getChannelInfo().get(0).getChannel().getId();
                            streamName = channell.getChannelInfo().get(0).getChannel().getStreamName();
                            getRed5ProGroupId();
//                            configRedPro(streamName);
//                            getLiveFeedsFromServer(matchContenstentId);
                            setupViewPager(mViewPager, matchContenstentId);
                            mViewPager.setOffscreenPageLimit(0);
                            mTabLayout.setVisibility(View.VISIBLE);
                            mTabLayout.setupWithViewPager(mViewPager);
                        } else if (userDatum.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("team")) {
                            channell = (FollowerListModel.Follwer) getIntent().getSerializableExtra("mUserDatum");
                            matchContenstentId = userDatum.getChannelInfo().get(0).getChannel().getMatchId();
                            txtMatchNameTop.setText(userDatum.getChannelInfo().get(0).getTeamInfo().getContestantName());
                            txtMatchTimeTop.setText("Rank:" + userDatum.getChannelInfo().get(0).getTeamInfo().getRank());
                            broadcasterName = userDatum.getChannelInfo().get(0).getChannel().getBroadcasterName();
                            txtBroadcasterName.setText(broadcasterName);
                            tvTeam1Name.setText(userDatum.getChannelInfo().get(0).getTeamInfo().getContestantClubName());
                            tvTeam2Name.setText("Points: " + userDatum.getChannelInfo().get(0).getTeamInfo().getPoints());
                            broadcasterId = channell.getChannelInfo().get(0).getChannel().getBroadcasterId();
                            channelId = channell.getChannelInfo().get(0).getChannel().getId();
                            tvNoData.setVisibility(View.VISIBLE);
                            tvNoData.setText(R.string.team_talk);
                            streamName = channell.getChannelInfo().get(0).getChannel().getStreamName();
                            getRed5ProGroupId();
                            mTabLayout.setVisibility(View.GONE);
//                            configRedPro(streamName);
                        }
                    }
                }
            } else if (getIntent().getStringExtra("userComingFrom").equalsIgnoreCase("followerListSwitch")) {
                if (getIntent().getSerializableExtra("mUserDatum") != null) {
                    FollowerListModel.Follwer channell;
                    FollowerListModel.Follwer userDatum = (FollowerListModel.Follwer) getIntent().getSerializableExtra("mUserDatum");
                    if (userDatum.getChannelInfo().get(0).getChannel().getMarkImage() != null && !userDatum.getChannelInfo().get(0).getChannel().getMarkImage().equalsIgnoreCase(""))
                        Picasso.with(mContext).load(userDatum.getChannelInfo().get(0).getChannel().getMarkImage()).into(ivLeagueLogo);
                    if (userDatum.getChannelInfo().get(0).getChannel() != null) {
                        if (userDatum.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("match")) {
                            channell = (FollowerListModel.Follwer) getIntent().getSerializableExtra("mUserDatum");
                            matchContenstentId = userDatum.getChannelInfo().get(0).getChannel().getMatchId();
                            tvTeam1Name.setText(userDatum.getChannelInfo().get(0).getMatchInfo().getTeam1Name() + ":");
                            tvTeam2Name.setText(userDatum.getChannelInfo().get(0).getMatchInfo().getTeam2Name() + ":");
                            txtMatchNameTop.setText(userDatum.getChannelInfo().get(0).getMatchInfo().getTeam1Name() + " Vs " + userDatum.getChannelInfo().get(0).getMatchInfo().getTeam2Name());
                            broadcasterName = channell.getChannelInfo().get(0).getChannel().getBroadcasterName();
                            txtBroadcasterName.setText(broadcasterName);
                            broadcasterId = channell.getChannelInfo().get(0).getChannel().getBroadcasterId();
                            channelId = channell.getChannelInfo().get(0).getChannel().getId();
                            streamName = channell.getChannelInfo().get(0).getChannel().getStreamName();
                            getRed5ProGroupId();
//                            configRedPro(streamName);
//                            getLiveFeedsFromServer(matchContenstentId);
                            setupViewPager(mViewPager, matchContenstentId);
                            mViewPager.setOffscreenPageLimit(0);
                            mTabLayout.setVisibility(View.VISIBLE);
                            mTabLayout.setupWithViewPager(mViewPager);
                        } else if (userDatum.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("team")) {
                            channell = (FollowerListModel.Follwer) getIntent().getSerializableExtra("mUserDatum");
                            matchContenstentId = userDatum.getChannelInfo().get(0).getChannel().getMatchId();
                            txtMatchNameTop.setText(userDatum.getChannelInfo().get(0).getTeamInfo().getContestantName());
                            txtMatchTimeTop.setText("Rank:" + userDatum.getChannelInfo().get(0).getTeamInfo().getRank());
                            broadcasterName = userDatum.getChannelInfo().get(0).getChannel().getBroadcasterName();
                            txtBroadcasterName.setText(broadcasterName);
                            tvTeam1Name.setText(userDatum.getChannelInfo().get(0).getTeamInfo().getContestantClubName());
                            tvTeam2Name.setText("Points: " + userDatum.getChannelInfo().get(0).getTeamInfo().getPoints());
                            broadcasterId = channell.getChannelInfo().get(0).getChannel().getBroadcasterId();
                            channelId = channell.getChannelInfo().get(0).getChannel().getId();
                            tvNoData.setVisibility(View.VISIBLE);
                            tvNoData.setText(R.string.team_talk);
                            streamName = channell.getChannelInfo().get(0).getChannel().getStreamName();
                            getRed5ProGroupId();
                            mTabLayout.setVisibility(View.GONE);
//                            configRedPro(streamName);
                        }
                    }
                }
            } else if (getIntent().getStringExtra("userComingFrom").equalsIgnoreCase("followingList")) {
                if (getIntent().getSerializableExtra("mUserDatum") != null) {
                    FollowingListModel.Following channell;
                    FollowingListModel.Following userDatum = (FollowingListModel.Following) getIntent().getSerializableExtra("mUserDatum");
                    if (userDatum.getChannelInfo().get(0).getChannel().getMarkImage() != null && !userDatum.getChannelInfo().get(0).getChannel().getMarkImage().equalsIgnoreCase(""))
                        Picasso.with(mContext).load(userDatum.getChannelInfo().get(0).getChannel().getMarkImage()).into(ivLeagueLogo);
                    if (userDatum.getChannelInfo().get(0).getChannel() != null) {
                        if (userDatum.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("match")) {
                            channell = (FollowingListModel.Following) getIntent().getSerializableExtra("mUserDatum");
                            matchContenstentId = userDatum.getChannelInfo().get(0).getChannel().getMatchId();
                            tvTeam1Name.setText(userDatum.getChannelInfo().get(0).getMatchInfo().getTeam1Name() + ":");
                            tvTeam2Name.setText(userDatum.getChannelInfo().get(0).getMatchInfo().getTeam2Name() + ":");
                            txtMatchNameTop.setText(userDatum.getChannelInfo().get(0).getMatchInfo().getTeam1Name() + " Vs " + userDatum.getChannelInfo().get(0).getMatchInfo().getTeam2Name());
                            broadcasterName = channell.getChannelInfo().get(0).getChannel().getBroadcasterName();
                            txtBroadcasterName.setText(broadcasterName);
                            broadcasterId = channell.getChannelInfo().get(0).getChannel().getBroadcasterId();
                            channelId = channell.getChannelInfo().get(0).getChannel().getId();
                            streamName = channell.getChannelInfo().get(0).getChannel().getStreamName();
                            getRed5ProGroupId();
//                            configRedPro(streamName);
//                            getLiveFeedsFromServer(matchContenstentId);
                            setupViewPager(mViewPager, matchContenstentId);
                            mViewPager.setOffscreenPageLimit(0);
                            mTabLayout.setVisibility(View.VISIBLE);
                            mTabLayout.setupWithViewPager(mViewPager);
                        } else if (userDatum.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("team")) {
                            channell = (FollowingListModel.Following) getIntent().getSerializableExtra("mUserDatum");
                            matchContenstentId = userDatum.getChannelInfo().get(0).getChannel().getMatchId();
                            txtMatchNameTop.setText(userDatum.getChannelInfo().get(0).getTeamInfo().getContestantName());
                            txtMatchTimeTop.setText("Rank:" + userDatum.getChannelInfo().get(0).getTeamInfo().getRank());
                            broadcasterName = userDatum.getChannelInfo().get(0).getChannel().getBroadcasterName();
                            txtBroadcasterName.setText(broadcasterName);
                            tvTeam1Name.setText(userDatum.getChannelInfo().get(0).getTeamInfo().getContestantClubName());
                            tvTeam2Name.setText("Points: " + userDatum.getChannelInfo().get(0).getTeamInfo().getPoints());
                            broadcasterId = channell.getChannelInfo().get(0).getChannel().getBroadcasterId();
                            channelId = channell.getChannelInfo().get(0).getChannel().getId();
                            tvNoData.setVisibility(View.VISIBLE);
                            tvNoData.setText(R.string.team_talk);
                            streamName = channell.getChannelInfo().get(0).getChannel().getStreamName();
                            getRed5ProGroupId();
                            mTabLayout.setVisibility(View.GONE);
//                            configRedPro(streamName);
                        }
                    }
                }
            } else if (getIntent().getStringExtra("userComingFrom").equalsIgnoreCase("followingListSwitch")) {
                if (getIntent().getSerializableExtra("mUserDatum") != null) {
                    FollowingListModel.Following channell;
                    FollowingListModel.Following userDatum = (FollowingListModel.Following) getIntent().getSerializableExtra("mUserDatum");
                    if (userDatum.getChannelInfo().get(0).getChannel().getMarkImage() != null && !userDatum.getChannelInfo().get(0).getChannel().getMarkImage().equalsIgnoreCase(""))
                        Picasso.with(mContext).load(userDatum.getChannelInfo().get(0).getChannel().getMarkImage()).into(ivLeagueLogo);
                    if (userDatum.getChannelInfo().get(0).getChannel() != null) {
                        if (userDatum.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("match")) {
                            channell = (FollowingListModel.Following) getIntent().getSerializableExtra("mUserDatum");
                            matchContenstentId = userDatum.getChannelInfo().get(0).getChannel().getMatchId();
                            tvTeam1Name.setText(userDatum.getChannelInfo().get(0).getMatchInfo().getTeam1Name() + ":");
                            tvTeam2Name.setText(userDatum.getChannelInfo().get(0).getMatchInfo().getTeam2Name() + ":");
                            txtMatchNameTop.setText(userDatum.getChannelInfo().get(0).getMatchInfo().getTeam1Name() + " Vs " + userDatum.getChannelInfo().get(0).getMatchInfo().getTeam2Name());
                            broadcasterName = channell.getChannelInfo().get(0).getChannel().getBroadcasterName();
                            txtBroadcasterName.setText(broadcasterName);
                            broadcasterId = channell.getChannelInfo().get(0).getChannel().getBroadcasterId();
                            channelId = channell.getChannelInfo().get(0).getChannel().getId();
                            streamName = channell.getChannelInfo().get(0).getChannel().getStreamName();
                            getRed5ProGroupId();
//                            configRedPro(streamName);
//                            getLiveFeedsFromServer(matchContenstentId);
                            setupViewPager(mViewPager, matchContenstentId);
                            mViewPager.setOffscreenPageLimit(0);
                            mTabLayout.setVisibility(View.VISIBLE);
                            mTabLayout.setupWithViewPager(mViewPager);
                        } else if (userDatum.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("team")) {
                            channell = (FollowingListModel.Following) getIntent().getSerializableExtra("mUserDatum");
                            matchContenstentId = userDatum.getChannelInfo().get(0).getChannel().getMatchId();
                            txtMatchNameTop.setText(userDatum.getChannelInfo().get(0).getTeamInfo().getContestantName());
                            txtMatchTimeTop.setText("Rank:" + userDatum.getChannelInfo().get(0).getTeamInfo().getRank());
                            broadcasterName = userDatum.getChannelInfo().get(0).getChannel().getBroadcasterName();
                            txtBroadcasterName.setText(broadcasterName);
                            tvTeam1Name.setText(userDatum.getChannelInfo().get(0).getTeamInfo().getContestantClubName());
                            tvTeam2Name.setText("Points: " + userDatum.getChannelInfo().get(0).getTeamInfo().getPoints());
                            broadcasterId = channell.getChannelInfo().get(0).getChannel().getBroadcasterId();
                            channelId = channell.getChannelInfo().get(0).getChannel().getId();
                            tvNoData.setVisibility(View.VISIBLE);
                            tvNoData.setText(R.string.team_talk);
                            streamName = channell.getChannelInfo().get(0).getChannel().getStreamName();
                            getRed5ProGroupId();
                            mTabLayout.setVisibility(View.GONE);
//                            configRedPro(streamName);
                        }
                    }
                }
            } else if (getIntent().getStringExtra("userComingFrom").equalsIgnoreCase("leaguesUserSearchSwitch")) {
                if (getIntent().getSerializableExtra("mUserDatum") != null) {
                    UserSearchLeagueModel.Datum userDatum = (UserSearchLeagueModel.Datum) getIntent().getSerializableExtra("mUserDatum");
                    if (userDatum.getChannelInfo().get(0).getChannel().getMarkImage() != null&& !userDatum.getChannelInfo().get(0).getChannel().getMarkImage().equalsIgnoreCase(""))
                        Picasso.with(mContext).load(userDatum.getChannelInfo().get(0).getChannel().getMarkImage()).into(ivLeagueLogo);
                    if (userDatum.getChannelInfo().get(0).getChannel() != null) {
                        if (userDatum.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("match")) {
                            channel = (LiveBroacastersListModel.Channel) getIntent().getSerializableExtra("mBrListDatum");
                            matchContenstentId = userDatum.getChannelInfo().get(0).getChannel().getMatchId();
                            tvTeam1Name.setText(userDatum.getChannelInfo().get(0).getMatchInfo().getTeam1Name() + ":");
                            tvTeam2Name.setText(userDatum.getChannelInfo().get(0).getMatchInfo().getTeam2Name() + ":");
                            txtMatchNameTop.setText(userDatum.getChannelInfo().get(0).getMatchInfo().getTeam1Name() + " Vs " + userDatum.getChannelInfo().get(0).getMatchInfo().getTeam2Name());
                            broadcasterName = channel.getBroadcasterName();
                            txtBroadcasterName.setText(broadcasterName);
                            broadcasterId = channel.getBroadcasterId();
                            channelId = channel.getId();
                            streamName = channel.getStreamName();
                            getRed5ProGroupId();
//                            configRedPro(streamName);
//                            getLiveFeedsFromServer(matchContenstentId);
                            setupViewPager(mViewPager, matchContenstentId);
                            mViewPager.setOffscreenPageLimit(0);
                            mTabLayout.setVisibility(View.VISIBLE);
                            mTabLayout.setupWithViewPager(mViewPager);
                        } else if (userDatum.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("team")) {
                            channel = (LiveBroacastersListModel.Channel) getIntent().getSerializableExtra("mBrListDatum");
                            matchContenstentId = userDatum.getChannelInfo().get(0).getChannel().getMatchId();
                            txtMatchNameTop.setText(userDatum.getChannelInfo().get(0).getTeamInfo().getContestantName());
                            txtMatchTimeTop.setText("Rank:" + userDatum.getChannelInfo().get(0).getTeamInfo().getRank());
                            broadcasterName = channel.getBroadcasterName();
                            txtBroadcasterName.setText(broadcasterName);
                            tvTeam1Name.setText(userDatum.getChannelInfo().get(0).getTeamInfo().getContestantClubName());
                            tvTeam2Name.setText("Points: " + userDatum.getChannelInfo().get(0).getTeamInfo().getPoints());
                            broadcasterId = channel.getBroadcasterId();
                            channelId = channel.getId();
                            tvNoData.setVisibility(View.VISIBLE);
                            tvNoData.setText(R.string.team_talk);
                            streamName = channel.getStreamName();
                            mTabLayout.setVisibility(View.GONE);
                            getRed5ProGroupId();
//                            configRedPro(streamName);
                        }
                    }
                }
            } else if (getIntent().getStringExtra("userComingFrom").equalsIgnoreCase("leaguesTeamSearch")) {
                if (getIntent().getSerializableExtra("mTeamSearchDatum") != null) {
                    TeamSearchSportsModel.Datum teamDatum = (TeamSearchSportsModel.Datum) getIntent().getSerializableExtra("mTeamSearchDatum");
                    if (teamDatum.getMarkImage() != null&& !teamDatum.getMarkImage().equalsIgnoreCase(""))
                        Picasso.with(mContext).load(teamDatum.getMarkImage()).into(ivLeagueLogo);
                    if (teamDatum.getChannelInfo() != null) {
                        {
                            channel = (LiveBroacastersListModel.Channel) getIntent().getSerializableExtra("mBrListDatum");
                            matchContenstentId = channel.getMatchId();
                            txtMatchNameTop.setText(channel.getBroadcasterName());
                            txtMatchTimeTop.setText("Rank:" + teamDatum.getRank());
                            broadcasterName = channel.getBroadcasterName();
                            txtBroadcasterName.setText(broadcasterName);
                            tvTeam1Name.setText(teamDatum.getContestantClubName());
                            tvTeam2Name.setText("Points: " + teamDatum.getPoints());
                            broadcasterId = channel.getBroadcasterId();
                            channelId = channel.getId();
                            tvNoData.setVisibility(View.VISIBLE);
                            tvNoData.setText(R.string.team_talk);
                            streamName = channel.getStreamName();
                            mTabLayout.setVisibility(View.GONE);
                            getRed5ProGroupId();
//                            configRedPro(streamName);
                        }
                    }
                }
            } else if (getIntent().getStringExtra("userComingFrom").equalsIgnoreCase("matchListNoBroadcast")) {
                if (getIntent().getSerializableExtra("mMatchDatum") != null) {
                    if (AppPreferences.init(mContext).getString(AppConstant.LEAGUE_IMAGE_URL) != null && !AppPreferences.init(mContext).getString(AppConstant.LEAGUE_IMAGE_URL).equalsIgnoreCase(""))
                        Picasso.with(mContext).load(AppPreferences.init(mContext).getString(AppConstant.LEAGUE_IMAGE_URL)).into(ivLeagueLogo);
                    MatchListListnerModel.ListenMatchList matchDatum = (MatchListListnerModel.ListenMatchList) getIntent().getSerializableExtra("mMatchDatum");
                    matchContenstentId = matchDatum.getMatchId();
                    tvTeam1Name.setText(matchDatum.getTeam1Name() + ":");
                    tvTeam2Name.setText(matchDatum.getTeam2Name() + ":");
                    txtMatchNameTop.setText(matchDatum.getTeam1Name() + " Vs " + matchDatum.getTeam2Name());
                    txtBroadcasterName.setVisibility(View.GONE);
                    rlSwitchTile.setClickable(false);
                    rlShareTile.setClickable(false);
                    rlPunditTile.setClickable(false);
                   /* broadcasterId = channel.getBroadcasterId();
                    channelId = channel.getId();
                    streamName = channel.getStreamName();*/
                    //getRed5ProGroupId();
//                    configRedPro(streamName);
//                    getLiveFeedsFromServer(matchContenstentId);
                    setupViewPager(mViewPager, matchContenstentId);
                    mViewPager.setOffscreenPageLimit(0);
                    mTabLayout.setVisibility(View.VISIBLE);
                    mTabLayout.setupWithViewPager(mViewPager);
                }
            }
            if (!(getIntent().getStringExtra("userComingFrom").equalsIgnoreCase("matchListNoBroadcast"))) {
                getBroadcastersDetails(matchContenstentId);
            }
            String getfollowunfollowpath = broadcasterId + "/" + AppPreferences.init(mContext).getString(AppConstant.USER_ID) + "/" + channelId;
            getUserFollowCount(getfollowunfollowpath);
            getBroadcastersProfile(broadcasterId);
        } else {
            SnackbarUtil.showWarningLongSnackbar(mContext, getResources().getString(R.string.internet_not_connected_text));
        }

    }

    private void setupViewPager(ViewPager viewPager, String strMatchId) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(LiveFeedsFragment.newInstance("Listening", strMatchId), "Overview");
        adapter.addFragment(LiveFeedsPlayerFragment.newInstance("Listening", strMatchId), "Lineups");
        viewPager.setAdapter(adapter);
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

    public void getRed5ProGroupId() {
        App.getApiHelper().getRedFiveProGroupId(new ApiCallBack<List<RedFiveProGroupIdModel>>() {
            @Override
            public void onSuccess(List<RedFiveProGroupIdModel> redFiveProGroupIdModels) {
                if (redFiveProGroupIdModels.get(0).getName() != null) {
                    groupID = redFiveProGroupIdModels.get(0).getName();
                    getServerAddress(groupID);
                }
            }

            @Override
            public void onFailure(String message) {
                Log.e("hello", message);
            }
        });

    }

    public void getServerAddress(String groupID) {
        App.getApiHelper().getListeningServerAddress(groupID, new ApiCallBack<List<ServerListenerAddressModel>>() {
            @Override
            public void onSuccess(List<ServerListenerAddressModel> serverListenerAddressModel) {
                for (int i = 0; i < serverListenerAddressModel.size(); i++)
                    if (serverListenerAddressModel.get(i).getState() != null) {
                        if (serverListenerAddressModel.get(i).getState().equalsIgnoreCase("inservice")) {
                            if (serverListenerAddressModel.get(i).getAddress() != null)
                                serverAddress = serverListenerAddressModel.get(i).getAddress();
                            if (streamName != null) {
                                    setkickofftime();
                                configRedPro(streamName, serverAddress);
                            } else {
                                SnackbarUtil.showWarningShortSnackbar(mContext, getResources().getString(R.string.fb_error_message));
                            }
                        }
                    }
            }

            @Override
            public void onFailure(String message) {
                Log.e("getserver", message);
            }
        });
    }


    @OnClick(R.id.rl_switch_tile)
    public void switchBroacaster() {
        showAlert();
    }

    public void showAlert() {
        new AlertDialog.Builder(mContext)
                .setTitle(getString(R.string.switch_listen_title))
                .setMessage(getString(R.string.switch_listen_message))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (subscribe != null) {
                            subscribe.stop();
                        }
                        unmountListner(listenerId);
                        if (getIntent().getStringExtra("userComingFrom").equalsIgnoreCase("pundits")) {
                            Intent intent = new Intent(mContext, LiveBroadcastersListActivity.class);
                            intent.putExtra("userComingFrom", "pundits");
                            intent.putExtra("matchidcontestentid", matchContenstentId);
                            intent.putExtra("chatChannelKey", getIntent().getStringExtra("chatChannelKey"));
                            intent.putExtra("mUserDatum", getIntent().getSerializableExtra("mUserDatum"));
                            startActivity(intent);
                            finish();
                        } else if (getIntent().getStringExtra("userComingFrom").equalsIgnoreCase("punditsSwitch")) {
                            Intent intent = new Intent(mContext, LiveBroadcastersListActivity.class);
                            intent.putExtra("userComingFrom", "punditsSwitch");
                            intent.putExtra("matchidcontestentid", matchContenstentId);
                            intent.putExtra("mUserDatum", getIntent().getSerializableExtra("mUserDatum"));
                            intent.putExtra("chatChannelKey", getIntent().getStringExtra("chatChannelKey"));
                            startActivity(intent);
                            finish();
                        } else if (getIntent().getStringExtra("userComingFrom").equalsIgnoreCase("sprotsUserSearch")) {
                            Intent intent = new Intent(mContext, LiveBroadcastersListActivity.class);
                            intent.putExtra("userComingFrom", "sprotsUserSearch");
                            intent.putExtra("matchidcontestentid", matchContenstentId);
                            intent.putExtra("mUserDatum", getIntent().getSerializableExtra("mUserDatum"));
                            intent.putExtra("chatChannelKey", getIntent().getStringExtra("chatChannelKey"));
                            startActivity(intent);
                            finish();
                        } else if (getIntent().getStringExtra("userComingFrom").equalsIgnoreCase("sprotsUserSearchSwitch")) {
                            Intent intent = new Intent(mContext, LiveBroadcastersListActivity.class);
                            intent.putExtra("userComingFrom", "sprotsUserSearchSwitch");
                            intent.putExtra("matchidcontestentid", matchContenstentId);
                            intent.putExtra("mUserDatum", getIntent().getSerializableExtra("mUserDatum"));
                            intent.putExtra("chatChannelKey", getIntent().getStringExtra("chatChannelKey"));
                            startActivity(intent);
                            finish();
                        } else if (getIntent().getStringExtra("userComingFrom").equalsIgnoreCase("leaguesUserSearch")) {
                            Intent intent = new Intent(mContext, LiveBroadcastersListActivity.class);
                            intent.putExtra("userComingFrom", "leaguesUserSearch");
                            intent.putExtra("matchidcontestentid", matchContenstentId);
                            intent.putExtra("mUserDatum", getIntent().getSerializableExtra("mUserDatum"));
                            intent.putExtra("chatChannelKey", getIntent().getStringExtra("chatChannelKey"));
                            startActivity(intent);
                            finish();
                        } else if (getIntent().getStringExtra("userComingFrom").equalsIgnoreCase("leaguesUserSearchSwitch")) {
                            Intent intent = new Intent(mContext, LiveBroadcastersListActivity.class);
                            intent.putExtra("userComingFrom", "leaguesUserSearchSwitch");
                            intent.putExtra("matchidcontestentid", matchContenstentId);
                            intent.putExtra("mUserDatum", getIntent().getSerializableExtra("mUserDatum"));
                            intent.putExtra("chatChannelKey", getIntent().getStringExtra("chatChannelKey"));
                            startActivity(intent);
                            finish();
                        } else if (getIntent().getStringExtra("userComingFrom").equalsIgnoreCase("sprotsTeamSearch")) {
                            Intent intent = new Intent(mContext, LiveBroadcastersListActivity.class);
                            intent.putExtra("userComingFrom", "sprotsTeamSearch");
                            intent.putExtra("matchidcontestentid", matchContenstentId);
                            intent.putExtra("mTeamSearchDatum", getIntent().getSerializableExtra("mTeamSearchDatum"));
                            intent.putExtra("chatChannelKey", getIntent().getStringExtra("chatChannelKey"));
                            startActivity(intent);
                            finish();
                        } else if (getIntent().getStringExtra("userComingFrom").equalsIgnoreCase("leaguesTeamSearch")) {
                            Intent intent = new Intent(mContext, LiveBroadcastersListActivity.class);
                            intent.putExtra("userComingFrom", "leaguesTeamSearch");
                            intent.putExtra("matchidcontestentid", matchContenstentId);
                            intent.putExtra("mTeamSearchDatum", getIntent().getSerializableExtra("mTeamSearchDatum"));
                            intent.putExtra("chatChannelKey", getIntent().getStringExtra("chatChannelKey"));
                            startActivity(intent);
                            finish();
                        } else if (getIntent().getStringExtra("userComingFrom").equalsIgnoreCase("followerList")) {
                            Intent intent = new Intent(mContext, LiveBroadcastersListActivity.class);
                            intent.putExtra("userComingFrom", "followerList");
                            intent.putExtra("matchidcontestentid", matchContenstentId);
                            intent.putExtra("chatChannelKey", getIntent().getStringExtra("chatChannelKey"));
                            intent.putExtra("mUserDatum", getIntent().getSerializableExtra("mUserDatum"));
                            startActivity(intent);
                            finish();
                        } else if (getIntent().getStringExtra("userComingFrom").equalsIgnoreCase("followingList")) {
                            Intent intent = new Intent(mContext, LiveBroadcastersListActivity.class);
                            intent.putExtra("userComingFrom", "followingList");
                            intent.putExtra("matchidcontestentid", matchContenstentId);
                            intent.putExtra("chatChannelKey", getIntent().getStringExtra("chatChannelKey"));
                            intent.putExtra("mUserDatum", getIntent().getSerializableExtra("mUserDatum"));
                            startActivity(intent);
                            finish();
                        } else if (getIntent().getStringExtra("userComingFrom").equalsIgnoreCase("followingListSwitch")) {
                            Intent intent = new Intent(mContext, LiveBroadcastersListActivity.class);
                            intent.putExtra("userComingFrom", "followingListSwitch");
                            intent.putExtra("matchidcontestentid", matchContenstentId);
                            intent.putExtra("mUserDatum", getIntent().getSerializableExtra("mUserDatum"));
                            intent.putExtra("chatChannelKey", getIntent().getStringExtra("chatChannelKey"));
                            startActivity(intent);
                            finish();
                        } else if (getIntent().getStringExtra("userComingFrom").equalsIgnoreCase("followerListSwitch")) {
                            Intent intent = new Intent(mContext, LiveBroadcastersListActivity.class);
                            intent.putExtra("userComingFrom", "followerListSwitch");
                            intent.putExtra("matchidcontestentid", matchContenstentId);
                            intent.putExtra("mUserDatum", getIntent().getSerializableExtra("mUserDatum"));
                            intent.putExtra("chatChannelKey", getIntent().getStringExtra("chatChannelKey"));
                            startActivity(intent);
                            finish();
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

    /*@OnClick(R.id.iv_play_pause)
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
    }*/

    /*  @OnClick(R.id.btn_show_profile)
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
  */
    @OnClick(R.id.rl_pundit_tile)
    public void showhideUserProfile() {
        if (visible == 0) {
            visible = 1;
            llBroadcastersProfile.setVisibility(View.VISIBLE);
            llBroadcastersProfile.animate()
                    .translationYBy(llBroadcastersProfile.getHeight())
                    .translationY(0)
                    .setDuration(10000)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            llBroadcastersProfile.animate().setListener(null);
                        }
                    });

        } else if (visible == 1) {
            visible = 0;
            llBroadcastersProfile.setVisibility(View.GONE);
            llBroadcastersProfile.animate()
                    .translationYBy(llBroadcastersProfile.getHeight())
                    .translationY(0)
                    .setDuration(10000)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            llBroadcastersProfile.animate().setListener(null);
                        }
                    });
        }
    }

    @OnClick(R.id.rl_live_listening_main)
    public void hideUserProfile() {
        if (visible == 1) {
            visible = 0;
            llBroadcastersProfile.setVisibility(View.GONE);
            llBroadcastersProfile.animate()
                    .translationYBy(llBroadcastersProfile.getHeight())
                    .translationY(0)
                    .setDuration(10000)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            llBroadcastersProfile.animate().setListener(null);
                        }
                    });
        }
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

    @OnClick(R.id.rl_share_tile)
    public void onShare() {
        if (ConnectivityReceivers.isConnected()) {
            String encodedBroadcastersId = Base64.encodeToString(broadcasterId.getBytes(), Base64.NO_WRAP);
            String username = broadcasterName.replace(" ", "");
            String shareUrl = ApiConstants.SHARE_BASE_URL + username + "-" + encodedBroadcastersId;
//            AppPreferences.init(mContext).putString(AppConstant.User_CURRENT_STATE, "3");
            if (getIntent().getStringExtra("userComingFrom") != null) {
                if (getIntent().getStringExtra("userComingFrom").equalsIgnoreCase("matchList")) {
                    if (getIntent().getSerializableExtra("mMatchDatum") != null) {
                        MatchListListnerModel.ListenMatchList matchDatum = (MatchListListnerModel.ListenMatchList) getIntent().getSerializableExtra("mMatchDatum");
                        status = "I'm live on Pundit now listening " + matchDatum.getTeam1Name() + " Vs " + matchDatum.getTeam2Name() + ", come join me.";
                        if (!matchDatum.getTeam1_twitter_id().equalsIgnoreCase("") && !matchDatum.getTeam2__twitter_id().equalsIgnoreCase(""))
                            twitterStatus = "Twitter: @" + matchDatum.getTeam1_twitter_id() + " @" + matchDatum.getTeam2__twitter_id();
                        else
                            twitterStatus = "";
                    }
                } else if (getIntent().getStringExtra("userComingFrom").equalsIgnoreCase("matchStandingListenList")) {
                    if (getIntent().getSerializableExtra("mMatchDatum") != null) {
                        StandingListeningModel.Datum matchDatum = (StandingListeningModel.Datum) getIntent().getSerializableExtra("mMatchDatum");
                        status = "I'm live on Pundit now listening " + matchDatum.getContestantClubName() + ", come join me.";
                        if (!matchDatum.getTwitter_id().equalsIgnoreCase(""))
                            twitterStatus = "Twitter: @" + matchDatum.getTwitter_id();
                        else
                            twitterStatus = "";

                    }
                } else if (getIntent().getStringExtra("userComingFrom").equalsIgnoreCase("pundits")) {
                    if (getIntent().getSerializableExtra("mUserDatum") != null) {
                        UserDetailsAndMatchDetailsModel.UsersList userDatum = (UserDetailsAndMatchDetailsModel.UsersList) getIntent().getSerializableExtra("mUserDatum");
                        if (userDatum.getChannelInfo().get(0).getChannel() != null) {
                            if (userDatum.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("match")) {
                                status = "I'm live on Pundit now listening " + userDatum.getChannelInfo().get(0).getMatchInfo().getTeam1Name() + " Vs " + userDatum.getChannelInfo().get(0).getMatchInfo().getTeam2Name() + ", come join me.";
                                if (!userDatum.getChannelInfo().get(0).getMatchInfo().getTeam1_twitter_id().equalsIgnoreCase("") && !userDatum.getChannelInfo().get(0).getMatchInfo().getTeam2__twitter_id().equalsIgnoreCase(""))
                                    twitterStatus = "Twitter: @" + userDatum.getChannelInfo().get(0).getMatchInfo().getTeam1_twitter_id() + " @" + userDatum.getChannelInfo().get(0).getMatchInfo().getTeam2__twitter_id();
                                else
                                    twitterStatus = "";

                            } else if (userDatum.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("team")) {
                                status = "I'm live on Pundit now listening " + userDatum.getChannelInfo().get(0).getTeamInfo().getContestantClubName() + ", come join me.";
                                if (!userDatum.getChannelInfo().get(0).getTeamInfo().getTwitter_id().equalsIgnoreCase(""))
                                    twitterStatus = "Twitter: @" + userDatum.getChannelInfo().get(0).getTeamInfo().getTwitter_id();
                                else
                                    twitterStatus = "";
                            }

                        }
                    }
                } else if (getIntent().getStringExtra("userComingFrom").equalsIgnoreCase("punditsSwitch")) {
                    if (getIntent().getSerializableExtra("mUserDatum") != null) {
                        UserDetailsAndMatchDetailsModel.UsersList userDatum = (UserDetailsAndMatchDetailsModel.UsersList) getIntent().getSerializableExtra("mUserDatum");
                        if (userDatum.getChannelInfo().get(0).getChannel() != null) {
                            if (userDatum.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("match")) {
                                status = "I'm live on Pundit now listening " + userDatum.getChannelInfo().get(0).getMatchInfo().getTeam1Name() + " Vs " + userDatum.getChannelInfo().get(0).getMatchInfo().getTeam2Name() + ", come join me.";
                                if (!userDatum.getChannelInfo().get(0).getMatchInfo().getTeam1_twitter_id().equalsIgnoreCase("") && !userDatum.getChannelInfo().get(0).getMatchInfo().getTeam2__twitter_id().equalsIgnoreCase(""))
                                    twitterStatus = "Twitter: @" + userDatum.getChannelInfo().get(0).getMatchInfo().getTeam1_twitter_id() + " @" + userDatum.getChannelInfo().get(0).getMatchInfo().getTeam2__twitter_id();
                                else
                                    twitterStatus = "";
                            } else if (userDatum.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("team")) {
                                status = "I'm live on Pundit now listening " + userDatum.getChannelInfo().get(0).getTeamInfo().getContestantClubName() + ", come join me.";
                                if (!userDatum.getChannelInfo().get(0).getTeamInfo().getTwitter_id().equalsIgnoreCase(""))
                                    twitterStatus = "Twitter: @" + userDatum.getChannelInfo().get(0).getTeamInfo().getTwitter_id();
                                else
                                    twitterStatus = "";
                            }
                        }
                    }
                } else if (getIntent().getStringExtra("userComingFrom").equalsIgnoreCase("sprotsUserSearch")) {
                    if (getIntent().getSerializableExtra("mUserDatum") != null) {
                        UserSearchSportsModel.UserDatum userDatum = (UserSearchSportsModel.UserDatum) getIntent().getSerializableExtra("mUserDatum");
                        if (userDatum.getChannelInfo().get(0).getChannel() != null) {
                            if (userDatum.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("match")) {
                                status = "I'm live on Pundit now listening " + userDatum.getChannelInfo().get(0).getMatchInfo().getTeam1Name() + " Vs " + userDatum.getChannelInfo().get(0).getMatchInfo().getTeam2Name() + ", come join me.";
                                if (!userDatum.getChannelInfo().get(0).getMatchInfo().getTeam1_twitter_id().equalsIgnoreCase("") && !userDatum.getChannelInfo().get(0).getMatchInfo().getTeam2__twitter_id().equalsIgnoreCase(""))
                                    twitterStatus = "Twitter: @" + userDatum.getChannelInfo().get(0).getMatchInfo().getTeam1_twitter_id() + " @" + userDatum.getChannelInfo().get(0).getMatchInfo().getTeam2__twitter_id();
                                else
                                    twitterStatus = "";
                            } else if (userDatum.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("team")) {
                                status = "I'm live on Pundit now listening " + userDatum.getChannelInfo().get(0).getTeamInfo().getContestantClubName() + ", come join me.";
                                if (!userDatum.getChannelInfo().get(0).getTeamInfo().getTwitter_id().equalsIgnoreCase(""))
                                    twitterStatus = "Twitter: @" + userDatum.getChannelInfo().get(0).getTeamInfo().getTwitter_id();
                                else
                                    twitterStatus = "";
                            }
                        }
                    }
                } else if (getIntent().getStringExtra("userComingFrom").equalsIgnoreCase("sprotsUserSearchSwitch")) {
                    if (getIntent().getSerializableExtra("mUserDatum") != null) {
                        UserSearchSportsModel.UserDatum userDatum = (UserSearchSportsModel.UserDatum) getIntent().getSerializableExtra("mUserDatum");
                        if (userDatum.getChannelInfo().get(0).getChannel() != null) {
                            if (userDatum.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("match")) {
                                status = "I'm live on Pundit now listening " + userDatum.getChannelInfo().get(0).getMatchInfo().getTeam1Name() + " Vs " + userDatum.getChannelInfo().get(0).getMatchInfo().getTeam2Name() + ", come join me.";
                                if (!userDatum.getChannelInfo().get(0).getMatchInfo().getTeam1_twitter_id().equalsIgnoreCase("") && !userDatum.getChannelInfo().get(0).getMatchInfo().getTeam2__twitter_id().equalsIgnoreCase(""))
                                    twitterStatus = "Twitter: @" + userDatum.getChannelInfo().get(0).getMatchInfo().getTeam1_twitter_id() + " @" + userDatum.getChannelInfo().get(0).getMatchInfo().getTeam2__twitter_id();
                                else
                                    twitterStatus = "";
                            } else if (userDatum.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("team")) {
                                status = "I'm live on Pundit now listening " + userDatum.getChannelInfo().get(0).getTeamInfo().getContestantClubName() + ", come join me.";
                                if (!userDatum.getChannelInfo().get(0).getTeamInfo().getTwitter_id().equalsIgnoreCase(""))
                                    twitterStatus = "Twitter: @" + userDatum.getChannelInfo().get(0).getTeamInfo().getTwitter_id();
                                else
                                    twitterStatus = "";
                            }
                        }
                    }
                } else if (getIntent().getStringExtra("userComingFrom").equalsIgnoreCase("leaguesUserSearch")) {
                    if (getIntent().getSerializableExtra("mUserDatum") != null) {
                        UserSearchLeagueModel.Datum userDatum = (UserSearchLeagueModel.Datum) getIntent().getSerializableExtra("mUserDatum");
                        if (userDatum.getChannelInfo().get(0).getChannel() != null) {
                            if (userDatum.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("match")) {
                                status = "I'm live on Pundit now listening " + userDatum.getChannelInfo().get(0).getMatchInfo().getTeam1Name() + " Vs " + userDatum.getChannelInfo().get(0).getMatchInfo().getTeam2Name() + ", come join me.";
                                if (!userDatum.getChannelInfo().get(0).getMatchInfo().getTeam1_twitter_id().equalsIgnoreCase("") && !userDatum.getChannelInfo().get(0).getMatchInfo().getTeam2__twitter_id().equalsIgnoreCase(""))
                                    twitterStatus = "Twitter: @" + userDatum.getChannelInfo().get(0).getMatchInfo().getTeam1_twitter_id() + " @" + userDatum.getChannelInfo().get(0).getMatchInfo().getTeam2__twitter_id();
                                else
                                    twitterStatus = "";
                            } else if (userDatum.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("team")) {
                                status = "I'm live on Pundit now listening " + userDatum.getChannelInfo().get(0).getTeamInfo().getContestantClubName() + ", come join me.";
                                if (!userDatum.getChannelInfo().get(0).getTeamInfo().getTwitter_id().equalsIgnoreCase(""))
                                    twitterStatus = "Twitter: @" + userDatum.getChannelInfo().get(0).getTeamInfo().getTwitter_id();
                                else
                                    twitterStatus = "";
                            }
                        }
                    }
                } else if (getIntent().getStringExtra("userComingFrom").equalsIgnoreCase("leaguesUserSearchSwitch")) {
                    if (getIntent().getSerializableExtra("mUserDatum") != null) {
                        UserSearchLeagueModel.Datum userDatum = (UserSearchLeagueModel.Datum) getIntent().getSerializableExtra("mUserDatum");
                        if (userDatum.getChannelInfo().get(0).getChannel() != null) {
                            if (userDatum.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("match")) {
                                status = "I'm live on Pundit now listening " + userDatum.getChannelInfo().get(0).getMatchInfo().getTeam1Name() + " Vs " + userDatum.getChannelInfo().get(0).getMatchInfo().getTeam2Name() + ", come join me.";
                                if (!userDatum.getChannelInfo().get(0).getMatchInfo().getTeam1_twitter_id().equalsIgnoreCase("") && !userDatum.getChannelInfo().get(0).getMatchInfo().getTeam2__twitter_id().equalsIgnoreCase(""))
                                    twitterStatus = "Twitter: @" + userDatum.getChannelInfo().get(0).getMatchInfo().getTeam1_twitter_id() + " @" + userDatum.getChannelInfo().get(0).getMatchInfo().getTeam2__twitter_id();
                                else
                                    twitterStatus = "";
                            } else if (userDatum.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("team")) {
                                status = "I'm live on Pundit now listening " + userDatum.getChannelInfo().get(0).getTeamInfo().getContestantClubName() + ", come join me.";
                                if (!userDatum.getChannelInfo().get(0).getTeamInfo().getTwitter_id().equalsIgnoreCase(""))
                                    twitterStatus = "Twitter: @" + userDatum.getChannelInfo().get(0).getTeamInfo().getTwitter_id();
                                else
                                    twitterStatus = "";
                            }
                        }
                    }
                } else if (getIntent().getStringExtra("userComingFrom").equalsIgnoreCase("sprotsTeamSearch")) {
                    if (getIntent().getSerializableExtra("mTeamSearchDatum") != null) {
                        TeamSearchSportsModel.Datum teamDatum = (TeamSearchSportsModel.Datum) getIntent().getSerializableExtra("mTeamSearchDatum");
                        status = "I'm live on Pundit now listening " + teamDatum.getContestantClubName() + ", come join me.";
                        if (!teamDatum.getTwitter_id().equalsIgnoreCase(""))
                            twitterStatus = "Twitter: @" + teamDatum.getTwitter_id();
                        else
                            twitterStatus = "";
                    }
                } else if (getIntent().getStringExtra("userComingFrom").equalsIgnoreCase("leaguesTeamSearch")) {
                    if (getIntent().getSerializableExtra("mTeamSearchDatum") != null) {
                        TeamSearchSportsModel.Datum teamDatum = (TeamSearchSportsModel.Datum) getIntent().getSerializableExtra("mTeamSearchDatum");
                        status = "I'm live on Pundit now listening " + teamDatum.getContestantClubName() + ", come join me.";
                        if (!teamDatum.getTwitter_id().equalsIgnoreCase(""))
                            twitterStatus = "Twitter: @" + teamDatum.getTwitter_id();
                        else
                            twitterStatus = "";
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
    }
           /* Intent emailIntent = new Intent();
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



    @OnClick(R.id.btn_follow_unfollow)
    public void buttonFollowUnfollow() {
        if (AppPreferences.init(mContext).getString(AppConstant.USER_ID).equalsIgnoreCase(broadcasterId)) {
            SnackbarUtil.showWarningShortSnackbar(mContext, getString(R.string.self_follow_text));
            btnFollowUnfollow.setClickable(false);
        } else {
            String followUnfoloowPath;
            if (followStatus == 0) {
                btnFollowUnfollow.setText(R.string.follow);
                followUnfoloowPath = AppPreferences.init(mContext).getString(AppConstant.USER_ID) + "/" + broadcasterId;
                followunfollowUser(followUnfoloowPath);
            } else if (followStatus == 1) {
                btnFollowUnfollow.setText(R.string.unfollow);
                followUnfoloowPath = AppPreferences.init(mContext).getString(AppConstant.USER_ID) + "/" + broadcasterId;
                followunfollowUser(followUnfoloowPath);
            }
        }
    }


    @Override
    public void onBackPressed() {
        openCancelDialog();
    }

    @Override
    public void onStop() {
        super.onStop();
     /*   if (AppPreferences.init(mContext).getString(AppConstant.User_CURRENT_STATE).equalsIgnoreCase("3")) {
//            Do Nothing
        } else {
            if (subscribe != null) {
                subscribe.stop();
            }
            unmountListner(listenerId);
        }*/
    }


    @Override
    protected void onResume() {
        super.onResume();
//        AppPreferences.init(mContext).putString(AppConstant.User_CURRENT_STATE, "2");
    }

   /* private void getLiveFeedsFromServer(final String matchId) {
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
                                    progressBarLiveListen.setVisibility(View.GONE);
                                    enableUserIntraction();
                                    if (liveFeedsNewModel != null) {
                                        if (liveFeedsNewModel.getMatch() != null) {
                                            if (liveFeedsNewModel.getMatch().getStatus().contains("Kick off")) {
                                                txtMatchTimeTop.setText(getString(R.string.fixture_string));
                                            } else if (liveFeedsNewModel.getMatch().getStatus().equalsIgnoreCase("Full Time")) {
                                                txtMatchTimeTop.setText(getString(R.string.ft_string));
                                            } else if (liveFeedsNewModel.getMatch().getStatus().contains("First Half") || liveFeedsNewModel.getMatch().getStatus().contains("Second Half")) {
                                                txtMatchTimeTop.setText(getString(R.string.playing_String));
                                            } else {
                                                txtMatchTimeTop.setText("-");
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
                                                tvNoData.setVisibility(View.GONE);
                                                liveFeedsAdapter = new LiveFeedsAdapter(mContext, playerDataModelsList);
                                                rvLivefeedsContainer.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
                                                rvLivefeedsContainer.setAdapter(liveFeedsAdapter);
                                            } else {
                                                tvNoData.setVisibility(View.VISIBLE);
                                                tvNoData.setText(R.string.no_live_feeds);
                                            }
                                        }
                                    }

                                }

                                @Override
                                public void onFailure(String message) {

                                }


                            });
                        } else {
                            progressBarLiveListen.setVisibility(View.GONE);
                            enableUserIntraction();
                            SnackbarUtil.showWarningLongSnackbar(mContext, getResources().getString(R.string.internet_not_connected_text));
                        }
                    }
                });
            }
        }, 0, 20000);
    }*/

    private void configRedPro(String media, String serverAddress) {
        R5Configuration configuration = new R5Configuration(R5StreamProtocol.RTSP, serverAddress, AppConstant.RED5PRO_SERVER_PORT, AppConstant.RED5PRO_SERVER_APP_NAME, AppConstant.RED5PRO_SERVER_CASHE);
        configuration.setLicenseKey(AppConstant.RED5PRO_LICENSE_KEY);
        configuration.setBundleID(mContext.getPackageName());
        R5Connection connection = new R5Connection(configuration);
        subscribe = new R5Stream(connection);
        subscribe.play(media);
        getListnerCountData(channelId);
        String path = broadcasterId + "/" + channelId;
        getAddsList(path);
    }

    private void getBroadcastersProfile(String broadcastersId) {
        if (ConnectivityReceivers.isConnected()) {
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
                        tvBroadcastersName.setText(userProfileResponseModel.getMessage().getFirstName());
                        Picasso.with(mContext).load(ApiConstants.PROFILE_IMAGE_BASE_URL + userProfileResponseModel.getMessage().getAvatar()).into(ivProfileImg);
                    }
                }

                @Override
                public void onFailure(String message) {
                    progressBarLiveListen.setVisibility(View.GONE);
                    enableUserIntraction();
                }
            });
        } else {
            progressBarLiveListen.setVisibility(View.GONE);
            enableUserIntraction();
            SnackbarUtil.showWarningLongSnackbar(mContext, getResources().getString(R.string.internet_not_connected_text));
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
                      //  mckeckbool=true;
                        strSec += 1;
                        newtime = String.format("%02d:%02d:%02d",strHours,strHours,strHours);
                        txtkickoffTime.setText(newtime);
                        if (strSec == 60) {
                            txtkickoffTime.setText(newtime);
                            strSec = 0;
                            strMin = strMin + 1;
                        }
                        if(strMin==60){
                            txtkickoffTime.setText(newtime);
                            strMin=0;
                            strHours=strHours+1;


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
                            if (subscribe != null) {
                                subscribe.stop();
                            }
                            unmountListner(listenerId);
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

    }

    private void followunfollowUser(final String stringPath) {
        progressBarLiveListen.setVisibility(View.VISIBLE);
        disableUserIntraction();
        if (ConnectivityReceivers.isConnected()) {
            App.getApiHelper().followunfollowlist(stringPath, new ApiCallBack<FollowUnfollowModel>() {
                @Override
                public void onSuccess(FollowUnfollowModel followUnfollowModel) {
                    if (followUnfollowModel != null) {
                        progressBarLiveListen.setVisibility(View.GONE);
                        enableUserIntraction();
                        followStatus = followUnfollowModel.getData().getResult();
                        if (followStatus == 0) {
                            btnFollowUnfollow.setText(R.string.follow);
                            tvPunditsFollowerCount.setText(followUnfollowModel.getData().getCount().toString());
                        } else if (followStatus == 1) {
                            btnFollowUnfollow.setText(R.string.unfollow);
                            tvPunditsFollowerCount.setText(followUnfollowModel.getData().getCount().toString());
                        }
                    }
                }

                @Override
                public void onFailure(String message) {
                    progressBarLiveListen.setVisibility(View.GONE);
                    enableUserIntraction();
//
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
                        } else if (followStatus == 1) {
                            btnFollowUnfollow.setText(R.string.unfollow);
                        }
                        if (followCheckModel.getListenerId() != null) {
                            listenerId = followCheckModel.getListenerId().toString();
                            tvPunditsFollowerCount.setText(followCheckModel.getInfo().getFollowing_count().toString());
                            tvPunditsFollowingCount.setText(followCheckModel.getInfo().getFollowerCount().toString());
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
                                    try {
                                        if (broadacstersDetailsModel != null && broadacstersDetailsModel.getChannel() != null) {
                                            if (broadacstersDetailsModel.getChannel().size() == 0) {
                                                t.cancel();
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
                                                    broadcasterLeaveDialog();
                                                }
                                            }
                                        }
                                    } catch (Exception e) {
                                        t.cancel();
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onFailure(String message) {
                                    t.cancel();
                                }
                            });
                        } else {
                            t.cancel();
                            SnackbarUtil.showWarningLongSnackbar(mContext, getResources().getString(R.string.internet_not_connected_text));
                        }
                    }
                });
            }
        }, 0, 5000);
    }


    private void getAddsList(final String path) {
        if (ConnectivityReceivers.isConnected()) {
            tm = new Timer();
            if (timer != null) {
                timer.cancel();
                timer = null;
            }
            tm.scheduleAtFixedRate(new TimerTask() {

                @Override
                public void run() {
                  /*  runOnUiThread(new Runnable() {
                        @Override
                        public void run() {*/
                    App.getApiHelper().getAdsList(path, new ApiCallBack<AddsModel>() {
                        @Override
                        public void onSuccess(AddsModel map) {
                            if (map != null) {
                                if (map.getAdsDetail().getPauseFlag().equalsIgnoreCase("1")) {
                                    if (!mPlayPauseCheckbool) {
                                        i = 0;
                                        playlists = map.getAdsDetail().getPlaylist();
                                        stopMediaPlayer();
                                        if (playlists.size() > 0) {
                                            mp = MediaPlayer.create(mContext, Uri.parse(playlists.get(i).getAdsAudio()));
                                            if(mp!=null) {
                                                mp.start();
                                                mediatime = mp.getDuration();
                                            }
                                            timer = new Timer();
                                            if (subscribe != null) {
                                                subscribe.stop();
                                                subscribe = null;
                                            }
                                            mPlayPauseCheckbool = true;
                                            if (playlists.size() > 0) playNext();
                                        }
                                    }
                                } else if (map.getAdsDetail().getPauseFlag().equalsIgnoreCase("0")) {
                                    if (mPlayPauseCheckbool) {
                                        mPlayPauseCheckbool = false;
                                        stopMediaPlayer();
                                        if (subscribe == null) {
                                            configRedPro(streamName, serverAddress);
                                        }
                                    }
                                }
                            }
                        }

                        @Override
                        public void onFailure(String message) {
                        }
                    });
                    //      }
                    // });
                }
            }, 0, 5000);
        } else {
            SnackbarUtil.showWarningLongSnackbar(mContext, getResources().getString(R.string.internet_not_connected_text));
        }
    }

    public void stopMediaPlayer() {
        if (mp != null) {
            if (mp.isPlaying())
                mp.stop();
            mp.reset();
            mp.release();
            mp = null;
        }
    }

    public void playNext() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                stopMediaPlayer();
                if ((playlists.size() - 1) == i) {
                    i = 0;
                } else {
                    i = i + 1;
                }
                mp = MediaPlayer.create(mContext, Uri.parse(playlists.get(i).getAdsAudio()));
                mp.start();
                mediatime = mp.getDuration();
                if (subscribe != null) {
                    subscribe.stop();
                }
                if (playlists.size() > i) {

                    playNext();
                }
//                else{
//                    i=0;
//                    playNext1();
//                }
            }
        }, mediatime);
    }

    private void unmountListner(String listenerId) {
        if (ConnectivityReceivers.isConnected()) {
            App.getApiHelper().unmountListenerOnServer(listenerId, new ApiCallBack<Map>() {
                @Override
                public void onSuccess(Map map) {
                    if (map != null) {
                        stopMediaPlayer();
                        if(t!=null){
                            t.cancel();
                        }
                        if (tm != null)
                            tm.cancel();
                        if (timer != null)
                            timer.cancel();
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


    private void broadcasterLeaveDialog() {
        try {
            if (!isFinishing() && mContext != null) {
                stopMediaPlayer();
                /*t.cancel();
                if (tm != null)
                    tm.cancel();
                if (timer != null)
                    timer.cancel();
                tvReconnecting.setVisibility(View.VISIBLE);
                refreshListening();*/

                final Dialog dialog = new Dialog(mContext);
                dialog.setContentView(R.layout.custom_alertdialog_for_listner);
                dialog.setCancelable(false);
                Button ok = dialog.findViewById(R.id.btn_ok_custom);
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        t.cancel();
                        if (tm != null)
                            tm.cancel();
                        if (timer != null)
                            timer.cancel();
                        dialog.dismiss();
                        finish();
                    }
                });
                dialog.show();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void refreshListening() {
        App.getApiHelper().refreshListening(broadcasterId, new ApiCallBack<ReconnectModel>() {
            @Override
            public void onSuccess(ReconnectModel reconnectModel) {
                streamName=reconnectModel.getChannel().getStreamName();
                configRedPro(streamName,serverAddress);
                tvReconnecting.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(String message) {

            }
        });
    }

    /*public static void stopListenAppBackground() {
        if (subscribe != null) {
            subscribe.stop();
            new LiveListeningActivity().unmountListner(listenerId);
            if (!mContext.isFinishing())
                mContext.finish();
        }
    }
*/
    @OnClick(R.id.rl_chat_tile)
    public void onClickChatListen() {
//        SnackbarUtil.showWarningShortSnackbar(mContext, getString(R.string.under_development_message));
        Intent intent = new Intent(mContext, ConversationActivity.class);
        intent.putExtra(ConversationUIService.GROUP_ID, Integer.parseInt(chatChannelId));
        intent.putExtra(ConversationUIService.TAKE_ORDER,true);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        unmountListner(listenerId);
        super.onDestroy();
    }

    /*private void onIncommingCallListen() {
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
    }*/


   /* private void getOpenFacebookIntent() {
        if (ConnectivityReceivers.isConnected()) {
            Intent intent;
            try {
                getPackageManager().getPackageInfo("com.facebook.katana", 0);
                String url = "https://facebook.com/" + userFbId;
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://facewebmodal/f?href=" + url));
            } catch (Exception e) {
                // no Facebook app, revert to browser
                String url = "https://facebook.com/" + userFbId;
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
            }
            this.startActivity(intent);
        }
        else{
            SnackbarUtil.showWarningLongSnackbar(mContext, getResources().getString(R.string.internet_not_connected_text));
        }
    }*/
}
