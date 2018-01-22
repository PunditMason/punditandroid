package com.softuvo.ipundit.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/*
 * Created by Neha Kalia on 15-06-2017.
 */

public class LiveFeedsNewModel implements Serializable {

    @SerializedName("match")
    @Expose
    private Match match;

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }
    public class Match implements Serializable {

        @SerializedName("competition")
        @Expose
        private String competition;
        @SerializedName("round")
        @Expose
        private String round;
        @SerializedName("date")
        @Expose
        private String date;
        @SerializedName("time")
        @Expose
        private String time;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("teams")
        @Expose
        private List<Team> teams = null;
        @SerializedName("attendance")
        @Expose
        private String attendance;
        @SerializedName("venue")
        @Expose
        private String venue;

        public String getCompetition() {
            return competition;
        }

        public void setCompetition(String competition) {
            this.competition = competition;
        }

        public String getRound() {
            return round;
        }

        public void setRound(String round) {
            this.round = round;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public List<Team> getTeams() {
            return teams;
        }

        public void setTeams(List<Team> teams) {
            this.teams = teams;
        }

        public String getAttendance() {
            return attendance;
        }

        public void setAttendance(String attendance) {
            this.attendance = attendance;
        }

        public String getVenue() {
            return venue;
        }

        public void setVenue(String venue) {
            this.venue = venue;
        }
        public class Team implements Serializable{

            @SerializedName("side")
            @Expose
            private String side;
            @SerializedName("no")
            @Expose
            private String no;
            @SerializedName("name")
            @Expose
            private String name;
            @SerializedName("halfTimeScore")
            @Expose
            private String halfTimeScore;
            @SerializedName("score")
            @Expose
            private String score;
            @SerializedName("goals")
            @Expose
            private List<Goal> goals = null;
            @SerializedName("players")
            @Expose
            private List<Player> players = null;

            public String getSide() {
                return side;
            }

            public void setSide(String side) {
                this.side = side;
            }

            public String getNo() {
                return no;
            }

            public void setNo(String no) {
                this.no = no;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getHalfTimeScore() {
                return halfTimeScore;
            }

            public void setHalfTimeScore(String halfTimeScore) {
                this.halfTimeScore = halfTimeScore;
            }

            public String getScore() {
                return score;
            }

            public void setScore(String score) {
                this.score = score;
            }

            public List<Goal> getGoals() {
                return goals;
            }

            public void setGoals(List<Goal> goals) {
                this.goals = goals;
            }

            public List<Player> getPlayers() {
                return players;
            }

            public void setPlayers(List<Player> players) {
                this.players = players;
            }

            public class Goal implements Serializable{

                @SerializedName("goal")
                @Expose
                private String goal;

                public String getGoal() {
                    return goal;
                }

                public void setGoal(String goal) {
                    this.goal = goal;
                }
            }
            public class Player implements Serializable{

                @SerializedName("shirtNo")
                @Expose
                private String shirtNo;
                @SerializedName("name")
                @Expose
                private String name;
                @SerializedName("caution")
                @Expose
                private Caution caution;
                @SerializedName("substitution")
                @Expose
                private Substitution substitution;

                public String getShirtNo() {
                    return shirtNo;
                }

                public void setShirtNo(String shirtNo) {
                    this.shirtNo = shirtNo;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public Caution getCaution() {
                    return caution;
                }

                public void setCaution(Caution caution) {
                    this.caution = caution;
                }

                public Substitution getSubstitution() {
                    return substitution;
                }

                public void setSubstitution(Substitution substitution) {
                    this.substitution = substitution;
                }

                public class Caution implements Serializable{

                    @SerializedName("minute")
                    @Expose
                    private String minute;

                    public String getMinute() {
                        return minute;
                    }

                    public void setMinute(String minute) {
                        this.minute = minute;
                    }

                }

                public class Substitution implements Serializable{

                    @SerializedName("replacedBy")
                    @Expose
                    private String replacedBy;
                    @SerializedName("minute")
                    @Expose
                    private String minute;

                    public String getReplacedBy() {
                        return replacedBy;
                    }

                    public void setReplacedBy(String replacedBy) {
                        this.replacedBy = replacedBy;
                    }

                    public String getMinute() {
                        return minute;
                    }

                    public void setMinute(String minute) {
                        this.minute = minute;
                    }

                }

            }

        }

    }
}
