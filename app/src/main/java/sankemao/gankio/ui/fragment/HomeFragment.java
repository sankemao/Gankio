package sankemao.gankio.ui.fragment;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import sankemao.baselib.mvp.base.BaseFragment;
import sankemao.baselib.ui.indicators.BaseIndicatorAdapter;
import sankemao.baselib.ui.indicators.TrackIndicatorView;
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
    protected void initView(View rootView) {

    }

    @Override
    protected void initData() {
        initViewPager();
        initIndicator();
    }

    private void initViewPager() {
        mViewPager.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {
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
                super.destroyItem(container, position, object);
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
//                FrameLayout frameLayout = new FrameLayout(getContext());
//                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 20);
//                frameLayout.setBackgroundColor(Color.BLUE);
//                frameLayout.setLayoutParams(params);
//                return frameLayout;
                return null;
            }

        }, mViewPager);
    }
}
