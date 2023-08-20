package com.example.youtubechannel.ui.video_playlist;

import com.example.youtubechannel.models.VideoItem;
import com.example.youtubechannel.ui.base.BaseScreenMvpView;

import java.util.ArrayList;

interface VideoPlaylistMVPView extends BaseScreenMvpView {

    void loadListVideo(ArrayList<VideoItem> listVideo, String nextPageToken);
}
