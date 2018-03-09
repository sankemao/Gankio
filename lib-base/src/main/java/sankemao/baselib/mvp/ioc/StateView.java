package sankemao.baselib.mvp.ioc;

import android.support.annotation.IdRes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description:TODO
 * Create Time: 2018/3/5.16:42
 * Author:jin
 * Email:210980059@qq.com
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface StateView {
    /**
     * 当未指定targetId时，targetView即全部的布局或排除了标题栏的布局（需要结合AutoLinearLayout注解）
     */
    @IdRes int targetId() default -1;
}
