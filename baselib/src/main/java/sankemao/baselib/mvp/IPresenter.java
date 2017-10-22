package sankemao.baselib.mvp;

/**
 * Description:TODO
 * Create Time:2017/9/30.10:43
 * Author:jin
 * Email:210980059@qq.com
 */
public interface IPresenter<V extends IView> {
    void attatchView(V v);

    void detachView();
}
