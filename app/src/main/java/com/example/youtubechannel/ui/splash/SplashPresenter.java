package com.example.youtubechannel.ui.splash;

import com.example.youtubechannel.data.DataManager;
import com.example.youtubechannel.ui.base.BasePresenter;

import javax.inject.Inject;

import retrofit2.Retrofit;

public class SplashPresenter extends BasePresenter<SplashMVPView> {

    @Inject
    public SplashPresenter(Retrofit mRetrofit, DataManager mDataManager) {
        super(mRetrofit, mDataManager);
    }

    @Override
    public void initialView(SplashMVPView mvpView) {
        super.initialView(mvpView);
    }
}
