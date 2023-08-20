package com.example.youtubechannel.injection.modules;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.example.youtubechannel.data.networking.YoutubeChannelService;
import com.example.youtubechannel.injection.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class ApplicationModule {
    private final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideApplicationContext() {
        return mApplication;
    }

    @Provides
    @Singleton
    Toast provideToast(@ApplicationContext Context context) {
        return Toast.makeText(context, "", Toast.LENGTH_LONG);
    }

    @Provides
    @Singleton
    YoutubeChannelService networkService(Retrofit retrofit){
        return retrofit.create(YoutubeChannelService.class);
    }

    @Provides
    @Singleton
    Retrofit provideRetrofitInstance() {
        return YoutubeChannelService.Creator.newRetrofitInstance();
    }
}
