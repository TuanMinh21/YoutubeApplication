package com.example.youtubechannel.injection.components;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.example.youtubechannel.data.DataManager;
import com.example.youtubechannel.injection.ApplicationContext;
import com.example.youtubechannel.injection.modules.ApplicationModule;
import com.example.youtubechannel.ui.base.BaseActivity;
import com.example.youtubechannel.ui.base.BaseFragment;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    @ApplicationContext
    Context context();

    Application application();

    Retrofit retrofit();

    DataManager dataManager();

    Toast toast();

    void inject(BaseActivity baseActivity);

    void inject(BaseFragment fragment);
}
