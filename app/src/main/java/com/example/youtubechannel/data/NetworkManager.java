package com.example.youtubechannel.data;

import com.example.youtubechannel.data.networking.YoutubeChannelService;
import com.example.youtubechannel.models.response.ChannelResponse;
import com.example.youtubechannel.models.response.CommentResponse;
import com.example.youtubechannel.models.response.PlaylistResponse;
import com.example.youtubechannel.models.response.VideoItemResponse;
import com.example.youtubechannel.models.response.VideoResponse;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

@Singleton
public class NetworkManager {

    private final YoutubeChannelService mYoutubeChannelService;

    @Inject
    public NetworkManager(YoutubeChannelService youtubeChannelService) {
        this.mYoutubeChannelService = youtubeChannelService;
    }

    public Observable<PlaylistResponse> getListPlaylist(String part, String channelId, String key, int maxResults) {
        return mYoutubeChannelService.getListPlaylist(part, channelId, key, maxResults);
    }

    public Observable<PlaylistResponse> getListPlaylist(String part, String channelId, String key, String nextPageToken) {
        return mYoutubeChannelService.getListPlaylist(part, channelId, key, nextPageToken);
    }

    public Observable<VideoResponse> getListPopularVideo(String part, String channelId, String key, String order, String type) {
        return mYoutubeChannelService.getListPopularVideo(part, channelId, key, order, type);
    }

    public Observable<VideoResponse> getListLatestVideo(String part, String channelId, String key, String order, String type, int maxResults) {
        return mYoutubeChannelService.getListLatestVideo(part, channelId, key, order, type, maxResults);
    }

    public Observable<VideoResponse> getAllVideo(String part, String channelId, String key, String order, String type, String nextPageToken) {
        return mYoutubeChannelService.getAllVideo(part, channelId, key, order, type, nextPageToken);
    }

    public Observable<VideoItemResponse> getListVideoOfPlaylist(String part, String playlistId, String key, String nextPageToken) {
        return mYoutubeChannelService.getListVideoOfPlaylist(part, playlistId, key, nextPageToken);
    }

    public Observable<ChannelResponse> getInforChannel(String part, String id, String key) {
        return mYoutubeChannelService.getInforChannel(part, id, key);
    }

    public Observable<VideoItemResponse> getInforVideo(String part, String id, String key) {
        return mYoutubeChannelService.getInforVideo(part, id, key);
    }

    public Observable<CommentResponse> getListComment(String part, String key, String videoId, int maxResults) {
        return mYoutubeChannelService.getListComment(part, key, videoId, maxResults);
    }

    public Observable<CommentResponse> getListComment(String part, String key, String videoId, int maxResults, String nextPageToken) {
        return mYoutubeChannelService.getListComment(part, key, videoId, maxResults, nextPageToken);
    }
}
