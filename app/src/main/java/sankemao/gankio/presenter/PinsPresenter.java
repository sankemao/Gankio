package sankemao.gankio.presenter;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.sankemao.quick.http.GoHttp;
import com.sankemao.quick.http.callback.DefaultCallback;
import com.sankemao.quick.retrofit.RetrofitClient;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import sankemao.baselib.mvp.base.BasePresenter;
import sankemao.gankio.app.App;
import sankemao.gankio.model.apis.HuabanApi;
import sankemao.gankio.model.bean.pins.PinsMainEntity;
import sankemao.gankio.ui.iview.IPinsLoadView;


/**
 * Description:TODO
 * Create Time:2017/11/8.22:28
 * Author:jin
 * Email:210980059@qq.com
 */
public class PinsPresenter extends BasePresenter<IPinsLoadView> {
    @Inject
    HuabanApi mHuabanApi;

    public PinsPresenter() {
        App.getAppComponent().inject(this);
    }

    /**
     * https://api.huaban.com/favorite/all?limit=20
     */
//    public void getTypePins(String type) {
//        mHuabanApi.getTypePins(type, 20)
//                .compose(RetrofitClient.IO_TRANSFORMER())
//                .subscribe((listPinsBean) -> {
//                    List<PinsMainEntity> pins = listPinsBean.getPins();
//                    getView().loadPinsSuccess(pins, getMaxId(pins));
//                }, throwable -> {LogUtils.e("请求失败" + throwable.getMessage());
//                    getView().loadFail(throwable.getCause());
//        });
//    }
    public void getTypePins(String type) {
         mHuabanApi.getTypePins(type, 20)
                .compose(RetrofitClient.IO_TRANSFORMER())
                .flatMap(listPinsBean -> {
                    if (listPinsBean.getPins().size() <= 0) {
                        return Observable.empty();
                    } else {
                        return Observable.just(listPinsBean);
                    }
                })
                .subscribe(
                        (listPinsBean) -> {
                            List<PinsMainEntity> pins = listPinsBean.getPins();
                            getView().loadPinsSuccess(pins, getMaxId(pins));
                        }, throwable -> {
                            LogUtils.e("请求失败" + throwable.getMessage());
                            getView().loadFail(throwable.getCause());
                        }, () -> ToastUtils.showShort("啥也没有")
                );
    }

    /**
     * https://api.huaban.com/favorite/anime?max=1389093486&limit=20
     */
    public void getTypePins(String type, int maxId) {
        mHuabanApi.getTypePins(type, 20, maxId)
                .compose(RetrofitClient.IO_TRANSFORMER())
                .subscribe(listPinsBean -> {
                    List<PinsMainEntity> pins = listPinsBean.getPins();
                    getView().loadPinsSuccess(pins, getMaxId(pins));
                }, throwable -> LogUtils.e("请求失败"));
    }


    /**
     * 获取推荐的
     */
    public void getPinsRecommend(String pinsId, int page) {
        GoHttp.with(getContext())
                .url("https://api.huaban.com/pins/" + pinsId + "/recommend/")
                .addParam("page", page)
                .addParam("limit", 20)
                .enqueue(new DefaultCallback<List<PinsMainEntity>>() {
                    @Override
                    public void onParseSuccess(List<PinsMainEntity> pins) {
                        getView().loadPinsSuccess(pins, getMaxId(pins));
                    }

                    @Override
                    public void onError(Exception e) {
                        getView().loadFail(e);
                    }
                });
    }

    /**
     * 获取maxId,集合最后一个条目中返回
     */
    private int getMaxId(List<PinsMainEntity> pins) {
        return pins.get(pins.size() - 1).getPin_id();
    }


}
