package com.example.youtubechannel.injection.components;

import com.example.youtubechannel.injection.PerActivity;
import com.example.youtubechannel.injection.modules.ActivityModule;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class)
public interface ActivityScopeComponent {
    ActivityComponent activityComponent(ActivityModule module);
}
