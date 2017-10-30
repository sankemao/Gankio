package sankemao.baselib.recyclerview.helper;

import android.content.Context;
import android.widget.ImageView;

import sankemao.baselib.recyclerview.JViewHolder;


/**
 * Created by jin on 2017/5/10.
 *
 */
public class JHolderImageLoader extends JViewHolder.HolderImageLoader {
    public JHolderImageLoader(Object imagePath) {
        super(imagePath);
    }

    @Override
    public void displayImage(Context context, ImageView imageView, Object imagePath) {
        if (imagePath == null) {
            return;
        }
        ImgLoadUtil.displayImage(imagePath, imageView);
    }

    @Override
    public void displayCircleImage(Context context, ImageView imageView, Object imagePath) {
        if (imagePath == null) {
            return;
        }
        ImgLoadUtil.displayCircle(imageView, imagePath);
    }
}
