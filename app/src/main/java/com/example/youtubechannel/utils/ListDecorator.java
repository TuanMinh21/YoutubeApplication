package com.example.youtubechannel.utils;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

public class ListDecorator extends ItemTouchHelper {

    public ListDecorator() {
        this(new Callback() {
            @Override
            public int getMovementFlags(@NotNull RecyclerView recyclerView,
                                        @NotNull RecyclerView.ViewHolder viewHolder) {
                return 0;
            }

            @Override
            public boolean onMove(@NotNull RecyclerView recyclerView,
                                  @NotNull RecyclerView.ViewHolder viewHolder,
                                  @NotNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NotNull RecyclerView.ViewHolder viewHolder, int direction) {

            }
        });
    }

    public ListDecorator(Callback callback) {
        super(callback);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (parent.getAdapter() != null && parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount() - 1) {
            outRect.bottom = 30;
        }
    }
}
