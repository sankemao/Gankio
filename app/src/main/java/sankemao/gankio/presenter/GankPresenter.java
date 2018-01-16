package sankemao.gankio.presenter;

import java.util.List;

import sankemao.baselib.http.HttpUtils;
import sankemao.baselib.mvp.base.BasePresenter;
import sankemao.baselib.mvp.IView;
import sankemao.framlib.http.callbacks.SimpleCallBack;
import sankemao.framlib.http.response.BaseResponse;
import sankemao.gankio.app.Actions;
import sankemao.gankio.data.bean.ResultsBean;

/**
 * Description:TODO
 * Create Time:2017/11/8.22:59
 * Author:jin
 * Email:210980059@qq.com
 */
public class GankPresenter extends BasePresenter<IView> {
    private int mPageCount = 1;

    private int mItemCount = 20;

    public void getGankPics() {
        HttpUtils.with(getContext())
                .url("http://gank.io/api/data/福利/" + mItemCount + "/" + mPageCount)
                .enqueue(new SimpleCallBack<BaseResponse<ResultsBean>>() {
                    @Override
                    public void onMainSuccess(BaseResponse<ResultsBean> result) {
                        List<ResultsBean> results = result.getResults();
                        //加载图片
//                        handleByView(Actions.Find.GANK_PICS, results);
                    }

                    @Override
                    protected void onMainError(Exception e) {

                    }
                });
    }
}
