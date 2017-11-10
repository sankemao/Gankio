package sankemao.baselib.mvp;

import android.content.Context;

/**
 * Description:TODO
 * Create Time:2017/9/30.10:43
 * Author:jin
 * Email:210980059@qq.com
 */
public interface IView {
    Context getContext();

    PresenterManager attachPresenters();

    void handleByView(int action, Object arg);
}
