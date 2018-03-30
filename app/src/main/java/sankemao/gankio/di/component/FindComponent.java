package sankemao.gankio.di.component;

import dagger.Subcomponent;
import sankemao.gankio.di.module.FindModule;
import sankemao.gankio.di.scope.PerFragment;
import sankemao.gankio.ui.fragment.FindFragment;

/**
 * Description:TODO
 * Create Time: 2018/3/29.13:40
 * Author:jin
 * Email:210980059@qq.com
 */
@PerFragment
@Subcomponent(modules = FindModule.class)
public interface FindComponent {
    void inject(FindFragment findFragment);

    @Subcomponent.Builder
    interface Builder{//继承，必须
        FindComponent build();
    }
}
