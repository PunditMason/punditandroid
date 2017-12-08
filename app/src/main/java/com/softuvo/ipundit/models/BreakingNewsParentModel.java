package com.softuvo.ipundit.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BreakingNewsParentModel {
    @SerializedName("news")
    @Expose
    private List<BreakingNewsDatum> data = null;

    public List<BreakingNewsDatum> getData() {
        return data;
    }

    public void setData(List<BreakingNewsDatum> data) {
        this.data = data;
    }

}