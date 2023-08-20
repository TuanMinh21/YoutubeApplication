package com.example.youtubechannel.data.networking;

import com.example.youtubechannel.BuildConfig;
import com.example.youtubechannel.constant.Config;
import com.example.youtubechannel.constant.Constant;
import com.example.youtubechannel.models.response.ChannelResponse;
import com.example.youtubechannel.models.response.CommentResponse;
import com.example.youtubechannel.models.response.PlaylistResponse;
import com.example.youtubechannel.models.response.VideoItemResponse;
import com.example.youtubechannel.models.response.VideoResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface YoutubeChannelService {

    class Creator {
        public static Retrofit newRetrofitInstance() {
            OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();
            okBuilder.readTimeout(30, TimeUnit.SECONDS);
            okBuilder.connectTimeout(30, TimeUnit.SECONDS);
            okBuilder.retryOnConnectionFailure(true);

            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                okBuilder.addInterceptor(interceptor);
            }
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

            return new Retrofit.Builder()
                    .baseUrl(Constant.HOST)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(okBuilder.build())
                    .build();
        }
    }

    @GET("playlists")
    Observable<PlaylistResponse> getListPlaylist(@Query(Config.KEY_PART) String part,
                                                 @Query(Config.KEY_CHANNEL_ID) String channelId,
                                                 @Query(Config.KEY) String key,
                                                 @Query(Config.KEY_MAX_RESULTS) int maxResults);

    @GET("playlists")
    Observable<PlaylistResponse> getListPlaylist(@Query(Config.KEY_PART) String part,
                                                 @Query(Config.KEY_CHANNEL_ID) String channelId,
                                                 @Query(Config.KEY) String key,
                                                 @Query(Config.KEY_PAGE_TOKEN) String nextPageToken);

    @GET("search")
    Observable<VideoResponse> getListPopularVideo(@Query(Config.KEY_PART) String part,
                                                  @Query(Config.KEY_CHANNEL_ID) String channelId,
                                                  @Query(Config.KEY) String key,
                                                  @Query(Config.KEY_ORDER) String order,
                                                  @Query(Config.KEY_TYPE) String type);

    @GET("search")
    Observable<VideoResponse> getListLatestVideo(@Query(Config.KEY_PART) String part,
                                                 @Query(Config.KEY_CHANNEL_ID) String channelId,
                                                 @Query(Config.KEY) String key,
                                                 @Query(Config.KEY_ORDER) String order,
                                                 @Query(Config.KEY_TYPE) String type,
                                                 @Query(Config.KEY_MAX_RESULTS) int maxResults);

    @GET("search")
    Observable<VideoResponse> getAllVideo(@Query(Config.KEY_PART) String part,
                                          @Query(Config.KEY_CHANNEL_ID) String channelId,
                                          @Query(Config.KEY) String key,
                                          @Query(Config.KEY_ORDER) String order,
                                          @Query(Config.KEY_TYPE) String type,
                                          @Query(Config.KEY_PAGE_TOKEN) String nextPageToken);

    @GET("playlistItems")
    Observable<VideoItemResponse> getListVideoOfPlaylist(@Query(Config.KEY_PART) String part,
                                                         @Query(Config.KEY_PLAYLIST_ID) String playlistId,
                                                         @Query(Config.KEY) String key,
                                                         @Query(Config.KEY_PAGE_TOKEN) String nextPageToken);

    @GET("channels")
    Observable<ChannelResponse> getInforChannel(@Query(Config.KEY_PART) String part,
                                                @Query(Config.KEY_ID) String id,
                                                @Query(Config.KEY) String key);

    @GET("videos")
    Observable<VideoItemResponse> getInforVideo(@Query(Config.KEY_PART) String part,
                                                @Query(Config.KEY_ID) String id,
                                                @Query(Config.KEY) String key);

    @GET("commentThreads")
    Observable<CommentResponse> getListComment(@Query(Config.KEY_PART) String part,
                                               @Query(Config.KEY) String key,
                                               @Query(Config.KEY_VIDEO_ID) String videoId,
                                               @Query(Config.KEY_MAX_RESULTS) int maxResults);

    @GET("commentThreads")
    Observable<CommentResponse> getListComment(@Query(Config.KEY_PART) String part,
                                               @Query(Config.KEY) String key,
                                               @Query(Config.KEY_VIDEO_ID) String videoId,
                                               @Query(Config.KEY_MAX_RESULTS) int maxResults,
                                               @Query(Config.KEY_PAGE_TOKEN) String nextPageToken);
}
