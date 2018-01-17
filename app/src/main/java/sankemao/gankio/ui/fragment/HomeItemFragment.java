package sankemao.gankio.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;
import sankemao.baselib.mvp.LazyFragment;
import sankemao.gankio.R;


/**
 * Description:TODO
 * Create Time:2017/10/10.11:33
 * Author:jin
 * Email:210980059@qq.com
 */
public class HomeItemFragment extends LazyFragment {
    private String mTitle;

    @BindView(R.id.tv_title)
    TextView mTvTitle;

    public static HomeItemFragment newInstance(String title) {
        HomeItemFragment itemFragment = new HomeItemFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        itemFragment.setArguments(bundle);
        return itemFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        mTitle = bundle.getString("title");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_item;
    }

    @Override
    protected void initView(View rootView) {
        mTvTitle.setText(mTitle);
        LogUtils.e(mTitle, "initView" + mTvTitle.getText());
    }

    @Override
    protected void initLazyData() {
        LogUtils.e(mTitle, "initLazyData" + mTvTitle.getText());
    }

    @Override
    protected void onInvisible() {
        super.onInvisible();
    }

    @Override
    protected void onVisible() {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @OnClick(R.id.btn_get_text)
    public void onClick() {
        ToastUtils.showShort("你好");
    }

}
