package com.example.youtubechannel.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Snippet implements Serializable {

    private String title;
    private String publishedAt;
    private String description;

    @SerializedName("thumbnails")
    private Thumbnail thumbnail;
    private ResourceId resourceId;

    @SerializedName("topLevelComment")
    private LevelComment levelComment;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }

    public ResourceId getResourceId() {
        return resourceId;
    }

    public void setResourceId(ResourceId resourceId) {
        this.resourceId = resourceId;
    }

    public LevelComment getLevelComment() {
        return levelComment;
    }

    public void setLevelComment(LevelComment levelComment) {
        this.levelComment = levelComment;
    }
}
