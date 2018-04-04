package sankemao.gankio.ui.iview;

import android.support.annotation.Nullable;

import java.util.List;

import sankemao.baselib.mvp.IView;
import sankemao.gankio.model.bean.pins.PinsMainEntity;

/**
 * Description: 处理数据请求
 * Create Time: 2018/1/17.10:07
 * Author:jin
 * Email:210980059@qq.com
 */
public interface IPinsLoadView extends IView{
    /**
     * 加载图片成功
     */
    void loadPinsSuccess(List<PinsMainEntity> pinsMainEntities, int maxId);

    /**
     * 加载失败
     * @param e  异常
     */
    void loadFail(@Nullable Throwable throwable);
}
