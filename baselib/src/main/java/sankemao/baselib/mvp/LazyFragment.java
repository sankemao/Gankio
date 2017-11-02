package sankemao.baselib.mvp;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;

/**
 * Description:懒加载
 * Create Time:2017/9/30.11:26
 * Author:jin
 * Email:210980059@qq.com
 */
public abstract class LazyFragment extends BaseFragment {
    protected boolean mIsVisible = false;   //当前fragment是否可见.
    private boolean mIsFirst = true;        //第一次加载数据.
    private boolean mIsPrepared = false;    //在onActivityCreated中变为true, 即已执行initView.
    /**
     * viewpager中, 进入第一页, 所有被加载的页面(setOffScreenPageLimit控制)都会执行setUserVisibleHint
     * 后续页面setUserVisibleHint(false)
     * 当切换到其他页面时, 会执行可见页面的setUserVisibleHint(true)
     * 不可见页面的setUserVisibleHint(false).
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mIsVisible = isVisibleToUser;
        if (isVisibleToUser) {
            lazy();
        } else if (!mIsFirst) {
            onInvisible();
        }
    }

    /**
     * viewpager: 在第一页时相邻的执行了.
     */
    @CallSuper
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mIsPrepared = true;
        lazy();//让第一个可见的fragment初始化数据.
    }

    /**
     * 懒加载
     */
    private void lazy() {
        if (mIsVisible && mIsPrepared && mIsFirst) {
            initLazyData();
            mIsFirst = false;
        }

        if (!mIsFirst) {
            onVisible();
        }
    }

    /**
     * 加载完数据后的页面每次不可见
     */
    protected void onInvisible() {

    }

    /**
     * 加载完数据后的页面每次可见
     */
    protected void onVisible() {

    }

    /**
     * 懒加载， 当fragment可见的时候执行。
     */
    protected abstract void initLazyData();

    /**
     * 默认实现非懒加载形式的initData.
     */
    @Override
    protected void initData() {

    }
}
