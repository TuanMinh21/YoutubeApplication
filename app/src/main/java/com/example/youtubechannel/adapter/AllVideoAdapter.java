package com.example.youtubechannel.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.youtubechannel.R;
import com.example.youtubechannel.adapter.base.BaseRecyclerViewAdapter;
import com.example.youtubechannel.constant.Constant;
import com.example.youtubechannel.constant.GlobalFunction;
import com.example.youtubechannel.injection.ActivityContext;
import com.example.youtubechannel.models.Video;
import com.example.youtubechannel.ui.video.VideoActivity;
import com.example.youtubechannel.utils.GlideUtils;
import com.example.youtubechannel.utils.StringUtil;
import com.google.android.gms.common.api.Releasable;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;

@SuppressLint("NonConstantResourceId")
public class AllVideoAdapter extends RecyclerView.Adapter<AllVideoAdapter.AllVideoViewHolder>
        implements Releasable {

    private Context context;
    private ArrayList<Video> listVideo;

    @Inject
    public AllVideoAdapter(@ActivityContext Context context) {
        this.context = context;
    }

    @NotNull
    @Override
    public AllVideoViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        return AllVideoViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(AllVideoViewHolder holder, int position) {
        holder.bindData(context, listVideo.get(position), position);
    }

    @Override
    public int getItemCount() {
        return null == listVideo ? 0 : listVideo.size();
    }


    @SuppressLint("NotifyDataSetChanged")
    public void setListData(ArrayList<Video> list) {
        this.listVideo = list;
        notifyDataSetChanged();
    }

    @Override
    public void release() {
        context = null;
    }

    public static class AllVideoViewHolder extends BaseRecyclerViewAdapter.BaseViewHolder<Video> {

        @BindView(R.id.img_video)
        ImageView imgVideo;

        @BindView(R.id.tv_title)
        TextView tvTitle;

        @BindView(R.id.tv_description)
        TextView tvDescription;

        @BindView(R.id.tv_date)
        TextView tvDate;

        @BindView(R.id.layout_item)
        LinearLayout layoutItem;

        public AllVideoViewHolder(View itemView) {
            super(itemView);
        }

        public static AllVideoViewHolder create(ViewGroup parent) {
            return new AllVideoViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_video_vertical, parent, false));
        }

        @Override
        public void bindData(Context context, Video video, int position) {
            if (video != null) {
                GlideUtils.loadUrl(video.getSnippet().getThumbnail().getThumbnailHigh().getUrl(), imgVideo);
                tvTitle.setText(video.getSnippet().getTitle());
                tvDescription.setText(video.getSnippet().getDescription());
                String date = video.getSnippet().getPublishedAt();
                if (!StringUtil.isEmpty(date)) {
                    tvDate.setVisibility(View.VISIBLE);
                    String[] temp;
                    temp = date.split("T");
                    String strDate = context.getString(R.string.label_published) + " " + temp[0];
                    tvDate.setText(strDate);
                } else {
                    tvDate.setVisibility(View.GONE);
                }

                layoutItem.setOnClickListener(v -> {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constant.KEY_VIDEO, video);
                    GlobalFunction.startActivity(context, VideoActivity.class, bundle);
                });
            }
        }
    }
}