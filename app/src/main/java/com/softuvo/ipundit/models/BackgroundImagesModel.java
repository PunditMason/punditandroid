package com.softuvo.ipundit.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BackgroundImagesModel implements Serializable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("background")
    @Expose
    private String background;
    @SerializedName("listeners")
    @Expose
    private String listeners;
    @SerializedName("broadcaster")
    @Expose
    private String broadcaster;
    @SerializedName("setting")
    @Expose
    private String setting;
    @SerializedName("about")
    @Expose
    private String about;
    @SerializedName("profile")
    @Expose
    private String profile;
    @SerializedName("login")
    @Expose
    private String login;
    @SerializedName("settingicon")
    @Expose
    private String settingicon;
    @SerializedName("abouticon")
    @Expose
    private String abouticon;
    @SerializedName("profileicon")
    @Expose
    private String profileicon;
    @SerializedName("loginicon")
    @Expose
    private String loginicon;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getListeners() {
        return listeners;
    }

    public void setListeners(String listeners) {
        this.listeners = listeners;
    }

    public String getBroadcaster() {
        return broadcaster;
    }

    public void setBroadcaster(String broadcaster) {
        this.broadcaster = broadcaster;
    }

    public String getSetting() {
        return setting;
    }

    public void setSetting(String setting) {
        this.setting = setting;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSettingicon() {
        return settingicon;
    }

    public void setSettingicon(String settingicon) {
        this.settingicon = settingicon;
    }

    public String getAbouticon() {
        return abouticon;
    }

    public void setAbouticon(String abouticon) {
        this.abouticon = abouticon;
    }

    public String getProfileicon() {
        return profileicon;
    }

    public void setProfileicon(String profileicon) {
        this.profileicon = profileicon;
    }

    public String getLoginicon() {
        return loginicon;
    }

    public void setLoginicon(String loginicon) {
        this.loginicon = loginicon;
    }

}
