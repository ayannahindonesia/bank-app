package com.ayannah.asira.custom;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

    private int spanCount;
    private int spacing;

    public GridSpacingItemDecoration(int spanCount, int spacing){
        this.spanCount = spanCount;
        this.spacing = spacing;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {

        int position = parent.getChildAdapterPosition(view);
        int colomn = position % spanCount;

        outRect.left = colomn * spacing / spanCount;
        outRect.right = spacing - (colomn - 1) * spanCount / spanCount;

        if(position >= spanCount){
            outRect.top = spacing; //item top
        }
    }
}
