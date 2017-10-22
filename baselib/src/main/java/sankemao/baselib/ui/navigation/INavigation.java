package sankemao.baselib.ui.navigation;

import android.view.View;
import android.view.ViewGroup;

/**
 * @author: jin .
 * @time: 2017/10/18 10:19
 * @description: TODO
 */
public interface INavigation {
    /**
     * navigation初始化， 绑定参数
     */
    void createNavigationBar();

    /**
     * 绑定NavigationView到布局中
     * @param navigationView 标题布局
     * @param parent        父布局
     */
    void attachParent(View navigationView, ViewGroup parent);

    /**
     * 绑定参数
     */
    void attachNavigationParams();
}
