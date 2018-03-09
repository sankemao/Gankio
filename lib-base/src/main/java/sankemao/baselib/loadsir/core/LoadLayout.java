package sankemao.baselib.loadsir.core;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.FrameLayout;

import sankemao.baselib.loadsir.LoadSirUtil;
import sankemao.baselib.loadsir.callback.Callback;
import sankemao.baselib.loadsir.callback.SuccessCallback;

import java.util.HashMap;
import java.util.Map;

/**
 * Description:各个状态页的根布局.
 * Create Time:2017/9/2 17:02
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */

public class LoadLayout extends FrameLayout {
    //存放的是每个页面的独有的callbacks, 它是将单例中配置的callback克隆的一遍，因为每个页面中的点击事件都是不能复用的。
    private Map<Class<? extends Callback>, Callback> callbacks = new HashMap<>();
    private Context context;
    private Callback.OnReloadListener onReloadListener;
    private Class<? extends Callback> preCallback;
    private static final int CALLBACK_CUSTOM_INDEX = 1;

    public LoadLayout(@NonNull Context context) {
        super(context);
    }

    public LoadLayout(@NonNull Context context, Callback.OnReloadListener onReloadListener) {
        this(context);
        this.context = context;
        this.onReloadListener = onReloadListener;
    }

    public void setupSuccessLayout(Callback callback) {
        addCallback(callback);
        View successView = callback.getRootView();
        successView.setVisibility(View.GONE);
        addView(successView);
    }

    /**
     * 克隆callback，设置独有的点击事件
     * @param callback
     */
    public void setupCallback(Callback callback) {
        Callback cloneCallback = callback.copy();
        cloneCallback.setCallback(null, context, onReloadListener);
        addCallback(cloneCallback);
    }

    public void addCallback(Callback callback) {
        if (!callbacks.containsKey(callback.getClass())) {
            callbacks.put(callback.getClass(), callback);
        }
    }

    public void showCallback(final Class<? extends Callback> callback) {
        checkCallbackExist(callback);
        if (LoadSirUtil.isMainThread()) {
            showCallbackView(callback);
        } else {
            postToMainThread(callback);
        }
    }

    private void postToMainThread(final Class<? extends Callback> status) {
        post(new Runnable() {
            @Override
            public void run() {
                showCallbackView(status);
            }
        });
    }

    private void showCallbackView(Class<? extends Callback> status) {
        //隐藏前一个状态页
        if (preCallback != null) {
            if (preCallback == status) {
                return;
            }
            callbacks.get(preCallback).onDetach();
        }
        if (getChildCount() > 1) {
            removeViewAt(CALLBACK_CUSTOM_INDEX);
        }

        //遍历所有页面
        for (Class key : callbacks.keySet()) {
            //找到该状态的页面
            if (key == status) {
                //成功页面
                SuccessCallback successCallback = (SuccessCallback) callbacks.get(SuccessCallback.class);
                if (key == SuccessCallback.class) {
                    //如果显示成功页
                    successCallback.show();
                } else {
                    //如果非成功页面显示。
                    successCallback.showWithCallback(callbacks.get(key).getSuccessVisible());
                    //获取根布局。
                    View rootView = callbacks.get(key).getRootView();
                    //添加根布局
                    addView(rootView);
                    callbacks.get(key).onAttach(context, rootView);
                }
                preCallback = status;
            }
        }
    }

    public void setCallBack(Class<? extends Callback> callback, Transport transport) {
        if (transport == null) {
            return;
        }
        checkCallbackExist(callback);
        transport.order(context, callbacks.get(callback).obtainRootView());
    }

    private void checkCallbackExist(Class<? extends Callback> callback) {
        if (!callbacks.containsKey(callback)) {
            throw new IllegalArgumentException(String.format("The Callback (%s) is nonexistent.", callback
                    .getSimpleName()));
        }
    }
}
