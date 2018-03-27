package com.softuvo.ipundit.models;

/*
 * Created by softuvo on 22/3/18.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ScheduleModel implements Serializable{
    @SerializedName("schedules")
    @Expose
    private List<Schedule> schedules = null;

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }
    public class Schedule implements Serializable{

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
        @SerializedName("match")
        @Expose
        private Match match;
        @SerializedName("team")
        @Expose
        private Team team;

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

        public Match getMatch() {
            return match;
        }

        public void setMatch(Match match) {
            this.match = match;
        }

        public Team getTeam() {
            return team;
        }

        public void setTeam(Team team) {
            this.team = team;
        }
        public class Match implements Serializable{

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
            @SerializedName("chatChannelid")
            @Expose
            private String chatChannelid;
            @SerializedName("team1_name")
            @Expose
            private String team1Name;
            @SerializedName("team2_name")
            @Expose
            private String team2Name;
            @SerializedName("team1_twitter_id")
            @Expose
            private String team1TwitterId;
            @SerializedName("team2__twitter_id")
            @Expose
            private String team2TwitterId;

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

            public String getChatChannelid() {
                return chatChannelid;
            }

            public void setChatChannelid(String chatChannelid) {
                this.chatChannelid = chatChannelid;
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

            public String getTeam1TwitterId() {
                return team1TwitterId;
            }

            public void setTeam1TwitterId(String team1TwitterId) {
                this.team1TwitterId = team1TwitterId;
            }

            public String getTeam2TwitterId() {
                return team2TwitterId;
            }

            public void setTeam2TwitterId(String team2TwitterId) {
                this.team2TwitterId = team2TwitterId;
            }

        }

        public class Team implements Serializable{

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
            @SerializedName("chatChannelid")
            @Expose
            private String chatChannelid;
            @SerializedName("max_listeners")
            @Expose
            private String maxListeners;
            @SerializedName("username")
            @Expose
            private String username;
            @SerializedName("password")
            @Expose
            private String password;
            @SerializedName("twitter_id")
            @Expose
            private String twitterId;
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

            public String getChatChannelid() {
                return chatChannelid;
            }

            public void setChatChannelid(String chatChannelid) {
                this.chatChannelid = chatChannelid;
            }

            public String getMaxListeners() {
                return maxListeners;
            }

            public void setMaxListeners(String maxListeners) {
                this.maxListeners = maxListeners;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public String getTwitterId() {
                return twitterId;
            }

            public void setTwitterId(String twitterId) {
                this.twitterId = twitterId;
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

        }


    }
}
