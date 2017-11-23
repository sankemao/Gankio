package sankemao.baselib.imageload;

import android.content.Context;
import android.view.View;

import sankemao.baselib.imageload.strategies.GlideStrategy;


/**
 * Description:图片加载管理类(管理策略和图片参数)
 * Create Time: 2017/11/21.16:29
 * Author:jin
 * Email:210980059@qq.com
 */
public enum ImageLoaderManager {

    INSTANCE;

    ImageLoaderStrategyInter mStrategy;
    ImageLoaderOptions mOptions;

    /**
     * 构造器
     * 默认采用Glide加载。
     * 图片参数默认。
     */
    private ImageLoaderManager() {
        mStrategy = new GlideStrategy();
        mOptions = ImageLoaderOptions.getDefault();
    }

    /**
     * 改变图片加载策略
     * @param strategy  图片加载策略
     */
    public void init(ImageLoaderStrategyInter strategy, ImageLoaderOptions options){
        this.mStrategy = strategy;
        this.mOptions = options;
    }

    /**
     * 加载图片，采用默认的图片加载参数。
     * @param container 图片容器
     * @param imagePath 图片path
     */
    public void showImage(View container, Object imagePath) {
        if (imagePath == null || container == null) {
            return;
        }
        mStrategy.showImage(container, imagePath, mOptions);
    }

    /**
     * 加载图片，图片参数可配置
     */
    public void showImage(View container, Object imagePath, ImageLoaderOptions options) {
        if (imagePath == null || container == null) {
            return;
        }
        if (options == null) {
            mStrategy.showImage(container, imagePath, mOptions);
        } else {
            mStrategy.showImage(container, imagePath, options);
        }
    }

    public void showRoundImage(View container, Object imagePath) {
        if (imagePath == null || container == null) {
            return;
        }
        mStrategy.showRoundImage(container, imagePath);
    }

    /**
     * 暂停图片加载
     */
    public void pauseRequest(Context context) {
        if (mStrategy != null) {
            mStrategy.onPause(context);
        }
    }

    /**
     * 恢复图片加载
     */
    public void resumeRequest(Context context) {
        if (mStrategy != null) {
            mStrategy.onResume(context);
        }
    }
}
