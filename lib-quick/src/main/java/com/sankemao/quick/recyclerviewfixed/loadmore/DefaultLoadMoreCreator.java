package com.sankemao.quick.recyclerviewfixed.loadmore;

import com.sankemao.quick.R;

/**
 * Description:TODO
 * Create Time: 2018/2/23.15:58
 * Author:jin
 * Email:210980059@qq.com
 */
public class DefaultLoadMoreCreator extends LoadMoreCreator {
    @Override
    public int getLayoutId() {
        return R.layout.brvah_quick_view_load_more;
    }

    @Override
    protected int getLoadingViewId() {
        return R.id.load_more_loading_view;
    }

    @Override
    protected int getLoadFailViewId() {
        return R.id.load_more_load_fail_view;
    }

    @Override
    protected int getLoadEndViewId() {
        return R.id.load_more_load_end_view;
    }
}
