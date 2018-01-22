package sankemao.baselib.ui.indicators;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 * Description:TODO
 * Create Time: 2018/1/22.11:02
 * Author:jin
 * Email:210980059@qq.com
 */
public class IndicatorContainerView extends FrameLayout {

    //动态的添加View - 指示器条目的容器
    private LinearLayout mIndicatorGroup;
    private int mItemWith;
    private View mBottomTrackView;
    private LayoutParams mTrackViewParams;
    private int mInitialleftMargin;

    public IndicatorContainerView(@NonNull Context context) {
        this(context, null);
    }

    public IndicatorContainerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndicatorContainerView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mIndicatorGroup = new LinearLayout(context);
        addView(mIndicatorGroup);
    }

    public void addItemView(View itemView) {
        mIndicatorGroup.addView(itemView);
    }

    public View getItemViewAt(int position) {
        return mIndicatorGroup.getChildAt(position);
    }

    public void addBottomTrackView(View bottomTrackView, int itemWith) {
        if (bottomTrackView == null) {
            return;
        }

        this.mBottomTrackView = bottomTrackView;
        this.mItemWith = itemWith;

        addView(bottomTrackView);
        mTrackViewParams = (LayoutParams) bottomTrackView.getLayoutParams();
        mTrackViewParams.gravity = Gravity.BOTTOM;
        int trackViewWidth = mTrackViewParams.width;

        if (trackViewWidth == ViewGroup.LayoutParams.MATCH_PARENT) {
            trackViewWidth = itemWith;
        }

        if (trackViewWidth > itemWith) {
            trackViewWidth = itemWith;
        }

        mTrackViewParams.width = trackViewWidth;

        //确定在中间
        mInitialleftMargin = (itemWith - trackViewWidth) / 2;
        mTrackViewParams.leftMargin = mInitialleftMargin;
    }

    public void scrollBottomTrack(int position, float positionOffset) {
        if (mBottomTrackView == null) {
            return;
        }

        int leftMargin = (int) ((position + positionOffset) * mItemWith);

        mTrackViewParams.leftMargin = mInitialleftMargin + leftMargin;
        mBottomTrackView.setLayoutParams(mTrackViewParams);
    }

    public void scrollBottomTrack(int position) {
        if (mBottomTrackView == null) {
            return;
        }

        int finalLeftMargin = position * mItemWith + mInitialleftMargin;

        final int currentMargin = mTrackViewParams.leftMargin;

        int distance = finalLeftMargin - currentMargin;

        ValueAnimator animator = ObjectAnimator.ofFloat(currentMargin, finalLeftMargin)
                .setDuration((long) (Math.abs(distance) * 0.4f));
        animator.setInterpolator(new DecelerateInterpolator());

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float currentValue = (float) animation.getAnimatedValue();
                mTrackViewParams.leftMargin = (int) currentValue;
                mBottomTrackView.setLayoutParams(mTrackViewParams);
            }
        });
        animator.start();
    }


}
