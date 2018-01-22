package com.softuvo.ipundit.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/*
 * Created by Neha Kalia on 30-06-2017.
 */

public class FollowCheckModel {

    @SerializedName("info")
    @Expose
    private Info info;
    @SerializedName("listener_id")
    @Expose
    private Integer listenerId;

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public Integer getListenerId() {
        return listenerId;
    }

    public void setListenerId(Integer listenerId) {
        this.listenerId = listenerId;
    }

    public class Info {

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
        private String countryId;
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
        @SerializedName("follow")
        @Expose
        private Integer follow;
        @SerializedName("follower_count")
        @Expose
        private Integer followerCount;
        @SerializedName("following_count")
        @Expose
        private Integer following_count;

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

        public String getCountryId() {
            return countryId;
        }

        public void setCountryId(String countryId) {
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

        public Integer getFollow() {
            return follow;
        }

        public void setFollow(Integer follow) {
            this.follow = follow;
        }

        public Integer getFollowerCount() {
            return followerCount;
        }

        public void setFollowerCount(Integer followerCount) {
            this.followerCount = followerCount;
        }

        public Integer getFollowing_count() {
            return following_count;
        }

        public void setFollowing_count(Integer following_count) {
            this.following_count = following_count;
        }
    }
}

