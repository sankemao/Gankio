package sankemao.baselib.mvp.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import sankemao.baselib.mvp.IView;
import sankemao.baselib.mvp.proxy.ActivityMvpProxy;
import sankemao.baselib.mvp.proxy.ActivityMvpProxyImp;

/**
 * Description:TODO
 * Create Time:2017/9/30.10:31
 * Author:jin
 * Email:210980059@qq.com
 */
public abstract class BaseActivity extends AppCompatActivity implements IView {

    private Unbinder mBind;

    private ActivityMvpProxy mMvpProxy;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mBind = ButterKnife.bind(this);

        if(mMvpProxy == null){
            mMvpProxy = new ActivityMvpProxyImp(this);
        }
        mMvpProxy.bindPresenter();

        initNavigationBar();
        initView(savedInstanceState);
        initData(savedInstanceState);
    }

    protected abstract @LayoutRes int getLayoutId();

    /**
     * 标题栏
     */
    protected abstract void initNavigationBar();

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
