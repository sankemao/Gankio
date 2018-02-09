package sankemao.baselib.imageload.strategies;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import sankemao.baselib.imageload.ImageLoaderOptions;
import sankemao.baselib.imageload.ImageLoaderStrategyInter;

/**
 * Description:TODO
 * Create Time: 2017/11/21.17:17
 * Author:jin
 * Email:210980059@qq.com
 */
public class GlideStrategy implements ImageLoaderStrategyInter {

    @Override
    public void showImage(View container, Object imagePath, ImageLoaderOptions options) {
        Glide.with(container.getContext()).load(imagePath).apply(generateOption(options)).into((ImageView) container);
    }

    private RequestOptions generateOption(ImageLoaderOptions options) {
        RequestOptions requestOptions = new RequestOptions();

        if (options.getCropType() == ImageLoaderOptions.fitCenter) {
            requestOptions = requestOptions.fitCenter();
        } else if(options.getCropType() == ImageLoaderOptions.centerCrop){
            requestOptions = requestOptions.centerCrop();
        }

        //设置占位图
        if (options.getHolderRes() > 0) {
            requestOptions = requestOptions.placeholder(options.getHolderRes());
        }

        //设置错误的占位图
        if (options.getErrorHolderRes() > 0) {
            requestOptions = requestOptions.error(options.getErrorHolderRes());
        }

        //重新设置大小
        if (options.getResizeWidth() > 0 || options.getResizeHeight() > 0) {
            requestOptions = requestOptions.override(options.getResizeWidth(), options.getResizeHeight());
        }

        return requestOptions;
    }


    @Override
    public void showRoundImage(View container, Object imagePath) {

    }

    @Override
    public void onPause(Context context) {
        Glide.with(context).pauseRequests();
    }

    @Override
    public void onResume(Context context) {
        Glide.with(context).resumeRequests();
    }
}
