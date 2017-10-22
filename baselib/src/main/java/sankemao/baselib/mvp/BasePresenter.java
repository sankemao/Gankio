package sankemao.baselib.mvp;

import java.lang.ref.WeakReference;

/**
 * Description:TODO
 * Create Time:2017/9/30.10:44
 * Author:jin
 * Email:210980059@qq.com
 */
public abstract class BasePresenter<V extends IView> implements IPresenter<V> {
    //弱引用, 防止内存泄漏
    private WeakReference<V> weakReference;

    @Override
    public void attatchView(V v) {
        weakReference = new WeakReference<>(v);
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
        if (weakReference != null) {
            return weakReference.get();
        }
        return null;
    }

}
