package sankemao.baselib.imageload;

/**
 * Description:图片加载参数
 * Create Time: 2017/11/21.15:43
 * Author:jin
 * Email:210980059@qq.com
 */
public class ImageLoaderOptions {

    private Builder mBuilder;

    private ImageLoaderOptions(Builder builder) {
        this.mBuilder = builder;
    }

    public static ImageLoaderOptions getDefault() {
        return new Builder().build();
    }

    public static class Builder {

        public Builder() {

        }


        public ImageLoaderOptions build() {
            return new ImageLoaderOptions(this);
        }
    }

}
