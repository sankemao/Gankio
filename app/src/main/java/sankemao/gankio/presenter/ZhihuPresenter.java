package sankemao.gankio.presenter;

import com.sankemao.quick.retrofit.RetrofitClient;

import java.util.List;

import javax.inject.Inject;

import sankemao.baselib.mvp.base.BasePresenter;
import sankemao.gankio.app.App;
import sankemao.gankio.model.apis.ZhihuApi;
import sankemao.gankio.model.bean.zhihu.NewsTimeLine;
import sankemao.gankio.model.bean.zhihu.Story;
import sankemao.gankio.model.bean.zhihu.TopStory;
import sankemao.gankio.ui.iview.IZhihuView;

/**
 * Description:TODO
 * Create Time: 2018/4/4.15:09
 * Author:jin
 * Email:210980059@qq.com
 */
public class ZhihuPresenter extends BasePresenter<IZhihuView> {

    @Inject
    ZhihuApi mZhihuApi;

    public ZhihuPresenter() {
        App.getAppComponent().inject(this);
    }

    public void getLatestNews() {
        mZhihuApi.getLatestNews()
                .compose(RetrofitClient.IO_TRANSFORMER())
                .subscribe((NewsTimeLine newsTimeLine) -> {
                    List<TopStory> bannerItems = newsTimeLine.getTop_stories();
                    List<Story> normalItems = newsTimeLine.getStories();
                    getView().showNormalItems(normalItems);
                    getView().showBannerItems(bannerItems);
                }, Throwable::printStackTrace);
    }
}
