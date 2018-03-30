package sankemao.gankio.di.module;

import dagger.Module;
import dagger.Provides;
import sankemao.gankio.model.adapter.AnotherPinsAdapter;
import sankemao.gankio.di.scope.PerFragment;

/**
 * Description:TODO
 * Create Time: 2018/3/29.13:41
 * Author:jin
 * Email:210980059@qq.com
 */
@Module
public class FindModule {

    @Provides
    @PerFragment
    AnotherPinsAdapter provideAdapter() {
        return new AnotherPinsAdapter(null);
    }
}
