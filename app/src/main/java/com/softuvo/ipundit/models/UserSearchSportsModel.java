package com.softuvo.ipundit.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/*
 * Created by Neha Kalia on 02-08-2017.
 */

public class UserSearchSportsModel implements Serializable {
    @SerializedName("data")
    @Expose
    private List<UserDatum> data = null;

    public List<UserDatum> getData() {
        return data;
    }

    public void setData(List<UserDatum> data) {
        this.data = data;
    }
    public class UserDatum implements Serializable{
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("fb_id")
        @Expose
        private String fbId;
        @SerializedName("first_name")
        @Expose
        private String firstName;
        @SerializedName("last_name")
        @Expose
        private String lastName;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("password")
        @Expose
        private String password;
        @SerializedName("avatar")
        @Expose
        private String avatar;
        @SerializedName("cover_photo")
        @Expose
        private Object coverPhoto;
        @SerializedName("user_bio")
        @Expose
        private String userBio;
        @SerializedName("country_id")
        @Expose
        private Object countryId;
        @SerializedName("facebook")
        @Expose
        private String facebook;
        @SerializedName("twitter")
        @Expose
        private String twitter;
        @SerializedName("youtube")
        @Expose
        private String youtube;
        @SerializedName("datecreated")
        @Expose
        private String datecreated;
        @SerializedName("UUID")
        @Expose
        private String uUID;
        @SerializedName("live")
        @Expose
        private Integer live;
        @SerializedName("deviceToken")
        @Expose
        private String deviceToken;
        @SerializedName("deviceType")
        @Expose
        private String deviceType;
        @SerializedName("followCount")
        @Expose
        private Integer followCount;
        @SerializedName("followCheck")
        @Expose
        private String followCheck;

        public String getFollowCheck() {
            return followCheck;
        }

        public void setFollowCheck(String followCheck) {
            this.followCheck = followCheck;
        }

        public String getuUID() {
            return uUID;
        }

        public void setuUID(String uUID) {
            this.uUID = uUID;
        }

        public String getDeviceToken() {
            return deviceToken;
        }

        public void setDeviceToken(String deviceToken) {
            this.deviceToken = deviceToken;
        }

        public String getDeviceType() {
            return deviceType;
        }

        public void setDeviceType(String deviceType) {
            this.deviceType = deviceType;
        }

        public Integer getFollowCount() {
            return followCount;
        }

        public void setFollowCount(Integer followCount) {
            this.followCount = followCount;
        }

        public Integer getFollowingCount() {
            return followingCount;
        }

        public void setFollowingCount(Integer followingCount) {
            this.followingCount = followingCount;
        }

        @SerializedName("followingCount")
        @Expose

        private Integer followingCount;
        @SerializedName("channel_info")
        @Expose
        private List<ChannelInfo> channelInfo = null;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getFbId() {
            return fbId;
        }

        public void setFbId(String fbId) {
            this.fbId = fbId;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public Object getCoverPhoto() {
            return coverPhoto;
        }

        public void setCoverPhoto(Object coverPhoto) {
            this.coverPhoto = coverPhoto;
        }

        public String getUserBio() {
            return userBio;
        }

        public void setUserBio(String userBio) {
            this.userBio = userBio;
        }

        public Object getCountryId() {
            return countryId;
        }

        public void setCountryId(Object countryId) {
            this.countryId = countryId;
        }

        public String getFacebook() {
            return facebook;
        }

        public void setFacebook(String facebook) {
            this.facebook = facebook;
        }

        public String getTwitter() {
            return twitter;
        }

        public void setTwitter(String twitter) {
            this.twitter = twitter;
        }

        public String getYoutube() {
            return youtube;
        }

        public void setYoutube(String youtube) {
            this.youtube = youtube;
        }

        public String getDatecreated() {
            return datecreated;
        }

        public void setDatecreated(String datecreated) {
            this.datecreated = datecreated;
        }

        public String getUUID() {
            return uUID;
        }

        public void setUUID(String uUID) {
            this.uUID = uUID;
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

        public class ChannelInfo implements Serializable {

            @SerializedName("channel")
            @Expose
            private Channel channel;
            @SerializedName("match_info")
            @Expose
            private MatchInfo matchInfo;
            @SerializedName("team_info")
            @Expose
            private TeamInfo teamInfo;

            public Channel getChannel() {
                return channel;
            }

            public void setChannel(Channel channel) {
                this.channel = channel;
            }

            public MatchInfo getMatchInfo() {
                return matchInfo;
            }

            public void setMatchInfo(MatchInfo matchInfo) {
                this.matchInfo = matchInfo;
            }

            public TeamInfo getTeamInfo() {
                return teamInfo;
            }

            public void setTeamInfo(TeamInfo teamInfo) {
                this.teamInfo = teamInfo;
            }

            public class Channel implements Serializable {

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
                @SerializedName("mark_image")
                @Expose
                private String markImage;

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

                public String getMarkImage() {
                    return markImage;
                }

                public void setMarkImage(String markImage) {
                    this.markImage = markImage;
                }

            }

            public class MatchInfo implements Serializable {

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
                @SerializedName("team1_name")
                @Expose
                private Object team1Name;
                @SerializedName("team2_name")
                @Expose
                private Object team2Name;
                @SerializedName("chatChannelid")
                @Expose
                private String chatChannelid;

                public String getChatChannelid() {
                    return chatChannelid;
                }

                public void setChatChannelid(String chatChannelid) {
                    this.chatChannelid = chatChannelid;
                }

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

                public Object getTeam1Name() {
                    return team1Name;
                }

                public void setTeam1Name(Object team1Name) {
                    this.team1Name = team1Name;
                }

                public Object getTeam2Name() {
                    return team2Name;
                }

                public void setTeam2Name(Object team2Name) {
                    this.team2Name = team2Name;
                }

            }

            public class TeamInfo implements Serializable{

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
                @SerializedName("chatChannelid")
                @Expose
                private String chatChannelid;

                public String getChatChannelid() {
                    return chatChannelid;
                }

                public void setChatChannelid(String chatChannelid) {
                    this.chatChannelid = chatChannelid;
                }

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
}
