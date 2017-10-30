package sankemao.gankio.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;

import com.blankj.utilcode.util.ToastUtils;

import sankemao.baselib.mvp.BaseActivity;
import sankemao.gankio.R;

public class MvpTestActivity extends BaseActivity<MvpTestContract.Presenter> implements MvpTestContract.View{

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mvp_test;
    }

    @Override
    public MvpTestContract.Presenter attachPresenter() {
        return new MvpTestPresenter();
    }

    @Override
    protected void initNavigationBar() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mPresenter.testDely(new Handler());
    }

    @Override
    public void showMesg() {
        ToastUtils.showShort("我是P层发过来的消息");
    }
}
