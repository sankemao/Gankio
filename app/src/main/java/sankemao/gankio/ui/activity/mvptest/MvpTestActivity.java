package sankemao.gankio.ui.activity.mvptest;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;

import com.blankj.utilcode.util.ToastUtils;

import butterknife.OnClick;
import sankemao.baselib.mvp.base.BaseActivity;
import sankemao.baselib.mvp.PresenterManager;
import sankemao.gankio.R;

public class MvpTestActivity extends BaseActivity implements IMvpTestV, ITestV {

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public PresenterManager attachPresenters() {
        return PresenterManager.begin()
                .addPresenter(new TestP())
                .addPresenter(new MvpTestP())
                .attach(this);
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
        getPresenter(MvpTestP.class).testDely(new Handler());
    }

    @Override
    public void showMesg() {
        ToastUtils.showShort("我是MvpTestPresenter中延时发过来的消息");
    }

    @OnClick(R.id.btn_testcontract)
    public void onViewClicked() {
        getPresenter(TestP.class).test();
    }

    @Override
    public void showTestMesg() {
        ToastUtils.showShort("我是TestPresenter中的发过来的消息");
    }

    @Override
    public void handleByView(int action, Object arg) {
        switch (action) {
            case IMvpTestV.ACTION_TOAST:
                ToastUtils.showShort(((String) arg));
                break;
            default:
                break;
        }
    }
}
