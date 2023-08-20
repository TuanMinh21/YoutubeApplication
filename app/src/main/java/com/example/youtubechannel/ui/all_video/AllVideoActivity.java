package com.example.youtubechannel.ui.all_video;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.youtubechannel.R;
import com.example.youtubechannel.adapter.AllVideoAdapter;
import com.example.youtubechannel.constant.Config;
import com.example.youtubechannel.constant.Constant;
import com.example.youtubechannel.constant.GlobalFunction;
import com.example.youtubechannel.models.Video;
import com.example.youtubechannel.ui.base.BaseMVPDialogActivity;
import com.example.youtubechannel.utils.ListDecorator;
import com.example.youtubechannel.utils.StringUtil;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("NonConstantResourceId")
public class AllVideoActivity extends BaseMVPDialogActivity implements AllVideoMVPView {

    @Inject
    AllVideoPresenter presenter;

    @BindView(R.id.rcvData)
    RecyclerView rcvData;

    AllVideoAdapter allVideoAdapter;
    private String mNextPageToken = "";
    private String mTypeOrder = Config.VALUE_ORDER_DATE;

    private int previousTotal = 0; // The total number of items in the dataset after the last load
    private boolean loading = true; // True if we are still waiting for the last set of data to load.
    int firstVisibleItem, visibleItemCount, totalItemCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivityComponent().inject(this);
        viewUnbind = ButterKnife.bind(this);
        presenter.initialView(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        initUi();
        getDataIntent();
    }

    private void initUi() {
        allVideoAdapter = new AllVideoAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rcvData.setNestedScrollingEnabled(false);
        rcvData.setFocusable(false);
        rcvData.setLayoutManager(layoutManager);
        rcvData.setAdapter(allVideoAdapter);
        rcvData.addItemDecoration(new ListDecorator());

        rcvData.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                visibleItemCount = recyclerView.getChildCount();
                totalItemCount = layoutManager.getItemCount();
                firstVisibleItem = layoutManager.findFirstVisibleItemPosition();
                if (loading) {
                    if (totalItemCount > previousTotal) {
                        previousTotal = totalItemCount;
                        loading = false;
                    }
                }
                if (((firstVisibleItem + visibleItemCount) == totalItemCount) && (!loading)) {
                    // End has been reached
                    loading = true;
                    if (!StringUtil.isEmpty(mNextPageToken))
                        presenter.getAllVideo(mTypeOrder, mNextPageToken);
                }
            }
        });
    }

    private void getDataIntent() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mTypeOrder = bundle.getString(Constant.KEY_TYPE_ORDER);
            String title;
            if (Config.VALUE_ORDER_VIEW_COUNT.equals(mTypeOrder)) {
                title = getString(R.string.video_popular);
            } else {
                title = getString(R.string.video_latest);
            }
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(title);
            }

            presenter.getAllVideo(mTypeOrder, mNextPageToken);
        }
    }

    @Override
    protected boolean bindView() {
        return true;
    }

    @Override
    protected int addContextView() {
        return R.layout.activity_all_video;
    }

    @Override
    protected void onDestroy() {
        presenter.destroyView();

        if (allVideoAdapter != null) {
            allVideoAdapter.release();
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
    public void loadListVideo(ArrayList<Video> listVideo, String nextPageToken) {
        mNextPageToken = nextPageToken;
        allVideoAdapter.setListData(listVideo);
    }
}
