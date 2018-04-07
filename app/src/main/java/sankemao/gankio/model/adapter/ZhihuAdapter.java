package sankemao.gankio.model.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import javax.inject.Inject;

import sankemao.gankio.R;
import sankemao.gankio.model.bean.zhihu.Story;

/**
 * Description:TODO
 * Create Time: 2018/4/4.16:02
 * Author:jin
 * Email:210980059@qq.com
 */
public class ZhihuAdapter extends BaseQuickAdapter<Story, BaseViewHolder> {

    @Inject
    public ZhihuAdapter(int layoutResId, @Nullable List<Story> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, Story story) {
        holder.setText(R.id.tv_title, story.getTitle());

        ImageView imageView = holder.getView(R.id.iv_img);
        Glide.with(imageView.getContext()).load(story.getImages()[0])
                .transition(new DrawableTransitionOptions().crossFade(800))
                .into(imageView);
    }
}
