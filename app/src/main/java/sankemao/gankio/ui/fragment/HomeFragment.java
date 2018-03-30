package sankemao.gankio.ui.fragment;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import sankemao.baselib.mvp.base.BaseFragment;
import sankemao.baselib.ui.indicators.BaseIndicatorAdapter;
import sankemao.baselib.ui.indicators.TrackIndicatorView;
import sankemao.baselib.ui.utils.StatusbarUtil;
import sankemao.gankio.R;

/**
 * Description:TODO
 * Create Time:2017/10/9.10:54
 * Author:jin
 * Email:210980059@qq.com
 */
public class HomeFragment extends BaseFragment {

    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.indicator)
    TrackIndicatorView mIndicatorView;

    private String[] items = {"直播", "推荐", "视频", "图片", "段子", "精华", "同城", "游戏"};

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initNavigationBar(ViewGroup rootView) {
        super.initNavigationBar(rootView);
        StatusbarUtil.setFakeStatusView(getActivity(), rootView, Color.parseColor("#ff00ddff"));
    }

    @Override
    protected void initView(View rootView) {

    }

    @Override
    protected void initData() {
        initViewPager();
        initIndicator();
    }

    private void initViewPager() {
        //如果不加这句，当页面切换过快时，加载成功页面可能不显示
        mViewPager.setOffscreenPageLimit(items.length);
        mViewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return HomeItemFragment.newInstance(items[position]);
            }

            @Override
            public int getCount() {
                return items.length;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
//                super.destroyItem(container, position, object);
            }
        });
    }

    private void initIndicator() {
        mIndicatorView.setBackgroundColor(Color.parseColor("#ff00ddff"));
        mIndicatorView.setAdapter(new BaseIndicatorAdapter<TextView>() {
            @Override
            public int getCount() {
                return items.length;
            }

            @Override
            public TextView getView(int position, ViewGroup parent) {
                TextView textView = new TextView(getContext());
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
                textView.setLayoutParams(layoutParams);
                textView.setGravity(Gravity.CENTER);
                textView.setTextColor(Color.WHITE);
                textView.setText(items[position]);
                return textView;
            }

            @Override
            public void highLightIndicator(TextView indicatorView) {
                indicatorView.setTextColor(Color.RED);
            }

            @Override
            public void restoreIndicator(TextView indicatorView) {
                indicatorView.setTextColor(Color.WHITE);
            }

            @Override
            public View getTrackItemView() {
                return null;
            }

        }, mViewPager);
    }
}
