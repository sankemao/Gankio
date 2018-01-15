package sankemao.gankio.ui.activity;

import android.content.Context;
import android.os.Bundle;

import sankemao.baselib.mvp.base.BaseActivity;
import sankemao.baselib.mvp.PresenterManager;

public class SplashActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public PresenterManager attachPresenters() {
        return null;
    }

    @Override
    protected void initNavigationBar() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

}
