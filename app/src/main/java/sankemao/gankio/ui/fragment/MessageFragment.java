package sankemao.gankio.ui.fragment;

import android.view.View;

import sankemao.baselib.mvp.BaseFragment;
import sankemao.baselib.mvp.IPresenter;
import sankemao.gankio.R;

/**
 * Description:TODO
 * Create Time:2017/10/10.11:06
 * Author:jin
 * Email:210980059@qq.com
 */
public class MessageFragment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_message;
    }

    @Override
    public IPresenter attachPresenter() {
        return null;
    }

    @Override
    protected void initView(View rootView) {

    }

    @Override
    protected void initData() {

    }
}
