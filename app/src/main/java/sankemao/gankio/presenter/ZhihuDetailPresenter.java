package sankemao.gankio.presenter;

import com.sankemao.quick.retrofit.RetrofitClient;

import javax.inject.Inject;

import sankemao.baselib.mvp.base.BasePresenter;
import sankemao.gankio.app.App;
import sankemao.gankio.model.apis.ZhihuApi;
import sankemao.gankio.ui.iview.IZhihuDetailView;

/**
 * Description:TODO
 * Create Time:2018/4/5.22:54
 * Author:jin
 * Email:210980059@qq.com
 */
public class ZhihuDetailPresenter extends BasePresenter<IZhihuDetailView> {
    @Inject
    ZhihuApi mZhihuApi;

    public ZhihuDetailPresenter() {
        App.getAppComponent().inject(this);
    }

    public void getDetail(String id) {
        mZhihuApi.getDetailNews(id)
                .compose(RetrofitClient.IO_TRANSFORMER())
                .subscribe(news -> {
                    getView().handleNews(news);
                });
    }
}
