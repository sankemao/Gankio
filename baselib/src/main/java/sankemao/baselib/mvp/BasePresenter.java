package sankemao.baselib.mvp;

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
public abstract class BasePresenter<V extends IView> implements IPresenter<V> {

    /**弱引用, 防止内存泄漏*/
    private WeakReference<V> weakReference;
    private V mProxyView;

    @Override
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

    @Override
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
            if (isViewAttached()) {
                return method.invoke(mvpView, args);
            }
            return null;
        }
    }

}
