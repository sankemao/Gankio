package sankemao.gankio.di.component;

import javax.inject.Singleton;

import dagger.Component;
import sankemao.gankio.di.module.ApiModule;
import sankemao.gankio.presenter.PinsPresenter;

/**
 * Description:TODO
 * Create Time: 2018/3/30.16:00
 * Author:jin
 * Email:210980059@qq.com
 */
@Singleton
@Component(modules = ApiModule.class)
public interface ApiComponent {
    void inject(PinsPresenter presenter);
}
