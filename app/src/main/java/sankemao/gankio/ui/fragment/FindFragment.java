package sankemao.gankio.ui.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.loadmore.LoadMoreView;

import java.util.List;

import butterknife.BindView;
import sankemao.baselib.mvp.base.BaseFragment;
import sankemao.baselib.mvp.inject.InjectPresenter;
import sankemao.framlib.ui.QuickNavigationBar;
import sankemao.gankio.R;
import sankemao.gankio.app.Constant;
import sankemao.gankio.data.adapter.AnotherPinsAdapter;
import sankemao.gankio.data.bean.pins.PinsMainEntity;
import sankemao.gankio.presenter.PinsPresenter;
import sankemao.gankio.ui.iview.IPinsLoadView;

import static sankemao.gankio.app.App.mContext;

/**
 * Description:TODO
 * Create Time:2017/10/10.11:06
 * Author:jin
 * Email:210980059@qq.com
 */
public class FindFragment extends BaseFragment implements IPinsLoadView {
    @BindView(R.id.rv_fuli)
    RecyclerView mRvFuli;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout mRefreshLayout;

    @InjectPresenter
    PinsPresenter mPinsPresenter;

    //用于区分是否是刷新
    private int maxId = Constant.Http.DEFAULT_VALUE_MINUS_ONE;
    private AnotherPinsAdapter mAnotherPinsAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_find;
    }

    @Override
    protected void initNavigationBar(ViewGroup rootView) {
        new QuickNavigationBar.Builder(mContext, rootView)
                .setTitle("图片")
                .build();
    }

    @Override
    protected void initView(View rootView) {
        mRvFuli.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //刷新时不允许加载更多
                mAnotherPinsAdapter.setEnableLoadMore(false);
                maxId = Constant.Http.DEFAULT_VALUE_MINUS_ONE;
                mPinsPresenter.getTypePins(Constant.Type.ALL);
            }
        });

        mAnotherPinsAdapter = new AnotherPinsAdapter(null);

        mAnotherPinsAdapter.setLoadMoreView(new LoadMoreView() {
            @Override
            public int getLayoutId() {
                return R.layout.loading_footer;
            }

            @Override
            protected int getLoadingViewId() {
                return R.id.load_more_loading_view;
            }

            @Override
            protected int getLoadFailViewId() {
                return R.id.load_more_load_fail_view;
            }

            @Override
            protected int getLoadEndViewId() {
                return R.id.load_more_load_end_view;
            }
        });

//        mAnotherPinsAdapter.setPreLoadNumber(15);

        mAnotherPinsAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mPinsPresenter.getTypePins(Constant.Type.ALL, maxId);
            }
        }, mRvFuli);
        mRvFuli.setAdapter(mAnotherPinsAdapter);
    }

    @Override
    protected void initData() {
        mPinsPresenter.getTypePins(Constant.Type.ALL);
    }

    @Override
    public void loadPinsSuccess(List<PinsMainEntity> pins, int refreshedMaxId) {
        //没有更多数据，则不继续加载了
        if (pins == null || pins.isEmpty()) {
            ToastUtils.showShort("没有数据");
            return;
        }

        if (maxId == Constant.Http.DEFAULT_VALUE_MINUS_ONE) {
            mAnotherPinsAdapter.replaceData(pins);
            mRefreshLayout.setRefreshing(false);
            mAnotherPinsAdapter.setEnableLoadMore(true);
        } else {
            mAnotherPinsAdapter.addData(pins);
        }


        if (pins.size() < 20) {
            mAnotherPinsAdapter.loadMoreEnd();
        } else {
            mAnotherPinsAdapter.loadMoreComplete();
        }

        //刷新maxId
        maxId = refreshedMaxId;
    }

    @Override
    public void loadFail(Exception e) {
        if (maxId == Constant.Http.DEFAULT_VALUE_MINUS_ONE) {
            //表示此时为刷新
            mRefreshLayout.setRefreshing(false);
            mAnotherPinsAdapter.setEnableLoadMore(true);
        } else {
            mAnotherPinsAdapter.loadMoreFail();
        }
    }

}
