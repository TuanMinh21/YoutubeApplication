package com.example.youtubechannel.ui.my_channel;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.youtubechannel.R;
import com.example.youtubechannel.constant.GlobalFunction;
import com.example.youtubechannel.models.Channel;
import com.example.youtubechannel.ui.base.BaseMVPDialogActivity;
import com.example.youtubechannel.utils.GlideUtils;
import com.example.youtubechannel.utils.StringUtil;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@SuppressLint("NonConstantResourceId")
public class MyChannelActivity extends BaseMVPDialogActivity implements MyChannelMVPView {

    @Inject
    MyChannelPresenter presenter;

    @BindView(R.id.img_channel)
    ImageView imgChannel;

    @BindView(R.id.tv_channel)
    TextView tvChannel;

    @BindView(R.id.tv_published)
    TextView tvPublished;

    @BindView(R.id.tv_description)
    TextView tvDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivityComponent().inject(this);
        viewUnbind = ButterKnife.bind(this);
        presenter.initialView(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getString(R.string.my_channel));
        }

        presenter.getInforChannel();
    }

    @Override
    protected boolean bindView() {
        return true;
    }

    @Override
    protected int addContextView() {
        return R.layout.activity_my_channel;
    }

    @Override
    protected void onDestroy() {
        presenter.destroyView();
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
    public void loadInforChannel(Channel channel) {
        if (channel != null) {
            GlideUtils.loadUrl(channel.getSnippet().getThumbnail().getThumbnailHigh().getUrl(), imgChannel);
            tvChannel.setText(channel.getSnippet().getTitle());
            String date = channel.getSnippet().getPublishedAt();
            if (!StringUtil.isEmpty(date)) {
                tvPublished.setVisibility(View.VISIBLE);
                String[] temp;
                temp = date.split("T");
                String strPublish = getString(R.string.label_published) + " " + temp[0];
                tvPublished.setText(strPublish);
            } else {
                tvPublished.setVisibility(View.GONE);
            }
            tvDescription.setText(channel.getSnippet().getDescription());
        }
    }

    @OnClick(R.id.tv_open_youtube_channel)
    public void onClickOpenYoutubeChannel() {
        GlobalFunction.onClickOpenYoutubeChannel(this);
    }
}
