package com.softuvo.ipundit.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/*
 * Created by Neha Kalia on 15-06-2017.
 */

public class MatchStandingListModel implements Serializable{
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
        @SerializedName("rank")
        @Expose
        private String rank;
        @SerializedName("rankStatus")
        @Expose
        private Object rankStatus;
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
        private String matchId;
        @SerializedName("channel_type")
        @Expose
        private String channelType;
        @SerializedName("live")
        @Expose
        private String live;
        @SerializedName("count_match")
        @Expose
        private String countMatch;
        @SerializedName("live_listeners")
        @Expose
        private Integer liveListeners;
        @SerializedName("chatChannelid")
        @Expose
        private String chatChannelid;
        @SerializedName("twitter_id")
        @Expose
        private String twitter_id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getRank() {
            return rank;
        }

        public void setRank(String rank) {
            this.rank = rank;
        }

        public Object getRankStatus() {
            return rankStatus;
        }

        public void setRankStatus(Object rankStatus) {
            this.rankStatus = rankStatus;
        }

        public String getLastRank() {
            return lastRank;
        }

        public void setLastRank(String lastRank) {
            this.lastRank = lastRank;
        }

        public String getTwitter_id() {
            return twitter_id;
        }

        public void setTwitter_id(String twitter_id) {
            this.twitter_id = twitter_id;
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

        public String getLive() {
            return live;
        }

        public void setLive(String live) {
            this.live = live;
        }

        public String getCountMatch() {
            return countMatch;
        }

        public void setCountMatch(String countMatch) {
            this.countMatch = countMatch;
        }

        public Integer getLiveListeners() {
            return liveListeners;
        }

        public void setLiveListeners(Integer liveListeners) {
            this.liveListeners = liveListeners;
        }

        public String getChatChannelid() {
            return chatChannelid;
        }

        public void setChatChannelid(String chatChannelid) {
            this.chatChannelid = chatChannelid;
        }
    }
}