package sankemao.gankio.model.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.sankemao.quick.recyclerviewfixed.adapters.BaseAdapter;
import com.sankemao.quick.recyclerviewfixed.adapters.BaseViewHolder;

import java.util.List;

import sankemao.baselib.imageload.ImageLoaderOptions;
import sankemao.gankio.R;
import sankemao.gankio.app.Constant;
import sankemao.gankio.model.bean.pins.PinsMainEntity;

/**
 * Description:TODO
 * Create Time: 2017/11/9.10:20
 * Author:jin
 * Email:210980059@qq.com
 */
public class PinsAdapter extends BaseAdapter<PinsMainEntity> {

    public static final int originWidth = ScreenUtils.getScreenWidth() / 2 - ConvertUtils.dp2px(8);

    private ImageLoaderOptions options;

    public PinsAdapter(Context context, List<PinsMainEntity> showItems) {
        super(context, showItems, R.layout.item_pins);
        options = ImageLoaderOptions.newOptions()
//                .placeHolder(R.drawable.shape_loading_fail)
                .setCropType(ImageLoaderOptions.centerCrop)
                .isCrossFade(true);
    }

    @Override
    protected void convert(BaseViewHolder holder, final PinsMainEntity itemData, int position) {
        //设置图片宽高比
        int width = itemData.getFile().getWidth();
        int height = itemData.getFile().getHeight();
        float scale = (float) width / height;
        if (scale < 0.7f) {
            scale = 0.7f;
        }

        ImageView imageView = holder.getViewById(R.id.iv_pin);
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

//        holder.setImgByUrl(R.id.iv_pin, new DefaultHolderImageLoader(imageUrl,
//                options))
//                .setOnItemClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        ImageDetailActivity.go(mContext, finalScale, itemData);
//                    }
//                });
        Glide.with(imageView)
                .load(imageUrl)
                .transition(new DrawableTransitionOptions().crossFade(1000))
                .into(imageView);
    }
}
