package sankemao.baselib.ui.popupwindow;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * Description:TODO
 * Create Time: 2017/10/23.15:31
 * Author:jin
 * Email:210980059@qq.com
 */
public class QuickPopup extends PopupWindow {

    private View mPopView;
    private QuickPopup.Builder mBuilder;

    public <T extends View> T getViewById(@IdRes int viewId) {
        return (T)mPopView.findViewById(viewId);
    }

    public View getRootView() {
        return mPopView;
    }

    public QuickPopup(Builder builder) {
        super(builder.mContext);
        this.mBuilder = builder;
        apply();
    }

    private void apply() {
        mPopView = mBuilder.mPopView;
        if (mPopView == null) {
            mPopView = LayoutInflater.from(mBuilder.mContext).inflate(mBuilder.mLayoutId, null, false);
        }

        //设置rootView
        setContentView(mPopView);

        //设置宽高
        setWidth(mBuilder.mWidth == 0 ? ViewGroup.LayoutParams.WRAP_CONTENT : mBuilder.mWidth);
        setHeight(mBuilder.mHeight == 0 ? ViewGroup.LayoutParams.WRAP_CONTENT : mBuilder.mHeight);

        //设置焦点
        setTouchable(true);
        setFocusable(true);
        setBackgroundDrawable(new ColorDrawable());

        //设置返回键事件, 返回
        getContentView().setFocusableInTouchMode(true);
        this.getContentView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    dismiss();
                    return true;
                }
                return false;
            }
        });

        //点击外部是否可取消.
        if (!mBuilder.mOutsideTouchable) {
            setTouchInterceptor(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    final int x = (int) event.getX();
                    final int y = (int) event.getY();

                    if ((event.getAction() == MotionEvent.ACTION_DOWN)
                            && ((x < 0) || (x >= getContentView().getWidth()) || (y < 0) || (y >= getContentView().getHeight()))) {
                        return true;
                    } else if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                        return true;
                    }
                    return false;
                }
            });
        }

        //消失监听事件
        setOnDismissListener(mBuilder.mOnDismissListener);

        initViews();
    }

    /**
     * 设置ui和点击事件
     */
    private void initViews() {
        //设置文字
        SparseArray<CharSequence> textsMap = mBuilder.mTexts;
        int textsSize = textsMap.size();
        for (int i = 0; i < textsSize; i++) {
            int viewId = textsMap.keyAt(i);
            TextView v = getViewById(viewId);
            v.setText(textsMap.get(viewId));
        }

        //点击事件
        SparseArray<View.OnClickListener> listenersMap = mBuilder.mClickListeners;
        int listenersSize = listenersMap.size();
        for (int i = 0; i < listenersSize; i++) {
            int viewId = listenersMap.keyAt(i);
            View v = getViewById(viewId);
            v.setOnClickListener(listenersMap.get(viewId));
        }

        //设置图片
        SparseArray<Integer> imageResMap = mBuilder.mImageResMap;
        int imageResSize = imageResMap.size();
        for (int i = 0; i < imageResSize; i++) {
            int viewId = imageResMap.keyAt(i);
            ImageView v = getViewById(viewId);
            v.setImageResource(imageResMap.get(viewId));
        }

        //设置可见性
        SparseArray<Integer> visibilityMap = mBuilder.mImageResMap;
        int visibilitySize = visibilityMap.size();
        for (int i = 0; i < visibilitySize; i++) {
            int viewId = visibilityMap.keyAt(i);
            View v = getViewById(viewId);
            v.setVisibility(visibilityMap.get(viewId));
        }
    }

    private int sp2px(float sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, mBuilder.mContext.getResources().getDisplayMetrics());
    }

    private float dp2px(int dip) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, mBuilder.mContext.getResources().getDisplayMetrics());
    }

    private static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    private static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static class Builder {
        private Context mContext;
        private int mLayoutId;
        private int mWidth;
        private int mHeight;
        private View mPopView;
        private boolean mOutsideTouchable;
        private OnDismissListener mOnDismissListener;
        private SparseArray<View.OnClickListener> mClickListeners;
        private SparseArray<CharSequence> mTexts;
        private SparseArray<Integer> mImageResMap;
        private SparseArray<Integer> mVisibilityMap;
        /**
         * 当pop在anchorView下方显示空间不够的时候, 是否需要显示上面.
         */
        private boolean mIsNeedShowUp;

        {
            mOutsideTouchable = true;
            mTexts = new SparseArray<>();
            mClickListeners = new SparseArray<>();
            mImageResMap = new SparseArray<>();
        }

        public Builder(Context context, View popView) {
            this.mContext = context;
            this.mPopView = popView;
        }

        public Builder(Context context, @LayoutRes int layoutId) {
            this.mContext = context;
            this.mLayoutId = layoutId;
        }

        public Builder setWH(int width, int height) {
            this.mWidth = width;
            this.mHeight = height;
            return this;
        }

        public Builder setOnDisMissListener(OnDismissListener onDisMissListener) {
            this.mOnDismissListener = onDisMissListener;
            return this;
        }

        public Builder setOutsideTouchable(boolean touchable) {
            this.mOutsideTouchable = touchable;
            return this;
        }

        public Builder setClickListener(@IdRes int viewId, View.OnClickListener listener) {
            mClickListeners.put(viewId, listener);
            return this;
        }

        public Builder setText(@IdRes int viewId, String text) {
            mTexts.put(viewId, text);
            return this;
        }

        public Builder setImageRes(int viewId, @DrawableRes int resourceId) {
            mImageResMap.put(viewId, resourceId);
            return this;
        }

        /**
         * 设置是否可见
         */
        public Builder setVisibity(int viewId, int visibility) {
            //设置可见性不常用， 集合的初始化放到这里。
            if (mVisibilityMap == null) {
                mVisibilityMap = new SparseArray<>();
            }
            mVisibilityMap.put(viewId, visibility);
            return this;
        }

        public QuickPopup build() {
            return new QuickPopup(this);
        }

        public QuickPopup show(View anchorView) {
            return show(anchorView, 0, 0);
        }

        public QuickPopup show(View anchorView, int xOffset, int yOffset) {
            QuickPopup popup = build();
            int[] windowPos = calculatePopWindowPos(anchorView, popup.getRootView(), yOffset);
            windowPos[0] += xOffset;
            if (mIsNeedShowUp) {
                windowPos[1] -= yOffset;
            } else {
                windowPos[1] += yOffset;
            }
            popup.showAtLocation(anchorView, Gravity.TOP | Gravity.START, windowPos[0], windowPos[1]);
            return popup;
        }

        private int[] calculatePopWindowPos(final View anchorView, View popView, int yOffSet) {

            final int[] windowPos = new int[2];
            final int[] anchorLoc = new int[2];
            // 获取锚点View在屏幕上的左上角坐标位置
            anchorView.getLocationOnScreen(anchorLoc);
            final int anchorHeight = anchorView.getHeight();

            // 获取屏幕的高宽
            final int screenHeight = getScreenHeight(anchorView.getContext());
            final int screenWidth = getScreenWidth(anchorView.getContext());

            popView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);

            // 计算popView的高宽
            final int popViewHeight = popView.getMeasuredHeight();
            final int popViewWidth = popView.getMeasuredWidth();
            // 判断需要向上弹出还是向下弹出显示
            mIsNeedShowUp = (screenHeight - (anchorLoc[1] + anchorHeight + yOffSet) < popViewHeight);
            if (mIsNeedShowUp) {
                mIsNeedShowUp = (screenHeight - (anchorLoc[1] + anchorHeight) < popViewHeight);
            }
            if (mIsNeedShowUp) {
                windowPos[0] = anchorLoc[0];
                windowPos[1] = anchorLoc[1] - popViewHeight;
            } else {
                windowPos[0] = anchorLoc[0];
                windowPos[1] = anchorLoc[1] + anchorHeight;
            }
            return windowPos;
        }
    }
}
