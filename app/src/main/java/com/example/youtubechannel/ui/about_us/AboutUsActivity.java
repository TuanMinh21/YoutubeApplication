package com.example.youtubechannel.ui.about_us;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.youtubechannel.R;
import com.example.youtubechannel.adapter.ContactAdapter;
import com.example.youtubechannel.constant.GlobalFunction;
import com.example.youtubechannel.models.Contact;
import com.example.youtubechannel.ui.base.BaseMVPDialogActivity;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("NonConstantResourceId")
public class AboutUsActivity extends BaseMVPDialogActivity implements AboutUsMVPView {

    @Inject
    AboutUsPresenter presenter;

    @BindView(R.id.rcvData)
    RecyclerView rcvData;

    ContactAdapter contactAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivityComponent().inject(this);
        viewUnbind = ButterKnife.bind(this);
        presenter.initialView(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getString(R.string.about_us));
        }

        initUi();
        presenter.getListContact();
    }

    private void initUi() {
        contactAdapter = new ContactAdapter(this, ()
                -> GlobalFunction.callPhoneNumber(AboutUsActivity.this));
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        rcvData.setNestedScrollingEnabled(false);
        rcvData.setFocusable(false);
        rcvData.setLayoutManager(layoutManager);
        rcvData.setAdapter(contactAdapter);
    }

    @Override
    protected boolean bindView() {
        return true;
    }

    @Override
    protected int addContextView() {
        return R.layout.activity_about_us;
    }

    @Override
    protected void onDestroy() {
        presenter.destroyView();

        if (contactAdapter != null) {
            contactAdapter.release();
        }
        super.onDestroy();
    }

    @Override
    public void showNoNetworkAlert() {
        showAlert(getString(R.string.error_not_connect_to_internet));
    }

    @Override
    public void onErrorCallApi(int code) {
        GlobalFunction.showMessageError(this);
    }

    @Override
    public void loadListContact(ArrayList<Contact> contacts) {
        contactAdapter.setListData(contacts);
    }
}
