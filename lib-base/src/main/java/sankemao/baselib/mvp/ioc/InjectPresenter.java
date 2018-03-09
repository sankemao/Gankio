package sankemao.baselib.mvp.ioc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description:注入Presenter
 * Create Time:2018/1/13.20:01
 * Author:jin
 * Email:210980059@qq.com
 */
@Target(ElementType.FIELD)//属性
@Retention(RetentionPolicy.RUNTIME)
public @interface InjectPresenter {

}
