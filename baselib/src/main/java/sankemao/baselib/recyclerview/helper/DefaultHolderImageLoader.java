package sankemao.baselib.recyclerview.helper;

import android.widget.ImageView;

import sankemao.baselib.imageload.ImageLoaderManager;
import sankemao.baselib.imageload.ImageLoaderOptions;
import sankemao.baselib.recyclerview.JViewHolder;


/**
 * Created by jin on 2017/5/10.
 *
 */
public class DefaultHolderImageLoader extends JViewHolder.HolderImageLoader {


    public DefaultHolderImageLoader(Object imagePath) {
        super(imagePath);
    }

    public DefaultHolderImageLoader(Object imagePath, ImageLoaderOptions options) {
        super(imagePath, options);
    }

    @Override
    public void displayImage(ImageView imageView, Object imagePath, ImageLoaderOptions options) {
        ImageLoaderManager.INSTANCE.showImage(imageView, imagePath, options);
    }

    @Override
    public void displayCircleImage(ImageView imageView, Object imagePath) {
        ImageLoaderManager.INSTANCE.showRoundImage(imageView, imagePath);
    }

}
