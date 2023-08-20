package com.example.youtubechannel.ui.video;

import com.example.youtubechannel.models.Comment;
import com.example.youtubechannel.models.VideoItem;
import com.example.youtubechannel.ui.base.BaseScreenMvpView;

import java.util.ArrayList;

interface VideoMVPView extends BaseScreenMvpView {

    void loadInforVideo(VideoItem videoItem);

    void loadListComment(ArrayList<Comment> listComment);

    @Override
    default void onErrorCallApi(int code) {

    }
}
