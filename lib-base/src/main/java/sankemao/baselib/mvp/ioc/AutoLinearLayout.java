package sankemao.baselib.mvp.ioc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description: 当Activity或fragment加上这个注解后，会在view外层添加LinearLayout
 * 当合stateView一起使用时，initNavigationBar中添加的标题会排除在stateView之外
 * Create Time: 2018/3/6.15:47
 * Author:jin
 * Email:210980059@qq.com
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoLinearLayout {
}
