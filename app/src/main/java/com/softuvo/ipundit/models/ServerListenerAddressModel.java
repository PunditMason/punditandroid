package com.softuvo.ipundit.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServerListenerAddressModel {
    @SerializedName("identifier")
    @Expose
    private String identifier;
    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("availabilityZone")
    @Expose
    private String availabilityZone;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("launchConfig")
    @Expose
    private String launchConfig;
    @SerializedName("capacity")
    @Expose
    private Integer capacity;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAvailabilityZone() {
        return availabilityZone;
    }

    public void setAvailabilityZone(String availabilityZone) {
        this.availabilityZone = availabilityZone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLaunchConfig() {
        return launchConfig;
    }

    public void setLaunchConfig(String launchConfig) {
        this.launchConfig = launchConfig;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
}
