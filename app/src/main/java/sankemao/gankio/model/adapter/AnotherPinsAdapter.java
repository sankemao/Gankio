package sankemao.gankio.model.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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
import sankemao.gankio.model.bean.pins.PinsMainEntity;
import sankemao.gankio.ui.custom.customview.NewGalleryView;

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
//        holder.itemView.setOnClickListener(view -> ImageDetailActivity.go(mContext, finalScale, itemData));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Rect startBounds = new Rect();
                imageView.getGlobalVisibleRect(startBounds);
//
//                final GalleryPhotoInfo galleryPhotoInfo = new GalleryPhotoInfo();
//                galleryPhotoInfo.imageObj = imageUrl;
//                galleryPhotoInfo.startBounds = startBounds;
//
//                final GalleryView galleryView = new GalleryView(imageView.getContext());
//                galleryView.showImageView(((Activity) imageView.getContext()), galleryPhotoInfo);


                Activity activity = (Activity) imageView.getContext();
                final WindowManager windowManager = activity.getWindowManager();
                final WindowManager.LayoutParams params = new WindowManager.LayoutParams();
                params.width = WindowManager.LayoutParams.MATCH_PARENT;
                params.height = WindowManager.LayoutParams.MATCH_PARENT;
                params.format = PixelFormat.RGBA_8888;
                params.flags = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                        | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;

                final NewGalleryView galleryView = new NewGalleryView(activity);
                Glide.with(activity).load(imageUrl).into(galleryView);
                //添加
                windowManager.addView(galleryView, params);
                galleryView.playEnterAnima(startBounds, new NewGalleryView.OnEnterAnimaEndListener() {
                    @Override
                    public void onEnterAnimaEnd() {

                    }
                });

                galleryView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        galleryView.playExitAnima(startBounds, null, new NewGalleryView.OnExitAnimaEndListener() {
                            @Override
                            public void onExitAnimaEnd() {
                                windowManager.removeViewImmediate(galleryView);
                            }
                        });
                    }
                });
            }
        });

    }
}
