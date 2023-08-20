package com.example.youtubechannel.models;

import java.io.Serializable;

public class ResourceId implements Serializable {

    private String videoId;

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }
}
