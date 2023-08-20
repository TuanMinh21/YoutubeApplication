package com.example.youtubechannel.models;

import com.google.gson.annotations.SerializedName;

public class LevelComment {

    private String id;

    @SerializedName("snippet")
    private SnippetComment snippetComment;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SnippetComment getSnippetComment() {
        return snippetComment;
    }

    public void setSnippetComment(SnippetComment snippetComment) {
        this.snippetComment = snippetComment;
    }
}
