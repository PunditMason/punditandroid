package com.softuvo.ipundit.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Nikhil Guleria on 7/3/2017.
 */

public class ListnerCountModel {
    public int getListnerCount() {
        return listnerCount;
    }

    public void setListnerCount(int listnerCount) {
        this.listnerCount = listnerCount;
    }

    @SerializedName("listeners_count")
    @Expose
    private int listnerCount;
    @SerializedName("broadcaster_count")
    @Expose
    private int broadcasterCount;

    public int getBroadcasterCount() {
        return broadcasterCount;
    }

    public void setBroadcasterCount(int broadcasterCount) {
        this.broadcasterCount = broadcasterCount;
    }
    @SerializedName("count")
    @Expose
    private int Count;

    public int getCount() {
        return Count;
    }

    public void setCount(int count) {
        Count = count;
    }
}
