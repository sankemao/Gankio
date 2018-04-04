package sankemao.baselib.mvp.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import sankemao.baselib.mvp.IView;
import sankemao.baselib.mvp.proxy.ActivityMvpProxy;
import sankemao.baselib.mvp.proxy.ActivityMvpProxyImp;
import sankemao.baselib.mvp.proxy.StateViewProxy;

/**
 * Description:TODO
 * Create Time:2017/9/30.10:31
 * Author:jin
 * Email:210980059@qq.com
 */
public abstract class BaseActivity extends AppCompatActivity implements IView {

    private Unbinder mBind;

    private ActivityMvpProxy mMvpProxy;

    private StateViewProxy mStateViewProxy;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewGroup parentView = (ViewGroup) findViewById(android.R.id.content);

        View rootView = LayoutInflater.from(this).inflate(getLayoutId(), parentView, false);

        if (mStateViewProxy == null) {
            mStateViewProxy = new StateViewProxy(this);
        }
        rootView = mStateViewProxy.handleStateView(rootView);

        setContentView(rootView);

        mBind = ButterKnife.bind(this);

        if(mMvpProxy == null){
            mMvpProxy = new ActivityMvpProxyImp(this);
        }
        mMvpProxy.bindPresenter();

        initView(savedInstanceState);
        initData(savedInstanceState);
    }

    @Override
    public void reload(View v) {

    }

    protected abstract @LayoutRes int getLayoutId();

    /**
     * 初始化view.
     */
    protected abstract void initView(Bundle savedInstanceState);

    /**
     * 初始化data
     */
    protected abstract void initData(Bundle savedInstanceState);

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMvpProxy.unbindPresenter();
        mBind.unbind();
    }

}
