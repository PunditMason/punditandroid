package com.softuvo.ipundit.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by softuvo on 27-09-2017.
 */

public class LoginUserModel {
    @SerializedName("user")
    @Expose
    private List<User> user = null;
    @SerializedName("responsestatus")
    @Expose
    private Boolean responsestatus;
    @SerializedName("message")
    @Expose
    private String message;

    public List<User> getUser() {
        return user;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }

    public Boolean getResponsestatus() {
        return responsestatus;
    }

    public void setResponsestatus(Boolean responsestatus) {
        this.responsestatus = responsestatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public class User {

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
        private Object facebook;
        @SerializedName("twitter")
        @Expose
        private Object twitter;
        @SerializedName("youtube")
        @Expose
        private Object youtube;
        @SerializedName("datecreated")
        @Expose
        private String datecreated;
        @SerializedName("UUID")
        @Expose
        private String uUID;
        @SerializedName("deviceToken")
        @Expose
        private String deviceToken;
        @SerializedName("deviceType")
        @Expose
        private String deviceType;
        @SerializedName("loginType")
        @Expose
        private String loginType;
        @SerializedName("allow_score")
        @Expose
        private String allowScore;
        @SerializedName("email_verified")
        @Expose
        private String emailVerified;
        @SerializedName("userToken")
        @Expose
        private String userToken;

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

        public Object getFacebook() {
            return facebook;
        }

        public void setFacebook(Object facebook) {
            this.facebook = facebook;
        }

        public Object getTwitter() {
            return twitter;
        }

        public void setTwitter(Object twitter) {
            this.twitter = twitter;
        }

        public Object getYoutube() {
            return youtube;
        }

        public void setYoutube(Object youtube) {
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

        public String getLoginType() {
            return loginType;
        }

        public void setLoginType(String loginType) {
            this.loginType = loginType;
        }

        public String getAllowScore() {
            return allowScore;
        }

        public void setAllowScore(String allowScore) {
            this.allowScore = allowScore;
        }

        public String getEmailVerified() {
            return emailVerified;
        }

        public void setEmailVerified(String emailVerified) {
            this.emailVerified = emailVerified;
        }

        public String getUserToken() {
            return userToken;
        }

        public void setUserToken(String userToken) {
            this.userToken = userToken;
        }
    }
}
