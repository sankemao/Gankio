package sankemao.baselib.mvp.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import sankemao.baselib.mvp.IView;
import sankemao.baselib.mvp.proxy.FragmentMvpProxy;
import sankemao.baselib.mvp.proxy.FragmentMvpProxyImp;

/**
 * Description:TODO
 * Create Time:2017/9/30.10:42
 * Author:jin
 * Email:210980059@qq.com
 */
public abstract class BaseFragment extends Fragment implements IView {

    private Unbinder mBind;
    private View mRootView;
    /**
     * 记录onCreateView()中rootView是否被初始化过.
     */
    private boolean mViewInflated;
    private FragmentMvpProxy mMvpProxy;

    /**
     * @return 该fragment所关联的view是否被初始化过
     */
    public boolean isViewInflated() {
        return mViewInflated;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(getLayoutId(), container, false);
        mViewInflated = true;
        mBind = ButterKnife.bind(this, mRootView);
        initNavigationBar((ViewGroup) mRootView);
        initView(mRootView);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mMvpProxy == null) {
            mMvpProxy = new FragmentMvpProxyImp(this);
        }
        mMvpProxy.bindPresenter();
        initData();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    protected abstract @LayoutRes int getLayoutId();

    /**
     * 顶栏, 不是必须的.
     */
    protected void initNavigationBar(ViewGroup rootView){

    }

    /**
     * 初始化View.
     */
    protected abstract void initView(View rootView);

    /**
     * 加载数据, 在onViewCreated中执行
     */
    protected abstract void initData();

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMvpProxy.unbindPresenter();
        mBind.unbind();
    }

}
