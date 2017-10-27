package sankemao.gankio.ui.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import sankemao.baselib.mvp.BaseFragment;
import sankemao.baselib.mvp.IPresenter;
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
    private String[] items = {"直播", "推荐", "视频", "图片", "段子", "精华", "同城", "游戏"};
//    private List<ItemFragment> mItemFragments;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public IPresenter attachPresenter() {
        return null;
    }


    @Override
    protected void initView(View rootView) {

    }

    @Override
    protected void initData() {
//        mItemFragments = new ArrayList<>();
//        for (String item : items) {
//            ItemFragment itemFragment = ItemFragment.newInstance(item);
//            mItemFragments.add(itemFragment);
//        }
        initViewPager();
    }

    private void initViewPager() {
        mViewPager.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return ItemFragment.newInstance(items[position]);
//                return mItemFragments.get(position);
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
