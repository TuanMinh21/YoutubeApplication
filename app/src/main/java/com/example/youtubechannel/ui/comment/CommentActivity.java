package com.example.youtubechannel.ui.comment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.youtubechannel.R;
import com.example.youtubechannel.adapter.CommentAdapter;
import com.example.youtubechannel.constant.Constant;
import com.example.youtubechannel.constant.GlobalFunction;
import com.example.youtubechannel.models.Comment;
import com.example.youtubechannel.ui.base.BaseMVPDialogActivity;
import com.example.youtubechannel.utils.ListDecorator;
import com.example.youtubechannel.utils.StringUtil;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("NonConstantResourceId")
public class CommentActivity extends BaseMVPDialogActivity implements CommentMVPView {

    @Inject
    CommentPresenter presenter;

    @BindView(R.id.rcvData)
    RecyclerView rcvData;

    CommentAdapter commentAdapter;
    private String mVideoId = "";
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
        }

        initUi();
        getDataIntent();
    }

    private void initUi() {
        commentAdapter = new CommentAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rcvData.setNestedScrollingEnabled(false);
        rcvData.setFocusable(false);
        rcvData.setLayoutManager(layoutManager);
        rcvData.setAdapter(commentAdapter);
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
                        presenter.getListComment(mVideoId, mNextPageToken);
                }
            }
        });
    }

    private void getDataIntent() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mVideoId = bundle.getString(Constant.KEY_VIDEO_ID);
            String videoTitle = bundle.getString(Constant.KEY_VIDEO_TITLE);
            if (!StringUtil.isEmpty(videoTitle) && getSupportActionBar() != null) {
                getSupportActionBar().setTitle(videoTitle);
            }
            if (!StringUtil.isEmpty(mVideoId)) presenter.getListComment(mVideoId, mNextPageToken);
        }
    }

    @Override
    protected boolean bindView() {
        return true;
    }

    @Override
    protected int addContextView() {
        return R.layout.activity_comment;
    }

    @Override
    protected void onDestroy() {
        presenter.destroyView();

        if (commentAdapter != null) {
            commentAdapter.release();
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
    public void loadListComment(ArrayList<Comment> listComment, String nextPageToken) {
        mNextPageToken = nextPageToken;
        commentAdapter.setListData(listComment);
    }
}
