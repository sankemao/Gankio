package sankemao.gankio.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;

import com.blankj.utilcode.util.ToastUtils;

import butterknife.OnClick;
import sankemao.baselib.mvp.BaseActivity;
import sankemao.gankio.R;

public class MvpTestActivity extends BaseActivity implements IMvpTestView, ITestView{

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void attachPresenters() {
        addPresenter(new MvpTestPresenter());
        addPresenter(new TestPresenter());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mvp_test;
    }

    @Override
    protected void initNavigationBar() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        getPresenter(MvpTestPresenter.class).testDely(new Handler());
    }

    @Override
    public void showMesg() {
        ToastUtils.showShort("我是MvpTestPresenter中延时发过来的消息");
    }

    @OnClick(R.id.btn_testcontract)
    public void onViewClicked() {
        getPresenter(TestPresenter.class).test();
    }

    @Override
    public void showTestMesg() {
        ToastUtils.showShort("我是TestPresenter中的发过来的消息");
    }
}
