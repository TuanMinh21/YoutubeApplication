package com.example.youtubechannel.models;

import java.io.Serializable;

public class VideoItem implements Serializable {

    private Snippet snippet;

    public Snippet getSnippet() {
        return snippet;
    }

    public void setSnippet(Snippet snippet) {
        this.snippet = snippet;
    }
}
