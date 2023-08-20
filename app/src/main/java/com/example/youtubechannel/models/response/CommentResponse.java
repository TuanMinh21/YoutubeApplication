package com.example.youtubechannel.models.response;

import com.example.youtubechannel.models.Comment;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CommentResponse {

    private String nextPageToken;

    @SerializedName("items")
    private ArrayList<Comment> listComment;

    public String getNextPageToken() {
        return nextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }

    public ArrayList<Comment> getListComment() {
        return listComment;
    }

    public void setListComment(ArrayList<Comment> listComment) {
        this.listComment = listComment;
    }
}
