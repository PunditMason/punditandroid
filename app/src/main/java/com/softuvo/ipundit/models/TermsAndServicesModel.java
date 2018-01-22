package com.softuvo.ipundit.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/*
 * Created by Neha Kalia on 15-06-2017.
 */

public class TermsAndServicesModel {
    @SerializedName("data")
    @Expose
    private  TermsAndServicesDataModal data;

    public TermsAndServicesDataModal getData() {
        return data;
    }

   public void setData(TermsAndServicesDataModal data) {
        this.data = data;
    }
    public class TermsAndServicesDataModal {

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



