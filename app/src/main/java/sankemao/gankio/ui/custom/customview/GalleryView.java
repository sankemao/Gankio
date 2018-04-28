package sankemao.gankio.ui.custom.customview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.bumptech.glide.Glide;


/**
 * Description:TODO
 * Create Time: 2018/4/26.17:00
 * Author:jin
 * Email:210980059@qq.com
 */
public class GalleryView extends ImageView {
    public GalleryView(Context context) {
        this(context, null);
    }

    public GalleryView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GalleryView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void showImageView(Activity activity, GalleryPhotoInfo info) {
        //windowManager
        final WindowManager windowManager = activity.getWindowManager();
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        params.format = PixelFormat.RGBA_8888;
        params.flags = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;


        Glide.with(activity).load(info.imageObj).into(this);
        //添加
        windowManager.addView(this, params);


        this.setOnClickListener(v -> doExitAnima(windowManager));


        getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                doEnterAnima(info);
                getViewTreeObserver().removeOnPreDrawListener(this);
                return false;
            }
        });

    }

    /**
     * 执行离开动画
     */
    private void doExitAnima(WindowManager windowManager) {
        windowManager.removeViewImmediate(GalleryView.this);
    }

    /**
     * 执行进入动画
     */
    private void doEnterAnima(GalleryPhotoInfo info) {
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();
        final Rect startBounds = info.startBounds;
        this.getGlobalVisibleRect(finalBounds, globalOffset);
        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);

        float startScale;
        if ((float) finalBounds.width() / finalBounds.height()
                > (float) startBounds.width() / startBounds.height()) {
            // Extend start bounds horizontally
            startScale = (float) startBounds.height() / finalBounds.height();
            float startWidth = startScale * finalBounds.width();
            float deltaWidth = (startWidth - startBounds.width()) / 2;
            startBounds.left -= deltaWidth;
            startBounds.right += deltaWidth;
        } else {
            // Extend start bounds vertically
            startScale = (float) startBounds.width() / finalBounds.width();
            float startHeight = startScale * finalBounds.height();
            float deltaHeight = (startHeight - startBounds.height()) / 2;
            startBounds.top -= deltaHeight;
            startBounds.bottom += deltaHeight;
        }

        this.setPivotX(startBounds.centerX() / finalBounds.width());
        this.setPivotY(startBounds.centerY() / finalBounds.height());

        AnimatorSet set = new AnimatorSet();
        set.play(ObjectAnimator.ofFloat(this, View.X, startBounds.left, finalBounds.left))
                .with(ObjectAnimator.ofFloat(this, View.Y, startBounds.top, finalBounds.top))
                .with(ObjectAnimator.ofFloat(this, View.SCALE_X, startScale, 1f))
                .with(ObjectAnimator.ofFloat(this, View.SCALE_Y, startScale, 1f));

        set.setDuration(400);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
//                mCurrentAnimator = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
//                mCurrentAnimator = null;
            }
        });
        set.start();
    }
}
