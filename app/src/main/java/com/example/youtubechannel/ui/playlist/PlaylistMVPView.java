package com.example.youtubechannel.ui.playlist;

import com.example.youtubechannel.models.Playlist;
import com.example.youtubechannel.ui.base.BaseScreenMvpView;

import java.util.ArrayList;

interface PlaylistMVPView extends BaseScreenMvpView {

    void loadListPlaylist(ArrayList<Playlist> playlists, String nextPageToken);
}
