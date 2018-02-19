package com.softuvo.ipundit.models;

/*
 * Created by softuvo on 13/2/18.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReconnectModel {
    @SerializedName("channel")
    @Expose
    private Channel channel;

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
    public class Channel {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("chatChannelid")
        @Expose
        private String chatChannelid;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("match_id")
        @Expose
        private String matchId;
        @SerializedName("broadcaster_id")
        @Expose
        private String broadcasterId;
        @SerializedName("broadcaster_name")
        @Expose
        private String broadcasterName;
        @SerializedName("station")
        @Expose
        private String station;
        @SerializedName("live")
        @Expose
        private String live;
        @SerializedName("appName")
        @Expose
        private String appName;
        @SerializedName("streamName")
        @Expose
        private String streamName;
        @SerializedName("channel_type")
        @Expose
        private String channelType;
        @SerializedName("time_stamp")
        @Expose
        private String timeStamp;
        @SerializedName("start_time")
        @Expose
        private String startTime;
        @SerializedName("last_update")
        @Expose
        private String lastUpdate;
        @SerializedName("league_id")
        @Expose
        private String leagueId;
        @SerializedName("sport_id")
        @Expose
        private String sportId;
        @SerializedName("pause_flag")
        @Expose
        private String pauseFlag;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getChatChannelid() {
            return chatChannelid;
        }

        public void setChatChannelid(String chatChannelid) {
            this.chatChannelid = chatChannelid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMatchId() {
            return matchId;
        }

        public void setMatchId(String matchId) {
            this.matchId = matchId;
        }

        public String getBroadcasterId() {
            return broadcasterId;
        }

        public void setBroadcasterId(String broadcasterId) {
            this.broadcasterId = broadcasterId;
        }

        public String getBroadcasterName() {
            return broadcasterName;
        }

        public void setBroadcasterName(String broadcasterName) {
            this.broadcasterName = broadcasterName;
        }

        public String getStation() {
            return station;
        }

        public void setStation(String station) {
            this.station = station;
        }

        public String getLive() {
            return live;
        }

        public void setLive(String live) {
            this.live = live;
        }

        public String getAppName() {
            return appName;
        }

        public void setAppName(String appName) {
            this.appName = appName;
        }

        public String getStreamName() {
            return streamName;
        }

        public void setStreamName(String streamName) {
            this.streamName = streamName;
        }

        public String getChannelType() {
            return channelType;
        }

        public void setChannelType(String channelType) {
            this.channelType = channelType;
        }

        public String getTimeStamp() {
            return timeStamp;
        }

        public void setTimeStamp(String timeStamp) {
            this.timeStamp = timeStamp;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getLastUpdate() {
            return lastUpdate;
        }

        public void setLastUpdate(String lastUpdate) {
            this.lastUpdate = lastUpdate;
        }

        public String getLeagueId() {
            return leagueId;
        }

        public void setLeagueId(String leagueId) {
            this.leagueId = leagueId;
        }

        public String getSportId() {
            return sportId;
        }

        public void setSportId(String sportId) {
            this.sportId = sportId;
        }

        public String getPauseFlag() {
            return pauseFlag;
        }

        public void setPauseFlag(String pauseFlag) {
            this.pauseFlag = pauseFlag;
        }

    }
}
