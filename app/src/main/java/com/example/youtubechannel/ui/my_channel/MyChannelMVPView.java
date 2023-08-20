package com.example.youtubechannel.ui.my_channel;

import com.example.youtubechannel.models.Channel;
import com.example.youtubechannel.ui.base.BaseScreenMvpView;

interface MyChannelMVPView extends BaseScreenMvpView {

    void loadInforChannel(Channel channel);
}
