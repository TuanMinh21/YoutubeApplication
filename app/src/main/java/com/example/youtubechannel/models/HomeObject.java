package com.example.youtubechannel.models;

import java.util.ArrayList;

public class HomeObject {

    private String title;
    private ArrayList<Video> listVideo;

    public HomeObject(String title, ArrayList<Video> listVideo) {
        this.title = title;
        this.listVideo = listVideo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Video> getListVideo() {
        return listVideo;
    }

    public void setListVideo(ArrayList<Video> listVideo) {
        this.listVideo = listVideo;
    }
}
