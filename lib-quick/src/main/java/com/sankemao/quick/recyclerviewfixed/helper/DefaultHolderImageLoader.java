package com.sankemao.quick.recyclerviewfixed.helper;

import android.widget.ImageView;

import com.sankemao.quick.recyclerviewfixed.adapters.BaseViewHolder;

import sankemao.baselib.imageload.ImageLoaderManager;
import sankemao.baselib.imageload.ImageLoaderOptions;


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

}
