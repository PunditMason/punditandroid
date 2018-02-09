package com.softuvo.ipundit.activities;

/*
 * Created by Neha Kalia on 02/09/2017.
 */

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.applozic.mobicomkit.api.people.ChannelInfo;
import com.applozic.mobicomkit.channel.service.ChannelService;
import com.applozic.mobicomkit.uiwidgets.async.ApplozicChannelAddMemberTask;
import com.applozic.mobicommons.people.channel.Channel;
import com.softuvo.ipundit.R;
import com.softuvo.ipundit.api.ApiCallBack;
import com.softuvo.ipundit.config.ApiConstants;
import com.softuvo.ipundit.config.App;
import com.softuvo.ipundit.config.AppConstant;
import com.softuvo.ipundit.config.AppPreferences;
import com.softuvo.ipundit.models.FollowUnfollowModel;
import com.softuvo.ipundit.models.FollowerListModel;
import com.softuvo.ipundit.models.FollowingListModel;
import com.softuvo.ipundit.models.UserDetailsAndMatchDetailsModel;
import com.softuvo.ipundit.models.UserSearchLeagueModel;
import com.softuvo.ipundit.models.UserSearchSportsModel;
import com.softuvo.ipundit.receivers.ConnectivityReceivers;
import com.softuvo.ipundit.utils.SnackbarUtil;
import com.softuvo.ipundit.views.CustomTextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.softuvo.ipundit.config.AppConstant.FB_ID;

public class PunditsProfileActivity extends BaseActivity {
    private Activity mContext;
    private String punditsName;
    private String profilepic;
    private String followingCount;
    private String followerCount;
    private String punditsBio;
    private String live, chatChannelId, chatChannelName, matchid, channelType,userId;
    private String punditsId;
    private int followStatus;


    @BindView(R.id.tv_pundits_name)
    CustomTextView tvPunditsName;

    @BindView(R.id.iv_pundits_profile_pic)
    ImageView ivPunditsProfilePic;

    @BindView(R.id.iv_follow_me)
    ImageView ivFollowMe;

    @BindView(R.id.iv_listen_live)
    ImageView ivListenLive;

    @BindView(R.id.tv_pundits_follower_count)
    CustomTextView tvPunditsFollowerCount;

    @BindView(R.id.tv_pundits_following_count)
    CustomTextView tvPunditsFollowingCount;

