package com.softuvo.ipundit.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


public class TeamSearchSportsModel implements Serializable {
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
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("country_id")
        @Expose
        private String countryId;
        @SerializedName("mark_image")
        @Expose
        private String markImage;
        @SerializedName("sport_id")
        @Expose
        private String sportId;
        @SerializedName("sport_name")
        @Expose
        private String sportName;
        @SerializedName("league_id")
        @Expose
        private String leagueId;
        @SerializedName("league_name")
        @Expose
        private String leagueName;
        @SerializedName("last_update")
        @Expose
        private String lastUpdate;
        @SerializedName("s_id")
        @Expose
        private String sId;
        @SerializedName("rank")
        @Expose
        private String rank;
        @SerializedName("rankStatus")
        @Expose
        private String rankStatus;
        @SerializedName("lastRank")
        @Expose
        private Object lastRank;
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
        @SerializedName("l_id")
        @Expose
        private String lId;
        @SerializedName("position")
        @Expose
        private String position;
        @SerializedName("competition_id")
        @Expose
        private String competitionId;
        @SerializedName("stage_id")
        @Expose
        private String stageId;
        @SerializedName("live")
        @Expose
        private Integer live;
        @SerializedName("channel_info")
        @Expose
        private List<ChannelInfo> channelInfo = null;

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

        public String getCountryId() {
            return countryId;
        }

        public void setCountryId(String countryId) {
            this.countryId = countryId;
        }

        public String getMarkImage() {
            return markImage;
        }

        public void setMarkImage(String markImage) {
            this.markImage = markImage;
        }

        public String getSportId() {
            return sportId;
        }

        public void setSportId(String sportId) {
            this.sportId = sportId;
        }

        public String getSportName() {
            return sportName;
        }

        public void setSportName(String sportName) {
            this.sportName = sportName;
        }

        public String getLeagueId() {
            return leagueId;
        }

        public void setLeagueId(String leagueId) {
            this.leagueId = leagueId;
        }

        public String getLeagueName() {
            return leagueName;
        }

        public void setLeagueName(String leagueName) {
            this.leagueName = leagueName;
        }

        public String getLastUpdate() {
            return lastUpdate;
        }

        public void setLastUpdate(String lastUpdate) {
            this.lastUpdate = lastUpdate;
        }

        public String getSId() {
            return sId;
        }

        public void setSId(String sId) {
            this.sId = sId;
        }

        public String getRank() {
            return rank;
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

        public Object getLastRank() {
            return lastRank;
        }

        public void setLastRank(Object lastRank) {
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

        public String getLId() {
            return lId;
        }

        public void setLId(String lId) {
            this.lId = lId;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getCompetitionId() {
            return competitionId;
        }

        public void setCompetitionId(String competitionId) {
            this.competitionId = competitionId;
        }

        public String getStageId() {
            return stageId;
        }

        public void setStageId(String stageId) {
            this.stageId = stageId;
        }

        public Integer getLive() {
            return live;
        }

        public void setLive(Integer live) {
            this.live = live;
        }

        public List<ChannelInfo> getChannelInfo() {
            return channelInfo;
        }

        public void setChannelInfo(List<ChannelInfo> channelInfo) {
            this.channelInfo = channelInfo;
        }
        public class ChannelInfo implements Serializable{

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
            @SerializedName("league_id")
            @Expose
            private String leagueId;
            @SerializedName("sport_id")
            @Expose
            private String sportId;

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

        }

    }
}
