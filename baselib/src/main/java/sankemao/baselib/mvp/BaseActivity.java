package sankemao.baselib.mvp;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Description:TODO
 * Create Time:2017/9/30.10:31
 * Author:jin
 * Email:210980059@qq.com
 */
public abstract class BaseActivity<P extends IPresenter> extends AppCompatActivity implements IView<P> {

    private Unbinder mBind;

    protected P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mBind = ButterKnife.bind(this);
        mPresenter = attachPresenter();
        if (mPresenter != null) {
            mPresenter.attatchView(this);
        }
        initNavigationBar();
        initView(savedInstanceState);
        initData(savedInstanceState);
    }

    protected abstract @LayoutRes int getLayoutId();

    @Override
    public abstract P attachPresenter();

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
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        mBind.unbind();
    }
}
