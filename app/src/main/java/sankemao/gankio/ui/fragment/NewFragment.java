package sankemao.gankio.ui.fragment;

import android.view.View;
import android.widget.ImageView;

import butterknife.BindView;
import sankemao.baselib.imageload.ImageLoaderManager;
import sankemao.baselib.mvp.base.BaseFragment;
import sankemao.baselib.mvp.PresenterManager;
import sankemao.gankio.R;

/**
 * Description:TODO
 * Create Time:2017/10/10.11:06
 * Author:jin
 * Email:210980059@qq.com
 */
public class NewFragment extends BaseFragment {
    @BindView(R.id.iv_test)
    ImageView mIvTest;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_new;
    }

    @Override
    protected void initView(View rootView) {

    }

    @Override
    protected void initData() {
        ImageLoaderManager.INSTANCE.showImage(mIvTest, "https://raw.githubusercontent.com/MindorksOpenSource/PRDownloader/master/assets/sample_download.png");

        //下载apk
//        new UpdateManager.Builder()
//                .with(mContext)
//                .setDownUrl("http://7xss53.com1.z0.glb.clouddn.com/file/okgo_v3.0.4.apk")
//                .setTargetPath(Constant.Commom.APP_DIR)
//                .setDownloadManager(new HttpUtilDownload(mContext))
//                .showNotification(true)
//                .build()
//                .download(new CallBacks.UpdateProcessCallback() {
//                    @Override
//                    public void onStart() {
//
//                    }
//
//                    @Override
//                    public void onProgress(float progress, long totalSize) {
//                        LogUtils.d("下载进度为: " + progress);
//                    }
//
//                    @Override
//                    public void setMax(long totalSize) {
//
//                    }
//
//                    @Override
//                    public boolean onFinish(File file) {
//                        return true;
//                    }
//
//                    @Override
//                    public void onError(String msg) {
//
//                    }
//                });
    }

    @Override
    public PresenterManager attachPresenters() {
        return null;
    }

}
