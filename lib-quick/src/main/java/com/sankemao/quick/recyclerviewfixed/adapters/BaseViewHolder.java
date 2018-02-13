package com.sankemao.quick.recyclerviewfixed.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import sankemao.baselib.imageload.ImageLoaderOptions;

/**
 * Created by jin on 2017/5/10.
 *
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> mViews;

    public BaseViewHolder(View itemView) {
        super(itemView);
        mViews = new SparseArray<>();
    }

    public <T extends View> T getViewById(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public BaseViewHolder setViewVisibility(int viewId, int visibility) {
        getViewById(viewId).setVisibility(visibility);
        return this;
    }

    public BaseViewHolder setText(int viewId, CharSequence text) {
        TextView tv = getViewById(viewId);
        tv.setText(text);
        return this;
    }

    public BaseViewHolder setSelected(int viewId, boolean selected) {
        View view = getViewById(viewId);
        view.setSelected(selected);
        return this;


    }

    public BaseViewHolder setImgByUrl(int viewId, HolderImageLoader imageLoader) {
        ImageView imageView = getViewById(viewId);
        if (imageLoader == null) {
            throw new NullPointerException("imageLoader is null");
        }
        imageLoader.displayImage(imageView, imageLoader.getImagePath(), imageLoader.getOptions());
        return this;
    }

    public BaseViewHolder setCircleImgByUrl(int viewId, HolderImageLoader imageLoader) {
        ImageView imageView = getViewById(viewId);
        if (imageLoader == null) {
            throw new NullPointerException("imageLoader is null");
        }
        imageLoader.displayCircleImage(imageView, imageLoader.getImagePath());
        return this;
    }

    public BaseViewHolder setTextStyle(int viewId, int style) {
        TextView textView = getViewById(viewId);
        textView.getPaint().setFlags(style);
        return this;
    }

    public BaseViewHolder setOnClickListener(int viewId,
                                             View.OnClickListener listener) {
        View view = getViewById(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    public static abstract class HolderImageLoader {
        private Object mImagePath;
        private ImageLoaderOptions mOptions;

        public HolderImageLoader(Object imagePath) {
            this(imagePath, null);
        }

        public HolderImageLoader(Object imagePath, ImageLoaderOptions options) {
            this.mImagePath = imagePath;
            this.mOptions = options;
        }

        public Object getImagePath() {
            return mImagePath;
        }

        public ImageLoaderOptions getOptions() {
            return mOptions;
        }

        public abstract void displayImage(ImageView imageView, Object imagePath, ImageLoaderOptions options);

        public abstract void displayCircleImage(ImageView imageView, Object imagePath);
    }


    /*****
     * 条目点击事件
     ************/
    public BaseViewHolder setOnItemClickListener(View.OnClickListener listener) {
        itemView.setOnClickListener(listener);
        return this;
    }

    public BaseViewHolder setOnItemLongClickListener(View.OnLongClickListener listener) {
        itemView.setOnLongClickListener(listener);
        return this;
    }

}
