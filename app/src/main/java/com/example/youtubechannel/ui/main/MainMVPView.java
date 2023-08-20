package com.example.youtubechannel.ui.main;

import com.example.youtubechannel.models.HomeObject;
import com.example.youtubechannel.models.Playlist;
import com.example.youtubechannel.models.Video;
import com.example.youtubechannel.ui.base.BaseScreenMvpView;

import java.util.ArrayList;

interface MainMVPView extends BaseScreenMvpView {

    void loadListPlaylist(ArrayList<Playlist> playlists);

    void loadListPopularVideo(ArrayList<Video> videos);

    void loadListHomeObject(ArrayList<HomeObject> listHomeObject);
}
