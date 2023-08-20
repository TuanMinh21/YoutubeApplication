package com.example.youtubechannel.models;

import java.io.Serializable;

public class ItemId implements Serializable {

    private String videoId;

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }
}
