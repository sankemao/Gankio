package sankemao.gankio.ui.activity.test;

import android.content.Context;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.sankemao.quick.recyclerviewfixed.adapters.BaseAdapter;
import com.sankemao.quick.recyclerviewfixed.adapters.BaseViewHolder;

import java.util.List;

import sankemao.gankio.R;

/**
 * Description:TODO
 * Create Time: 2018/2/12.16:06
 * Author:jin
 * Email:210980059@qq.com
 */
public class TestAdapter extends BaseAdapter<String> {

    public TestAdapter(Context context, List showItems) {
        super(context, showItems, R.layout.item_test_rv_1);
    }

    @Override
    protected void convert(BaseViewHolder holder, final String itemData, final int position) {
        holder.setText(R.id.tv_content, itemData)
                .setOnClickListener(R.id.tv_content, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ToastUtils.showShort("这是第" + itemData + "个条目" + position);
                    }
                });
    }
}
