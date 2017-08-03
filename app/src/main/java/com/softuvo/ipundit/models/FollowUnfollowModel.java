package com.softuvo.ipundit.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by softuvo on 30-06-2017.
 */

public class FollowUnfollowModel {

    @SerializedName("data")
    @Expose
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("follower_id")
        @Expose
        private String followerId;
        @SerializedName("following_id")
        @Expose
        private String followingId;
        @SerializedName("station")
        @Expose
        private String station;
        @SerializedName("live")
        @Expose
        private String live;
        @SerializedName("time_stamp")
        @Expose
        private String timeStamp;
        @SerializedName("start_time")
        @Expose
        private String startTime;
        @SerializedName("result")
        @Expose
        private int result;
        @SerializedName("count")
        @Expose
        private Integer count;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getFollowerId() {
            return followerId;
        }

        public void setFollowerId(String followerId) {
            this.followerId = followerId;
        }

        public String getFollowingId() {
            return followingId;
        }

        public void setFollowingId(String followingId) {
            this.followingId = followingId;
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

        public int getResult() {
            return result;
        }

        public void setResult(int result) {
            this.result = result;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }
    }

}
