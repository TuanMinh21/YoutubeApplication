package com.example.youtubechannel.injection.components;

import com.example.youtubechannel.injection.modules.ActivityModule;
import com.example.youtubechannel.ui.about_us.AboutUsActivity;
import com.example.youtubechannel.ui.all_video.AllVideoActivity;
import com.example.youtubechannel.ui.comment.CommentActivity;
import com.example.youtubechannel.ui.main.MainActivity;
import com.example.youtubechannel.ui.my_channel.MyChannelActivity;
import com.example.youtubechannel.ui.playlist.PlaylistActivity;
import com.example.youtubechannel.ui.splash.SplashActivity;
import com.example.youtubechannel.ui.video.VideoActivity;
import com.example.youtubechannel.ui.video_playlist.VideoPlaylistActivity;

import dagger.Subcomponent;

@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(SplashActivity loginActivity);

    void inject(MainActivity mainActivity);

    void inject(VideoPlaylistActivity videoPlaylistActivity);

    void inject(PlaylistActivity playlistActivity);

    void inject(AllVideoActivity allVideoActivity);

    void inject(MyChannelActivity myChannelActivity);

    void inject(VideoActivity videoActivity);

    void inject(CommentActivity commentActivity);

    void inject(AboutUsActivity contactUsActivity);
}
