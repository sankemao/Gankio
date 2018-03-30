package sankemao.gankio.model.apis;

import com.sankemao.quick.retrofit.ioc.BaseUrl;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import sankemao.gankio.model.bean.pins.ListPinsBean;
import sankemao.gankio.model.bean.pins.PinsMainEntity;

/**
 * Description:TODO
 * Create Time: 2018/3/30.14:53
 * Author:jin
 * Email:210980059@qq.com
 */
@BaseUrl(url = "https://api.huaban.com/")
public interface HuabanApi {

    @GET("favorite/{type}")
    Observable<ListPinsBean> getTypePins(@Path("type") String type, @Query("limit") int limit);


    @GET("favorite/{type}")
    Observable<ListPinsBean> getTypePins(@Path("type") String type, @Query("limit") int limit, @Query("max") int maxId);

//    https://api.huaban.com/pins/" + pinsId + "/recommend/
    @GET("pins/{pinsId}/recommend")
    Observable<List<PinsMainEntity>> getTypePinsRecommend(@Path("pinsId") String pinsId, @Query("page") int page, @Query("limit") int limit);
}
