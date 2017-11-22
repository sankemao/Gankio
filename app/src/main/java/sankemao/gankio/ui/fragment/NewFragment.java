package sankemao.gankio.ui.fragment;

import android.view.View;
import android.widget.ImageView;

import butterknife.BindView;
import sankemao.baselib.imageload.ImageLoaderManager;
import sankemao.baselib.mvp.BaseFragment;
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
    }

    @Override
    public PresenterManager attachPresenters() {
        return null;
    }

}
