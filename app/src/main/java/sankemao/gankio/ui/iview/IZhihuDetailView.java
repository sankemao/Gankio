package sankemao.gankio.ui.iview;

import sankemao.baselib.mvp.IView;
import sankemao.gankio.model.bean.zhihu.News;

/**
 * Description:TODO
 * Create Time:2018/4/5.22:55
 * Author:jin
 * Email:210980059@qq.com
 */
public interface IZhihuDetailView extends IView{
    void handleNews(News news);
}
