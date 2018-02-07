package sankemao.baselib.mvp.proxy;

/**
 * Description:TODO
 * Create Time:2018/1/14.14:38
 * Author:jin
 * Email:210980059@qq.com
 */
public interface IMvpProxy {
    /**
     * 创建并绑定Presenter.
     */
    void bindPresenter();

    /**
     * 解绑Presenter.
     */
    void unbindPresenter();
}
