package sankemao.gankio.ui.custom.behavior;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.CoordinatorLayout.Behavior;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

import sankemao.gankio.R;

/**
 * Description: 底部导航栏，随recyclerView向上滑动隐藏
 * Create Time: 2018/1/11.14:52
 * Author:jin
 * Email:210980059@qq.com
 */
public class FooterBehavior extends Behavior<View> {
    private ObjectAnimator mHideAnimator;
    private ObjectAnimator mShowAnimator;

    public FooterBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 判断滑动方向
     */
    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        return (axes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0 && target.getId() == R.id.refresh_layout;
    }

    /**
     * 准备滚动
     * dy>0 向上滑动，底部隐藏
     * dy<0 向下滑动，底部显示
     */
    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        if (dy > 0) {
            if (mHideAnimator == null) {
                mHideAnimator = ObjectAnimator.ofFloat(child, "translationY", 0, child.getHeight());
                mHideAnimator.setDuration(200);
            }
            //动画不在执行且child在原始位置
            if (!mHideAnimator.isRunning() && child.getTranslationY() <= 0) {
                mHideAnimator.start();
            }
        } else if (dy < 0) {
            if (mShowAnimator == null) {
                mShowAnimator = ObjectAnimator.ofFloat(child, "translationY", child.getHeight(), 0);
                mShowAnimator.setDuration(200);
            }
            //动画不在执行且child完全隐藏
            if (!mShowAnimator.isRunning() && child.getTranslationY() >= child.getHeight()) {
                mShowAnimator.start();
            }
        }
    }

    /**
     * 正在滚动
     */
    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return super.layoutDependsOn(parent, child, dependency);
    }
}
