package com.example.youtubechannel;

import android.app.Application;
import android.content.Context;

import com.example.youtubechannel.injection.components.ApplicationComponent;
import com.example.youtubechannel.injection.components.DaggerApplicationComponent;
import com.example.youtubechannel.injection.modules.ApplicationModule;

public class YoutubeChannelApplication extends Application {

    private final Object lock = new Object();
    private volatile ApplicationComponent mApplicationComponent;

    public static YoutubeChannelApplication get(Context context) {
        return (YoutubeChannelApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public ApplicationComponent getComponent() {
        if (mApplicationComponent == null) {
            synchronized (lock) {
                if (mApplicationComponent == null) {
                    mApplicationComponent = DaggerApplicationComponent.builder()
                            .applicationModule(new ApplicationModule(this))
                            .build();
                }
            }
        }
        return mApplicationComponent;
    }
}
