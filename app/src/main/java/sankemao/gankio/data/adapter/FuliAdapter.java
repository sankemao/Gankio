package sankemao.gankio.data.adapter;

import android.content.Context;

import com.sankemao.quick.recyclerview.BaseViewHolder;
import com.sankemao.quick.recyclerview.BaseAdapter;
import com.sankemao.quick.recyclerview.helper.DefaultHolderImageLoader;

import java.util.List;

import sankemao.gankio.R;
import sankemao.gankio.data.bean.gankio.ResultsBean;

/**
 * Description:TODO
 * Create Time: 2017/10/30.16:04
 * Author:jin
 * Email:210980059@qq.com
 */
public class FuliAdapter extends BaseAdapter<ResultsBean> {

    public FuliAdapter(Context context, List<ResultsBean> showItems, int layoutId) {
        super(context, showItems, layoutId);
    }

    @Override
    protected void convert(BaseViewHolder holder, ResultsBean itemData, int position) {
        holder.setImgByUrl(R.id.iv_fuli, new DefaultHolderImageLoader(itemData.getUrl()));
    }
}
