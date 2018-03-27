package com.softuvo.ipundit.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.applozic.mobicomkit.api.people.ChannelInfo;
import com.applozic.mobicomkit.channel.service.ChannelService;
import com.applozic.mobicomkit.uiwidgets.async.ApplozicChannelAddMemberTask;
import com.applozic.mobicommons.people.channel.Channel;
import com.softuvo.ipundit.R;
import com.softuvo.ipundit.adapters.ScheduleBroadcastAdapter;
import com.softuvo.ipundit.api.ApiCallBack;
import com.softuvo.ipundit.config.App;
import com.softuvo.ipundit.config.AppConstant;
import com.softuvo.ipundit.config.AppPreferences;
import com.softuvo.ipundit.models.ScheduleModel;
import com.softuvo.ipundit.receivers.ConnectivityReceivers;
import com.softuvo.ipundit.utils.SnackbarUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.softuvo.ipundit.config.AppConstant.FB_ID;

public class ScheduledBroadcastingListActivity extends BaseActivity {
    private Activity mContext;
    private List<ScheduleModel.Schedule> scheduleList;
    ScheduleBroadcastAdapter scheduleBroadcastAdapter;
    private String chatChannelId, chatChannelName,matchid,channelType;

    @BindView(R.id.rv_schedule_list)
    RecyclerView rvScheduledList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduled_broadcasting_list);
        ButterKnife.bind(this);
        mContext=ScheduledBroadcastingListActivity.this;
        getScheduledLsit();

    }

    private void getScheduledLsit() {
        if (ConnectivityReceivers.isConnected()) {
            Map<String, String> mountMap = new HashMap<>();
            mountMap.put("user_id", AppPreferences.init(mContext).getString(AppConstant.USER_ID));
            App.getApiHelper().getscheduleBroadcast(mountMap, new ApiCallBack<ScheduleModel>() {
                @Override
                public void onSuccess(ScheduleModel scheduleModel) {
                    if(scheduleModel!=null){
                        scheduleList = new ArrayList<>();
                        rvScheduledList.setLayoutManager(new LinearLayoutManager(mContext));
                        scheduleList = scheduleModel.getSchedules();
                        scheduleBroadcastAdapter=new ScheduleBroadcastAdapter(mContext, scheduleList,
                        new ScheduleBroadcastAdapter.ItemClickListener() {
                            @Override
                            public void onClick(int position) {
                                if(scheduleList.get(position).getChannelType().equalsIgnoreCase("match")) {
                                    chatChannelId = scheduleList.get(position).getMatch().getChatChannelid();
                                    chatChannelName = scheduleList.get(position).getMatch().getTeam1Name() + " Vs " + scheduleList.get(position).getMatch().getTeam2Name();

                                }
                                else {
                                    chatChannelId = scheduleList.get(position).getTeam().getChatChannelid();
                                    chatChannelName=scheduleList.get(position).getTeam().getContestantName();
                                }
                                channelType=scheduleList.get(position).getChannelType();
                                matchid = scheduleList.get(position).getMatchId();
                                if (chatChannelId.equalsIgnoreCase("0")) {
//                                        new createChannel().execute();
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
                                Intent intent = new Intent(mContext, LiveBroadCastingActivity.class);
                                intent.putExtra("userComingFrom", "schedule");
                                intent.putExtra("mBrDatum", scheduleList.get(position));
                                intent.putExtra("chatChannelKey", chatChannelId);
                                startActivity(intent);
                            }
                        });
                        rvScheduledList.setAdapter(scheduleBroadcastAdapter);
                    }

                }

                @Override
                public void onFailure(String message) {

                }
            });

        }
        else{
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
        mountMap.put("channeltype", channelType);
        if (chatChannelId == null)
            mountMap.put("chatChannelid", "0");
        else {
            mountMap.put("chatChannelid", chatChannelId);
        }
        Log.e("===4",""+chatChannelId);
        App.getApiHelper().updateChatId(mountMap, new ApiCallBack<Map>() {
            @Override
            public void onSuccess(Map map) {

            }

            @Override
            public void onFailure(String message) {

            }
        });
    }
}
