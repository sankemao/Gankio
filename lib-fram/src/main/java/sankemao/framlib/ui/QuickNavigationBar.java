package sankemao.framlib.ui;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import sankemao.baselib.ui.navigation.AbsNavigationBar;
import sankemao.framlib.R;


/**
 * author: jin .
 * time: 2017/10/18 10:17
 * description: 默认的导航栏(项目中大部分用到的, 自带viewGroup)
 */
public class QuickNavigationBar extends AbsNavigationBar<QuickNavigationBar.Builder> {

    public QuickNavigationBar(Builder builder) {
        super(builder);

        applyCustomAttr();
    }

    /**
     * 设置一些特有属性
     */
    private void applyCustomAttr() {

    }

    public static class Builder extends AbsNavigationBar.AbsBuilder<Builder> {

        /**
         * 采用默认的根部局（activity的linearLayout）
         */
        public Builder(Context context) {
            super(context, R.layout.ui_quick_navigationbar, null);
        }

        /**
         * 自定义根部局
         */
        public Builder(Context context, ViewGroup parent) {
            super(context, R.layout.ui_quick_navigationbar, parent);
        }

        @Override
        public AbsNavigationBar build() {
            return new QuickNavigationBar(this);
        }

        //以下为结合项目通用NavigationBar的一些通用的设置

        /**
         * 开启退出按键
         */
        public Builder setLeftExit() {
            //当没有主动设置返回键点击事件时， 默认设置finish.
            if (mClickListenerMap.get(R.id.left_icon) == null) {
                mClickListenerMap.put(R.id.left_icon, new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        ((Activity) mContext).finish();
                    }
                });
            }

            if (mImageResMap.get(R.id.left_icon) == null) {
                mImageResMap.put(R.id.left_icon, R.drawable.ic_exit);
            }
            return this;
        }

        /**
         * 返回键的点击事件
         */
        public Builder setLeftClickListener(View.OnClickListener clickListener){
            setOnClickListener(R.id.left_icon, clickListener);
            return this;
        }

        /**
         * 设置标题
         */
        public Builder setTitle(String title) {
            setText(R.id.title, title);
            return this;
        }
    }
}

