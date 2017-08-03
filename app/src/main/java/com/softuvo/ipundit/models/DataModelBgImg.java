package com.softuvo.ipundit.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class DataModelBgImg implements Serializable {
    @SerializedName("data")
    @Expose
    private BackgroundImagesModel data;

    public BackgroundImagesModel getData() {
        return data;
    }

    public void setData(BackgroundImagesModel data) {
        this.data = data;
    }

}

