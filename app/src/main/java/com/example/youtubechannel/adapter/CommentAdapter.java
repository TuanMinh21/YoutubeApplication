package com.example.youtubechannel.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.youtubechannel.R;
import com.example.youtubechannel.adapter.base.BaseRecyclerViewAdapter;
import com.example.youtubechannel.adapter.base.Releasable;
import com.example.youtubechannel.injection.ActivityContext;
import com.example.youtubechannel.models.Comment;
import com.example.youtubechannel.utils.GlideUtils;
import com.example.youtubechannel.utils.StringUtil;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;

@SuppressLint("NonConstantResourceId")
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder>
        implements Releasable {

    private Context context;
    private ArrayList<Comment> listComment;

    @Inject
    public CommentAdapter(@ActivityContext Context context) {
        this.context = context;
    }

    @NotNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        return CommentViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, int position) {
        holder.bindData(context, listComment.get(position), position);
    }

    @Override
    public int getItemCount() {
        return null == listComment ? 0 : listComment.size();
    }


    @SuppressLint("NotifyDataSetChanged")
    public void setListData(ArrayList<Comment> list) {
        this.listComment = list;
        notifyDataSetChanged();
    }

    @Override
    public void release() {
        context = null;
    }

    public static class CommentViewHolder extends BaseRecyclerViewAdapter.BaseViewHolder<Comment> {

        @BindView(R.id.img_avatar)
        ImageView imgAvatar;

        @BindView(R.id.tv_author)
        TextView tvAuthor;

        @BindView(R.id.tv_comment)
        TextView tvComment;

        @BindView(R.id.tv_date)
        TextView tvDate;

        @BindView(R.id.layout_item)
        LinearLayout layoutItem;

        public CommentViewHolder(View itemView) {
            super(itemView);
        }

        public static CommentViewHolder create(ViewGroup parent) {
            return new CommentViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_comment, parent, false));
        }

        @Override
        public void bindData(Context context, Comment comment, int position) {
            if (comment != null) {
                GlideUtils.loadUrl(comment.getSnippet().getLevelComment().getSnippetComment().getAuthorProfileImageUrl(), imgAvatar);
                tvAuthor.setText(comment.getSnippet().getLevelComment().getSnippetComment().getAuthorDisplayName());
                String date = comment.getSnippet().getLevelComment().getSnippetComment().getPublishedAt();
                if (!StringUtil.isEmpty(date)) {
                    tvDate.setVisibility(View.VISIBLE);
                    String[] temp;
                    temp = date.split("T");
                    String strDate = context.getString(R.string.label_published) + " " + temp[0];
                    tvDate.setText(strDate);
                } else {
                    tvDate.setVisibility(View.GONE);
                }
                tvComment.setText(comment.getSnippet().getLevelComment().getSnippetComment().getTextDisplay());
            }
        }
    }
}