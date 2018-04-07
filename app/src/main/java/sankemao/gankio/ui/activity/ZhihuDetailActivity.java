package sankemao.gankio.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import sankemao.baselib.mvp.base.BaseActivity;
import sankemao.baselib.mvp.ioc.InjectPresenter;
import sankemao.gankio.R;
import sankemao.gankio.model.bean.zhihu.News;
import sankemao.gankio.presenter.ZhihuDetailPresenter;
import sankemao.gankio.ui.iview.IZhihuDetailView;

public class ZhihuDetailActivity extends BaseActivity implements IZhihuDetailView {
    private static final String ID = "id";

    @BindView(R.id.web_view)
    WebView mWebView;
    private String mId;

    @BindView(R.id.iv_web_img)
    ImageView mImageView;

    @BindView(R.id.tv_img_source)
    TextView mTvImagSource;

    @BindView(R.id.tv_img_title)
    TextView mTvImgTitle;

    @InjectPresenter
    ZhihuDetailPresenter mPresenter;

    public static void go(Context context, String id) {
        Intent intent = new Intent(context, ZhihuDetailActivity.class);
        intent.putExtra(ID, id);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_zhihu_detail;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mId = getIntent().getStringExtra(ID);
        mPresenter.getDetail(mId);
    }

    @Override
    public void initNavigationBar(ViewGroup rootView) {

    }

    @Override
    public void handleNews(News news) {
        String head = "<head>\n" +
                "\t<link rel=\"stylesheet\" href=\"" + news.getCss()[0] + "\"/>\n" +
                "</head>";
        String img = "<div class=\"headline\">";
        String html = head + news.getBody().replace(img, " ");
        mWebView.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);

        Glide.with(getContext()).load(news.getImage()).apply(new RequestOptions().centerCrop()).into(mImageView);

        mTvImagSource.setText(news.getImage_source());
        mTvImgTitle.setText(news.getTitle());
    }
}
