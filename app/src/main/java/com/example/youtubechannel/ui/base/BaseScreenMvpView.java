package com.example.youtubechannel.ui.base;

import androidx.annotation.StringRes;

public interface BaseScreenMvpView extends MvpView {

    void createProgressDialog();

    void createAlertDialog();

    void showProgressDialog(boolean value);

    void showAlertDialog(@StringRes int resourceId);

    void showAlertDialog(String errorMessage);

    void dismissProgressDialog();

    void showAlert(String s);

    void showAlert(@StringRes int resourceId);

    void showNoNetworkAlert();

    boolean isConnectToInternet();

    void onErrorCallApi(int code);
}
