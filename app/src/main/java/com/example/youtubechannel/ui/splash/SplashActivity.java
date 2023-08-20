package com.example.youtubechannel.ui.splash;

import android.os.Bundle;
import android.os.Handler;

import com.example.youtubechannel.R;
import com.example.youtubechannel.constant.GlobalFunction;
import com.example.youtubechannel.ui.base.BaseMVPDialogActivity;
import com.example.youtubechannel.ui.main.MainActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class SplashActivity extends BaseMVPDialogActivity implements SplashMVPView {

    @Inject
    SplashPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivityComponent().inject(this);
        viewUnbind = ButterKnife.bind(this);
        presenter.initialView(this);

        startMainActivity();
    }

    @Override
    protected boolean bindView() {
        return true;
    }

    @Override
    protected int addContextView() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onDestroy() {
        presenter.destroyView();
        super.onDestroy();
    }

    @Override
    public void showNoNetworkAlert() {
        showAlert(getString(R.string.error_not_connect_to_internet));
    }

    @Override
    public void onErrorCallApi(int code) {
        GlobalFunction.showMessageError(this);
    }

    private void startMainActivity() {
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            GlobalFunction.startActivity(SplashActivity.this, MainActivity.class);
            finish();
        }, 1500);
    }
}
