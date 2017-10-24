package sankemao.baselib.ui.navigation;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author: jin .
 * @time: 2017/10/18 10:28
 * @description: TODO
 */
public class AbsNavigationBar<B extends AbsNavigationBar.AbsBuilder> implements INavigation {

    protected B mBuilder;

    private View mNavigationView;

    public AbsNavigationBar(B builder) {
        this.mBuilder = builder;
        createNavigationBar();
    }

    @Override
    public void createNavigationBar() {
        ViewGroup parent = mBuilder.mParent;

        //不传入父布局的时候， 使用默认的布局， Activity的根部局必须为LinearLayout.
        if (parent == null) {
            ViewGroup activityRoot = (ViewGroup) ((Activity) mBuilder.mContext).findViewById(android.R.id.content);
            parent = (ViewGroup) activityRoot.getChildAt(0);
        }

        mNavigationView = LayoutInflater.from(mBuilder.mContext)
                .inflate(mBuilder.mLayoutId, parent, false);

        attachParent(mNavigationView, parent);

        attachNavigationParams();
    }

    @Override
    public void attachParent(View navigationView, ViewGroup parent) {
       parent.addView(navigationView, 0);
    }

    @Override
    public void attachNavigationParams() {
        // 设置文本
        SparseArray<CharSequence> textMap = mBuilder.mTextMap;
        int textMapSize = textMap.size();
        for (int i = 0; i < textMapSize; i++) {
            int key = textMap.keyAt(i);
            TextView tv = getViewById(key);
            tv.setText(textMap.get(key));
        }

        // 设置点击事件
        SparseArray <View.OnClickListener> clickListenerMap = mBuilder.mClickListenerMap;
        int clickMapSize = clickListenerMap.size();
        for (int i = 0; i <clickMapSize ; i++) {
            int key = clickListenerMap.keyAt(i);
            View view = getViewById(key);
            view.setOnClickListener(clickListenerMap.get(key));
        }

        //设置图片
        SparseArray <Integer> imageResMap = mBuilder.mImageResMap;
        int imageResMapSize = imageResMap.size();
        for (int i = 0; i <imageResMapSize ; i++) {
            int key = imageResMap.keyAt(i);
            ImageView iv = getViewById(key);
            if (iv != null) {
                iv.setVisibility(View.VISIBLE);
                iv.setImageResource(imageResMap.get(key));
            }
        }

        //设置可见性
        SparseArray<Integer> visibityMap = mBuilder.mVisibilityMap;
        if (visibityMap != null) {
            int visibityMapSize = visibityMap.size();
            for (int i = 0; i < visibityMapSize; i++) {
                int key = visibityMap.keyAt(i);
                View view = getViewById(key);
                view.setVisibility(visibityMap.get(key));
            }
        }

    }

    public <T extends View> T getViewById(@IdRes int viewId) {
        return (T)mNavigationView.findViewById(viewId);
    }

    public static abstract class AbsBuilder<B> {
        public Context mContext;
        public int mLayoutId;
        public ViewGroup mParent;
        public SparseArray<CharSequence> mTextMap;
        public SparseArray<View.OnClickListener> mClickListenerMap;
        public SparseArray<Integer> mImageResMap;
        public SparseArray<Integer> mVisibilityMap;

        public AbsBuilder(Context context, @LayoutRes int layoutId, ViewGroup parent) {
            this.mContext = context;
            this.mLayoutId = layoutId;
            this.mParent = parent;
            mTextMap = new SparseArray<>();
            mClickListenerMap = new SparseArray<>();
            mImageResMap = new SparseArray<>();
        }

        /**
         * 构建NavigationBar.
         * @return 构建完成的NavigationBar
         */
        public abstract AbsNavigationBar build();

        /**
         * 设置文字
         */
        public B setText(@IdRes int viewId, String text) {
            mTextMap.put(viewId, text);
            return (B) this;
        }

        /**
         * 设置点击事件
         */
        public B setOnClickListener(int viewId, View.OnClickListener clickListener) {
            mClickListenerMap.put(viewId, clickListener);
            return (B) this;
        }

        /**
         * 设置图标
         */
        public B setImageRes(int viewId, @DrawableRes int resourceId) {
            mImageResMap.put(viewId, resourceId);
            return (B) this;
        }

        /**
         * 设置是否可见
         */
        public B setVisibity(int viewId, int visibity) {
            //设置可见性不常用， 集合的初始化放到这里。
            if (mVisibilityMap == null) {
                mVisibilityMap = new SparseArray<>();
            }
            mVisibilityMap.put(viewId, visibity);
            return (B) this;
        }

    }

}
