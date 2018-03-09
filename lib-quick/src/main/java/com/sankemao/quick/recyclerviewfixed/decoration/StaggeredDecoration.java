package com.sankemao.quick.recyclerviewfixed.decoration;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

/**
 * Description:TODO
 * Create Time: 2018/3/7.16:56
 * Author:jin
 * Email:210980059@qq.com
 */
public class StaggeredDecoration extends RecyclerView.ItemDecoration {
    private int space;
    private Paint mPaint;

    public StaggeredDecoration(int space, int color) {
        this.space = space;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(color);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
        /**
         * 根据params.getSpanIndex()来判断左右边确定分割线
         * 第一列设置左边距为space，右边距为space/2  （第二列反之）
         */
        if (params.getSpanIndex() % 2 == 0) {
            outRect.left = space;
            outRect.right = space / 2;
        } else {
            outRect.left = space / 2;
            outRect.right = space;
        }

        outRect.top = space;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
//        int childCount = parent.getChildCount();
//        final int left = parent.getPaddingLeft();
//        final int right = parent.getWidth() - parent.getPaddingRight();
//        for (int i = 0; i < childCount; i++) {
//            View child = parent.getChildAt(i);
//            int adapterPosition = parent.getChildAdapterPosition(child);
//            //最后一个条目不绘制分割线.
//            if (adapterPosition == parent.getAdapter().getItemCount() - 1) {
//                continue;
//            }
//            int top = child.getBottom();
//            int bottom = top + space;
//            c.drawRect(left, top, right, bottom, mPaint);
//        }
    }
}
