package com.softuvo.ipundit.models;

/*
 * Created by softuvo on 23/3/18.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ScheduleFormModel implements Serializable{
    @SerializedName("responsestatus")
    @Expose
    private Boolean responsestatus;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("broadcast")
    @Expose
    private Broadcast broadcast;

    public Boolean getResponsestatus() {
        return responsestatus;
    }

    public void setResponsestatus(Boolean responsestatus) {
        this.responsestatus = responsestatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Broadcast getBroadcast() {
        return broadcast;
    }

    public void setBroadcast(Broadcast broadcast) {
        this.broadcast = broadcast;
    }

    public class Broadcast implements Serializable{

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("schedule_date")
        @Expose
        private String scheduleDate;
        @SerializedName("time")
        @Expose
        private String time;
        @SerializedName("match_id")
        @Expose
        private String matchId;
        @SerializedName("channel_type")
        @Expose
        private String channelType;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getScheduleDate() {
            return scheduleDate;
        }

        public void setScheduleDate(String scheduleDate) {
            this.scheduleDate = scheduleDate;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getMatchId() {
            return matchId;
        }

        public void setMatchId(String matchId) {
            this.matchId = matchId;
        }

        public String getChannelType() {
            return channelType;
        }

        public void setChannelType(String channelType) {
            this.channelType = channelType;
        }

    }
}
