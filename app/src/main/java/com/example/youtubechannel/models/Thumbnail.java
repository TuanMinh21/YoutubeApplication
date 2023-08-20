package com.example.youtubechannel.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Thumbnail implements Serializable {

    @SerializedName("medium")
    private ThumbnailDetail thumbnailMedium;

    @SerializedName("high")
    private ThumbnailDetail thumbnailHigh;

    public ThumbnailDetail getThumbnailMedium() {
        return thumbnailMedium;
    }

    public void setThumbnailMedium(ThumbnailDetail thumbnailMedium) {
        this.thumbnailMedium = thumbnailMedium;
    }

    public ThumbnailDetail getThumbnailHigh() {
        return thumbnailHigh;
    }

    public void setThumbnailHigh(ThumbnailDetail thumbnailHigh) {
        this.thumbnailHigh = thumbnailHigh;
    }
}
