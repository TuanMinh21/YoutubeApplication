package com.example.youtubechannel.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.youtubechannel.R;
import com.example.youtubechannel.adapter.base.Releasable;
import com.example.youtubechannel.constant.Constant;
import com.example.youtubechannel.constant.GlobalFunction;
import com.example.youtubechannel.models.Video;
import com.example.youtubechannel.ui.video.VideoActivity;
import com.example.youtubechannel.utils.GlideUtils;
import com.example.youtubechannel.utils.StringUtil;

import java.util.ArrayList;

public class VideoPopularAdapter extends RecyclerView.Adapter<VideoPopularAdapter.VideoPopularViewHolder> implements Releasable {

    private Context mContext;
    private final ArrayList<Video> mListVideo;

    public VideoPopularAdapter(Context context, ArrayList<Video> mListData) {
        this.mContext = context;
        this.mListVideo = mListData;
    }

    @Override
    public void release() {
        mContext = null;
    }

    @NonNull
    @Override
    public VideoPopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video_popular, parent, false);
        return new VideoPopularViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoPopularViewHolder holder, int position) {
        Video video = mListVideo.get(position);
        if (video == null) {
            return;
        }
        String imageVideo = video.getSnippet().getThumbnail().getThumbnailHigh().getUrl();
        if (!StringUtil.isEmpty(imageVideo)) {
            GlideUtils.loadUrl(imageVideo, holder.imgFeatured);

            holder.imgFeatured.setOnClickListener(v -> {
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constant.KEY_VIDEO, video);
                GlobalFunction.startActivity(mContext, VideoActivity.class, bundle);
            });
        }
    }

    @Override
    public int getItemCount() {
        if (mListVideo != null) {
            return mListVideo.size();
        }
        return 0;
    }

    public static class VideoPopularViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imgFeatured;

        public VideoPopularViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFeatured = itemView.findViewById(R.id.image_featured);
        }
    }
}
