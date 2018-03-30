package sankemao.gankio.di.component;

import javax.inject.Singleton;

import dagger.Component;
import sankemao.gankio.di.module.AppModule;

/**
 * Description:TODO
 * Create Time: 2018/3/29.10:23
 * Author:jin
 * Email:210980059@qq.com
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    FindComponent.Builder find();
}
