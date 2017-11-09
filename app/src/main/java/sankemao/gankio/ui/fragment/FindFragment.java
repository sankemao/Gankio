package sankemao.gankio.ui.fragment;

import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import sankemao.baselib.mvp.BaseFragment;
import sankemao.baselib.recyclerview.WrapRecyclerView;
import sankemao.framlib.ui.QuickNavigationBar;
import sankemao.gankio.R;
import sankemao.gankio.app.Actions;
import sankemao.gankio.data.adapter.PinsAdapter;
import sankemao.gankio.data.bean.pins.PinsMainEntity;
import sankemao.gankio.presenter.PinsPresenter;
import sankemao.gankio.ui.iview.IFindV;

/**
 * Description:TODO
 * Create Time:2017/10/10.11:06
 * Author:jin
 * Email:210980059@qq.com
 */
public class FindFragment extends BaseFragment implements IFindV{
    @BindView(R.id.rv_fuli)
    WrapRecyclerView mRvFuli;
    private PinsAdapter mPinsAdapter;

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
        mPinsAdapter = new PinsAdapter(mContext, null);
        mRvFuli.setAdapter(mPinsAdapter);
    }

    @Override
    protected void initData() {
        getPresenter(PinsPresenter.class).getPinsByType();
    }

    @Override
    public void attachPresenters() {
        addPresenter(new PinsPresenter());
    }

    @Override
    public void handleByView(int action, Object arg) {
        switch (action) {
            case Actions.Find.PIN_PICS:
                mPinsAdapter.refreshAllData((List<PinsMainEntity>) arg);
                break;
            default:
                break;
        }
    }

}
