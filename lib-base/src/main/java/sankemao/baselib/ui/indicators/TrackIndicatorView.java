package sankemao.baselib.ui.indicators;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;

import com.blankj.utilcode.util.LogUtils;

import sankemao.baselib.R;

/**
 * Description:TODO
 * Create Time: 2018/1/17.14:05
 * Author:jin
 * Email:210980059@qq.com
 */
public class TrackIndicatorView extends HorizontalScrollView implements ViewPager.OnPageChangeListener {
    private BaseIndicatorAdapter mAdapter;
//    private LinearLayout mContainer;

    private IndicatorContainerView mContainer;
    //可见indicator数目，默认为0
    private int mTabVisibleNums = 0;
    //indicator 宽度
    private int mItemWidth = 0;
    //与viewpager关联
    private ViewPager mViewPager;
    //点击indicator后viewpager页面切换是否需要平滑滚动
    private boolean mSmoothScroll;
    //当前选中的viewpager。
    private int mCurrentPosition;

    private boolean mIsViewPagerScroll;

    public TrackIndicatorView(Context context) {
        this(context, null);
    }

    public TrackIndicatorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TrackIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        mContainer = new LinearLayout(context);
//        this.addView(mContainer);
        mContainer = new IndicatorContainerView(context);
        this.addView(mContainer);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TrackIndicatorView);
        mTabVisibleNums = typedArray.getInt(R.styleable.TrackIndicatorView_tabVisibleNums, mTabVisibleNums);
        typedArray.recycle();
    }

    /**
     * 与viewpager关联
     */
    public void setAdapter(BaseIndicatorAdapter adapter, ViewPager viewPager) {
        setAdapter(adapter, viewPager, true);
    }

    public void setAdapter(BaseIndicatorAdapter adapter) {
        if (adapter == null) {
            throw new NullPointerException("adapter can not be null");
        }

        this.mAdapter = adapter;
        int viewCount = adapter.getCount();
        for (int i = 0; i < viewCount; i++) {
            View indicatorView = adapter.getView(i, mContainer);
            mContainer.addItemView(indicatorView);

            //设置点击事件，与viewpager联动
            if (mViewPager != null) {
                switchItemClick(indicatorView, i);
            }
        }

        mAdapter.highLightIndicator(mContainer.getItemViewAt(0));
    }

    public void setAdapter(BaseIndicatorAdapter adapter, ViewPager viewPager, boolean smoothScroll) {
        if (viewPager == null) {
            throw new NullPointerException("viewPager can not be null");
        }
        this.mSmoothScroll = smoothScroll;
        this.mViewPager = viewPager;
        viewPager.addOnPageChangeListener(this);
        setAdapter(adapter);
    }

    /**
     * 点击indicatorView切换viewpager
     */
    private void switchItemClick(View indicatorView, final int position) {
        indicatorView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(position, mSmoothScroll);
                //滚动indicator,带动画，让选中的tab尽可能居中。
                smoothScrollIndicator(position);
                mContainer.scrollBottomTrack(position);
            }
        });
    }

    private void smoothScrollIndicator(int position) {
        float leftMargin = position * mItemWidth;
        int offsetScroll = (getWidth() - mItemWidth) / 2;
        int finalScroll = (int) (leftMargin - offsetScroll);

        smoothScrollTo(finalScroll, 0);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            //changed，当viewGroup中位置或大小发生变化时为true.
            mItemWidth = getItemWidth();
            int itemCount = mAdapter.getCount();
            for (int i = 0; i < itemCount; i++) {
                mContainer.getItemViewAt(i).getLayoutParams().width = mItemWidth;
            }
            LogUtils.d("item宽度-> " + mItemWidth);

            mContainer.addBottomTrackView(mAdapter.getTrackItemView(), mItemWidth);
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
            View itemView = mContainer.getItemViewAt(i);
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

    /**
     * viewpager滚动时滚动指示器
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (mIsViewPagerScroll) {
            scrollCurrentIndicator(position, positionOffset);
            mContainer.scrollBottomTrack(position, positionOffset);
        }
    }

    /**
     * viewpager滚动时滚动指示器
     */
    private void scrollCurrentIndicator(int position, float positionOffset) {
        LogUtils.d("position->" + position + " , positionOffset->" + positionOffset);
        float leftMargin = (position + positionOffset) * mItemWidth;
        int offsetScroll = (getWidth() - mItemWidth) / 2;
        //以view最左边竖线为y轴， 左加右减（少偏移offset）
        int finalScroll = (int) (leftMargin - offsetScroll);
        scrollTo(finalScroll, 0);
    }

    /**
     * 根据viewpager滚动后停留的页面设置tab的状态。
     * @param position viewpager页面index。
     */
    @Override
    public void onPageSelected(int position) {
        View lastSelectedView = mContainer.getItemViewAt(mCurrentPosition);
        mAdapter.restoreIndicator(lastSelectedView);

        mCurrentPosition = position;
        View currentSelectedView = mContainer.getItemViewAt(mCurrentPosition);
        mAdapter.highLightIndicator(currentSelectedView);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == ViewPager.SCROLL_STATE_IDLE) {
            mIsViewPagerScroll = false;
        }

        if (state == ViewPager.SCROLL_STATE_DRAGGING) {
            mIsViewPagerScroll = true;
        }
    }
}
