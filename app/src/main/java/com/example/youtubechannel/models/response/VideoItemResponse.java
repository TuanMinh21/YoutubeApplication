package com.example.youtubechannel.models.response;

import com.example.youtubechannel.models.PageInfo;
import com.example.youtubechannel.models.VideoItem;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class VideoItemResponse {

    private String nextPageToken;
    private PageInfo pageInfo;

    @SerializedName("items")
    private ArrayList<VideoItem> listVideo;

    public String getNextPageToken() {
        return nextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public ArrayList<VideoItem> getListVideo() {
        return listVideo;
    }

    public void setListVideo(ArrayList<VideoItem> listVideo) {
        this.listVideo = listVideo;
    }
}
