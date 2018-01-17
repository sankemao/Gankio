package sankemao.gankio.ui.fragment;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
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

    private void initIndicator() {
        mIndicatorView.setBackgroundColor(Color.parseColor("#ff00ddff"));
        mIndicatorView.setAdapter(new BaseIndicatorAdapter() {
            @Override
            public int getCount() {
                return items.length;
            }

            @Override
            public View getView(int position, ViewGroup parent) {
                TextView textView = new TextView(getContext());
                textView.setTextColor(Color.WHITE);
                textView.setText(items[position]);
                return textView;
            }
        });
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
}
