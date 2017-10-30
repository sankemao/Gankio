package sankemao.gankio.ui.activity;

import android.os.Handler;

import sankemao.baselib.mvp.IPresenter;
import sankemao.baselib.mvp.IView;

/**
 * Description:TODO
 * Create Time: 2017/10/26.14:43
 * Author:jin
 * Email:210980059@qq.com
 */
public interface MvpTestContract {
    public interface View extends IView<Presenter> {
        void showMesg();
    }

    public interface Presenter extends IPresenter<View> {
        void testDely(Handler handler);
    }

}
