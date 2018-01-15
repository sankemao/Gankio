package sankemao.baselib.mvp.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.lang.reflect.Field;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import sankemao.baselib.mvp.IView;
import sankemao.baselib.mvp.PresenterManager;
import sankemao.baselib.mvp.inject.InjectPresenter;

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

        //通过反射注入presenter
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            InjectPresenter injectPresenter = field.getAnnotation(InjectPresenter.class);
            if (injectPresenter != null) {
                //创建注入
                Class<? extends BasePresenter> presenterClazz = null;
                //获取父类， 如果不是继承自BasePresenter则报错。

                try {
                    presenterClazz = (Class<? extends BasePresenter>) field.getType();
                } catch (Exception e) {
                    throw new RuntimeException("presenter注入失败");
                }

                try {
                    //创建presenter对象
                    BasePresenter presenter = presenterClazz.newInstance();
                    presenter.attachView(this);
                    //设置
                    field.setAccessible(true);
                    field.set(this, presenter);
                } catch (Exception e) {
                    e.printStackTrace();
                }

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
        if (mPresenterManager != null) {
            mPresenterManager.destroy();
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
