package sankemao.gankio.di.module;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import sankemao.gankio.di.component.FindComponent;

/**
 * Description:TODO
 * Create Time: 2018/3/29.10:24
 * Author:jin
 * Email:210980059@qq.com
 */
@Module(subcomponents = FindComponent.class)
public class AppModule {

    private final Context mContext;

    public AppModule(Context context) {
        mContext = context;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return mContext;
    }
}
