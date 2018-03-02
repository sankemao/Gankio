package sankemao.gankio.data.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import sankemao.gankio.R;
import sankemao.gankio.app.Constant;
import sankemao.gankio.data.bean.pins.PinsMainEntity;
import sankemao.gankio.ui.activity.ImageDetailActivity;

/**
 * Description:TODO
 * Create Time: 2018/3/1.16:09
 * Author:jin
 * Email:210980059@qq.com
 */
public class AnotherPinsAdapter extends BaseQuickAdapter<PinsMainEntity, BaseViewHolder> {

    public static final int originWidth = ScreenUtils.getScreenWidth() / 2 - ConvertUtils.dp2px(8);
    public AnotherPinsAdapter(@Nullable List<PinsMainEntity> data) {
        super(R.layout.item_pins, data);
    }

    @Override
    protected void convert(final BaseViewHolder holder, final PinsMainEntity itemData) {

        //设置图片宽高比
        int width = itemData.getFile().getWidth();
        int height = itemData.getFile().getHeight();
        float scale = (float) width / height;
        if (scale < 0.7f) {
            scale = 0.7f;
        }

        ImageView imageView = holder.getView(R.id.iv_pin);
        ViewGroup.LayoutParams imageViewParams = imageView.getLayoutParams();
        imageViewParams.width = originWidth;
        imageViewParams.height = (int) (originWidth / scale);
        imageView.setLayoutParams(imageViewParams);

        String themeColor = "#" + itemData.getFile().getTheme();

        LogUtils.d("颜色为： " + themeColor);
        try {
            imageView.setBackgroundColor(Color.parseColor(themeColor));
        } catch (Exception e) {
            imageView.setBackgroundColor(Color.parseColor("#ffffff"));
        }

        final String imageUrl = String.format(Constant.Http.FORMAT_URL_IMAGE_GENERAL, itemData.getFile().getKey());
        final float finalScale = scale;

        Glide.with(imageView)
                .load(imageUrl)
                .transition(new DrawableTransitionOptions().crossFade(800))
                .into(imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageDetailActivity.go(mContext, finalScale, itemData);
            }
        });
    }
}
