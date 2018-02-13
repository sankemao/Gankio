package sankemao.gankio.ui.fragment;

import android.content.Intent;
import android.view.View;

import butterknife.OnClick;
import sankemao.baselib.mvp.base.BaseFragment;
import sankemao.gankio.R;
import sankemao.gankio.ui.activity.test.RecyclerViewTestActivity;

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
    protected void initView(View rootView) {

    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.btn_click)
    public void gotoTest() {
        Intent intent = new Intent(getContext(), RecyclerViewTestActivity.class);
        getContext().startActivity(intent);
    }
}
