package com.softuvo.ipundit.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class MatchListListnerModel implements Serializable{
    @SerializedName("data")
    @Expose
    private List<ListenMatchList> data = null;

    public List<ListenMatchList> getData() {
        return data;
    }

    @SerializedName("team_broadcaster_count")
    @Expose
    private String teamBroadcasterCount;

    public String getTeamBroadcasterCount() {
        return teamBroadcasterCount;
    }

    public void setTeamBroadcasterCount(String teamBroadcasterCount) {
        this.teamBroadcasterCount = teamBroadcasterCount;
    }

    public void setData(List<ListenMatchList> data) {
        this.data = data;
    }

    public class ListenMatchList implements Serializable{
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("league_id")
        @Expose
        private String leagueId;
        @SerializedName("competition_id")
        @Expose
        private String competitionId;
        @SerializedName("stage_id")
        @Expose
        private String stageId;
        @SerializedName("sport_id")
        @Expose
        private String sportId;
        @SerializedName("match_id")
        @Expose
        private String matchId;
        @SerializedName("matchStatus")
        @Expose
        private String matchStatus;
        @SerializedName("venue")
        @Expose
        private String venue;
        @SerializedName("match_start_date")
        @Expose
        private String matchStartDate;
        @SerializedName("match_start_time")
        @Expose
        private String matchStartTime;
        @SerializedName("periodId")
        @Expose
        private String periodId;
        @SerializedName("winner")
        @Expose
        private Object winner;
        @SerializedName("matchLengthMin")
        @Expose
        private Object matchLengthMin;
        @SerializedName("matchLengthSec")
        @Expose
        private Object matchLengthSec;
        @SerializedName("team1_id")
        @Expose
        private String team1Id;
        @SerializedName("team2_id")
        @Expose
        private String team2Id;
        @SerializedName("team1_score")
        @Expose
        private String team1Score;
        @SerializedName("team2_score")
        @Expose
        private String team2Score;
        @SerializedName("time_now")
        @Expose
        private String timeNow;
        @SerializedName("country_id")
        @Expose
        private String countryId;
        @SerializedName("season_id")
        @Expose
        private String seasonId;
        @SerializedName("match_week")
        @Expose
        private String matchWeek;
        @SerializedName("startDate")
        @Expose
        private String startDate;
        @SerializedName("endDate")
        @Expose
        private String endDate;
        @SerializedName("tournamentCalendar")
        @Expose
        private String tournamentCalendar;
        @SerializedName("ruleset_id")
        @Expose
        private String rulesetId;
        @SerializedName("ruleset_name")
        @Expose
        private String rulesetName;
        @SerializedName("last_update")
        @Expose
        private String lastUpdate;
        @SerializedName("match_date")
        @Expose
        private String matchDate;
        @SerializedName("team1_name")
        @Expose
        private String team1Name;
        @SerializedName("team2_name")
        @Expose
        private String team2Name;
        @SerializedName("season_name")
        @Expose
        private String seasonName;
        @SerializedName("channel")
        @Expose
        private List<Channel> channel = null;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLeagueId() {
            return leagueId;
        }

        public void setLeagueId(String leagueId) {
            this.leagueId = leagueId;
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

        public String getSportId() {
            return sportId;
        }

        public void setSportId(String sportId) {
            this.sportId = sportId;
        }

        public String getMatchId() {
            return matchId;
        }

        public void setMatchId(String matchId) {
            this.matchId = matchId;
        }

        public String getMatchStatus() {
            return matchStatus;
        }

        public void setMatchStatus(String matchStatus) {
            this.matchStatus = matchStatus;
        }

        public String getVenue() {
            return venue;
        }

        public void setVenue(String venue) {
            this.venue = venue;
        }

        public String getMatchStartDate() {
            return matchStartDate;
        }

        public void setMatchStartDate(String matchStartDate) {
            this.matchStartDate = matchStartDate;
        }

        public String getMatchStartTime() {
            return matchStartTime;
        }

        public void setMatchStartTime(String matchStartTime) {
            this.matchStartTime = matchStartTime;
        }

        public String getPeriodId() {
            return periodId;
        }

        public void setPeriodId(String periodId) {
            this.periodId = periodId;
        }

        public Object getWinner() {
            return winner;
        }

        public void setWinner(Object winner) {
            this.winner = winner;
        }

        public Object getMatchLengthMin() {
            return matchLengthMin;
        }

        public void setMatchLengthMin(Object matchLengthMin) {
            this.matchLengthMin = matchLengthMin;
        }

        public Object getMatchLengthSec() {
            return matchLengthSec;
        }

        public void setMatchLengthSec(Object matchLengthSec) {
            this.matchLengthSec = matchLengthSec;
        }

        public String getTeam1Id() {
            return team1Id;
        }

        public void setTeam1Id(String team1Id) {
            this.team1Id = team1Id;
        }

        public String getTeam2Id() {
            return team2Id;
        }

        public void setTeam2Id(String team2Id) {
            this.team2Id = team2Id;
        }

        public String getTeam1Score() {
            return team1Score;
        }

        public void setTeam1Score(String team1Score) {
            this.team1Score = team1Score;
        }

        public String getTeam2Score() {
            return team2Score;
        }

        public void setTeam2Score(String team2Score) {
            this.team2Score = team2Score;
        }

        public String getTimeNow() {
            return timeNow;
        }

        public void setTimeNow(String timeNow) {
            this.timeNow = timeNow;
        }

        public String getCountryId() {
            return countryId;
        }

        public void setCountryId(String countryId) {
            this.countryId = countryId;
        }

        public String getSeasonId() {
            return seasonId;
        }

        public void setSeasonId(String seasonId) {
            this.seasonId = seasonId;
        }

        public String getMatchWeek() {
            return matchWeek;
        }

        public void setMatchWeek(String matchWeek) {
            this.matchWeek = matchWeek;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public String getEndDate() {
            return endDate;
        }

        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }

        public String getTournamentCalendar() {
            return tournamentCalendar;
        }

        public void setTournamentCalendar(String tournamentCalendar) {
            this.tournamentCalendar = tournamentCalendar;
        }

        public String getRulesetId() {
            return rulesetId;
        }

        public void setRulesetId(String rulesetId) {
            this.rulesetId = rulesetId;
        }

        public String getRulesetName() {
            return rulesetName;
        }

        public void setRulesetName(String rulesetName) {
            this.rulesetName = rulesetName;
        }

        public String getLastUpdate() {
            return lastUpdate;
        }

        public void setLastUpdate(String lastUpdate) {
            this.lastUpdate = lastUpdate;
        }

        public String getMatchDate() {
            return matchDate;
        }

        public void setMatchDate(String matchDate) {
            this.matchDate = matchDate;
        }

        public String getTeam1Name() {
            return team1Name;
        }

        public void setTeam1Name(String team1Name) {
            this.team1Name = team1Name;
        }

        public String getTeam2Name() {
            return team2Name;
        }

        public void setTeam2Name(String team2Name) {
            this.team2Name = team2Name;
        }

        public String getSeasonName() {
            return seasonName;
        }

        public void setSeasonName(String seasonName) {
            this.seasonName = seasonName;
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
        }
    }
}
