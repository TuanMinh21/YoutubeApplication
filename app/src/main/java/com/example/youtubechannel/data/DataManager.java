package com.example.youtubechannel.data;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DataManager {

    private final NetworkManager mNetworkManager;

    @Inject
    public DataManager(NetworkManager networkManager) {
        this.mNetworkManager = networkManager;
    }

    public NetworkManager getNetworkManager() {
        return mNetworkManager;
    }
}
