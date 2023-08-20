package com.example.youtubechannel.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.youtubechannel.R;
import com.example.youtubechannel.adapter.base.BaseRecyclerViewAdapter;
import com.example.youtubechannel.adapter.base.Releasable;
import com.example.youtubechannel.constant.Config;
import com.example.youtubechannel.constant.Constant;
import com.example.youtubechannel.constant.GlobalFunction;
import com.example.youtubechannel.injection.ActivityContext;
import com.example.youtubechannel.models.HomeObject;
import com.example.youtubechannel.ui.all_video.AllVideoActivity;
import com.example.youtubechannel.utils.ListDecorator;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;

@SuppressLint("NonConstantResourceId")
public class ListHomeAdapter extends RecyclerView.Adapter<ListHomeAdapter.LatestVideoViewHolder>
        implements Releasable {

    private Context context;
    private ArrayList<HomeObject> listHomeObject;

    @Inject
    public ListHomeAdapter(@ActivityContext Context context) {
        this.context = context;
    }

    @NotNull
    @Override
    public LatestVideoViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        return LatestVideoViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(LatestVideoViewHolder holder, int position) {
        holder.bindData(context, listHomeObject.get(position), position);
    }

    @Override
    public int getItemCount() {
        return null == listHomeObject ? 0 : listHomeObject.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(ArrayList<HomeObject> list) {
        this.listHomeObject = list;
        notifyDataSetChanged();
    }

    public void injectInto(RecyclerView recyclerView) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setFocusable(false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(this);
        recyclerView.addItemDecoration(new ListDecorator());
    }

    @Override
    public void release() {
        context = null;
    }

    public static class LatestVideoViewHolder extends BaseRecyclerViewAdapter.BaseViewHolder<HomeObject> {

        @BindView(R.id.tvTitle)
        TextView tvTitle;

        @BindView(R.id.tvSeeAll)
        TextView tvSeeAll;

        @BindView(R.id.rcvData)
        RecyclerView rcvData;

        public LatestVideoViewHolder(View itemView) {
            super(itemView);
        }

        public static LatestVideoViewHolder create(ViewGroup parent) {
            return new LatestVideoViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list_latest_video, parent, false));
        }

        @Override
        public void bindData(Context context, HomeObject homeObject, int position) {
            if (homeObject != null) {
                tvTitle.setText(homeObject.getTitle());

                VideoGridViewAdapter videoGridViewAdapter = new VideoGridViewAdapter(context);
                videoGridViewAdapter.injectInto(rcvData);
                videoGridViewAdapter.setListData(homeObject.getListVideo());

                tvSeeAll.setOnClickListener(v -> {
                    Bundle bundle = new Bundle();
                    bundle.putString(Constant.KEY_TYPE_ORDER, Config.VALUE_ORDER_DATE);
                    GlobalFunction.startActivity(context, AllVideoActivity.class, bundle);
                });
            }
        }
    }
}
