package com.softuvo.ipundit.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/*
 * Created by Neha Kalia on 15-06-2017.
 */

public class SportsNameModel implements Serializable{

    @SerializedName("data")
    @Expose
    private List<Sports> data = null;

    public List<Sports> getData() {
        return data;
    }

    public void setData(List<Sports> data) {
        this.data = data;
    }
    public class Sports implements Serializable{

        @SerializedName("tb_id")
        @Expose
        private String tbId;
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("avatar")
        @Expose
        private String avatar;
        @SerializedName("image_name")
        @Expose
        private String imageName;
        @SerializedName("cover_image")
        @Expose
        private String coverImage;
        @SerializedName("time_stamp")
        @Expose
        private String timeStamp;
        @SerializedName("broadcaster_count")
        @Expose
        private String broadcasteCount;
        @SerializedName("league")
        @Expose
        private List<League> league = null;

        public String getBroadcasteCount() {
            return broadcasteCount;
        }

        public void setBroadcasteCount(String broadcasteCount) {
            this.broadcasteCount = broadcasteCount;
        }

        public String getTbId() {
            return tbId;
        }

        public void setTbId(String tbId) {
            this.tbId = tbId;
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

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getImageName() {
            return imageName;
        }

        public void setImageName(String imageName) {
            this.imageName = imageName;
        }

        public String getCoverImage() {
            return coverImage;
        }

        public void setCoverImage(String coverImage) {
            this.coverImage = coverImage;
        }

        public String getTimeStamp() {
            return timeStamp;
        }

        public void setTimeStamp(String timeStamp) {
            this.timeStamp = timeStamp;
        }

        public List<League> getLeague() {
            return league;
        }

        public void setLeague(List<League> league) {
            this.league = league;
        }
        public class League implements Serializable {

            @SerializedName("tb_id")
            @Expose
            private String tbId;
            @SerializedName("id")
            @Expose
            private String id;
            @SerializedName("name")
            @Expose
            private String name;
            @SerializedName("avatar")
            @Expose
            private String avatar;
            @SerializedName("image_name")
            @Expose
            private String imageName;
            @SerializedName("cover_image")
            @Expose
            private String coverImage;
            @SerializedName("time_stamp")
            @Expose
            private String timeStamp;
            @SerializedName("competition_id")
            @Expose
            private String competitionId;
            @SerializedName("stage_id")
            @Expose
            private String stageId;
            @SerializedName("mark_image")
            @Expose
            private String markImage;
            @SerializedName("sport_id")
            @Expose
            private String sportId;
            @SerializedName("last_update")
            @Expose
            private String lastUpdate;
            @SerializedName("broadcaster_count")
            @Expose
            private String broadcasterCount;
            @SerializedName("selected_league")
            @Expose
            private Boolean selectedLeague;

            public String getBroadcasterCount() {
                return broadcasterCount;
            }

            public void setBroadcasterCount(String broadcasterCount) {
                this.broadcasterCount = broadcasterCount;
            }

            public String getTbId() {
                return tbId;
            }

            public void setTbId(String tbId) {
                this.tbId = tbId;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public Boolean getSelectedLeague() {
                return selectedLeague;
            }

            public void setSelectedLeague(Boolean selectedLeague) {
                this.selectedLeague = selectedLeague;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getImageName() {
                return imageName;
            }

            public void setImageName(String imageName) {
                this.imageName = imageName;
            }

            public String getCoverImage() {
                return coverImage;
            }

            public void setCoverImage(String coverImage) {
                this.coverImage = coverImage;
            }

            public String getTimeStamp() {
                return timeStamp;
            }

            public void setTimeStamp(String timeStamp) {
                this.timeStamp = timeStamp;
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

            public String getLastUpdate() {
                return lastUpdate;
            }

            public void setLastUpdate(String lastUpdate) {
                this.lastUpdate = lastUpdate;
            }

        }
    }
}
