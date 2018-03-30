package sankemao.gankio.presenter;

import com.sankemao.quick.http.GoHttp;

import java.util.List;

import sankemao.baselib.mvp.IView;
import sankemao.baselib.mvp.base.BasePresenter;
import sankemao.framlib.http.callbacks.SimpleCallback;
import sankemao.framlib.http.response.BaseResponse;
import sankemao.gankio.model.bean.gankio.ResultsBean;

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
        GoHttp.with(getContext())
                .url("http://gank.io/api/data/福利/" + mItemCount + "/" + mPageCount)
                .enqueue(new SimpleCallback<BaseResponse<ResultsBean>>() {
                    @Override
                    public void onParseSuccess(BaseResponse<ResultsBean> result) {
                        List<ResultsBean> results = result.getResults();
                        //加载图片
//                        handleByView(Actions.Find.GANK_PICS, results);
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
    }
}
