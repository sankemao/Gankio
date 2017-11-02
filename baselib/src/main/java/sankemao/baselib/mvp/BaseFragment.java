package sankemao.baselib.mvp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Description:TODO
 * Create Time:2017/9/30.10:42
 * Author:jin
 * Email:210980059@qq.com
 */
public abstract class BaseFragment extends Fragment implements IView {

    private Unbinder mBind;
    protected Context mContext;
    private View mRootView;

    private HashMap<String, BasePresenter> mPresenters;

    /**
     * 记录onCreateView()中rootView是否被解析了.
     */
    private boolean mViewInflated;

    public boolean isViewInflated() {
        return mViewInflated;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(getLayoutId(), container, false);
        mViewInflated = true;
        mBind = ButterKnife.bind(this, mRootView);
        mContext = getContext();
        initNavigationBar((ViewGroup) mRootView);
        initView(mRootView);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        attachPresenters();
        if (mPresenters != null) {
            for (String tag : mPresenters.keySet()) {
                BasePresenter presenter = mPresenters.get(tag);
                if (presenter != null) {
                    presenter.attatchView(this);
                }
            }
        }
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
        if (mPresenters != null) {
            for (String tag : mPresenters.keySet()) {
                BasePresenter presenter = mPresenters.get(tag);
                if (presenter != null) {
                    presenter.detachView();
                }
            }
        }
        mBind.unbind();
    }

    protected BaseFragment addPresenter(BasePresenter presenter) {
        if (mPresenters == null) {
            mPresenters = new HashMap<>();
        }
        mPresenters.put(presenter.getClass().getName(), presenter);
        return this;
    }

    protected <T> T getPresenter(Class<T> clazz) {
        return (T) mPresenters.get(clazz.getName());
    }
}
