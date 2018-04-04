package sankemao.gankio.model.apis;

import com.sankemao.quick.retrofit.ioc.BaseUrl;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import sankemao.gankio.model.bean.zhihu.News;
import sankemao.gankio.model.bean.zhihu.NewsTimeLine;

/**
 * Description:TODO
 * Create Time: 2018/4/4.14:56
 * Author:jin
 * Email:210980059@qq.com
 */
@BaseUrl(url = "http://news-at.zhihu.com/api/4/")
public interface ZhihuApi {

    @GET("news/latest")
    Observable<NewsTimeLine> getLatestNews();

    @GET("news/before/{time}")
    Observable<NewsTimeLine> getBeforetNews(@Path("time") String time);

    @GET("news/{id}")
    Observable<News> getDetailNews(@Path("id") String id);
}
