package com.softuvo.ipundit.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/*
 * Created by softuvo on 29/1/18.
 */

public class AddsModel implements Serializable{

    @SerializedName("adsDetail")
    @Expose
    private AdsDetail adsDetail;

    public AdsDetail getAdsDetail() {
        return adsDetail;
    }

    public void setAdsDetail(AdsDetail adsDetail) {
        this.adsDetail = adsDetail;
    }

    public class AdsDetail implements Serializable{

        @SerializedName("playlist")
        @Expose
        private List<Playlist> playlist = null;
        @SerializedName("pause_flag")
        @Expose
        private String pauseFlag;

        public List<Playlist> getPlaylist() {
            return playlist;
        }

        public void setPlaylist(List<Playlist> playlist) {
            this.playlist = playlist;
        }

        public String getPauseFlag() {
            return pauseFlag;
        }

        public void setPauseFlag(String pauseFlag) {
            this.pauseFlag = pauseFlag;
        }

        public class Playlist implements Serializable{

            @SerializedName("id")
            @Expose
            private String id;
            @SerializedName("audio_name")
            @Expose
            private String audioName;
            @SerializedName("ads_audio")
            @Expose
            private String adsAudio;
            @SerializedName("ads_position")
            @Expose
            private String adsPosition;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getAudioName() {
                return audioName;
            }

            public void setAudioName(String audioName) {
                this.audioName = audioName;
            }

            public String getAdsAudio() {
                return adsAudio;
            }

            public void setAdsAudio(String adsAudio) {
                this.adsAudio = adsAudio;
            }

            public String getAdsPosition() {
                return adsPosition;
            }

            public void setAdsPosition(String adsPosition) {
                this.adsPosition = adsPosition;
            }
        }
    }
}
