package com.example.youtubechannel.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.youtubechannel.R;
import com.example.youtubechannel.models.Playlist;

import java.util.ArrayList;

public class PlaylistAdapter extends BaseAdapter {

    private final ArrayList<Playlist> listPlaylist;

    public PlaylistAdapter(ArrayList<Playlist> list) {
        this.listPlaylist = list;
    }

    @Override
    public int getCount() {
        try {
            return listPlaylist.size();
        } catch (NullPointerException ex) {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_playlist, parent, false);
            holder.tvTitle = convertView.findViewById(R.id.tvTitle);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Playlist playlist = listPlaylist.get(position);
        if (playlist != null) {
            holder.tvTitle.setText(playlist.getSnippet().getTitle());
        }

        return convertView;
    }

    static class ViewHolder {
        private TextView tvTitle;
    }
}
