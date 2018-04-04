package sankemao.baselib.mvp;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * Description:TODO
 * Create Time:2017/9/30.10:43
 * Author:jin
 * Email:210980059@qq.com
 */
public interface IView {
    Context getContext();

    /**
     * 出现错误页面后，重新加载
     */
    void reload(View view);

    /**
     * 标题栏
     * @param rootView
     */
    void initNavigationBar(ViewGroup rootView);
}
