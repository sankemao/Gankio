package sankemao.gankio.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;

import butterknife.BindView;
import sankemao.baselib.imageload.ImageLoaderManager;
import sankemao.baselib.mvp.FragmentManagerHelper;
import sankemao.baselib.mvp.base.BaseActivity;
import sankemao.baselib.ui.utils.StatusbarUtil;
import sankemao.gankio.R;
import sankemao.gankio.app.Constant;
import sankemao.gankio.model.bean.pins.PinsMainEntity;
import sankemao.gankio.ui.fragment.ImageDetailFragment;

public class ImageDetailActivity extends BaseActivity {

    @BindView(R.id.big_image)
    ImageView mBigImageView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_author)
    TextView mTvAuthor;
    @BindView(R.id.tv_discribe)
    TextView mTvDiscribe;

    public static void go(Context context, float scale, PinsMainEntity pinsMainEntity) {
        Intent intent = new Intent(context, ImageDetailActivity.class);
        intent.putExtra("scale", scale);
        intent.putExtra("entity", pinsMainEntity);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_view_image;
    }

    @Override
    public void initNavigationBar(ViewGroup rootView) {
        Toolbar toolbar = rootView.findViewById(R.id.toolbar);
        StatusbarUtil.setTitlePadding(this, toolbar);
        StatusbarUtil.setStatusBarTrans(this, true);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        FragmentManagerHelper fragmentManagerHelper = new FragmentManagerHelper(getSupportFragmentManager(), R.id.image_detail_container);
        fragmentManagerHelper.add(ImageDetailFragment.newInstance());
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

        Intent intent = getIntent();
        PinsMainEntity mainEntity = (PinsMainEntity) intent.getSerializableExtra("entity");
        String imagePartUrl = mainEntity.getFile().getKey();
        String imageType = mainEntity.getFile().getType();
        //大图url
        final String imageHighUrl = String.format(Constant.Http.FORMAT_URL_IMAGE_BIG, imagePartUrl);
        String imageLowUrl = String.format(Constant.Http.FORMAT_URL_IMAGE_SMALL, imagePartUrl);
        //原图url.
        final String imageOrigin = String.format(Constant.Http.FORMAT_URL_IMAGE_ORIGIN, mainEntity.getFile().getKey());
        String pinId = String.valueOf(mainEntity.getPin_id());

        //图片比例
        float scale = intent.getFloatExtra("scale", 1.0f);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mBigImageView.getLayoutParams();
        params.height = (int) (ScreenUtils.getScreenWidth() / scale);
        mBigImageView.setLayoutParams(params);

        //设置文字
        mTvAuthor.setText(mainEntity.getUser().getUrlname());
        mTvDiscribe.setText(mainEntity.getRaw_text());

        ImageLoaderManager.INSTANCE.showImage(mBigImageView, imageHighUrl);

        mBigImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageSingleActivity.go(getContext(), imageHighUrl, imageOrigin);
            }
        });
    }
}
