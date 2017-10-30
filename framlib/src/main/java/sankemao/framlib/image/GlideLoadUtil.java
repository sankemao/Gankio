package sankemao.framlib.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import sankemao.framlib.R;

/**
 * Glide加载图片
 */

public class GlideLoadUtil {

    private static int getDefaultPic(int type) {
        switch (type) {
            case 0:
                return R.drawable.shape_loading_fail;
            case 1:
                return R.drawable.ic_default_head;
            default:
                return R.drawable.shape_loading_fail;
        }
    }

    public static void displayImage(Context context, Object url, ImageView imageView) {
        Glide.with(context).load(url)
                .placeholder(getDefaultPic(0))
                .error(getDefaultPic(0))
                .centerCrop()
                .into(imageView);
    }

    public static void displayImage(Context context, Object path, ImageView imageView, boolean placeHolder) {
        if (placeHolder) {
            displayImage(context, path, imageView);
        } else {
            Glide.with(context).load(path)
                    .into(imageView);
        }
    }

    /**
     * 加载圆形图片
     */
    public static void displayCircle(ImageView imageView, Object imageUrl) {
        Glide.with(imageView.getContext()).load(imageUrl)
                .transform(new GlideCircleTransform(imageView.getContext()))
                .placeholder(getDefaultPic(1))
                .error(getDefaultPic(1))
                .dontAnimate()
                .into(imageView);
    }

    public static Bitmap getBitmapFromUrl(Context context, String url) {
        try {
            return  Glide.with(context)
                    .load(url)
                    .asBitmap() //必须
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
