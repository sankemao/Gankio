package sankemao.gankio.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.sankemao.quick.recyclerview.JViewHolder;
import com.sankemao.quick.recyclerview.JrecyAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import sankemao.baselib.imageload.ImageLoaderManager;
import sankemao.baselib.mvp.base.BaseActivity;
import sankemao.baselib.ui.utils.StatusbarUtil;
import sankemao.gankio.R;

public class ViewImageActivity extends BaseActivity {
    private List<String> mShowItems = new ArrayList<>();
    @BindView(R.id.rv_test)
    RecyclerView mRecyclerView;
    @BindView(R.id.big_image)
    ImageView mBigImageView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    public static void go(Context context, String imageUrl, float scale) {
        Intent intent = new Intent(context, ViewImageActivity.class);
        intent.putExtra("url", imageUrl);
        intent.putExtra("scale", scale);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_view_image;
    }

    @Override
    protected void initNavigationBar() {
        StatusbarUtil.setStatusBarTrans(this, false);
        StatusbarUtil.setTitlePadding(this, mToolbar);
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

        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        float scale = intent.getFloatExtra("scale", 1.0f);

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) mBigImageView.getLayoutParams();
        params.height = (int) (ScreenUtils.getScreenWidth() / scale);
        mBigImageView.setLayoutParams(params);

        ImageLoaderManager.INSTANCE.showImage(mBigImageView, url);
        for (int i = 0; i < 100; i++) {
            mShowItems.add("我是第" + i + "条");
        }
    }
}
