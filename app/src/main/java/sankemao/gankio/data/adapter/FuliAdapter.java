package sankemao.gankio.data.adapter;

import android.content.Context;

import java.util.List;

import sankemao.baselib.recyclerview.JViewHolder;
import sankemao.baselib.recyclerview.JrecyAdapter;
import sankemao.gankio.data.bean.ResultsBean;

/**
 * Description:TODO
 * Create Time: 2017/10/30.16:04
 * Author:jin
 * Email:210980059@qq.com
 */
public class FuliAdapter extends JrecyAdapter<ResultsBean> {

    public FuliAdapter(Context context, List<ResultsBean> showItems, int layoutId) {
        super(context, showItems, layoutId);
    }

    @Override
    protected void convert(JViewHolder holder, ResultsBean itemData, int position) {

    }
}
