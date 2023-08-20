package com.example.youtubechannel.models.response;

import com.example.youtubechannel.models.Channel;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ChannelResponse {

    @SerializedName("items")
    private ArrayList<Channel> listChannel;

    public ArrayList<Channel> getListChannel() {
        return listChannel;
    }

    public void setListChannel(ArrayList<Channel> listChannel) {
        this.listChannel = listChannel;
    }
}