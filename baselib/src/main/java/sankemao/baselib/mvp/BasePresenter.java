package sankemao.baselib.mvp;

import android.content.Context;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Description:TODO
 * Create Time:2017/9/30.10:44
 * Author:jin
 * Email:210980059@qq.com
 */
public abstract class BasePresenter<V extends IView> {

    /**弱引用, 防止内存泄漏*/
    private WeakReference<V> weakReference;
    private V mProxyView;

    /**
     * 关联V层和P层
     */
    public void attatchView(V v) {
        weakReference = new WeakReference<>(v);
        MvpViewHandler viewHandler = new MvpViewHandler(weakReference.get());
        mProxyView = (V) Proxy.newProxyInstance(v.getClass().getClassLoader(), v.getClass().getInterfaces(), viewHandler);
    }

    /**
     * @return P层和V层是否关联.
     */
    public boolean isViewAttached() {
        return weakReference != null && weakReference.get() != null;
    }

    /**
     * 断开V层和P层
     */
    public void detachView() {
        if (isViewAttached()) {
            weakReference.clear();
            weakReference = null;
        }
    }

    public V getView() {
        return mProxyView;
    }

    private class MvpViewHandler implements InvocationHandler {
        private IView mvpView;

        MvpViewHandler(IView mvpView) {
            this.mvpView = mvpView;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            //如果V层没被销毁, 执行V层的方法.
            if (isViewAttached()) {
                try {
                    return method.invoke(mvpView, args);
                } catch (Exception e) {
                    throw e.getCause();
                }
            }
            //P层不需要关注V层的返回值
            return null;
        }
    }

    /**
     * 获取上下文
     * @return
     */
    protected Context getContext() {
        return getView().getContext();
    }

    protected void handleByView(int action, Object obj) {
        getView().handleByView(action, obj);
    }

}
