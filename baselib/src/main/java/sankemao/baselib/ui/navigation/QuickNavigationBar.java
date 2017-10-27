//package sankemao.baselib.ui.navigation;
//
//import android.app.Activity;
//import android.content.Context;
//import android.view.View;
//import android.view.ViewGroup;
//
//import sankemao.baselib.R;
//
//
///**
// * @author: jin .
// * @time: 2017/10/18 10:17
// * @description: 默认的导航栏(项目中大部分用到的, 自带viewGroup)
// */
//public class QuickNavigationBar extends AbsNavigationBar <QuickNavigationBar.Builder> {
//
//
//    public QuickNavigationBar(Builder builder) {
//        super(builder);
//
//        applyCustomAttr();
//    }
//
//    /**
//     * 设置一些特有属性
//     */
//    private void applyCustomAttr() {
//
//    }
//
//    public static class Builder extends AbsNavigationBar.AbsBuilder<Builder> {
//
//        private View.OnClickListener mDefaultLeftClickListener;
//
//        /**
//         * 采用默认的根部局（activity的linearLayout）
//         */
//        public Builder(Context context) {
//            super(context, R.layout.widget_navigationbar, null);
//        }
//
//        /**
//         * 自定义根部局
//         */
//        public Builder(Context context, ViewGroup parent) {
//            super(context, R.layout.widget_navigationbar, parent);
//        }
//
//        @Override
//        public AbsNavigationBar build() {
//            initQuickAttr();
//            return new QuickNavigationBar(this);
//        }
//
//        /**
//         * 设置一些特有的属性
//         */
//        private void initQuickAttr() {
//            //当没有主动设置返回键点击事件时， 默认设置finish.
//            if (mClickListenerMap.get(R.id.left_icon) == null) {
//                mClickListenerMap.put(R.id.left_icon, new View.OnClickListener(){
//                    @Override
//                    public void onClick(View v) {
//                        ((Activity) mContext).finish();
//                    }
//                });
//            }
//
//            if (mImageResMap.get(R.id.left_icon) == null) {
//                mImageResMap.put(R.id.left_icon, R.drawable.icon_exit);
//            }
//        }
//
//        //以下为结合项目通用NavigationBar的一些通用的设置
//
//        /**
//         * 返回键的点击事件
//         */
//        public Builder setLeftClickListener(View.OnClickListener clickListener){
//            setOnClickListener(R.id.left_icon, clickListener);
//            return this;
//        }
//
//        /**
//         * 设置标题
//         */
//        public Builder setTitle(String title) {
//            setText(R.id.title, title);
//            return this;
//        }
//    }
//}
//
