package com.softuvo.ipundit.models;

/*
 * Created by Neha Kalia on 15-06-2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AboutUsModel {
    @SerializedName("data")
    @Expose
    private List<AboutUsDataModel> data = null;

    public List<AboutUsDataModel> getData() {
        return data;
    }

    public void setData(List<AboutUsDataModel> data) {
        this.data = data;
    }

    public class AboutUsDataModel {
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("content")
        @Expose
        private String content;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

    }
}
