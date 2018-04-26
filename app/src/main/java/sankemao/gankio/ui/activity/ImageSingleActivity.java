package sankemao.gankio.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ToastUtils;
import com.github.piasy.biv.indicator.progresspie.ProgressPieIndicator;
import com.github.piasy.biv.view.BigImageView;
import com.github.piasy.biv.view.ImageSaveCallback;
import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;
import sankemao.baselib.mvp.base.BaseActivity;
import sankemao.baselib.ui.utils.StatusbarUtil;
import sankemao.gankio.R;

public class ImageSingleActivity extends BaseActivity {
    @BindView(R.id.big_image)
    BigImageView mBigImageView;
    private RxPermissions mRxPermissions;

    public static void go(Context context, String imageHigh, String imageOrigin) {
        Intent intent = new Intent(context, ImageSingleActivity.class);
        intent.putExtra("imageOrigin", imageOrigin);
        intent.putExtra("imageHigh", imageHigh);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_image_single;
    }

    @Override
    public void initNavigationBar(ViewGroup rootView) {
        StatusbarUtil.setStatusBarTrans(this, true);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        Intent intent = getIntent();
        String imageOrigin = intent.getStringExtra("imageOrigin");
//        String imageHigh = intent.getStringExtra("imageHigh");
        mBigImageView.showImage(Uri.parse(imageOrigin));
        mBigImageView.setProgressIndicator(new ProgressPieIndicator());
        mBigImageView.setImageSaveCallback(new ImageSaveCallback() {
            @Override
            public void onSuccess(String s) {
                ToastUtils.showShort("图片保存成功");
            }

            @Override
            public void onFail(Throwable throwable) {
                ToastUtils.showShort("图片保存失败");
            }
        });


        mRxPermissions = new RxPermissions(this);
    }

    @OnClick(R.id.btn_save)
    public void saveClick() {
        mRxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            ToastUtils.showShort("正在保存图片...");
                            mBigImageView.saveImageIntoGallery();
                        } else {
                            ToastUtils.showShort("您拒绝了存储权限");
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ToastUtils.showShort("权限申请出错");
                    }
                });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }
}
