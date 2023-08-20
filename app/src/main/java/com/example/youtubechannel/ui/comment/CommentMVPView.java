package com.example.youtubechannel.ui.comment;

import com.example.youtubechannel.models.Comment;
import com.example.youtubechannel.ui.base.BaseScreenMvpView;

import java.util.ArrayList;

interface CommentMVPView extends BaseScreenMvpView {

    void loadListComment(ArrayList<Comment> listComment, String nextPageToken);
}
