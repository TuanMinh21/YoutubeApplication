package com.example.youtubechannel.models.response;

import com.example.youtubechannel.models.Video;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class VideoResponse {

    private String nextPageToken;

    @SerializedName("items")
    private ArrayList<Video> listVideo;

    public String getNextPageToken() {
        return nextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }

    public ArrayList<Video> getListVideo() {
        return listVideo;
    }

    public void setListVideo(ArrayList<Video> listVideo) {
        this.listVideo = listVideo;
    }
}
