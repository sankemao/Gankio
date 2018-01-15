package sankemao.gankio.presenter;

import java.util.List;

import sankemao.baselib.http.HttpUtils;
import sankemao.baselib.mvp.base.BasePresenter;
import sankemao.baselib.mvp.IView;
import sankemao.framlib.http.callbacks.DefaultCallBack;
import sankemao.gankio.app.Actions;
import sankemao.gankio.data.bean.pins.ListPinsBean;
import sankemao.gankio.data.bean.pins.PinsMainEntity;


/**
 * Description:TODO
 * Create Time:2017/11/8.22:28
 * Author:jin
 * Email:210980059@qq.com
 */
public class PinsPresenter extends BasePresenter<IView> {

    //https://api.huaban.com/favorite/all?limit=20

    public void getTypePins(String type) {
        HttpUtils.with(getContext())
                .url("https://api.huaban.com/favorite/" + type)
                .addParam("limit", 20)
                .enqueue(new DefaultCallBack<ListPinsBean>() {
                    @Override
                    public void onMainSuccess(ListPinsBean result) {
                        List<PinsMainEntity> pins = result.getPins();
                        handleByView(Actions.Find.PIN_PICS, pins);
                    }

                    @Override
                    public void onMainError(Exception e) {
                        handleByView(Actions.Error.HTTP_FAIL, e);
                    }
                });

    }

    //https://api.huaban.com/favorite/anime?max=1389093486&limit=20
    public void getTypePins(String type, int maxId) {
        HttpUtils.with(getContext())
                .url("https://api.huaban.com/favorite/" + type)
                .addParam("limit", 20)
                .addParam("max", maxId)
                .enqueue(new DefaultCallBack<ListPinsBean>() {
                    @Override
                    public void onMainSuccess(ListPinsBean result) {
                        List<PinsMainEntity> pins = result.getPins();
                        handleByView(Actions.Find.PIN_PICS, pins);
                    }

                    @Override
                    public void onMainError(Exception e) {
                        handleByView(Actions.Error.HTTP_FAIL, e);
                    }
                });

    }
}
