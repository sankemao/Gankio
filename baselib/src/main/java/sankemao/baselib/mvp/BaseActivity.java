package sankemao.baselib.mvp;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.LinkedHashMap;

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

    private LinkedHashMap<String, BasePresenter> mPresenters;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mBind = ButterKnife.bind(this);

        attachPresenters();

        if (mPresenters != null) {
            for (String tag : mPresenters.keySet()) {
                BasePresenter presenter = mPresenters.get(tag);
                presenter.attatchView(this);
            }
        }
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

    protected void addPresenter(BasePresenter presenter) {
        if (mPresenters == null) {
            mPresenters = new LinkedHashMap<>();
        }
        mPresenters.put(presenter.getClass().getName(), presenter);
    }

    protected <T> T getPresenter(Class<T> clazz) {
        return (T) mPresenters.get(clazz.getName());
    }

    @Override
    public void handleByView(int action, Object arg) {

    }
}
