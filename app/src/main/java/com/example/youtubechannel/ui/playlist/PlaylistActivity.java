package com.example.youtubechannel.ui.playlist;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.youtubechannel.R;
import com.example.youtubechannel.adapter.PlaylistGridViewAdapter;
import com.example.youtubechannel.constant.GlobalFunction;
import com.example.youtubechannel.models.Playlist;
import com.example.youtubechannel.ui.base.BaseMVPDialogActivity;
import com.example.youtubechannel.utils.StringUtil;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("NonConstantResourceId")
public class PlaylistActivity extends BaseMVPDialogActivity implements PlaylistMVPView {

    @Inject
    PlaylistPresenter presenter;

    @BindView(R.id.rcvData)
    RecyclerView rcvData;

    PlaylistGridViewAdapter playlistGridViewAdapter;
    private String mNextPageToken = "";

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
            getSupportActionBar().setTitle(getString(R.string.playlist));
        }

        initUi();
        presenter.getListPlaylist(mNextPageToken);
    }

    private void initUi() {
        playlistGridViewAdapter = new PlaylistGridViewAdapter(this);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        rcvData.setNestedScrollingEnabled(false);
        rcvData.setFocusable(false);
        rcvData.setLayoutManager(layoutManager);
        rcvData.setAdapter(playlistGridViewAdapter);

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
                        presenter.getListPlaylist(mNextPageToken);
                }
            }
        });
    }

    @Override
    protected boolean bindView() {
        return true;
    }

    @Override
    protected int addContextView() {
        return R.layout.activity_playlist;
    }

    @Override
    protected void onDestroy() {
        presenter.destroyView();

        if (playlistGridViewAdapter != null) {
            playlistGridViewAdapter.release();
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
    public void loadListPlaylist(ArrayList<Playlist> playlists, String nextPageToken) {
        mNextPageToken = nextPageToken;
        playlistGridViewAdapter.setListData(playlists);
    }
}
