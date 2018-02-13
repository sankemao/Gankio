package com.sankemao.quick.recyclerview.helper;

import android.widget.ImageView;

import sankemao.baselib.imageload.ImageLoaderManager;
import sankemao.baselib.imageload.ImageLoaderOptions;
import com.sankemao.quick.recyclerview.BaseViewHolder;


/**
 * Created by jin on 2017/5/10.
 *
 */
public class DefaultHolderImageLoader extends BaseViewHolder.HolderImageLoader {


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
