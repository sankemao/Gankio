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
import sankemao.gankio.app.Constant;
import sankemao.gankio.data.bean.pins.PinsMainEntity;

public class ViewImageActivity extends BaseActivity {
    private List<String> mShowItems = new ArrayList<>();
    @BindView(R.id.rv_test)
    RecyclerView mRecyclerView;
    @BindView(R.id.big_image)
    ImageView mBigImageView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    public static void go(Context context, float scale, PinsMainEntity pinsMainEntity) {
        Intent intent = new Intent(context, ViewImageActivity.class);
        intent.putExtra("scale", scale);
        intent.putExtra("entity", pinsMainEntity);
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
        PinsMainEntity mainEntity = (PinsMainEntity) intent.getSerializableExtra("entity");
        String imagePartUrl = mainEntity.getFile().getKey();
        String imageType = mainEntity.getFile().getType();
        String imageHighUrl = String.format(Constant.Http.FORMAT_URL_IMAGE_BIG, imagePartUrl);
        String imageLowUrl = String.format(Constant.Http.FORMAT_URL_IMAGE_SMALL, imagePartUrl);
        String pinId = String.valueOf(mainEntity.getPin_id());
        //图片比例
        float scale = intent.getFloatExtra("scale", 1.0f);
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) mBigImageView.getLayoutParams();
        params.height = (int) (ScreenUtils.getScreenWidth() / scale);
        mBigImageView.setLayoutParams(params);

        ImageLoaderManager.INSTANCE.showImage(mBigImageView, imageHighUrl);

        //列表
        for (int i = 0; i < 100; i++) {
            mShowItems.add("我是第" + i + "条");
        }
    }
}
