package sankemao.gankio.ui.iview;

import java.util.List;

import sankemao.baselib.mvp.IView;
import sankemao.gankio.model.bean.zhihu.Story;
import sankemao.gankio.model.bean.zhihu.TopStory;

/**
 * Description:TODO
 * Create Time: 2018/4/4.15:10
 * Author:jin
 * Email:210980059@qq.com
 */
public interface IZhihuView extends IView{

    void showNormalItems(List<Story> normalItems);

    void showBannerItems(List<TopStory> bannerItems);
}
