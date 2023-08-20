package com.example.youtubechannel.ui.base;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.StringRes;
import androidx.annotation.UiThread;
import androidx.fragment.app.Fragment;

import com.example.youtubechannel.YoutubeChannelApplication;
import com.example.youtubechannel.injection.ActivityScopeComponentCache;
import com.example.youtubechannel.injection.components.ActivityComponent;
import com.example.youtubechannel.injection.components.ActivityScopeComponent;
import com.example.youtubechannel.injection.components.DaggerActivityScopeComponent;
import com.example.youtubechannel.injection.modules.ActivityModule;

import javax.inject.Inject;

import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {

    private ActivityComponent activityComponent;
    private ActivityScopeComponent activityScopeComponent;

    protected Unbinder viewUnbind;

    private long componentStateKey = -1;
    private boolean isDestroyBySystem = false;

    @Inject
    ActivityScopeComponentCache activityScopeComponentCache;

    @Inject
    Toast mToast;

    private ActivityScopeComponent getActivityScopeComponent() {
        if (activityScopeComponent == null && getActivity() != null) {
            activityScopeComponent = DaggerActivityScopeComponent.builder()
                    .applicationComponent(YoutubeChannelApplication.get(getActivity()).getComponent())
                    .build();
        }
        return activityScopeComponent;
    }

    public final ActivityComponent getActivityComponent() {
        if (activityComponent == null) {
            activityComponent = getActivityScopeComponent()
                    .activityComponent(new ActivityModule(getActivity()));
        }
        return activityComponent;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() != null) {
            YoutubeChannelApplication.get(getActivity()).getComponent().inject(this);
        }
        activityScopeComponent = activityScopeComponentCache.restoreComponent(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (activityScopeComponentCache != null) {
            componentStateKey = activityScopeComponentCache
                    .saveComponentInstance(activityScopeComponent, outState);
        }
        isDestroyBySystem = true;
    }

    @Override
    public void onDestroyView() {
        if (viewUnbind != null) {
            viewUnbind.unbind();
        }
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        if (!isDestroyBySystem) {
            if (activityScopeComponentCache != null) {
                activityScopeComponentCache.clearComponent(componentStateKey);
            }
        }
        isDestroyBySystem = false;
        super.onDestroy();
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
        if (toast != null) {
            toast.setText(message);
        }
        Context context = getContext();
        if (context != null) {
            toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        }
        if (toast != null) {
            toast.show();
        }
    }
}
