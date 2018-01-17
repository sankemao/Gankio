package sankemao.baselib.ui.indicators;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import sankemao.baselib.R;

/**
 * Description:TODO
 * Create Time: 2018/1/17.14:05
 * Author:jin
 * Email:210980059@qq.com
 */
public class TrackIndicatorView extends HorizontalScrollView {
    private BaseIndicatorAdapter mAdapter;
    private LinearLayout mContainer;
    //可见indicator数目，默认为0
    private int mTabVisibleNums = 0;
    //indicator 宽度
    private int mItemWidth = 0;

    public TrackIndicatorView(Context context) {
        this(context, null);
    }

    public TrackIndicatorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TrackIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContainer = new LinearLayout(context);
        this.addView(mContainer);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TrackIndicatorView);
        mTabVisibleNums = typedArray.getInt(R.styleable.TrackIndicatorView_tabVisibleNums, mTabVisibleNums);
        typedArray.recycle();
    }

    public void setAdapter(BaseIndicatorAdapter adapter) {
        if (adapter == null) {
            throw new NullPointerException("adapter can not be null");
        }

        this.mAdapter = adapter;
        int viewCount = adapter.getCount();
        for (int i = 0; i < viewCount; i++) {
            View indicatorView = adapter.getView(i, mContainer);
            mContainer.addView(indicatorView);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            mItemWidth = getItemWidth();
        }
    }

    /**
     * 获取每个indicatorView的宽度
     * @return
     */
    private int getItemWidth() {
        int itemWidth = 0;
        int width = getWidth();
        if (mTabVisibleNums != 0) {
            itemWidth = width / mTabVisibleNums;
            return itemWidth;
        }
        //如果没有指定可见条数，则选取最大的view的宽度作为标准
        int maxItemWidth = 0;
        int itemCount = mAdapter.getCount();
        //总宽度
        int allWidth = 0;
        for (int i = 0; i < itemCount; i++) {
            View itemView = mContainer.getChildAt(i);
            int childWidth = itemView.getMeasuredWidth();
            maxItemWidth = Math.max(maxItemWidth, childWidth);
            allWidth += childWidth;
        }
        itemWidth = maxItemWidth;
        //如果宽度不足一屏，则view的宽度为width/mItemCount
        if (allWidth < width) {
            itemWidth = width / itemCount;
        }
        return itemWidth;
    }
}
