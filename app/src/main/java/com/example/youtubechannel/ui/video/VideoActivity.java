package com.example.youtubechannel.ui.video;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.youtubechannel.R;
import com.example.youtubechannel.adapter.CommentAdapter;
import com.example.youtubechannel.constant.Constant;
import com.example.youtubechannel.constant.GlobalFunction;
import com.example.youtubechannel.models.Comment;
import com.example.youtubechannel.models.Video;
import com.example.youtubechannel.models.VideoItem;
import com.example.youtubechannel.ui.base.BaseMVPDialogActivity;
import com.example.youtubechannel.ui.comment.CommentActivity;
import com.example.youtubechannel.utils.StringUtil;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@SuppressLint("NonConstantResourceId")
public class VideoActivity extends BaseMVPDialogActivity implements VideoMVPView {

    @Inject
    VideoPresenter presenter;

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.tv_date)
    TextView tvDate;

    @BindView(R.id.tv_description)
    TextView tvDescription;

    @BindView(R.id.rcv_comment)
    RecyclerView rcvComment;

    @BindView(R.id.tv_no_comment)
    TextView tvNoComment;

    @BindView(R.id.tv_see_all)
    TextView tvSeeAll;

    private CommentAdapter commentAdapter;
    YouTubePlayer.OnInitializedListener onInitializedListener;

    private String mVideoId;
    private boolean mIsFullScreen;
    private YouTubePlayer mYouTubePlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivityComponent().inject(this);
        viewUnbind = ButterKnife.bind(this);
        presenter.initialView(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getString(R.string.video_player));
        }

        getDataIntent();
        initUi(mVideoId);
        presenter.getInforVideo(mVideoId);
    }

    @Override
    protected boolean bindView() {
        return true;
    }

    @Override
    protected int addContextView() {
        return R.layout.activity_video;
    }

    private void getDataIntent() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Video video = (Video) bundle.get(Constant.KEY_VIDEO);
            VideoItem videoItem = (VideoItem) bundle.get(Constant.KEY_VIDEO_ITEM);
            if (video != null) {
                mVideoId = video.getId().getVideoId();
            } else if (videoItem != null) {
                mVideoId = videoItem.getSnippet().getResourceId().getVideoId();
            }
        }
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

    private void initUi(String videoId) {
        onInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                mYouTubePlayer = youTubePlayer;
                if (!b) youTubePlayer.loadVideo(videoId);
                mYouTubePlayer.loadVideo(videoId);
                mYouTubePlayer.setOnFullscreenListener(b1 -> mIsFullScreen = b1);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };

        YouTubePlayerSupportFragment frag = (YouTubePlayerSupportFragment) getSupportFragmentManager().findFragmentById(R.id.youtube_fragment);
        if (frag != null) {
            frag.initialize(Constant.VALUE_KEY, onInitializedListener);
        }

        commentAdapter = new CommentAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rcvComment.setNestedScrollingEnabled(false);
        rcvComment.setFocusable(false);
        rcvComment.setLayoutManager(layoutManager);
        rcvComment.setAdapter(commentAdapter);
    }

    @Override
    public void loadInforVideo(VideoItem videoItem) {
        if (videoItem != null) {
            tvTitle.setText(videoItem.getSnippet().getTitle());
            String date = videoItem.getSnippet().getPublishedAt();
            if (!StringUtil.isEmpty(date)) {
                tvDate.setVisibility(View.VISIBLE);
                String[] temp;
                temp = date.split("T");
                String strDate = getString(R.string.label_published) + " " + temp[0];
                tvDate.setText(strDate);
            } else {
                tvDate.setVisibility(View.GONE);
            }
            tvDescription.setText(videoItem.getSnippet().getDescription());

            presenter.getListComment(mVideoId);
        }
    }

    @Override
    public void loadListComment(ArrayList<Comment> listComment) {
        commentAdapter.setListData(listComment);
        if (listComment != null && listComment.size() > 0) {
            tvNoComment.setVisibility(View.GONE);
            if (listComment.size() > Constant.VALUE_MAX_RESULTS_COMMENT) {
                tvSeeAll.setVisibility(View.VISIBLE);
            } else {
                tvSeeAll.setVisibility(View.GONE);
            }
        } else {
            tvNoComment.setVisibility(View.VISIBLE);
            tvSeeAll.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.tv_open_youtube_video)
    public void onClickOpenYoutubeVideo() {
        if (!StringUtil.isEmpty(mVideoId))
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + mVideoId)));
    }

    @OnClick(R.id.tv_see_all)
    public void onClickSeeAllComment() {
        Bundle bundle = new Bundle();
        bundle.putString(Constant.KEY_VIDEO_ID, mVideoId);
        bundle.putString(Constant.KEY_VIDEO_TITLE, tvTitle.getText().toString().trim());
        GlobalFunction.startActivity(this, CommentActivity.class, bundle);
    }

    @Override
    public void onBackPressed() {
        if (mIsFullScreen) {
            mYouTubePlayer.setFullscreen(false);
        } else {
            super.onBackPressed();
        }
    }
}
