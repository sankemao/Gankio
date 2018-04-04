package sankemao.gankio.di.module;

import com.sankemao.quick.retrofit.RetrofitClient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import sankemao.gankio.model.apis.GankApi;
import sankemao.gankio.model.apis.HuabanApi;
import sankemao.gankio.model.apis.ZhihuApi;

/**
 * Description:TODO
 * Create Time: 2018/3/30.14:47
 * Author:jin
 * Email:210980059@qq.com
 */
@Module
public class ApiModule {
    @Provides
    @Singleton
    GankApi provideGankApi() {
        return RetrofitClient.getApi(GankApi.class);
    }

    @Provides
    @Singleton
    HuabanApi provideHuabanApi() {
        return RetrofitClient.getApi(HuabanApi.class);
    }

    @Provides
    @Singleton
    ZhihuApi provideZhihuApi() {
        return RetrofitClient.getApi(ZhihuApi.class);
    }

}
