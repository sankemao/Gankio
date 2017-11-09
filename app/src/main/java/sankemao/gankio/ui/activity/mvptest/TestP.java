package sankemao.gankio.ui.activity.mvptest;


import com.blankj.utilcode.util.ToastUtils;

import sankemao.baselib.mvp.BasePresenter;

/**
 * Description:TODO
 * Create Time: 2017/11/8.10:19
 * Author:jin
 * Email:210980059@qq.com
 */
public class TestP extends BasePresenter<ITestV> {
    public void test() {
        ToastUtils.showShort("hahha");
    }
}
