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
public abstract class BaseActivity extends AppCompatActivity implements IView {

    private Unbinder mBind;

    private PresenterManager mPresenterManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mBind = ButterKnife.bind(this);

        mPresenterManager = attachPresenters();

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
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenterManager != null) {
            mPresenterManager.destory();
        }
        mBind.unbind();
    }

    protected <T> T getPresenter(Class<T> clazz) {
        if (mPresenterManager == null) {
            return null;
        }
        return (T) mPresenterManager.getPresenter(clazz.getName());
    }

    @Override
    public void handleByView(int action, Object arg) {

    }

}
