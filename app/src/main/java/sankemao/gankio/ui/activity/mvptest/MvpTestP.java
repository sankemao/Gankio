package sankemao.gankio.ui.activity.mvptest;

import android.os.Handler;

import sankemao.baselib.mvp.BasePresenter;

/**
 * Description:TODO
 * Create Time: 2017/10/26.14:43
 * Author:jin
 * Email:210980059@qq.com
 */
public class MvpTestP extends BasePresenter<IMvpTestV> {

    public void testDely(Handler handler) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                getView().showMesg();
                handleByView(IMvpTestV.ACTION_TOAST, "ssss");
            }
        }, 2000);
    }

}
