package com.example.youtubechannel.ui.all_video;

import com.example.youtubechannel.constant.Config;
import com.example.youtubechannel.constant.Constant;
import com.example.youtubechannel.data.DataManager;
import com.example.youtubechannel.models.Video;
import com.example.youtubechannel.models.response.VideoResponse;
import com.example.youtubechannel.ui.base.BasePresenter;

import java.util.ArrayList;

import javax.inject.Inject;

import retrofit2.Retrofit;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AllVideoPresenter extends BasePresenter<AllVideoMVPView> {

    private final ArrayList<Video> mListVideo = new ArrayList<>();

    @Inject
    public AllVideoPresenter(Retrofit mRetrofit, DataManager mDataManager) {
        super(mRetrofit, mDataManager);
    }

    @Override
    public void initialView(AllVideoMVPView mvpView) {
        super.initialView(mvpView);
    }

    public void getAllVideo(String order, String nextPageToken) {
        if (!isConnectToInternet()) {
            notifyNoNetwork();
        } else {
            mDataManager.getNetworkManager().getAllVideo(Config.VALUE_PART, Constant.VALUE_CHANNEL_ID,
                    Constant.VALUE_KEY, order, Config.VALUE_TYPE_VIDEO, nextPageToken)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<VideoResponse>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                            getMvpView().onErrorCallApi(getErrorFromHttp(e).getCode());
                        }

                        @Override
                        public void onNext(VideoResponse videoResponse) {
                            if (videoResponse != null) {
                                mListVideo.addAll(videoResponse.getListVideo());
                                getMvpView().loadListVideo(mListVideo, videoResponse.getNextPageToken());
                            }
                        }
                    });
        }
    }
}
