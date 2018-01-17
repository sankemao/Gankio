package sankemao.gankio.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import butterknife.BindView;
import butterknife.OnClick;
import sankemao.baselib.mvp.FragmentManagerHelper;
import sankemao.baselib.mvp.base.BaseActivity;
import sankemao.baselib.ui.utils.StatusbarUtil;
import sankemao.gankio.R;
import sankemao.gankio.ui.fragment.FindFragment;
import sankemao.gankio.ui.fragment.HomeFragment;
import sankemao.gankio.ui.fragment.MessageFragment;
import sankemao.gankio.ui.fragment.NewFragment;

public class HomeAcitivy extends BaseActivity {

    @BindView(R.id.fl_container)
    FrameLayout mFlContainer;


    private HomeFragment mHomeFragment;
    private FindFragment mFindFragment;
    private NewFragment mNewFragment;
    private MessageFragment mMessageFragment;

    private FragmentManagerHelper mFragmentHelper;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void initNavigationBar() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        //沉浸式状态栏
        StatusbarUtil.setStatusBarColor(this, Color.parseColor("#ff00ddff"));

        mFragmentHelper = new FragmentManagerHelper(getSupportFragmentManager(), R.id.fl_container);
        HomeFragment homeFragment = mFragmentHelper.findfragment(HomeFragment.class);
        if (homeFragment == null) {
            mHomeFragment = new HomeFragment();
            mFragmentHelper.add(mHomeFragment);
        } else {
            mHomeFragment = homeFragment;
            mFindFragment = mFragmentHelper.findfragment(FindFragment.class);
            mNewFragment = mFragmentHelper.findfragment(NewFragment.class);
            mMessageFragment = mFragmentHelper.findfragment(MessageFragment.class);
        }
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @OnClick({R.id.home_rb, R.id.find_rb, R.id.new_rb, R.id.message_rb})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home_rb:
                if (mHomeFragment == null) {
                    mHomeFragment = new HomeFragment();
                }
                mFragmentHelper.switchFragment(mHomeFragment);
                break;
            case R.id.find_rb:
                if (mFindFragment == null) {
                    mFindFragment = new FindFragment();
                }
                mFragmentHelper.switchFragment(mFindFragment);
                break;
            case R.id.new_rb:
                if (mNewFragment == null) {
                    mNewFragment = new NewFragment();
                }
                mFragmentHelper.switchFragment(mNewFragment);
                break;
            case R.id.message_rb:
                if (mMessageFragment == null) {
                    mMessageFragment = new MessageFragment();
                }
                mFragmentHelper.switchFragment(mMessageFragment);
                break;
            default:
                break;
        }
    }

}
