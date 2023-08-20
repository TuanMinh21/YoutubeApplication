package com.example.youtubechannel.models.response;

import com.example.youtubechannel.models.PageInfo;
import com.example.youtubechannel.models.Playlist;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PlaylistResponse {

    private String nextPageToken;

    @SerializedName("items")
    private ArrayList<Playlist> listPlaylist;

    private PageInfo pageInfo;

    public String getNextPageToken() {
        return nextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }

    public ArrayList<Playlist> getListPlaylist() {
        return listPlaylist;
    }

    public void setListPlaylist(ArrayList<Playlist> listPlaylist) {
        this.listPlaylist = listPlaylist;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }
}
