package sankemao.baselib.mvp.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import sankemao.baselib.loadsir.core.LoadService;
import sankemao.baselib.mvp.IView;
import sankemao.baselib.mvp.proxy.FragmentMvpProxy;
import sankemao.baselib.mvp.proxy.FragmentMvpProxyImp;
import sankemao.baselib.mvp.proxy.StateViewProxy;

/**
 * Description:TODO
 * Create Time:2017/9/30.10:42
 * Author:jin
 * Email:210980059@qq.com
 */
public abstract class BaseFragment extends Fragment implements IView {

    private Unbinder mBind;
    /**
     * 记录onCreateView()中rootView是否被初始化过.
     */
    private boolean mViewInflated;
    private FragmentMvpProxy mMvpProxy;
    private StateViewProxy mStateViewProxy;
    private Context mContext;

    /**
     * @return 该fragment所关联的view是否被初始化过
     */
    public boolean isViewInflated() {
        return mViewInflated;
    }

    /**
     * @return 控制显示页
     */
    public LoadService getLoadService() {
        if (mStateViewProxy == null) {
            throw new RuntimeException("请在" + this.getClass().getSimpleName() + "类上加@StateView()注解");
        }
        return mStateViewProxy.getLoadService();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }


    @Nullable
    @Override
    public Context getContext() {
        if (this.mContext == null) {
            mContext = super.getContext();
        }
        return mContext;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(getLayoutId(), container, false);
        mViewInflated = true;

        if (mStateViewProxy == null) {
            mStateViewProxy = new StateViewProxy(this);
        }
        rootView = mStateViewProxy.handleStateView(rootView);
        mBind = ButterKnife.bind(this, rootView);
        initView(rootView);
        return rootView;
    }

    /**
     * 重新请求网络数据
     */
    public void reload(View v) {

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

    protected abstract @LayoutRes
    int getLayoutId();

    /**
     * 顶栏, 不是必须的.
     */
    @Override
    public void initNavigationBar(ViewGroup rootView) {

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