    @BindView(R.id.tv_pundits_bio)
    CustomTextView tvPunditsBio;
    int i=0;
    int pos=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pundits_profile);
        mContext = PunditsProfileActivity.this;
        ButterKnife.bind(this);
        setUIBackGrounds();
    }


    private void setUIBackGrounds() {
        if (getIntent().getStringExtra("userComingFrom").equalsIgnoreCase("pundits")) {
            if (getIntent().getSerializableExtra("mUserDatum") != null) {
                UserDetailsAndMatchDetailsModel.UsersList userDatum = (UserDetailsAndMatchDetailsModel.UsersList) getIntent().getSerializableExtra("mUserDatum");
                punditsName = userDatum.getFirstName();
                profilepic = userDatum.getAvatar();
                punditsBio = userDatum.getUserBio();
                followingCount = userDatum.getFollowingCount().toString();
                followerCount = userDatum.getFollowCount().toString();
                live = userDatum.getLive().toString();
                punditsId = userDatum.getId();
                if(userDatum.getLive().toString().equals("1")) {
                    if (userDatum.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("match")) {
                        chatChannelId = userDatum.getChannelInfo().get(0).getMatchInfo().getChatChannelid();
                        chatChannelName = userDatum.getChannelInfo().get(0).getMatchInfo().getTeam1Name() + " Vs " + userDatum.getChannelInfo().get(0).getMatchInfo().getTeam1Name();
                        matchid = userDatum.getChannelInfo().get(0).getChannel().getMatchId();
                        channelType = userDatum.getChannelInfo().get(0).getChannel().getChannelType();
                    } else if (userDatum.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("team")) {
                        chatChannelId = userDatum.getChannelInfo().get(0).getTeamInfo().getChatChannelid();
                        chatChannelName = userDatum.getChannelInfo().get(0).getTeamInfo().getContestantName();
                        matchid = userDatum.getChannelInfo().get(0).getTeamInfo().getContestantId();
                        channelType = userDatum.getChannelInfo().get(0).getChannel().getChannelType();
                    }
                    if (chatChannelId.equalsIgnoreCase("0")) {
                       // new createChannel().execute();
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
                }

                if (userDatum.getFollowCheck().equalsIgnoreCase("TRUE"))
                    followStatus = 1;
                else if (userDatum.getFollowCheck().equalsIgnoreCase("FALSE"))
                    followStatus = 0;
            }
        } else if (getIntent().getStringExtra("userComingFrom").equalsIgnoreCase("sprotsUserSearch")) {
            if (getIntent().getSerializableExtra("mUserDatum") != null) {
                UserSearchSportsModel.UserDatum userSearchSportsDatum = (UserSearchSportsModel.UserDatum) getIntent().getSerializableExtra("mUserDatum");
                punditsName = userSearchSportsDatum.getFirstName();
                profilepic = userSearchSportsDatum.getAvatar();
                punditsBio = userSearchSportsDatum.getUserBio();
                followingCount = userSearchSportsDatum.getFollowingCount().toString();
                followerCount = userSearchSportsDatum.getFollowCount().toString();
                live = userSearchSportsDatum.getLive().toString();
                punditsId = userSearchSportsDatum.getId();
                if(userSearchSportsDatum.getLive().toString().equals("1")) {
                    if (userSearchSportsDatum.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("match")) {
                        chatChannelId = userSearchSportsDatum.getChannelInfo().get(0).getMatchInfo().getChatChannelid();
                        chatChannelName = userSearchSportsDatum.getChannelInfo().get(0).getMatchInfo().getTeam1Name() + " Vs " + userSearchSportsDatum.getChannelInfo().get(0).getMatchInfo().getTeam1Name();
                        matchid = userSearchSportsDatum.getChannelInfo().get(0).getChannel().getMatchId();
                        channelType = userSearchSportsDatum.getChannelInfo().get(0).getChannel().getChannelType();
                    } else if (userSearchSportsDatum.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("team")) {
                        chatChannelId = userSearchSportsDatum.getChannelInfo().get(0).getTeamInfo().getChatChannelid();
                        chatChannelName = userSearchSportsDatum.getChannelInfo().get(0).getTeamInfo().getContestantName();
                        matchid = userSearchSportsDatum.getChannelInfo().get(0).getTeamInfo().getContestantId();
                        channelType = userSearchSportsDatum.getChannelInfo().get(0).getChannel().getChannelType();
                    }
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
                }
                if (userSearchSportsDatum.getFollowCheck().equalsIgnoreCase("TRUE"))
                    followStatus = 1;
                else if (userSearchSportsDatum.getFollowCheck().equalsIgnoreCase("FALSE"))
                    followStatus = 0;

            }
        } else if (getIntent().getStringExtra("userComingFrom").equalsIgnoreCase("leaguesUserSearch")) {
            if (getIntent().getSerializableExtra("mUserDatum") != null) {
                UserSearchLeagueModel.Datum userSearchLeagueDatum = (UserSearchLeagueModel.Datum) getIntent().getSerializableExtra("mUserDatum");
                punditsName = userSearchLeagueDatum.getFirstName();
                profilepic = userSearchLeagueDatum.getAvatar();
                punditsBio = userSearchLeagueDatum.getUserBio();
                followingCount = userSearchLeagueDatum.getFollowingCount().toString();
                followerCount = userSearchLeagueDatum.getFollowCount().toString();
                live = userSearchLeagueDatum.getLive().toString();
                punditsId = userSearchLeagueDatum.getId();
                if(userSearchLeagueDatum.getLive().toString().equals("1")) {
                    if (userSearchLeagueDatum.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("match")) {
                        chatChannelId = userSearchLeagueDatum.getChannelInfo().get(0).getMatchInfo().getChatChannelid();
                        chatChannelName = userSearchLeagueDatum.getChannelInfo().get(0).getMatchInfo().getTeam1Name() + " Vs " + userSearchLeagueDatum.getChannelInfo().get(0).getMatchInfo().getTeam1Name();
                        matchid = userSearchLeagueDatum.getChannelInfo().get(0).getChannel().getMatchId();
                        channelType = userSearchLeagueDatum.getChannelInfo().get(0).getChannel().getChannelType();
                    } else if (userSearchLeagueDatum.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("team")) {
                        chatChannelId = userSearchLeagueDatum.getChannelInfo().get(0).getTeamInfo().getChatChannelid();
                        chatChannelName = userSearchLeagueDatum.getChannelInfo().get(0).getTeamInfo().getContestantName();
                        matchid = userSearchLeagueDatum.getChannelInfo().get(0).getTeamInfo().getContestantId();
                        channelType = userSearchLeagueDatum.getChannelInfo().get(0).getChannel().getChannelType();
                    }
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
                }
                if (userSearchLeagueDatum.getFollowCheck().equalsIgnoreCase("TRUE"))
                    followStatus = 1;
                else if (userSearchLeagueDatum.getFollowCheck().equalsIgnoreCase("FALSE"))
                    followStatus = 0;

            }
        }
        else if(getIntent().getStringExtra("userComingFrom").equalsIgnoreCase("followerList")){
            if (getIntent().getSerializableExtra("mUserDatum") != null) {
                FollowerListModel.Follwer follwerlist=(FollowerListModel.Follwer)getIntent().getSerializableExtra("mUserDatum");
                punditsName = follwerlist.getFirstName();
                profilepic = follwerlist.getAvatar();
                punditsBio = follwerlist.getUserBio();
                followingCount = follwerlist.getFollowingCount().toString();
                followerCount = follwerlist.getFollowCount().toString();
                live = follwerlist.getLive().toString();
                punditsId = follwerlist.getId();
                if(follwerlist.getLive().toString().equals("1")) {
                    if (follwerlist.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("match")) {
                        chatChannelId = follwerlist.getChannelInfo().get(0).getMatchInfo().getChatChannelid();
                        chatChannelName = follwerlist.getChannelInfo().get(0).getMatchInfo().getTeam1Name() + " Vs " + follwerlist.getChannelInfo().get(0).getMatchInfo().getTeam1Name();
                        matchid = follwerlist.getChannelInfo().get(0).getChannel().getMatchId();
                        channelType = follwerlist.getChannelInfo().get(0).getChannel().getChannelType();
                    } else if (follwerlist.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("team")) {
                        chatChannelId = follwerlist.getChannelInfo().get(0).getTeamInfo().getChatChannelid();
                        chatChannelName = follwerlist.getChannelInfo().get(0).getTeamInfo().getContestantName();
                        matchid = follwerlist.getChannelInfo().get(0).getTeamInfo().getContestantId();
                        channelType = follwerlist.getChannelInfo().get(0).getChannel().getChannelType();
                    }
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
                }
                if (follwerlist.getFollowCheck().equalsIgnoreCase("TRUE"))
                    followStatus = 1;
                else if (follwerlist.getFollowCheck().equalsIgnoreCase("FALSE"))
                    followStatus = 0;
            }
        }
        else if(getIntent().getStringExtra("userComingFrom").equalsIgnoreCase("followingList")){
            if (getIntent().getSerializableExtra("mUserDatum") != null) {
                FollowingListModel.Following follwinglist=(FollowingListModel.Following)getIntent().getSerializableExtra("mUserDatum");
                punditsName = follwinglist.getFirstName();
                profilepic = follwinglist.getAvatar();
                punditsBio = follwinglist.getUserBio();
                followingCount = follwinglist.getFollowingCount().toString();
                followerCount = follwinglist.getFollowCount().toString();
                live = follwinglist.getLive().toString();
                punditsId = follwinglist.getId();
                followStatus = follwinglist.getFollow();
                if(follwinglist.getLive().toString().equals("1")) {
                    if (follwinglist.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("match")) {
                        chatChannelId = follwinglist.getChannelInfo().get(0).getMatchInfo().getChatChannelid();
                        chatChannelName = follwinglist.getChannelInfo().get(0).getMatchInfo().getTeam1Name() + " Vs " + follwinglist.getChannelInfo().get(0).getMatchInfo().getTeam1Name();
                        matchid = follwinglist.getChannelInfo().get(0).getChannel().getMatchId();
                        channelType = follwinglist.getChannelInfo().get(0).getChannel().getChannelType();
                    } else if (follwinglist.getChannelInfo().get(0).getChannel().getChannelType().equalsIgnoreCase("team")) {
                        chatChannelId = follwinglist.getChannelInfo().get(0).getTeamInfo().getChatChannelid();
                        chatChannelName = follwinglist.getChannelInfo().get(0).getTeamInfo().getContestantName();
                        matchid = follwinglist.getChannelInfo().get(0).getTeamInfo().getContestantId();
                        channelType = follwinglist.getChannelInfo().get(0).getChannel().getChannelType();
                    }
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
                }
                if (follwinglist.getFollowCheck().equalsIgnoreCase("TRUE"))
                    followStatus = 1;
                else if (follwinglist.getFollowCheck().equalsIgnoreCase("FALSE"))
                    followStatus = 0;
            }
        }
        tvPunditsName.setText(punditsName);
        Picasso.with(mContext).load(ApiConstants.PROFILE_IMAGE_BASE_URL + profilepic).into(ivPunditsProfilePic);
        tvPunditsFollowerCount.setText(followingCount);
        tvPunditsFollowingCount.setText(followerCount);
        tvPunditsBio.setText(punditsBio);
        if (live.equals("1")) {
            ivListenLive.setImageResource(R.drawable.listen_live_red);
            ivListenLive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, LiveListeningActivity.class);
                    intent.putExtra("userComingFrom", getIntent().getStringExtra("userComingFrom"));
                    intent.putExtra("mUserDatum", getIntent().getSerializableExtra("mUserDatum"));
                    intent.putExtra("chatChannelKey",chatChannelId);
                    startActivity(intent);
                }
            });
        } else {
            ivListenLive.setImageResource(R.drawable.listen_live_gray);
            ivListenLive.setClickable(false);
        }
        if (followStatus == 0)
            ivFollowMe.setImageResource(R.drawable.follow_me);
        else if (followStatus == 1) {
            ivFollowMe.setImageResource(R.drawable.unfollow);
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

    @OnClick(R.id.iv_follow_me)
    public void buttonFollowUnfollow() {
        if (AppPreferences.init(mContext).getString(AppConstant.USER_ID).equalsIgnoreCase(punditsId)) {
            SnackbarUtil.showWarningShortSnackbar(mContext, getString(R.string.self_follow_text));
            ivFollowMe.setClickable(false);
        } else {
            String followUnfoloowPath;
            if (followStatus == 0) {
                ivFollowMe.setImageResource(R.drawable.follow_me);
                followUnfoloowPath = AppPreferences.init(mContext).getString(AppConstant.USER_ID) + "/" + punditsId;
                followunfollowUser(followUnfoloowPath);

            } else if (followStatus == 1) {
                ivFollowMe.setImageResource(R.drawable.unfollow);
                followUnfoloowPath = AppPreferences.init(mContext).getString(AppConstant.USER_ID) + "/" + punditsId;
                followunfollowUser(followUnfoloowPath);
            }
        }
    }

    private void followunfollowUser(final String stringPath) {
        if (ConnectivityReceivers.isConnected()) {
            App.getApiHelper().followunfollowlist(stringPath, new ApiCallBack<FollowUnfollowModel>() {
                @Override
                public void onSuccess(FollowUnfollowModel followUnfollowModel) {
                    if (followUnfollowModel != null) {
                        followStatus = followUnfollowModel.getData().getResult();
                        if (followStatus == 0) {
                            ivFollowMe.setImageResource(R.drawable.follow_me);
                            tvPunditsFollowerCount.setText(followUnfollowModel.getData().getCount().toString());
                        } else if (followStatus == 1) {
                            ivFollowMe.setImageResource(R.drawable.unfollow);
                            tvPunditsFollowerCount.setText(followUnfollowModel.getData().getCount().toString());
                        }
                    }
                }

                @Override
                public void onFailure(String message) {

//
                }
            });
        } else {
            enableUserIntraction();
            SnackbarUtil.showWarningLongSnackbar(mContext, getResources().getString(R.string.internet_not_connected_text));
        }
    }

    private void updateChatChannelId(){
        Map<String, String> mountMap = new HashMap<>();
        mountMap.put("match_id", matchid);
        mountMap.put("channeltype", channelType);
        mountMap.put("chatChannelid", chatChannelId);
        App.getApiHelper().updateChatId(mountMap, new ApiCallBack<Map>() {
            @Override
            public void onSuccess(Map map) {

            }

            @Override
            public void onFailure(String message) {

            }
        });
    }

    @OnClick(R.id.ll_followers_main)
    public void onClickllFollwersMain(){
        Intent intent=new Intent(mContext,FollowersListActivity.class);
        intent.putExtra("usercomingfrom","punditsProfile");
        intent.putExtra("id",AppPreferences.init(mContext).getString(AppConstant.USER_ID)+"/"+punditsId);
        startActivity(intent);
    }

    @OnClick(R.id.ll_following_main)
    public void onClickllFollwingMain(){
        Intent intent=new Intent(mContext,FollowingListActivity.class);
        intent.putExtra("usercomingfrom","punditsProfile");
        intent.putExtra("id",AppPreferences.init(mContext).getString(AppConstant.USER_ID)+"/"+punditsId);
        startActivity(intent);
    }

    @OnClick(R.id.iv_podcast)
    public void onClickPodcast(){
        Intent intent=new Intent(mContext,PodcastActivity.class);
        intent.putExtra("punditsId",punditsId);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUIBackGrounds();
    }
}
