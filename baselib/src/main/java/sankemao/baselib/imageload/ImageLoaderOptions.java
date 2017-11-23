package sankemao.baselib.imageload;

import android.support.annotation.DrawableRes;

import sankemao.baselib.R;

/**
 * Description:图片加载参数
 * Create Time: 2017/11/21.15:43
 * Author:jin
 * Email:210980059@qq.com
 */
public class ImageLoaderOptions {

    public static final int centerCrop = 1000;
    public static final int fitCenter = 1001;

    private int mHolderRes = -1;
    private int mErrorHolderRes = -1;
    private boolean mIsCrossFade;
    private int mResizeWidth = -1;
    private int mResizeHeight = -1;

    public int getHolderRes() {
        return mHolderRes;
    }

    public int getErrorHolderRes() {
        return mErrorHolderRes;
    }

    public boolean isCrossFade() {
        return mIsCrossFade;
    }

    public int getResizeWidth() {
        return mResizeWidth;
    }

    public int getResizeHeight() {
        return mResizeHeight;
    }

    public int getCropType() {
        return mCropType;
    }

    private int mCropType = -1;

    private ImageLoaderOptions() {

    }

    /**
     * 设置默认的占位图
     */
    public ImageLoaderOptions placeHolder(@DrawableRes int holderRes) {
        this.mHolderRes = holderRes;
        return this;
    }

    /**
     * 设置图片是否渐变显示
     */
    public ImageLoaderOptions isCrossFade(boolean isCrossFade) {
        this.mIsCrossFade = isCrossFade;
        return this;
    }

    /**
     * 设置图片加载失败占位图
     */
    public ImageLoaderOptions errorHolder(@DrawableRes int holderRes) {
        this.mErrorHolderRes = holderRes;
        return this;
    }

    /**
     * 重新设置图片大小
     */
    public ImageLoaderOptions override(int width, int height) {
        this.mResizeWidth = width;
        this.mResizeHeight = height;
        return this;
    }

    public ImageLoaderOptions setCropType(int cropType) {
        if (cropType == centerCrop || cropType == fitCenter) {
            this.mCropType = cropType;
        }
        return this;
    }

    private static ImageLoaderOptions DEFAULT_OPS = new ImageLoaderOptions()
            .placeHolder(R.drawable.shape_loading_fail)
            .setCropType(ImageLoaderOptions.centerCrop);

    /**
     * 提供一个默认的参数配置。
     */
    public static ImageLoaderOptions getDefault() {
        return DEFAULT_OPS;
    }

    public static ImageLoaderOptions newOptions() {
        return new ImageLoaderOptions();
    }

}
