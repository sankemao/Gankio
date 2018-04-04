package sankemao.baselib.mvp.proxy;

import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import sankemao.baselib.loadsir.callback.Callback;
import sankemao.baselib.loadsir.core.LoadService;
import sankemao.baselib.loadsir.core.LoadSir;
import sankemao.baselib.mvp.IView;
import sankemao.baselib.mvp.ioc.AutoLinearLayout;
import sankemao.baselib.mvp.ioc.StateView;

/**
 * Description: TODO
 * Create Time: 2018/3/7.9:26
 * Author:jin
 * Email:210980059@qq.com
 */
public class StateViewProxy {
    private IView mView;
    private LoadService mLoadService;

    public StateViewProxy(IView view) {
        this.mView = view;
    }

    public View handleStateView(View rootView) {
        //loadSir
        StateView stateView = mView.getClass().getAnnotation(StateView.class);
        if (stateView != null) {
            int targetId = stateView.targetId();
            View targetView = rootView;
            if (targetId > 0) {
                targetView = rootView.findViewById(targetId);
            }
            mLoadService = LoadSir.getDefault().register(targetView, new Callback.OnReloadListener() {
                @Override
                public void onReload(View v) {
                    mView.reload(v);
                }
            });
        }

        //找到真实的根布局
        View contentView;
        if (stateView == null || stateView.targetId() > 0) {
            contentView = rootView;
        } else {
            contentView = mLoadService.getLoadLayout();
        }

        AutoLinearLayout autoLinearLayout = mView.getClass().getAnnotation(AutoLinearLayout.class);
        //在内容布局外层套一个LinearLayout.使标题和内容竖直排列
        if (autoLinearLayout != null) {
            LinearLayout linearLayout = new LinearLayout(rootView.getContext());
            linearLayout.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            mView.initNavigationBar(linearLayout);

            linearLayout.addView(contentView,
                    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            //根布局为包裹在外层的linearLayout
            return linearLayout;
        } else {
            mView.initNavigationBar((ViewGroup) rootView);
            return contentView;
        }
    }

    public LoadService getLoadService() {
        return mLoadService;
    }

}
