package com.example.youtubechannel.ui.main;

import android.content.Context;

import com.example.youtubechannel.R;
import com.example.youtubechannel.constant.Config;
import com.example.youtubechannel.constant.Constant;
import com.example.youtubechannel.data.DataManager;
import com.example.youtubechannel.models.HomeObject;
import com.example.youtubechannel.models.response.PlaylistResponse;
import com.example.youtubechannel.models.response.VideoResponse;
import com.example.youtubechannel.ui.base.BasePresenter;

import java.util.ArrayList;

import javax.inject.Inject;

import retrofit2.Retrofit;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainPresenter extends BasePresenter<MainMVPView> {

    @Inject
    public MainPresenter(Retrofit mRetrofit, DataManager mDataManager) {
        super(mRetrofit, mDataManager);
    }

    @Override
    public void initialView(MainMVPView mvpView) {
        super.initialView(mvpView);
    }

    public void getListPlaylist() {
        if (!isConnectToInternet()) {
            notifyNoNetwork();
        } else {
            getMvpView().showProgressDialog(true);
            mDataManager.getNetworkManager().getListPlaylist(Config.VALUE_PART, Constant.VALUE_CHANNEL_ID,
                    Constant.VALUE_KEY, Constant.VALUE_MAX_RESULTS_PLAYLIST)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<PlaylistResponse>() {
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
                        public void onNext(PlaylistResponse playlistResponse) {
                            if (playlistResponse != null) {
                                getMvpView().loadListPlaylist(playlistResponse.getListPlaylist());
                            }
                        }
                    });
        }
    }

    public void getListPopularVideo() {
        if (!isConnectToInternet()) {
            notifyNoNetwork();
        } else {
            getMvpView().showProgressDialog(true);
            mDataManager.getNetworkManager().getListPopularVideo(Config.VALUE_PART, Constant.VALUE_CHANNEL_ID,
                    Constant.VALUE_KEY, Config.VALUE_ORDER_VIEW_COUNT, Config.VALUE_TYPE_VIDEO)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<VideoResponse>() {
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
                        public void onNext(VideoResponse videoResponse) {
                            if (videoResponse != null) {
                                getMvpView().loadListPopularVideo(videoResponse.getListVideo());
                            }
                        }
                    });
        }
    }

    public void getListHomeObject(Context context) {
        if (!isConnectToInternet()) {
            notifyNoNetwork();
        } else {
            getMvpView().showProgressDialog(true);
            mDataManager.getNetworkManager().getListLatestVideo(Config.VALUE_PART, Constant.VALUE_CHANNEL_ID,
                    Constant.VALUE_KEY, Config.VALUE_ORDER_DATE, Config.VALUE_TYPE_VIDEO,
                    Constant.VALUE_MAX_RESULTS_LATEST_VIDEO)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<VideoResponse>() {
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
                        public void onNext(VideoResponse videoResponse) {
                            if (videoResponse != null) {
                                ArrayList<HomeObject> listHomeObject = new ArrayList<>();
                                // add list video
                                listHomeObject.add(new HomeObject(context.getString(R.string.video_latest),
                                        videoResponse.getListVideo()));

                                getMvpView().loadListHomeObject(listHomeObject);
                            }
                        }
                    });
        }
    }
}