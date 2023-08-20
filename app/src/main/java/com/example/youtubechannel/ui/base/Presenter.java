package com.example.youtubechannel.ui.base;

public interface Presenter<V extends MvpView> {

    void initialView(V mvpView);

    void destroyView();

    void destroy();
}
