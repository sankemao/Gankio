package sankemao.gankio.ui.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;

import butterknife.BindView;
import sankemao.baselib.mvp.base.LazyFragment;
import sankemao.baselib.mvp.ioc.StateView;
import sankemao.gankio.R;


/**
 * Description:TODO
 * Create Time:2017/10/10.11:33
 * Author:jin
 * Email:210980059@qq.com
 */
@StateView
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
    }

    @Override
    protected void initLazyData() {
        LogUtils.e(mTitle, "initLazyData" + mTvTitle.getText());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getLoadService().showSuccess();
                LogUtils.e(mTitle, "initLazyData 完毕" + mTvTitle.getText());
            }
        }, 1500);
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

}
