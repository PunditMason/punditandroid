package com.softuvo.ipundit.activities;

/*
 * Created by Neha Kalia on 12/08/2017.
 */

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.red5pro.streaming.R5Connection;
import com.red5pro.streaming.R5Stream;
import com.red5pro.streaming.R5StreamProtocol;
import com.red5pro.streaming.config.R5Configuration;
import com.softuvo.ipundit.R;
import com.softuvo.ipundit.adapters.LiveFeedsAdapter;
import com.softuvo.ipundit.adapters.ViewPagerAdapter;
import com.softuvo.ipundit.api.ApiCallBack;
import com.softuvo.ipundit.config.ApiConstants;
import com.softuvo.ipundit.config.App;
import com.softuvo.ipundit.config.AppConstant;
import com.softuvo.ipundit.config.AppPreferences;
import com.softuvo.ipundit.fragments.LiveFeedsFragment;
import com.softuvo.ipundit.fragments.LiveFeedsPlayerFragment;
import com.softuvo.ipundit.models.BroadacstersDetailsModel;
import com.softuvo.ipundit.models.FollowCheckModel;
import com.softuvo.ipundit.models.FollowUnfollowModel;
import com.softuvo.ipundit.models.FollowerListModel;
import com.softuvo.ipundit.models.FollowingListModel;
import com.softuvo.ipundit.models.ListnerCountModel;
import com.softuvo.ipundit.models.LiveBroacastersListModel;
import com.softuvo.ipundit.models.LiveFeedsModel;
import com.softuvo.ipundit.models.LiveFeedsNewModel;
import com.softuvo.ipundit.models.MatchListListnerModel;
import com.softuvo.ipundit.models.PlayerDataModel;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.softuvo.ipundit.config.AppConstant.APP_BACKGROUND;

public class LiveListeningActivity extends BaseActivity {
    private static Activity mContext;
    public static R5Stream subscribe;
    private Timer t;
    private static String listenerId;
    private String shareUrl, matchContenstentId, followUnfoloowPath,
            getfollowunfollowpath, status, broadcasterId, broadcasterName, channelId, streamName, serverAddress, groupID, chatChannelId;
    private int followStatus, strMin = 0, strSec = 0, visible = 0;

