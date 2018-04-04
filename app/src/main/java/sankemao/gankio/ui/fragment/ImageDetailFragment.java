package sankemao.gankio.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.sankemao.quick.recyclerviewfixed.decoration.StaggeredDecoration;
import com.sankemao.quick.recyclerviewfixed.loadmore.LoadMoreDelegate;

import java.util.List;

import butterknife.BindView;
import sankemao.baselib.mvp.base.BaseFragment;
import sankemao.baselib.mvp.ioc.InjectPresenter;
import sankemao.gankio.R;
import sankemao.gankio.app.Constant;
import sankemao.gankio.model.adapter.PinsAdapter;
import sankemao.gankio.model.bean.pins.PinsMainEntity;
import sankemao.gankio.presenter.PinsPresenter;
import sankemao.gankio.ui.iview.IPinsLoadView;

/**
 * Description:TODO
 * Create Time: 2018/2/9.14:09
 * Author:jin
 * Email:210980059@qq.com
 */
public class ImageDetailFragment extends BaseFragment implements IPinsLoadView{
    @InjectPresenter
    PinsPresenter mPinsPresenter;

    @BindView(R.id.rv)
    RecyclerView mRecyclerView;

    private int page;
    private String mPinsId;
    private PinsAdapter mPinsAdapter;

    public static ImageDetailFragment newInstance() {
        return new ImageDetailFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_image_detail;
    }

    @Override
    protected void initView(View rootView) {
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.addItemDecoration(new StaggeredDecoration(ConvertUtils.dp2px(10f), Color.RED));
        mPinsAdapter = new PinsAdapter(getContext(), null);

        mPinsAdapter.openLoadMore(new LoadMoreDelegate.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mPinsPresenter.getPinsRecommend(mPinsId, page);
            }
        });

        mRecyclerView.setAdapter(mPinsAdapter);
    }

    @Override
    protected void initData() {
        Intent intent = getActivity().getIntent();
        PinsMainEntity mainEntity = (PinsMainEntity)intent.getSerializableExtra("entity");
        mPinsId = String.valueOf(mainEntity.getPin_id());
        mPinsPresenter.getPinsRecommend(mPinsId, page);
    }

    @Override
    public void loadPinsSuccess(List<PinsMainEntity> pins, int maxId) {
        //没有更多数据，则不继续加载了
        if (pins == null || pins.isEmpty()) {
            ToastUtils.showShort("没有数据");
            return;
        }

        mPinsAdapter.addDatas(pins);

        if (pins.size() < Constant.Http.LIMIT) {
            mPinsAdapter.loadMoreEnd();
        } else {
            mPinsAdapter.loadMoreComplete();
        }
        page++;
    }

    @Override
    public void loadFail(@Nullable Throwable throwable) {

    }
}
