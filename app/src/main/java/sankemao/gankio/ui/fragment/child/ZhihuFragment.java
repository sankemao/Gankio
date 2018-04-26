package sankemao.gankio.ui.fragment.child;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import butterknife.BindView;
import sankemao.baselib.mvp.base.LazyFragment;
import sankemao.baselib.mvp.ioc.InjectPresenter;
import sankemao.baselib.mvp.ioc.StateView;
import sankemao.gankio.R;
import sankemao.gankio.model.adapter.ZhihuAdapter;
import sankemao.gankio.model.bean.zhihu.Story;
import sankemao.gankio.model.bean.zhihu.TopStory;
import sankemao.gankio.presenter.ZhihuPresenter;
import sankemao.gankio.ui.activity.ZhihuDetailActivity;
import sankemao.gankio.ui.iview.IZhihuView;

/**
 * Description:TODO
 * Create Time: 2018/4/4.15:09
 * Author:jin
 * Email:210980059@qq.com
 */
@StateView
public class ZhihuFragment extends LazyFragment implements IZhihuView {

    @InjectPresenter
    public ZhihuPresenter mZhihuPresenter;

    @BindView(R.id.rv_zhihu)
    RecyclerView mRvZhihu;

    private ZhihuAdapter mZhihuAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_child_zhihu;
    }

    @Override
    protected void initView(View rootView) {
        mRvZhihu.setLayoutManager(new LinearLayoutManager(getContext()));

        mZhihuAdapter = new ZhihuAdapter(R.layout.item_zhihu_normal, null);
        mRvZhihu.setAdapter(mZhihuAdapter);

        mZhihuAdapter.setOnItemClickListener((baseQuickAdapter, view, i) ->
                ZhihuDetailActivity.go(getContext(), ((ZhihuAdapter) baseQuickAdapter).getItem(i).getId())
        );

    }

    @Override
    protected void initLazyData() {
        mZhihuPresenter.getLatestNews();
    }

    @Override
    public void showNormalItems(List<Story> normalItems) {
        mZhihuAdapter.replaceData(normalItems);
        getLoadService().showSuccess();
    }

    @Override
    public void showBannerItems(List<TopStory> bannerItems) {

    }
}
