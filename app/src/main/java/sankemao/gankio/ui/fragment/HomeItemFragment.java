package sankemao.gankio.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.sankemao.quick.http.GoHttp;
import com.sankemao.quick.http.callback.DefaultCallback;

import butterknife.BindView;
import butterknife.OnClick;
import sankemao.baselib.mvp.base.LazyFragment;
import sankemao.gankio.R;
import sankemao.gankio.data.bean.TestBean;


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
        GoHttp.with(getContext())
                .url("http://proorder.cn/pronline/Msg?FunName@dishesManage&groupid=650456179")
                .enqueue(new DefaultCallback<TestBean>() {
                    @Override
                    public void onParseSuccess(TestBean result) {
                        ToastUtils.showShort("总条数： " + result.getEimdata().size());
                    }

                    @Override
                    public void onError(Exception e) {
                        ToastUtils.showShort("出错了");
                    }
                });
    }

}
