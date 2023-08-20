package com.example.youtubechannel.ui.base;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.MainThread;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.annotation.UiThread;
import androidx.appcompat.app.AppCompatActivity;

import com.example.youtubechannel.R;
import com.example.youtubechannel.YoutubeChannelApplication;
import com.example.youtubechannel.injection.components.ActivityComponent;
import com.example.youtubechannel.injection.components.ActivityScopeComponent;
import com.example.youtubechannel.injection.components.DaggerActivityScopeComponent;
import com.example.youtubechannel.injection.modules.ActivityModule;
import com.example.youtubechannel.utils.Utils;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends AppCompatActivity implements MvpView {

    private ActivityComponent mActivityComponent;
    private ActivityScopeComponent mActivityScopeComponent;

    protected Unbinder viewUnbind;
    @Inject
    Toast mToast;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(addContextView());
        if (bindView()) {
            viewUnbind = ButterKnife.bind(this);
        }
        YoutubeChannelApplication.get(this).getComponent().inject(this);
    }

    protected abstract boolean bindView();

    protected abstract int addContextView();

    private ActivityScopeComponent getActivityScopeComponent() {
        if (mActivityScopeComponent == null) {
            mActivityScopeComponent = DaggerActivityScopeComponent.builder()
                    .applicationComponent(YoutubeChannelApplication.get(this).getComponent())
                    .build();
        }
        return mActivityScopeComponent;
    }

    public final ActivityComponent getActivityComponent() {
        if (mActivityComponent == null) {
            mActivityComponent = getActivityScopeComponent()
                    .activityComponent(new ActivityModule(this));
        }
        return mActivityComponent;
    }


    @Override
    public void showNoNetworkAlert() {
        showMessage(R.string.error_not_connect_to_internet);
    }

    @Override
    public boolean isConnectToInternet() {
        return Utils.isConnectivityAvailable(this);
    }


    @MainThread
    @UiThread
    protected void showMessage(@StringRes int strRes) {
        showMessage(getString(strRes));
    }

    @MainThread
    @UiThread
    protected void showMessage(String message) {
        Toast toast = mToast;
        if (toast == null) {
            toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        } else {
            toast.setText(message);
        }
        toast.show();
    }

    @Override
    protected void onDestroy() {
        if (viewUnbind != null) {
            viewUnbind.unbind();
        }
        super.onDestroy();
    }
}
