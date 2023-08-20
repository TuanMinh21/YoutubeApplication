package com.example.youtubechannel.ui.about_us;

import com.example.youtubechannel.R;
import com.example.youtubechannel.data.DataManager;
import com.example.youtubechannel.models.Contact;
import com.example.youtubechannel.ui.base.BasePresenter;

import java.util.ArrayList;

import javax.inject.Inject;

import retrofit2.Retrofit;

public class AboutUsPresenter extends BasePresenter<AboutUsMVPView> {

    @Inject
    public AboutUsPresenter(Retrofit mRetrofit, DataManager mDataManager) {
        super(mRetrofit, mDataManager);
    }

    @Override
    public void initialView(AboutUsMVPView mvpView) {
        super.initialView(mvpView);
    }

    public void getListContact() {
        ArrayList<Contact> contactArrayList = new ArrayList<>();
        contactArrayList.add(new Contact(Contact.FACEBOOK, R.drawable.ic_facebook));
        contactArrayList.add(new Contact(Contact.HOTLINE, R.drawable.ic_hotline));
        contactArrayList.add(new Contact(Contact.GMAIL, R.drawable.ic_gmail));
        contactArrayList.add(new Contact(Contact.SKYPE, R.drawable.ic_skype));
        contactArrayList.add(new Contact(Contact.YOUTUBE, R.drawable.ic_youtube));
        contactArrayList.add(new Contact(Contact.ZALO, R.drawable.ic_zalo));

        getMvpView().loadListContact(contactArrayList);
    }
}
