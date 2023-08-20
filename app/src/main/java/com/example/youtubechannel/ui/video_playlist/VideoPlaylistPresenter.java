package com.example.youtubechannel.ui.video_playlist;

import com.example.youtubechannel.constant.Config;
import com.example.youtubechannel.constant.Constant;
import com.example.youtubechannel.data.DataManager;
import com.example.youtubechannel.models.VideoItem;
import com.example.youtubechannel.models.response.VideoItemResponse;
import com.example.youtubechannel.ui.base.BasePresenter;

import java.util.ArrayList;

import javax.inject.Inject;

import retrofit2.Retrofit;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class VideoPlaylistPresenter extends BasePresenter<VideoPlaylistMVPView> {

    private final ArrayList<VideoItem> mListVideo = new ArrayList<>();

    @Inject
    public VideoPlaylistPresenter(Retrofit mRetrofit, DataManager mDataManager) {
        super(mRetrofit, mDataManager);
    }

    @Override
    public void initialView(VideoPlaylistMVPView mvpView) {
        super.initialView(mvpView);
    }

    public void getListVideoOfPlaylist(String playlistId, String nextPageToken) {
        if (!isConnectToInternet()) {
            notifyNoNetwork();
        } else {
            mDataManager.getNetworkManager().getListVideoOfPlaylist(Config.VALUE_PART, playlistId, Constant.VALUE_KEY, nextPageToken)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<VideoItemResponse>() {
                        @Override
                        public void onCompleted() {}

                        @Override
                        public void onError(Throwable e) {
                            getMvpView().onErrorCallApi(getErrorFromHttp(e).getCode());
                        }

                        @Override
                        public void onNext(VideoItemResponse videoItemResponse) {
                            if (videoItemResponse != null) {
                                mListVideo.addAll(videoItemResponse.getListVideo());
                                getMvpView().loadListVideo(mListVideo, videoItemResponse.getNextPageToken());
                            }
                        }
                    });
        }
    }
}
