package sankemao.gankio.ui.custom.customview;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;


/**
 * Description:TODO
 * Create Time: 2018/4/26.17:00
 * Author:jin
 * Email:210980059@qq.com
 */
public class GalleryView extends RelativeLayout {
    public GalleryView(Context context) {
        this(context, null);
    }

    public GalleryView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GalleryView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


    }

    public void showImageView(Activity activity, GalleryPhotoInfo info) {
        //windowManager
        final WindowManager windowManager = activity.getWindowManager();
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;

        params.flags = params.flags | WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;

        final ImageView imageView = new ImageView(activity);
        this.addView(imageView, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        //添加
        windowManager.addView(this, params);

        Glide.with(activity).load(info.imageObj).into(imageView);
    }
}
