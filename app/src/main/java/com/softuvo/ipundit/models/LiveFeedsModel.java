package com.softuvo.ipundit.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LiveFeedsModel {

    @SerializedName("matchinfo")
    @Expose
    private Matchinfo matchinfo;
    @SerializedName("feeds")
    @Expose
    private List<Feed> feeds = null;

    public Matchinfo getMatchinfo() {
        return matchinfo;
    }

    public void setMatchinfo(Matchinfo matchinfo) {
        this.matchinfo = matchinfo;
    }

    public List<Feed> getFeeds() {
        return feeds;
    }

    public void setFeeds(List<Feed> feeds) {
        this.feeds = feeds;
    }
    public class Matchinfo{
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
        private String winner;
        @SerializedName("ft_lengthMin")
        @Expose
        private String ftLengthMin;
        @SerializedName("ft_lengthSec")
        @Expose
        private String ftLengthSec;
        @SerializedName("st_lengthMin")
        @Expose
        private String stLengthMin;
        @SerializedName("st_lengthSec")
        @Expose
        private String stLengthSec;
        @SerializedName("matchLengthMin")
        @Expose
        private String matchLengthMin;
        @SerializedName("matchLengthSec")
        @Expose
        private String matchLengthSec;
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

        public String getWinner() {
            return winner;
        }

        public void setWinner(String winner) {
            this.winner = winner;
        }

        public String getFtLengthMin() {
            return ftLengthMin;
        }

        public void setFtLengthMin(String ftLengthMin) {
            this.ftLengthMin = ftLengthMin;
        }

        public String getFtLengthSec() {
            return ftLengthSec;
        }

        public void setFtLengthSec(String ftLengthSec) {
            this.ftLengthSec = ftLengthSec;
        }

        public String getStLengthMin() {
            return stLengthMin;
        }

        public void setStLengthMin(String stLengthMin) {
            this.stLengthMin = stLengthMin;
        }

        public String getStLengthSec() {
            return stLengthSec;
        }

        public void setStLengthSec(String stLengthSec) {
            this.stLengthSec = stLengthSec;
        }

        public String getMatchLengthMin() {
            return matchLengthMin;
        }

        public void setMatchLengthMin(String matchLengthMin) {
            this.matchLengthMin = matchLengthMin;
        }

        public String getMatchLengthSec() {
            return matchLengthSec;
        }

        public void setMatchLengthSec(String matchLengthSec) {
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
    }
    public class Feed{
        @SerializedName("league_id")
        @Expose
        private String leagueId;
        @SerializedName("match_id")
        @Expose
        private String matchId;
        @SerializedName("match_start_date")
        @Expose
        private String matchStartDate;
        @SerializedName("team_id")
        @Expose
        private String teamId;
        @SerializedName("name")
        @Expose
        private Object name;
        @SerializedName("contestantId")
        @Expose
        private String contestantId;
        @SerializedName("periodId")
        @Expose
        private String periodId;
        @SerializedName("timeMin")
        @Expose
        private String timeMin;
        @SerializedName("lastUpdated")
        @Expose
        private String lastUpdated;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("playerId")
        @Expose
        private String playerId;
        @SerializedName("playerName")
        @Expose
        private String playerName;
        @SerializedName("optaEventId")
        @Expose
        private String optaEventId;
        @SerializedName("last_update")
        @Expose
        private String lastUpdate;
        @SerializedName("now_update")
        @Expose
        private String nowUpdate;
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("playerOffName")
        @Expose
        private String playerOffName;
        @SerializedName("scorerName")
        @Expose
        private String scorerName;
        @SerializedName("playerOffId")
        @Expose
        private String playerOffId;
        @SerializedName("playerOnName")
        @Expose
        private String playerOnName;
        @SerializedName("playerOnId")
        @Expose
        private String playerOnId;

        public String getLeagueId() {
            return leagueId;
        }

        public void setLeagueId(String leagueId) {
            this.leagueId = leagueId;
        }

        public String getMatchId() {
            return matchId;
        }

        public void setMatchId(String matchId) {
            this.matchId = matchId;
        }

        public String getMatchStartDate() {
            return matchStartDate;
        }

        public void setMatchStartDate(String matchStartDate) {
            this.matchStartDate = matchStartDate;
        }

        public String getTeamId() {
            return teamId;
        }

        public void setTeamId(String teamId) {
            this.teamId = teamId;
        }

        public Object getName() {
            return name;
        }

        public void setName(Object name) {
            this.name = name;
        }

        public String getContestantId() {
            return contestantId;
        }

        public void setContestantId(String contestantId) {
            this.contestantId = contestantId;
        }

        public String getPeriodId() {
            return periodId;
        }

        public void setPeriodId(String periodId) {
            this.periodId = periodId;
        }

        public String getTimeMin() {
            return timeMin;
        }

        public void setTimeMin(String timeMin) {
            this.timeMin = timeMin;
        }

        public String getLastUpdated() {
            return lastUpdated;
        }

        public void setLastUpdated(String lastUpdated) {
            this.lastUpdated = lastUpdated;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getPlayerId() {
            return playerId;
        }

        public void setPlayerId(String playerId) {
            this.playerId = playerId;
        }

        public String getPlayerName() {
            return playerName;
        }

        public void setPlayerName(String playerName) {
            this.playerName = playerName;
        }

        public String getOptaEventId() {
            return optaEventId;
        }

        public void setOptaEventId(String optaEventId) {
            this.optaEventId = optaEventId;
        }

        public String getLastUpdate() {
            return lastUpdate;
        }

        public void setLastUpdate(String lastUpdate) {
            this.lastUpdate = lastUpdate;
        }

        public String getNowUpdate() {
            return nowUpdate;
        }

        public void setNowUpdate(String nowUpdate) {
            this.nowUpdate = nowUpdate;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPlayerOffName() {
            return playerOffName;
        }

        public void setPlayerOffName(String playerOffName) {
            this.playerOffName = playerOffName;
        }

        public String getPlayerOffId() {
            return playerOffId;
        }

        public void setPlayerOffId(String playerOffId) {
            this.playerOffId = playerOffId;
        }

        public String getPlayerOnName() {
            return playerOnName;
        }

        public void setPlayerOnName(String playerOnName) {
            this.playerOnName = playerOnName;
        }

        public String getPlayerOnId() {
            return playerOnId;
        }

        public void setPlayerOnId(String playerOnId) {
            this.playerOnId = playerOnId;
        }
        public String getScorerName() {
            return scorerName;
        }

        public void setScorerName(String scorerName) {
            this.scorerName = scorerName;
        }

    }
}

