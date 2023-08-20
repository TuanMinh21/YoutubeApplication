package com.example.youtubechannel.ui.video;

import com.example.youtubechannel.constant.Config;
import com.example.youtubechannel.constant.Constant;
import com.example.youtubechannel.data.DataManager;
import com.example.youtubechannel.models.VideoItem;
import com.example.youtubechannel.models.response.CommentResponse;
import com.example.youtubechannel.models.response.VideoItemResponse;
import com.example.youtubechannel.ui.base.BasePresenter;

import java.util.ArrayList;

import javax.inject.Inject;

import retrofit2.Retrofit;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class VideoPresenter extends BasePresenter<VideoMVPView> {

    @Inject
    public VideoPresenter(Retrofit mRetrofit, DataManager mDataManager) {
        super(mRetrofit, mDataManager);
    }

    @Override
    public void initialView(VideoMVPView mvpView) {
        super.initialView(mvpView);
    }

    public void getInforVideo(String videoId) {
        if (!isConnectToInternet()) {
            notifyNoNetwork();
        } else {
            getMvpView().showProgressDialog(true);
            mDataManager.getNetworkManager().getInforVideo(Config.VALUE_PART, videoId, Constant.VALUE_KEY)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<VideoItemResponse>() {
                        @Override
                        public void onCompleted() {
                            getMvpView().showProgressDialog(false);
                        }

                        @Override
                        public void onError(Throwable e) {
                            getMvpView().showProgressDialog(false);
                            getMvpView().onErrorCallApi(getErrorFromHttp(e).getCode());
                        }

                        @Override
                        public void onNext(VideoItemResponse videoItemResponse) {
                            if (videoItemResponse != null) {
                                ArrayList<VideoItem> listVideo = videoItemResponse.getListVideo();
                                if (listVideo != null && listVideo.size() > 0) {
                                    VideoItem videoItem = listVideo.get(0);
                                    getMvpView().loadInforVideo(videoItem);
                                }
                            }
                        }
                    });
        }
    }

    public void getListComment(String videoId) {
        if (!isConnectToInternet()) {
            notifyNoNetwork();
        } else {
            getMvpView().showProgressDialog(true);
            mDataManager.getNetworkManager().getListComment(Config.VALUE_PART, Constant.VALUE_KEY,
                    videoId, Constant.VALUE_MAX_RESULTS_COMMENT)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<CommentResponse>() {
                        @Override
                        public void onCompleted() {
                            getMvpView().showProgressDialog(false);
                        }

                        @Override
                        public void onError(Throwable e) {
                            getMvpView().showProgressDialog(false);
                            getMvpView().onErrorCallApi(getErrorFromHttp(e).getCode());
                        }

                        @Override
                        public void onNext(CommentResponse commentResponse) {
                            if (commentResponse != null) {
                                getMvpView().loadListComment(commentResponse.getListComment());
                            }
                        }
                    });
        }
    }
}
