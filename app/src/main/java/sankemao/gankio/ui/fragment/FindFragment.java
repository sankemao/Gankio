package sankemao.gankio.ui.fragment;

import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ToastUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;
import http.callbacks.SimpleCallBack;
import http.response.BaseResponse;
import sankemao.baselib.http.HttpUtils;
import sankemao.baselib.mvp.BaseFragment;
import sankemao.baselib.recyclerview.WrapRecyclerView;
import sankemao.gankio.R;
import sankemao.gankio.data.adapter.FuliAdapter;
import sankemao.gankio.data.bean.ResultsBean;
import ui.QuickNavigationBar;

/**
 * Description:TODO
 * Create Time:2017/10/10.11:06
 * Author:jin
 * Email:210980059@qq.com
 */
public class FindFragment extends BaseFragment {
    @BindView(R.id.rv_fuli)
    WrapRecyclerView mRvFuli;
    Unbinder unbinder;

    private int mPageCount = 1;
    private int mItemCount = 20;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_find;
    }

    @Override
    protected void initNavigationBar(ViewGroup rootView) {
        super.initNavigationBar(rootView);
        new QuickNavigationBar.Builder(mContext, rootView)
                .setTitle("图片")
                .build();
    }

    @Override
    protected void initView(View rootView) {
        mRvFuli.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        FuliAdapter fuliAdapter = new FuliAdapter(mContext, null, R.layout.item_fuli);
        mRvFuli.setAdapter(fuliAdapter);
    }

    @Override
    protected void initData() {
        HttpUtils.with(mContext)
                .url("http://gank.io/api/data/福利/" + mItemCount + "/" + mPageCount)
                .enqueue(new SimpleCallBack<BaseResponse<ResultsBean>>() {
                    @Override
                    public void onMainSuccess(BaseResponse<ResultsBean> result) {
                        List<ResultsBean> results = result.getResults();
                        int size = results.size();
                        ToastUtils.showShort("总条目为: " + size);
                    }

                    @Override
                    protected void onMainError(Exception e) {

                    }
                });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void attachPresenters() {

    }
}
