package sankemao.baselib.mvp.base;

import android.content.Context;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import sankemao.baselib.mvp.IView;

/**
 * Description:TODO
 * Create Time:2017/9/30.10:44
 * Author:jin
 * Email:210980059@qq.com
 */
public abstract class BasePresenter<V extends IView> {
    //用于记录P层和V层是否有关联。
    private V mView;
    private V mProxyView;

    /**
     * 关联V层和P层
     */
    public void attachView(V view) {
        mView = view;
        MvpViewHandler viewHandler = new MvpViewHandler(mView);
        mProxyView = (V) Proxy.newProxyInstance(view.getClass().getClassLoader(), view.getClass().getInterfaces(), viewHandler);
    }

    /**
     * @return P层和V层是否关联.
     */
    public boolean isViewAttached() {
        return mView != null;
    }

    /**
     * 断开V层和P层
     */
    public void detachView() {
        mView = null;
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
     */
    protected Context getContext() {
        return getView().getContext();
    }

}
