package com.example.youtubechannel.ui.about_us;

import com.example.youtubechannel.models.Contact;
import com.example.youtubechannel.ui.base.BaseScreenMvpView;

import java.util.ArrayList;

interface AboutUsMVPView extends BaseScreenMvpView {

    void loadListContact(ArrayList<Contact> contacts);
}
