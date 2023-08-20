package com.example.youtubechannel.ui.playlist;

import com.example.youtubechannel.constant.Config;
import com.example.youtubechannel.constant.Constant;
import com.example.youtubechannel.data.DataManager;
import com.example.youtubechannel.models.Playlist;
import com.example.youtubechannel.models.response.PlaylistResponse;
import com.example.youtubechannel.ui.base.BasePresenter;

import java.util.ArrayList;

import javax.inject.Inject;

import retrofit2.Retrofit;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class PlaylistPresenter extends BasePresenter<PlaylistMVPView> {

    private final ArrayList<Playlist> mListPlaylist = new ArrayList<>();

    @Inject
    public PlaylistPresenter(Retrofit mRetrofit, DataManager mDataManager) {
        super(mRetrofit, mDataManager);
    }

    @Override
    public void initialView(PlaylistMVPView mvpView) {
        super.initialView(mvpView);
    }

    public void getListPlaylist(String nextPageToken) {
        if (!isConnectToInternet()) {
            notifyNoNetwork();
        } else {
            mDataManager.getNetworkManager().getListPlaylist(Config.VALUE_PART, Constant.VALUE_CHANNEL_ID,
                    Constant.VALUE_KEY, nextPageToken)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<PlaylistResponse>() {
                        @Override
                        public void onCompleted() {}

                        @Override
                        public void onError(Throwable e) {
                            getMvpView().onErrorCallApi(getErrorFromHttp(e).getCode());
                        }

                        @Override
                        public void onNext(PlaylistResponse playlistResponse) {
                            if (playlistResponse != null) {
                                mListPlaylist.addAll(playlistResponse.getListPlaylist());
                                getMvpView().loadListPlaylist(mListPlaylist, playlistResponse.getNextPageToken());
                            }
                        }
                    });
        }
    }
}
