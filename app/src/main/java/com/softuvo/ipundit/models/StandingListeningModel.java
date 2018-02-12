package com.softuvo.ipundit.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/*
 * Created by Neha Kalia on 30-06-2017.
 */

public class StandingListeningModel implements Serializable {

    @SerializedName("data")
    @Expose
    private List<Datum> data = null;

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }
    public class Datum implements Serializable{

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("c_id")
        @Expose
        private Object cId;
        @SerializedName("rank")
        @Expose
        private String rank;
        @SerializedName("rankStatus")
        @Expose
        private String rankStatus;
        @SerializedName("lastRank")
        @Expose
        private String lastRank;
        @SerializedName("contestantId")
        @Expose
        private String contestantId;
        @SerializedName("contestantName")
        @Expose
        private String contestantName;
        @SerializedName("contestantShortName")
        @Expose
        private String contestantShortName;
        @SerializedName("contestantClubName")
        @Expose
        private String contestantClubName;
        @SerializedName("contestantCode")
        @Expose
        private String contestantCode;
        @SerializedName("points")
        @Expose
        private String points;
        @SerializedName("matchesPlayed")
        @Expose
        private String matchesPlayed;
        @SerializedName("matchesWon")
        @Expose
        private String matchesWon;
        @SerializedName("matchesLost")
        @Expose
        private String matchesLost;
        @SerializedName("matchesDrawn")
        @Expose
        private String matchesDrawn;
        @SerializedName("goalsFor")
        @Expose
        private String goalsFor;
        @SerializedName("goalsAgainst")
        @Expose
        private String goalsAgainst;
        @SerializedName("goaldifference")
        @Expose
        private String goaldifference;
        @SerializedName("last_update")
        @Expose
        private String lastUpdate;
        @SerializedName("match_id")
        @Expose
        private Object matchId;
        @SerializedName("channel_type")
        @Expose
        private Object channelType;
        @SerializedName("live")
        @Expose
        private Object live;
        @SerializedName("count_match")
        @Expose
        private String countMatch;
        @SerializedName("twitter_id")
        @Expose
        private String twitter_id;
        @SerializedName("channel")
        @Expose
        private List<Channel> channel = null;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Object getCId() {
            return cId;
        }

        public void setCId(Object cId) {
            this.cId = cId;
        }

        public String getRank() {
            return rank;
        }

        public Object getcId() {
            return cId;
        }

        public void setcId(Object cId) {
            this.cId = cId;
        }

        public String getTwitter_id() {
            return twitter_id;
        }

        public void setTwitter_id(String twitter_id) {
            this.twitter_id = twitter_id;
        }

        public void setRank(String rank) {
            this.rank = rank;
        }

        public String getRankStatus() {
            return rankStatus;
        }

        public void setRankStatus(String rankStatus) {
            this.rankStatus = rankStatus;
        }

        public String getLastRank() {
            return lastRank;
        }

        public void setLastRank(String lastRank) {
            this.lastRank = lastRank;
        }

        public String getContestantId() {
            return contestantId;
        }

        public void setContestantId(String contestantId) {
            this.contestantId = contestantId;
        }

        public String getContestantName() {
            return contestantName;
        }

        public void setContestantName(String contestantName) {
            this.contestantName = contestantName;
        }

        public String getContestantShortName() {
            return contestantShortName;
        }

        public void setContestantShortName(String contestantShortName) {
            this.contestantShortName = contestantShortName;
        }

        public String getContestantClubName() {
            return contestantClubName;
        }

        public void setContestantClubName(String contestantClubName) {
            this.contestantClubName = contestantClubName;
        }

        public String getContestantCode() {
            return contestantCode;
        }

        public void setContestantCode(String contestantCode) {
            this.contestantCode = contestantCode;
        }

        public String getPoints() {
            return points;
        }

        public void setPoints(String points) {
            this.points = points;
        }

        public String getMatchesPlayed() {
            return matchesPlayed;
        }

        public void setMatchesPlayed(String matchesPlayed) {
            this.matchesPlayed = matchesPlayed;
        }

        public String getMatchesWon() {
            return matchesWon;
        }

        public void setMatchesWon(String matchesWon) {
            this.matchesWon = matchesWon;
        }

        public String getMatchesLost() {
            return matchesLost;
        }

        public void setMatchesLost(String matchesLost) {
            this.matchesLost = matchesLost;
        }

        public String getMatchesDrawn() {
            return matchesDrawn;
        }

        public void setMatchesDrawn(String matchesDrawn) {
            this.matchesDrawn = matchesDrawn;
        }

        public String getGoalsFor() {
            return goalsFor;
        }

        public void setGoalsFor(String goalsFor) {
            this.goalsFor = goalsFor;
        }

        public String getGoalsAgainst() {
            return goalsAgainst;
        }

        public void setGoalsAgainst(String goalsAgainst) {
            this.goalsAgainst = goalsAgainst;
        }

        public String getGoaldifference() {
            return goaldifference;
        }

        public void setGoaldifference(String goaldifference) {
            this.goaldifference = goaldifference;
        }

        public String getLastUpdate() {
            return lastUpdate;
        }

        public void setLastUpdate(String lastUpdate) {
            this.lastUpdate = lastUpdate;
        }

        public Object getMatchId() {
            return matchId;
        }

        public void setMatchId(Object matchId) {
            this.matchId = matchId;
        }

        public Object getChannelType() {
            return channelType;
        }

        public void setChannelType(Object channelType) {
            this.channelType = channelType;
        }

        public Object getLive() {
            return live;
        }

        public void setLive(Object live) {
            this.live = live;
        }

        public String getCountMatch() {
            return countMatch;
        }

        public void setCountMatch(String countMatch) {
            this.countMatch = countMatch;
        }

        public List<Channel> getChannel() {
            return channel;
        }

        public void setChannel(List<Channel> channel) {
            this.channel = channel;
        }
        public class Channel implements Serializable{
            @SerializedName("id")
            @Expose
            private String id;
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
            @SerializedName("chatChannelid")
            @Expose
            private String chatChannelid;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
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

            public String getChatChannelid() {
                return chatChannelid;
            }

            public void setChatChannelid(String chatChannelid) {
                this.chatChannelid = chatChannelid;
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
        }

    }

}
