package sankemao.gankio.presenter;

import java.util.List;

import sankemao.baselib.http.HttpUtils;
import sankemao.baselib.mvp.BasePresenter;
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

    public void getPinsByType() {
        HttpUtils.with(getContext())
                .url("https://api.huaban.com/favorite/all")
                .addParam("limit", 20)
                .enqueue(new DefaultCallBack<ListPinsBean>() {
                    @Override
                    public void onSuccess(ListPinsBean result) {
                        List<PinsMainEntity> pins = result.getPins();
                        handleByView(Actions.Find.PIN_PICS, pins);
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });

    }
}