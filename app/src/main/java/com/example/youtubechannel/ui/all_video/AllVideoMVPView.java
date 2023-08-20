package com.example.youtubechannel.ui.all_video;

import com.example.youtubechannel.models.Video;
import com.example.youtubechannel.ui.base.BaseScreenMvpView;

import java.util.ArrayList;

interface AllVideoMVPView extends BaseScreenMvpView {

    void loadListVideo(ArrayList<Video> listVideo, String nextPageToken);
}
