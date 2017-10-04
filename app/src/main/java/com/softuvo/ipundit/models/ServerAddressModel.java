package com.softuvo.ipundit.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServerAddressModel {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("scope")
    @Expose
    private String scope;
    @SerializedName("serverAddress")
    @Expose
    private String serverAddress;
    @SerializedName("region")
    @Expose
    private String region;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

}
