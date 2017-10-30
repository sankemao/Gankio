package sankemao.gankio.ui.activity;

import android.os.Handler;

import sankemao.baselib.mvp.BasePresenter;

/**
 * Description:TODO
 * Create Time: 2017/10/26.14:43
 * Author:jin
 * Email:210980059@qq.com
 */
public class MvpTestPresenter extends BasePresenter<MvpTestContract.View> implements MvpTestContract.Presenter{

    @Override
    public void testDely(Handler handler) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getView().showMesg();
            }
        }, 2000);
    }

}
