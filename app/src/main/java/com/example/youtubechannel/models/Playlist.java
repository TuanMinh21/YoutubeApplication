package com.example.youtubechannel.models;

import java.io.Serializable;

public class Playlist implements Serializable {

    private String id;
    private Snippet snippet;
    private int countTotalVideo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Snippet getSnippet() {
        return snippet;
    }

    public void setSnippet(Snippet snippet) {
        this.snippet = snippet;
    }

    public int getCountTotalVideo() {
        return countTotalVideo;
    }

    public void setCountTotalVideo(int countTotalVideo) {
        this.countTotalVideo = countTotalVideo;
    }
}
