package sankemao.gankio.data.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ScreenUtils;

import java.util.List;

import sankemao.baselib.imageload.ImageLoaderOptions;
import sankemao.baselib.recyclerview.JViewHolder;
import sankemao.baselib.recyclerview.JrecyAdapter;
import sankemao.baselib.recyclerview.helper.DefaultHolderImageLoader;
import sankemao.gankio.R;
import sankemao.gankio.app.Constant;
import sankemao.gankio.data.bean.pins.PinsMainEntity;
import sankemao.gankio.ui.activity.ViewImageActivity;

/**
 * Description:TODO
 * Create Time: 2017/11/9.10:20
 * Author:jin
 * Email:210980059@qq.com
 */
public class PinsAdapter extends JrecyAdapter<PinsMainEntity> {

    public static final int originWidth = ScreenUtils.getScreenWidth() / 2 - ConvertUtils.dp2px(8);

    private ImageLoaderOptions options;

    public PinsAdapter(Context context, List<PinsMainEntity> showItems) {
        super(context, showItems, R.layout.item_pins);
        options = ImageLoaderOptions.newOptions()
                .placeHolder(R.drawable.shape_loading_fail)
                .setCropType(ImageLoaderOptions.centerCrop);
    }

    @Override
    protected void convert(JViewHolder holder, PinsMainEntity itemData, int position) {
        //设置图片宽高比
        int width = itemData.getFile().getWidth();
        int height = itemData.getFile().getHeight();
        float scale = (float) width / height;
        if (scale < 0.7f) {
            scale = 0.7f;
        }

        ImageView imageView = holder.getViewById(R.id.iv_pin);
        ViewGroup.LayoutParams imageViewParams = imageView.getLayoutParams();
        imageViewParams.width = originWidth;
        imageViewParams.height = (int) (originWidth / scale);
        imageView.setLayoutParams(imageViewParams);

        holder.setImgByUrl(R.id.iv_pin, new DefaultHolderImageLoader(String.format(Constant.Http.URL_GENERAL_FORMAT, itemData.getFile().getKey()),
                options.override(imageViewParams.width, imageViewParams.height)))
                .setOnItemClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ViewImageActivity.go(mContext);
                    }
                });
    }
}
