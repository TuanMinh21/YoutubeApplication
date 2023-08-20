package com.example.youtubechannel.ui.my_channel;

import com.example.youtubechannel.constant.Config;
import com.example.youtubechannel.constant.Constant;
import com.example.youtubechannel.data.DataManager;
import com.example.youtubechannel.models.Channel;
import com.example.youtubechannel.models.response.ChannelResponse;
import com.example.youtubechannel.ui.base.BasePresenter;

import java.util.ArrayList;

import javax.inject.Inject;

import retrofit2.Retrofit;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MyChannelPresenter extends BasePresenter<MyChannelMVPView> {

    @Inject
    public MyChannelPresenter(Retrofit mRetrofit, DataManager mDataManager) {
        super(mRetrofit, mDataManager);
    }

    @Override
    public void initialView(MyChannelMVPView mvpView) {
        super.initialView(mvpView);
    }

    public void getInforChannel() {
        if (!isConnectToInternet()) {
            notifyNoNetwork();
        } else {
            getMvpView().showProgressDialog(true);
            mDataManager.getNetworkManager().getInforChannel(Config.VALUE_PART, Constant.VALUE_CHANNEL_ID, Constant.VALUE_KEY)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ChannelResponse>() {
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
                        public void onNext(ChannelResponse channelResponse) {
                            if (channelResponse != null) {
                                ArrayList<Channel> listChannel = channelResponse.getListChannel();
                                if (listChannel != null && listChannel.size() > 0) {
                                    Channel channel = listChannel.get(0);
                                    getMvpView().loadInforChannel(channel);
                                }
                            }
                        }
                    });
        }
    }
}
