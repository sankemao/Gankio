package sankemao.framlib.image;

import android.content.Context;
import android.widget.ImageView;

import sankemao.baselib.recyclerview.JViewHolder;


/**
 * Created by jin on 2017/7/13.
 * recyclerView holder 图片加载
 */
public class GlideHolderLoader extends JViewHolder.HolderImageLoader {
    boolean mPlaceHolder = true;
    int x;
    int y;

    /**
     * 设置是否使用占位图.
     */
    public GlideHolderLoader(Object imagePath, boolean placeHolder) {
        this(imagePath);
        this.mPlaceHolder = placeHolder;
    }

    public GlideHolderLoader(Object imagePath, boolean placeHolder, int x, int y) {
        this(imagePath);
        this.mPlaceHolder = placeHolder;
        this.x = x;
        this.y = y;
    }

    /**
     * 默认使用占位图
     */
    public GlideHolderLoader(Object imagePath) {
        super(imagePath);
    }

    @Override
    public void displayImage(Context context, ImageView imageView, Object imagePath) {
        if (imagePath == null) {
            return;
        }
        GlideLoadUtil.displayImage(context, imagePath, imageView, mPlaceHolder, x, y);
    }

    @Override
    public void displayCircleImage(Context context, ImageView imageView, Object imagePath) {
        if (imagePath == null) {
            return;
        }
        GlideLoadUtil.displayCircle(imageView, imagePath);
    }
}
