package sankemao.gankio.model.bean.zhihu;

import java.util.List;

/**
 * Description:TODO
 * Create Time: 2018/4/4.14:59
 * Author:jin
 * Email:210980059@qq.com
 */
public class NewsTimeLine {
    private String date;
    private List<Story> stories;
    private List<TopStory> top_stories;

    public String getDate() {
        return date;
    }

    public List<Story> getStories() {
        return stories;
    }

    public List<TopStory> getTop_stories() {
        return top_stories;
    }

    @Override
    public String toString() {
        return "NewsTimeLine{" +
                "date='" + date + '\'' +
                ", mStories=" + stories +
                ", top_stories=" + top_stories +
                '}';
    }
}
