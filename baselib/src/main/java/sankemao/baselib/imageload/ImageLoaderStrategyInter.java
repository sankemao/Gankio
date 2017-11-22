package sankemao.baselib.imageload;

import android.content.Context;
import android.view.View;

/**
 * Description:图片策略加载规范
 * Create Time: 2017/11/21.15:42
 * Author:jin
 * Email:210980059@qq.com
 */
public interface ImageLoaderStrategyInter {

    /**
     * 自定义图片参数选项的加载图片
     * @param imageView 图片容器
     * @param imagePath 图片path
     * @param options   图片加载参数
     */
    void showImage(View container, Object imagePath, ImageLoaderOptions options);

    /**
     * 加载圆形图片
     */
    void showRoundImage(View container, Object imagePath);

    /**
     * 暂停图片加载， 可以在BaseActivity中调用
     * @param context   activity的上下文
     */
    void onPause(Context context);

    /**
     * 恢复图片加载
     * @param context   activity的上下文
     */
    void onResume(Context context);
}
