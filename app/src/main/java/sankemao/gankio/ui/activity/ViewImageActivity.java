package sankemao.gankio.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import sankemao.baselib.mvp.BaseActivity;
import sankemao.baselib.mvp.PresenterManager;
import sankemao.baselib.recyclerview.JViewHolder;
import sankemao.baselib.recyclerview.JrecyAdapter;
import sankemao.gankio.R;

public class ViewImageActivity extends BaseActivity {
    private List<String> mShowItems = new ArrayList<>();
    @BindView(R.id.rv_test)
    RecyclerView mRecyclerView;

    public static void go(Context context) {
        Intent intent = new Intent(context, ViewImageActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_view_image;
    }

    @Override
    protected void initNavigationBar() {
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        JrecyAdapter<String> adapter = new JrecyAdapter<String>(this, mShowItems, R.layout.item_test) {
            @Override
            protected void convert(JViewHolder holder, String itemData, int position) {
                ((TextView) holder.itemView).setText(itemData);
            }
        };
        mRecyclerView.setAdapter(adapter);

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        for (int i = 0; i < 100; i++) {
            mShowItems.add("我是第" + i + "条");
        }
    }

    @Override
    public Context getContext() {
        return null;
    }

    @Override
    public PresenterManager attachPresenters() {
        return null;
    }
}
