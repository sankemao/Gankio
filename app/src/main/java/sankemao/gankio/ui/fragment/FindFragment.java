package sankemao.gankio.ui.fragment;

import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ToastUtils;

import java.util.List;

import butterknife.BindView;
import sankemao.baselib.mvp.BaseFragment;
import sankemao.baselib.mvp.PresenterManager;
import sankemao.baselib.recyclerview.LoadRefreshRecyclerView;
import sankemao.baselib.recyclerview.RefreshRecyclerView;
import sankemao.baselib.recyclerview.headfootview.DefaultLoadMoreCreator;
import sankemao.baselib.recyclerview.headfootview.DefaultRefreshCreator;
import sankemao.gankio.R;
import sankemao.gankio.app.Actions;
import sankemao.gankio.app.Constant;
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
public class FindFragment extends BaseFragment implements IFindV {
    @BindView(R.id.rv_fuli)
    LoadRefreshRecyclerView mRvFuli;
    private PinsAdapter mPinsAdapter;

    private int maxId = Constant.Http.DEFAULT_VALUE_MINUS_ONE;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_find;
    }

    @Override
    protected void initNavigationBar(ViewGroup rootView) {
//        new QuickNavigationBar.Builder(mContext, rootView)
//                .setTitle("图片")
//                .build();
    }

    @Override
    protected void initView(View rootView) {
        mRvFuli.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mPinsAdapter = new PinsAdapter(mContext, null);
        mRvFuli.addLoadViewCreator(new DefaultLoadMoreCreator());
        mRvFuli.addRefreshViewCreator(new DefaultRefreshCreator());
        //加载更多
        mRvFuli.setOnLoadMoreListener(new LoadRefreshRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoad() {
                getPresenter(PinsPresenter.class).getTypePins(Constant.Type.ALL, maxId);
            }
        });
        //刷新
        mRvFuli.setOnRefreshListener(new RefreshRecyclerView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                maxId = Constant.Http.DEFAULT_VALUE_MINUS_ONE;
                getPresenter(PinsPresenter.class).getTypePins(Constant.Type.ALL);
            }
        });
        mRvFuli.setAdapter(mPinsAdapter);
    }

    @Override
    protected void initData() {
        getPresenter(PinsPresenter.class).getTypePins(Constant.Type.ALL);
    }

    @Override
    public PresenterManager attachPresenters() {
        return PresenterManager.begin()
                .addPresenter(new PinsPresenter())
                .attach(this);
    }

    @Override
    public void handleByView(int action, Object arg) {
        switch (action) {
            case Actions.Find.PIN_PICS:
                List<PinsMainEntity> pins = (List<PinsMainEntity>) arg;
                //没有更多数据，则不继续加载了
                if (pins == null || pins.isEmpty() || pins.size() < Constant.Http.LIMIT){
                    ToastUtils.showShort("没有数据");
                }

                if (maxId == Constant.Http.DEFAULT_VALUE_MINUS_ONE){
                    mPinsAdapter.clear();
                }

                mPinsAdapter.addAllData(pins);
                mRvFuli.stopRefreshLoad(20);

                maxId = refreshMaxId(pins);
                break;
            case Actions.Error.HTTP_FAIL:
                Exception exception = (Exception) arg;
                mRvFuli.stopRefreshLoadByfail();
                break;
            default:
                break;
        }
    }

    private int refreshMaxId(List<PinsMainEntity> pins) {
        return pins.get(pins.size() - 1).getPin_id();
    }

}