    @BindView(R.id.rl_live_listening_main)
    CustomRelativeLayout rlLiveListeningMain;

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
        setData();
    }

    private void setData() {
        // getApplication();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        t = new Timer();
        Picasso.with(mContext).load(AppPreferences.init(mContext).getString(APP_BACKGROUND)).into(rlLiveListeningMain);
//        AppPreferences.init(mContext).putString(AppConstant.User_CURRENT_STATE, "2");
        chatChannelId = getIntent().getStringExtra("chatChannelKey");
//        streamingGif.setGifImageResource(R.drawable.listning_gif);
        LiveBroacastersListModel.Channel channel;
        if (ConnectivityReceivers.isConnected()) {
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
                    broadcasterName = channel.getBroadcasterName();
                    txtBroadcasterName.setText("BroadCasting this Game:" + broadcasterName);
                    broadcasterId = channel.getBroadcasterId();
                    channelId = channel.getId();
                    streamName = channel.getStreamName();
                    getRed5ProGroupId();
//                    configRedPro(streamName);
//                    getLiveFeedsFromServer(matchContenstentId);
                    setupViewPager(mViewPager, matchContenstentId);
                    mViewPager.setOffscreenPageLimit(0);
                    mTabLayout.setupWithViewPager(mViewPager);
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
                    broadcasterName = channel.getBroadcasterName();
                    txtBroadcasterName.setText("BroadCasting this Game:" + broadcasterName);
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
                    if (userDatum.getChannelInfo().get(0).getChannel().getMarkImage() != null)
                        Picasso.with(mContext).load(ApiConstants.LEAGUE_IMAGE_BASE_URL + userDatum.getChannelInfo().get(0).getChannel().getMarkImage()).into(ivLeagueLogo);
                    if (userDatum.getChannelInfo().get(0).getChannel() != null) {
                        if (userDatum.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("match")) {
                            channell = (UserDetailsAndMatchDetailsModel.UsersList) getIntent().getSerializableExtra("mUserDatum");
                            matchContenstentId = userDatum.getChannelInfo().get(0).getChannel().getMatchId();
                            tvTeam1Name.setText(userDatum.getChannelInfo().get(0).getMatchInfo().getTeam1Name() + ":");
                            tvTeam2Name.setText(userDatum.getChannelInfo().get(0).getMatchInfo().getTeam2Name() + ":");
                            txtMatchNameTop.setText(userDatum.getChannelInfo().get(0).getMatchInfo().getTeam1Name() + " Vs " + userDatum.getChannelInfo().get(0).getMatchInfo().getTeam2Name());
                            broadcasterName = channell.getChannelInfo().get(0).getChannel().getBroadcasterName();
                            txtBroadcasterName.setText("BroadCasting this Game:" + broadcasterName);
                            broadcasterId = channell.getChannelInfo().get(0).getChannel().getBroadcasterId();
                            channelId = channell.getChannelInfo().get(0).getChannel().getId();
                            streamName = channell.getChannelInfo().get(0).getChannel().getStreamName();
                            getRed5ProGroupId();
//                            configRedPro(streamName);
//                            getLiveFeedsFromServer(matchContenstentId);
                            setupViewPager(mViewPager, matchContenstentId);
                            mViewPager.setOffscreenPageLimit(0);
                            mTabLayout.setupWithViewPager(mViewPager);

                        } else if (userDatum.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("team")) {
                            channell = (UserDetailsAndMatchDetailsModel.UsersList) getIntent().getSerializableExtra("mUserDatum");
                            matchContenstentId = userDatum.getChannelInfo().get(0).getChannel().getMatchId();
                            txtMatchNameTop.setText(userDatum.getChannelInfo().get(0).getTeamInfo().getContestantName());
                            txtMatchTimeTop.setText("Rank:" + userDatum.getChannelInfo().get(0).getTeamInfo().getRank());
                            broadcasterName = userDatum.getChannelInfo().get(0).getChannel().getBroadcasterName();
                            txtBroadcasterName.setText("BroadCasting this Game:" + broadcasterName);
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
                    if (userDatum.getChannelInfo().get(0).getChannel().getMarkImage() != null)
                        Picasso.with(mContext).load(ApiConstants.LEAGUE_IMAGE_BASE_URL + userDatum.getChannelInfo().get(0).getChannel().getMarkImage()).into(ivLeagueLogo);
                    if (userDatum.getChannelInfo().get(0).getChannel() != null) {
                        if (userDatum.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("match")) {
                            channel = (LiveBroacastersListModel.Channel) getIntent().getSerializableExtra("mBrListDatum");
                            matchContenstentId = userDatum.getChannelInfo().get(0).getChannel().getMatchId();
                            tvTeam1Name.setText(userDatum.getChannelInfo().get(0).getMatchInfo().getTeam1Name() + ":");
                            tvTeam2Name.setText(userDatum.getChannelInfo().get(0).getMatchInfo().getTeam2Name() + ":");
                            txtMatchNameTop.setText(userDatum.getChannelInfo().get(0).getMatchInfo().getTeam1Name() + " Vs " + userDatum.getChannelInfo().get(0).getMatchInfo().getTeam2Name());
                            broadcasterName = channel.getBroadcasterName();
                            txtBroadcasterName.setText("BroadCasting this Game:" + broadcasterName);
                            broadcasterId = channel.getBroadcasterId();
                            channelId = channel.getId();
                            streamName = channel.getStreamName();
                            getRed5ProGroupId();
//                            configRedPro(streamName);
//                            getLiveFeedsFromServer(matchContenstentId);
                            setupViewPager(mViewPager, matchContenstentId);
                            mViewPager.setOffscreenPageLimit(0);
                            mTabLayout.setupWithViewPager(mViewPager);
                        } else if (userDatum.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("team")) {
                            channel = (LiveBroacastersListModel.Channel) getIntent().getSerializableExtra("mBrListDatum");
                            matchContenstentId = userDatum.getChannelInfo().get(0).getChannel().getMatchId();
                            txtMatchNameTop.setText(userDatum.getChannelInfo().get(0).getTeamInfo().getContestantName());
                            txtMatchTimeTop.setText("Rank:" + userDatum.getChannelInfo().get(0).getTeamInfo().getRank());
                            broadcasterName = channel.getBroadcasterName();
                            txtBroadcasterName.setText("BroadCasting this Game:" + broadcasterName);
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
                    if (userDatum.getChannelInfo().get(0).getChannel().getMarkImage() != null)
                        Picasso.with(mContext).load(ApiConstants.LEAGUE_IMAGE_BASE_URL + userDatum.getChannelInfo().get(0).getChannel().getMarkImage()).into(ivLeagueLogo);
                    if (userDatum.getChannelInfo().get(0).getChannel() != null) {
                        if (userDatum.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("match")) {
                            channell = (UserSearchSportsModel.UserDatum) getIntent().getSerializableExtra("mUserDatum");
                            matchContenstentId = userDatum.getChannelInfo().get(0).getChannel().getMatchId();
                            tvTeam1Name.setText(userDatum.getChannelInfo().get(0).getMatchInfo().getTeam1Name() + ":");
                            tvTeam2Name.setText(userDatum.getChannelInfo().get(0).getMatchInfo().getTeam2Name() + ":");
                            txtMatchNameTop.setText(userDatum.getChannelInfo().get(0).getMatchInfo().getTeam1Name() + " Vs " + userDatum.getChannelInfo().get(0).getMatchInfo().getTeam2Name());
                            broadcasterName = channell.getChannelInfo().get(0).getChannel().getBroadcasterName();
                            txtBroadcasterName.setText("BroadCasting this Game:" + broadcasterName);
                            broadcasterId = channell.getChannelInfo().get(0).getChannel().getBroadcasterId();
                            channelId = channell.getChannelInfo().get(0).getChannel().getId();
                            streamName = channell.getChannelInfo().get(0).getChannel().getStreamName();
                            getRed5ProGroupId();
//                            configRedPro(streamName);
//                            getLiveFeedsFromServer(matchContenstentId);
                            setupViewPager(mViewPager, matchContenstentId);
                            mViewPager.setOffscreenPageLimit(0);
                            mTabLayout.setupWithViewPager(mViewPager);
                        } else if (userDatum.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("team")) {
                            channell = (UserSearchSportsModel.UserDatum) getIntent().getSerializableExtra("mUserDatum");
                            matchContenstentId = userDatum.getChannelInfo().get(0).getChannel().getMatchId();
                            txtMatchNameTop.setText(userDatum.getChannelInfo().get(0).getTeamInfo().getContestantName());
                            txtMatchTimeTop.setText("Rank:" + userDatum.getChannelInfo().get(0).getTeamInfo().getRank());
                            broadcasterName = userDatum.getChannelInfo().get(0).getChannel().getBroadcasterName();
                            txtBroadcasterName.setText("BroadCasting this Game:" + broadcasterName);
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
                    if (userDatum.getChannelInfo().get(0).getChannel().getMarkImage() != null)
                        Picasso.with(mContext).load(ApiConstants.LEAGUE_IMAGE_BASE_URL + userDatum.getChannelInfo().get(0).getChannel().getMarkImage()).into(ivLeagueLogo);
                    if (userDatum.getChannelInfo().get(0).getChannel() != null) {
                        if (userDatum.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("match")) {
                            channel = (LiveBroacastersListModel.Channel) getIntent().getSerializableExtra("mBrListDatum");
                            matchContenstentId = userDatum.getChannelInfo().get(0).getChannel().getMatchId();
                            tvTeam1Name.setText(userDatum.getChannelInfo().get(0).getMatchInfo().getTeam1Name() + ":");
                            tvTeam2Name.setText(userDatum.getChannelInfo().get(0).getMatchInfo().getTeam2Name() + ":");
                            txtMatchNameTop.setText(userDatum.getChannelInfo().get(0).getMatchInfo().getTeam1Name() + " Vs " + userDatum.getChannelInfo().get(0).getMatchInfo().getTeam2Name());
                            broadcasterName = channel.getBroadcasterName();
                            txtBroadcasterName.setText("BroadCasting this Game:" + broadcasterName);
                            broadcasterId = channel.getBroadcasterId();
                            channelId = channel.getId();
                            streamName = channel.getStreamName();
                            getRed5ProGroupId();
//                            configRedPro(streamName);
//                            getLiveFeedsFromServer(matchContenstentId);
                            setupViewPager(mViewPager, matchContenstentId);
                            mViewPager.setOffscreenPageLimit(0);
                            mTabLayout.setupWithViewPager(mViewPager);
                        } else if (userDatum.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("team")) {
                            channel = (LiveBroacastersListModel.Channel) getIntent().getSerializableExtra("mBrListDatum");
                            matchContenstentId = userDatum.getChannelInfo().get(0).getChannel().getMatchId();
                            txtMatchNameTop.setText(userDatum.getChannelInfo().get(0).getTeamInfo().getContestantName());
                            txtMatchTimeTop.setText("Rank:" + userDatum.getChannelInfo().get(0).getTeamInfo().getRank());
                            broadcasterName = channel.getBroadcasterName();
                            txtBroadcasterName.setText("BroadCasting this Game:" + broadcasterName);
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
                    if (teamDatum.getMarkImage() != null)
                        Picasso.with(mContext).load(ApiConstants.LEAGUE_IMAGE_BASE_URL + teamDatum.getMarkImage()).into(ivLeagueLogo);
                    if (teamDatum.getChannelInfo() != null) {
                        {
                            channel = (LiveBroacastersListModel.Channel) getIntent().getSerializableExtra("mBrListDatum");
                            matchContenstentId = channel.getMatchId();
                            txtMatchNameTop.setText(channel.getBroadcasterName());
                            txtMatchTimeTop.setText("Rank:" + teamDatum.getRank());
                            broadcasterName = channel.getBroadcasterName();
                            txtBroadcasterName.setText("BroadCasting this Game:" + broadcasterName);
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
                    if (userDatum.getChannelInfo().get(0).getChannel().getMarkImage() != null)
                        Picasso.with(mContext).load(ApiConstants.LEAGUE_IMAGE_BASE_URL + userDatum.getChannelInfo().get(0).getChannel().getMarkImage()).into(ivLeagueLogo);
                    if (userDatum.getChannelInfo().get(0).getChannel() != null) {
                        if (userDatum.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("match")) {
                            channell = (UserSearchLeagueModel.Datum) getIntent().getSerializableExtra("mUserDatum");
                            matchContenstentId = userDatum.getChannelInfo().get(0).getChannel().getMatchId();
                            tvTeam1Name.setText(userDatum.getChannelInfo().get(0).getMatchInfo().getTeam1Name() + ":");
                            tvTeam2Name.setText(userDatum.getChannelInfo().get(0).getMatchInfo().getTeam2Name() + ":");
                            txtMatchNameTop.setText(userDatum.getChannelInfo().get(0).getMatchInfo().getTeam1Name() + " Vs " + userDatum.getChannelInfo().get(0).getMatchInfo().getTeam2Name());
                            broadcasterName = channell.getChannelInfo().get(0).getChannel().getBroadcasterName();
                            txtBroadcasterName.setText("BroadCasting this Game:" + broadcasterName);
                            broadcasterId = channell.getChannelInfo().get(0).getChannel().getBroadcasterId();
                            channelId = channell.getChannelInfo().get(0).getChannel().getId();
                            streamName = channell.getChannelInfo().get(0).getChannel().getStreamName();
                            getRed5ProGroupId();
//                            configRedPro(streamName);
//                            getLiveFeedsFromServer(matchContenstentId);
                            setupViewPager(mViewPager, matchContenstentId);
                            mViewPager.setOffscreenPageLimit(0);
                            mTabLayout.setupWithViewPager(mViewPager);
                        } else if (userDatum.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("team")) {
                            channell = (UserSearchLeagueModel.Datum) getIntent().getSerializableExtra("mUserDatum");
                            matchContenstentId = userDatum.getChannelInfo().get(0).getChannel().getMatchId();
                            txtMatchNameTop.setText(userDatum.getChannelInfo().get(0).getTeamInfo().getContestantName());
                            txtMatchTimeTop.setText("Rank:" + userDatum.getChannelInfo().get(0).getTeamInfo().getRank());
                            broadcasterName = userDatum.getChannelInfo().get(0).getChannel().getBroadcasterName();
                            txtBroadcasterName.setText("BroadCasting this Game:" + broadcasterName);
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
                    if (userDatum.getChannelInfo().get(0).getChannel().getMarkImage() != null)
                        Picasso.with(mContext).load(ApiConstants.LEAGUE_IMAGE_BASE_URL + userDatum.getChannelInfo().get(0).getChannel().getMarkImage()).into(ivLeagueLogo);
                    if (userDatum.getChannelInfo().get(0).getChannel() != null) {
                        if (userDatum.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("match")) {
                            channell = (FollowerListModel.Follwer) getIntent().getSerializableExtra("mUserDatum");
                            matchContenstentId = userDatum.getChannelInfo().get(0).getChannel().getMatchId();
                            tvTeam1Name.setText(userDatum.getChannelInfo().get(0).getMatchInfo().getTeam1Name() + ":");
                            tvTeam2Name.setText(userDatum.getChannelInfo().get(0).getMatchInfo().getTeam2Name() + ":");
                            txtMatchNameTop.setText(userDatum.getChannelInfo().get(0).getMatchInfo().getTeam1Name() + " Vs " + userDatum.getChannelInfo().get(0).getMatchInfo().getTeam2Name());
                            broadcasterName = channell.getChannelInfo().get(0).getChannel().getBroadcasterName();
                            txtBroadcasterName.setText("BroadCasting this Game:" + broadcasterName);
                            broadcasterId = channell.getChannelInfo().get(0).getChannel().getBroadcasterId();
                            channelId = channell.getChannelInfo().get(0).getChannel().getId();
                            streamName = channell.getChannelInfo().get(0).getChannel().getStreamName();
                            getRed5ProGroupId();
//                            configRedPro(streamName);
//                            getLiveFeedsFromServer(matchContenstentId);
                            setupViewPager(mViewPager, matchContenstentId);
                            mViewPager.setOffscreenPageLimit(0);
                            mTabLayout.setupWithViewPager(mViewPager);
                        } else if (userDatum.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("team")) {
                            channell = (FollowerListModel.Follwer) getIntent().getSerializableExtra("mUserDatum");
                            matchContenstentId = userDatum.getChannelInfo().get(0).getChannel().getMatchId();
                            txtMatchNameTop.setText(userDatum.getChannelInfo().get(0).getTeamInfo().getContestantName());
                            txtMatchTimeTop.setText("Rank:" + userDatum.getChannelInfo().get(0).getTeamInfo().getRank());
                            broadcasterName = userDatum.getChannelInfo().get(0).getChannel().getBroadcasterName();
                            txtBroadcasterName.setText("BroadCasting this Game:" + broadcasterName);
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
            }else if (getIntent().getStringExtra("userComingFrom").equalsIgnoreCase("followerListSwitch")) {
                if (getIntent().getSerializableExtra("mUserDatum") != null) {
                    FollowerListModel.Follwer channell;
                    FollowerListModel.Follwer userDatum = (FollowerListModel.Follwer) getIntent().getSerializableExtra("mUserDatum");
                    if (userDatum.getChannelInfo().get(0).getChannel().getMarkImage() != null)
                        Picasso.with(mContext).load(ApiConstants.LEAGUE_IMAGE_BASE_URL + userDatum.getChannelInfo().get(0).getChannel().getMarkImage()).into(ivLeagueLogo);
                    if (userDatum.getChannelInfo().get(0).getChannel() != null) {
                        if (userDatum.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("match")) {
                            channell = (FollowerListModel.Follwer) getIntent().getSerializableExtra("mUserDatum");
                            matchContenstentId = userDatum.getChannelInfo().get(0).getChannel().getMatchId();
                            tvTeam1Name.setText(userDatum.getChannelInfo().get(0).getMatchInfo().getTeam1Name() + ":");
                            tvTeam2Name.setText(userDatum.getChannelInfo().get(0).getMatchInfo().getTeam2Name() + ":");
                            txtMatchNameTop.setText(userDatum.getChannelInfo().get(0).getMatchInfo().getTeam1Name() + " Vs " + userDatum.getChannelInfo().get(0).getMatchInfo().getTeam2Name());
                            broadcasterName = channell.getChannelInfo().get(0).getChannel().getBroadcasterName();
                            txtBroadcasterName.setText("BroadCasting this Game:" + broadcasterName);
                            broadcasterId = channell.getChannelInfo().get(0).getChannel().getBroadcasterId();
                            channelId = channell.getChannelInfo().get(0).getChannel().getId();
                            streamName = channell.getChannelInfo().get(0).getChannel().getStreamName();
                            getRed5ProGroupId();
//                            configRedPro(streamName);
//                            getLiveFeedsFromServer(matchContenstentId);
                            setupViewPager(mViewPager, matchContenstentId);
                            mViewPager.setOffscreenPageLimit(0);
                            mTabLayout.setupWithViewPager(mViewPager);
                        } else if (userDatum.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("team")) {
                            channell = (FollowerListModel.Follwer) getIntent().getSerializableExtra("mUserDatum");
                            matchContenstentId = userDatum.getChannelInfo().get(0).getChannel().getMatchId();
                            txtMatchNameTop.setText(userDatum.getChannelInfo().get(0).getTeamInfo().getContestantName());
                            txtMatchTimeTop.setText("Rank:" + userDatum.getChannelInfo().get(0).getTeamInfo().getRank());
                            broadcasterName = userDatum.getChannelInfo().get(0).getChannel().getBroadcasterName();
                            txtBroadcasterName.setText("BroadCasting this Game:" + broadcasterName);
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
            }else if (getIntent().getStringExtra("userComingFrom").equalsIgnoreCase("followingList")) {
                if (getIntent().getSerializableExtra("mUserDatum") != null) {
                    FollowingListModel.Following channell;
                    FollowingListModel.Following userDatum = (FollowingListModel.Following) getIntent().getSerializableExtra("mUserDatum");
                    if (userDatum.getChannelInfo().get(0).getChannel().getMarkImage() != null)
                        Picasso.with(mContext).load(ApiConstants.LEAGUE_IMAGE_BASE_URL + userDatum.getChannelInfo().get(0).getChannel().getMarkImage()).into(ivLeagueLogo);
                    if (userDatum.getChannelInfo().get(0).getChannel() != null) {
                        if (userDatum.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("match")) {
                            channell = (FollowingListModel.Following) getIntent().getSerializableExtra("mUserDatum");
                            matchContenstentId = userDatum.getChannelInfo().get(0).getChannel().getMatchId();
                            tvTeam1Name.setText(userDatum.getChannelInfo().get(0).getMatchInfo().getTeam1Name() + ":");
                            tvTeam2Name.setText(userDatum.getChannelInfo().get(0).getMatchInfo().getTeam2Name() + ":");
                            txtMatchNameTop.setText(userDatum.getChannelInfo().get(0).getMatchInfo().getTeam1Name() + " Vs " + userDatum.getChannelInfo().get(0).getMatchInfo().getTeam2Name());
                            broadcasterName = channell.getChannelInfo().get(0).getChannel().getBroadcasterName();
                            txtBroadcasterName.setText("BroadCasting this Game:" + broadcasterName);
                            broadcasterId = channell.getChannelInfo().get(0).getChannel().getBroadcasterId();
                            channelId = channell.getChannelInfo().get(0).getChannel().getId();
                            streamName = channell.getChannelInfo().get(0).getChannel().getStreamName();
                            getRed5ProGroupId();
//                            configRedPro(streamName);
//                            getLiveFeedsFromServer(matchContenstentId);
                            setupViewPager(mViewPager, matchContenstentId);
                            mViewPager.setOffscreenPageLimit(0);
                            mTabLayout.setupWithViewPager(mViewPager);
                        } else if (userDatum.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("team")) {
                            channell = (FollowingListModel.Following) getIntent().getSerializableExtra("mUserDatum");
                            matchContenstentId = userDatum.getChannelInfo().get(0).getChannel().getMatchId();
                            txtMatchNameTop.setText(userDatum.getChannelInfo().get(0).getTeamInfo().getContestantName());
                            txtMatchTimeTop.setText("Rank:" + userDatum.getChannelInfo().get(0).getTeamInfo().getRank());
                            broadcasterName = userDatum.getChannelInfo().get(0).getChannel().getBroadcasterName();
                            txtBroadcasterName.setText("BroadCasting this Game:" + broadcasterName);
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
            }else if (getIntent().getStringExtra("userComingFrom").equalsIgnoreCase("followingListSwitch")) {
                if (getIntent().getSerializableExtra("mUserDatum") != null) {
                    FollowingListModel.Following channell;
                    FollowingListModel.Following userDatum = (FollowingListModel.Following) getIntent().getSerializableExtra("mUserDatum");
                    if (userDatum.getChannelInfo().get(0).getChannel().getMarkImage() != null)
                        Picasso.with(mContext).load(ApiConstants.LEAGUE_IMAGE_BASE_URL + userDatum.getChannelInfo().get(0).getChannel().getMarkImage()).into(ivLeagueLogo);
                    if (userDatum.getChannelInfo().get(0).getChannel() != null) {
                        if (userDatum.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("match")) {
                            channell = (FollowingListModel.Following) getIntent().getSerializableExtra("mUserDatum");
                            matchContenstentId = userDatum.getChannelInfo().get(0).getChannel().getMatchId();
                            tvTeam1Name.setText(userDatum.getChannelInfo().get(0).getMatchInfo().getTeam1Name() + ":");
                            tvTeam2Name.setText(userDatum.getChannelInfo().get(0).getMatchInfo().getTeam2Name() + ":");
                            txtMatchNameTop.setText(userDatum.getChannelInfo().get(0).getMatchInfo().getTeam1Name() + " Vs " + userDatum.getChannelInfo().get(0).getMatchInfo().getTeam2Name());
                            broadcasterName = channell.getChannelInfo().get(0).getChannel().getBroadcasterName();
                            txtBroadcasterName.setText("BroadCasting this Game:" + broadcasterName);
                            broadcasterId = channell.getChannelInfo().get(0).getChannel().getBroadcasterId();
                            channelId = channell.getChannelInfo().get(0).getChannel().getId();
                            streamName = channell.getChannelInfo().get(0).getChannel().getStreamName();
                            getRed5ProGroupId();
//                            configRedPro(streamName);
//                            getLiveFeedsFromServer(matchContenstentId);
                            setupViewPager(mViewPager, matchContenstentId);
                            mViewPager.setOffscreenPageLimit(0);
                            mTabLayout.setupWithViewPager(mViewPager);
                        } else if (userDatum.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("team")) {
                            channell = (FollowingListModel.Following) getIntent().getSerializableExtra("mUserDatum");
                            matchContenstentId = userDatum.getChannelInfo().get(0).getChannel().getMatchId();
                            txtMatchNameTop.setText(userDatum.getChannelInfo().get(0).getTeamInfo().getContestantName());
                            txtMatchTimeTop.setText("Rank:" + userDatum.getChannelInfo().get(0).getTeamInfo().getRank());
                            broadcasterName = userDatum.getChannelInfo().get(0).getChannel().getBroadcasterName();
                            txtBroadcasterName.setText("BroadCasting this Game:" + broadcasterName);
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
            }else if (getIntent().getStringExtra("userComingFrom").equalsIgnoreCase("leaguesUserSearchSwitch")) {
                if (getIntent().getSerializableExtra("mUserDatum") != null) {
                    UserSearchLeagueModel.Datum userDatum = (UserSearchLeagueModel.Datum) getIntent().getSerializableExtra("mUserDatum");
                    if (userDatum.getChannelInfo().get(0).getChannel().getMarkImage() != null)
                        Picasso.with(mContext).load(ApiConstants.LEAGUE_IMAGE_BASE_URL + userDatum.getChannelInfo().get(0).getChannel().getMarkImage()).into(ivLeagueLogo);
                    if (userDatum.getChannelInfo().get(0).getChannel() != null) {
                        if (userDatum.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("match")) {
                            channel = (LiveBroacastersListModel.Channel) getIntent().getSerializableExtra("mBrListDatum");
                            matchContenstentId = userDatum.getChannelInfo().get(0).getChannel().getMatchId();
                            tvTeam1Name.setText(userDatum.getChannelInfo().get(0).getMatchInfo().getTeam1Name() + ":");
                            tvTeam2Name.setText(userDatum.getChannelInfo().get(0).getMatchInfo().getTeam2Name() + ":");
                            txtMatchNameTop.setText(userDatum.getChannelInfo().get(0).getMatchInfo().getTeam1Name() + " Vs " + userDatum.getChannelInfo().get(0).getMatchInfo().getTeam2Name());
                            broadcasterName = channel.getBroadcasterName();
                            txtBroadcasterName.setText("BroadCasting this Game:" + broadcasterName);
                            broadcasterId = channel.getBroadcasterId();
                            channelId = channel.getId();
                            streamName = channel.getStreamName();
                            getRed5ProGroupId();
//                            configRedPro(streamName);
//                            getLiveFeedsFromServer(matchContenstentId);
                            setupViewPager(mViewPager, matchContenstentId);
                            mViewPager.setOffscreenPageLimit(0);
                            mTabLayout.setupWithViewPager(mViewPager);
                        } else if (userDatum.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("team")) {
                            channel = (LiveBroacastersListModel.Channel) getIntent().getSerializableExtra("mBrListDatum");
                            matchContenstentId = userDatum.getChannelInfo().get(0).getChannel().getMatchId();
                            txtMatchNameTop.setText(userDatum.getChannelInfo().get(0).getTeamInfo().getContestantName());
                            txtMatchTimeTop.setText("Rank:" + userDatum.getChannelInfo().get(0).getTeamInfo().getRank());
                            broadcasterName = channel.getBroadcasterName();
                            txtBroadcasterName.setText("BroadCasting this Game:" + broadcasterName);
                            tvTeam1Name.setText(userDatum.getChannelInfo().get(0).getTeamInfo().getContestantClubName());
                            tvTeam2Name.setText("Points: " + userDatum.getChannelInfo().get(0).getTeamInfo().getPoints());
                            broadcasterId = channel.getBroadcasterId();
                            channelId = channel.getId();
                            tvNoData.setVisibility(View.VISIBLE);
                            tvNoData.setText(R.string.team_talk);
                            streamName = channel.getStreamName();
                            getRed5ProGroupId();
//                            configRedPro(streamName);
                        }
                    }
                }
            } else if (getIntent().getStringExtra("userComingFrom").equalsIgnoreCase("leaguesTeamSearch")) {
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
                            broadcasterName = channel.getBroadcasterName();
                            txtBroadcasterName.setText("BroadCasting this Game:" + broadcasterName);
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
                    if (AppPreferences.init(mContext).getString(AppConstant.LEAGUE_IMAGE_URL) != null)
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
                    mTabLayout.setupWithViewPager(mViewPager);
                }
            }
            if (!(getIntent().getStringExtra("userComingFrom").equalsIgnoreCase("matchListNoBroadcast"))) {
                getBroadcastersDetails(matchContenstentId);
            }
            getfollowunfollowpath = broadcasterId + "/" + AppPreferences.init(mContext).getString(AppConstant.USER_ID) + "/" + channelId;
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

    public void showAlert(){
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
                        }
                        else if (getIntent().getStringExtra("userComingFrom").equalsIgnoreCase("punditsSwitch")) {
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
                        }else if (getIntent().getStringExtra("userComingFrom").equalsIgnoreCase("followingList")) {
                            Intent intent = new Intent(mContext, LiveBroadcastersListActivity.class);
                            intent.putExtra("userComingFrom", "followingList");
                            intent.putExtra("matchidcontestentid", matchContenstentId);
                            intent.putExtra("chatChannelKey", getIntent().getStringExtra("chatChannelKey"));
                            intent.putExtra("mUserDatum", getIntent().getSerializableExtra("mUserDatum"));
                            startActivity(intent);
                            finish();
                        }
                        else if (getIntent().getStringExtra("userComingFrom").equalsIgnoreCase("followingListSwitch")) {
                            Intent intent = new Intent(mContext, LiveBroadcastersListActivity.class);
                            intent.putExtra("userComingFrom", "followingListSwitch");
                            intent.putExtra("matchidcontestentid", matchContenstentId);
                            intent.putExtra("mUserDatum", getIntent().getSerializableExtra("mUserDatum"));
                            intent.putExtra("chatChannelKey", getIntent().getStringExtra("chatChannelKey"));
                            startActivity(intent);
                            finish();
                        }
                        else if (getIntent().getStringExtra("userComingFrom").equalsIgnoreCase("followerListSwitch")) {
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
            shareUrl = ApiConstants.SHARE_BASE_URL + username + "-" + encodedBroadcastersId;
//            AppPreferences.init(mContext).putString(AppConstant.User_CURRENT_STATE, "3");
            if (getIntent().getStringExtra("userComingFrom") != null) {
                if (getIntent().getStringExtra("userComingFrom").equalsIgnoreCase("matchList")) {
                    if (getIntent().getSerializableExtra("mMatchDatum") != null) {
                        MatchListListnerModel.ListenMatchList matchDatum = (MatchListListnerModel.ListenMatchList) getIntent().getSerializableExtra("mMatchDatum");
                        status = "I'm live on Pundit now listening " + matchDatum.getTeam1Name() + " Vs " + matchDatum.getTeam2Name() + ", come join me.";
                    }
                } else if (getIntent().getStringExtra("userComingFrom").equalsIgnoreCase("matchStandingListenList")) {
                    if (getIntent().getSerializableExtra("mMatchDatum") != null) {
                        StandingListeningModel.Datum matchDatum = (StandingListeningModel.Datum) getIntent().getSerializableExtra("mMatchDatum");
                        status = "I'm live on Pundit now listening " + matchDatum.getContestantClubName() + ", come join me.";

                    }
                } else if (getIntent().getStringExtra("userComingFrom").equalsIgnoreCase("pundits")) {
                    if (getIntent().getSerializableExtra("mUserDatum") != null) {
                        UserDetailsAndMatchDetailsModel.UsersList userDatum = (UserDetailsAndMatchDetailsModel.UsersList) getIntent().getSerializableExtra("mUserDatum");
                        if (userDatum.getChannelInfo().get(0).getChannel() != null) {
                            if (userDatum.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("match")) {
                                status = "I'm live on Pundit now listening " + userDatum.getChannelInfo().get(0).getMatchInfo().getTeam1Name() + " Vs " + userDatum.getChannelInfo().get(0).getMatchInfo().getTeam2Name() + ", come join me.";

                            } else if (userDatum.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("team")) {
                                status = "I'm live on Pundit now listening " + userDatum.getChannelInfo().get(0).getTeamInfo().getContestantClubName() + ", come join me.";

                            }
                        }
                    }
                } else if (getIntent().getStringExtra("userComingFrom").equalsIgnoreCase("punditsSwitch")) {
                    if (getIntent().getSerializableExtra("mUserDatum") != null) {
                        UserDetailsAndMatchDetailsModel.UsersList userDatum = (UserDetailsAndMatchDetailsModel.UsersList) getIntent().getSerializableExtra("mUserDatum");
                        if (userDatum.getChannelInfo().get(0).getChannel() != null) {
                            if (userDatum.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("match")) {
                                status = "I'm live on Pundit now listening " + userDatum.getChannelInfo().get(0).getMatchInfo().getTeam1Name() + " Vs " + userDatum.getChannelInfo().get(0).getMatchInfo().getTeam2Name() + ", come join me.";

                            } else if (userDatum.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("team")) {
                                status = "I'm live on Pundit now listening " + userDatum.getChannelInfo().get(0).getTeamInfo().getContestantClubName() + ", come join me.";

                            }
                        }
                    }
                } else if (getIntent().getStringExtra("userComingFrom").equalsIgnoreCase("sprotsUserSearch")) {
                    if (getIntent().getSerializableExtra("mUserDatum") != null) {
                        UserSearchSportsModel.UserDatum userDatum = (UserSearchSportsModel.UserDatum) getIntent().getSerializableExtra("mUserDatum");
                        if (userDatum.getChannelInfo().get(0).getChannel() != null) {
                            if (userDatum.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("match")) {
                                status = "I'm live on Pundit now listening " + userDatum.getChannelInfo().get(0).getMatchInfo().getTeam1Name() + " Vs " + userDatum.getChannelInfo().get(0).getMatchInfo().getTeam2Name() + ", come join me.";
                            } else if (userDatum.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("team")) {
                                status = "I'm live on Pundit now listening " + userDatum.getChannelInfo().get(0).getTeamInfo().getContestantClubName() + ", come join me.";

                            }
                        }
                    }
                } else if (getIntent().getStringExtra("userComingFrom").equalsIgnoreCase("sprotsUserSearchSwitch")) {
                    if (getIntent().getSerializableExtra("mUserDatum") != null) {
                        UserSearchSportsModel.UserDatum userDatum = (UserSearchSportsModel.UserDatum) getIntent().getSerializableExtra("mUserDatum");
                        if (userDatum.getChannelInfo().get(0).getChannel() != null) {
                            if (userDatum.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("match")) {
                                status = "I'm live on Pundit now listening " + userDatum.getChannelInfo().get(0).getMatchInfo().getTeam1Name() + " Vs " + userDatum.getChannelInfo().get(0).getMatchInfo().getTeam2Name() + ", come join me.";
                            } else if (userDatum.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("team")) {
                                status = "I'm live on Pundit now listening " + userDatum.getChannelInfo().get(0).getTeamInfo().getContestantClubName() + ", come join me.";

                            }
                        }
                    }
                } else if (getIntent().getStringExtra("userComingFrom").equalsIgnoreCase("leaguesUserSearch")) {
                    if (getIntent().getSerializableExtra("mUserDatum") != null) {
                        UserSearchLeagueModel.Datum userDatum = (UserSearchLeagueModel.Datum) getIntent().getSerializableExtra("mUserDatum");
                        if (userDatum.getChannelInfo().get(0).getChannel() != null) {
                            if (userDatum.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("match")) {
                                status = "I'm live on Pundit now listening " + userDatum.getChannelInfo().get(0).getMatchInfo().getTeam1Name() + " Vs " + userDatum.getChannelInfo().get(0).getMatchInfo().getTeam2Name() + ", come join me.";

                            } else if (userDatum.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("team")) {
                                status = "I'm live on Pundit now listening " + userDatum.getChannelInfo().get(0).getTeamInfo().getContestantClubName() + ", come join me.";

                            }
                        }
                    }
                } else if (getIntent().getStringExtra("userComingFrom").equalsIgnoreCase("leaguesUserSearchSwitch")) {
                    if (getIntent().getSerializableExtra("mUserDatum") != null) {
                        UserSearchLeagueModel.Datum userDatum = (UserSearchLeagueModel.Datum) getIntent().getSerializableExtra("mUserDatum");
                        if (userDatum.getChannelInfo().get(0).getChannel() != null) {
                            if (userDatum.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("match")) {
                                status = "I'm live on Pundit now listening " + userDatum.getChannelInfo().get(0).getMatchInfo().getTeam1Name() + " Vs " + userDatum.getChannelInfo().get(0).getMatchInfo().getTeam2Name() + ", come join me.";

                            } else if (userDatum.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("team")) {
                                status = "I'm live on Pundit now listening " + userDatum.getChannelInfo().get(0).getTeamInfo().getContestantClubName() + ", come join me.";

                            }
                        }
                    }
                } else if (getIntent().getStringExtra("userComingFrom").equalsIgnoreCase("sprotsTeamSearch")) {
                    if (getIntent().getSerializableExtra("mTeamSearchDatum") != null) {
                        TeamSearchSportsModel.Datum teamDatum = (TeamSearchSportsModel.Datum) getIntent().getSerializableExtra("mTeamSearchDatum");
                        status = "I'm live on Pundit now listening " + teamDatum.getContestantClubName() + ", come join me.";

                    }
                } else if (getIntent().getStringExtra("userComingFrom").equalsIgnoreCase("leaguesTeamSearch")) {
                    if (getIntent().getSerializableExtra("mTeamSearchDatum") != null) {
                        TeamSearchSportsModel.Datum teamDatum = (TeamSearchSportsModel.Datum) getIntent().getSerializableExtra("mTeamSearchDatum");
                        status = "I'm live on Pundit now listening " + teamDatum.getContestantClubName() + ", come join me.";

                    }
                }
            }
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
          /*  i.putExtra(Intent.EXTRA_SUBJECT, "Pundit");
            i.putExtra(Intent.EXTRA_TEXT, shareUrl + "\n" + status);
            startActivity(Intent.createChooser(i, "Share Via...."));*/
            PackageManager pm = getApplication().getPackageManager();
            List<ResolveInfo> activityList = pm.queryIntentActivities(i, 0);
            for(final ResolveInfo app : activityList) {
                if("com.facebook.katana.ShareLinkActivity".equals(app.activityInfo.name)) {
                    ShareDialog shareDialog;
                    shareDialog = new ShareDialog(mContext);
                    ShareLinkContent content = new ShareLinkContent.Builder()
                            .setContentUrl(Uri.parse(shareUrl))
                            .setQuote(status)
                            .build();
                    shareDialog.show(content);
                    break;
                } else {
                    i.putExtra(Intent.EXTRA_SUBJECT, "Pundit");
                    i.putExtra(Intent.EXTRA_TEXT, status + "\n" + shareUrl);
                    startActivity(Intent.createChooser(i, "Share"));
                    break;
                }
            }
        }

    }

    @OnClick(R.id.btn_follow_unfollow)
    public void buttonFollowUnfollow() {
        if (AppPreferences.init(mContext).getString(AppConstant.USER_ID).equalsIgnoreCase(broadcasterId)) {
            SnackbarUtil.showWarningShortSnackbar(mContext, getString(R.string.self_follow_text));
            btnFollowUnfollow.setClickable(false);
        } else {
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
        setkickofftime();
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
                        txtkickoffTime.setText(String.valueOf(strMin) + ":" + String.valueOf(strSec));
                        strSec += 1;
                        if (strSec == 59) {
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
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onFailure(String message) {
                                }
                            });
                        } else {
                            SnackbarUtil.showWarningLongSnackbar(mContext, getResources().getString(R.string.internet_not_connected_text));
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
        try {
            if (!isFinishing() && mContext != null) {
                final Dialog dialog = new Dialog(mContext);
                dialog.setContentView(R.layout.custom_alertdialog_for_listner);
                dialog.setCancelable(false);
                Button ok = dialog.findViewById(R.id.btn_ok_custom);
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        t.cancel();
                        AppPreferences.init(mContext).putString(AppConstant.LIVE, "0");
                        dialog.dismiss();
                        finish();
                    }
                });
                dialog.show();

            }
        }catch (Exception e){e.printStackTrace();}
    }

    public static void stopListenAppBackground() {
        if (subscribe != null) {
            subscribe.stop();
            new LiveListeningActivity().unmountListner(listenerId);
            if (!mContext.isFinishing())
                mContext.finish();
        }
    }

    @OnClick(R.id.rl_chat_tile)
    public void onClickChatListen() {
        SnackbarUtil.showWarningShortSnackbar(mContext, getString(R.string.under_development_message));
     /*   AppPreferences.init(mContext).putString(AppConstant.User_CURRENT_STATE, "3");
        Intent intent = new Intent(this, ConversationActivity.class);
        intent.putExtra(ConversationUIService.GROUP_ID, Integer.parseInt(chatChannelId));
        intent.putExtra(ConversationUIService.TAKE_ORDER, true);
        startActivity(intent);*/
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
