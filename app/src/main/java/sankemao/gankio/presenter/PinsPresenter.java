package sankemao.gankio.presenter;

import com.sankemao.quick.http.GoHttp;
import com.sankemao.quick.http.callback.DefaultCallback;

import java.util.List;

import sankemao.baselib.mvp.base.BasePresenter;
import sankemao.gankio.data.bean.pins.ListPinsBean;
import sankemao.gankio.data.bean.pins.PinsMainEntity;
import sankemao.gankio.ui.iview.IPinsLoadView;


/**
 * Description:TODO
 * Create Time:2017/11/8.22:28
 * Author:jin
 * Email:210980059@qq.com
 */
public class PinsPresenter extends BasePresenter<IPinsLoadView> {

    /**
     * https://api.huaban.com/favorite/all?limit=20
     */
    public void getTypePins(String type) {
        GoHttp.with(getContext())
                .url("https://api.huaban.com/favorite/" + type)
                .addParam("limit", 20)
                .enqueue(new DefaultCallback<ListPinsBean>() {
                    @Override
                    public void onError(Exception e) {
                        getView().loadFail(e);
                    }

                    @Override
                    public void onParseSuccess(ListPinsBean result) {
                        List<PinsMainEntity> pins = result.getPins();
                        getView().loadPinsSuccess(pins);
                    }
                });

    }

    /**
     * https://api.huaban.com/favorite/anime?max=1389093486&limit=20
     */
    public void getTypePins(String type, int maxId) {
        GoHttp.with(getContext())
                .url("https://api.huaban.com/favorite/" + type)
                .addParam("limit", 20)
                .addParam("max", maxId)
                .enqueue(new DefaultCallback<ListPinsBean>() {
                    @Override
                    public void onParseSuccess(ListPinsBean result) {
                        List<PinsMainEntity> pins = result.getPins();
                        getView().loadPinsSuccess(pins);
                    }

                    @Override
                    public void onError(Exception e) {
                        getView().loadFail(e);
                    }
                });
    }

}
