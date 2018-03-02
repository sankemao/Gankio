package com.sankemao.quick.recyclerviewfixed.loadmore;

import com.sankemao.quick.recyclerviewfixed.adapters.BaseAdapter;

/**
 * Description: 处理加载更多的逻辑
 * Create Time: 2018/2/23.10:20
 * Author:jin
 * Email:210980059@qq.com
 */
public class LoadMoreDelegate {

    private BaseAdapter mBaseAdapter;
    private LoadMoreCreator mLoadMoreCreator;
    //提前加载更多，有个loading布局，所以默认为1
    private int mPreLoadNumber = 1;
    private RequestLoadMoreListener mRequestLoadMoreListener;
    //与能否加载更多相关
    private boolean mNextLoadEnable;
    //控制加载更多，用于下拉刷新时屏蔽加载更多
    private boolean mLoadMoreEnable;
    private boolean mLoading;

    public LoadMoreDelegate(BaseAdapter baseAdapter, LoadMoreCreator loadMoreCreator) {
        this.mBaseAdapter = baseAdapter;
        this.mLoadMoreCreator = loadMoreCreator;
    }

    /**
     * 重置状态
     */
    public void resetState() {
        if (mRequestLoadMoreListener != null) {
            mNextLoadEnable = true;
            mLoadMoreEnable = true;
            mLoading = false;
            mLoadMoreCreator.setLoadMoreStatus(LoadMoreCreator.STATUS_DEFAULT);
        }
    }

    public void setPreLoadNumber(int preLoadNumber) {
        if (preLoadNumber > 1) {
            this.mPreLoadNumber = preLoadNumber;
        }
    }


    public void autoLoadMore(int position) {
        if (getLoadMoreViewCount() == 0) {
            return;
        }
        if (position < mBaseAdapter.getItemCount() - mPreLoadNumber) {
            return;
        }
        if (mLoadMoreCreator.getLoadMoreStatus() != LoadMoreCreator.STATUS_DEFAULT) {
            return;
        }

        mLoadMoreCreator.setLoadMoreStatus(LoadMoreCreator.STATUS_LOADING);

        if (!mLoading) {
            mLoading = true;
//            if (getRecyclerView() != null) {
//                getRecyclerView().post(new Runnable() {
//                    @Override
//                    public void run() {
//                        mRequestLoadMoreListener.onLoadMoreRequested();
//                    }
//                });
//            } else {
//                mRequestLoadMoreListener.onLoadMoreRequested();
//            }
            mRequestLoadMoreListener.onLoadMoreRequested();
        }
    }

    /**
     * Load more view count
     *
     * @return 0 or 1
     */
    public int getLoadMoreViewCount() {
        //如果没有开启过加载更多，或者加载更多布局被主动设置了false, 直接返回0
        if (mRequestLoadMoreListener == null || !mLoadMoreEnable) {
            return 0;
        }
        //如果不能够加载更多了，且加载更多布局gone
        if (!mNextLoadEnable && mLoadMoreCreator.isLoadEndMoreGone()) {
            return 0;
        }
        //如果内容为空
        if (mBaseAdapter.getShowItems() == null || mBaseAdapter.getShowItems().size() == 0) {
            return 0;
        }
        return 1;
    }

    /**
     * 加载结束
     * @param gone 是否移除加载更多布局
     */
    public void loadMoreEnd(boolean gone) {
        if (getLoadMoreViewCount() == 0) {
            return;
        }
        mLoading = false;
        mNextLoadEnable = false;
        //只是设置gone的状态，并没有更新视图
        mLoadMoreCreator.setLoadMoreEndGone(gone);
        if (gone) {
            //这句才真正更新视图
            mBaseAdapter.notifyItemRemoved(getLoadMoreViewPosition());
        } else {
            mLoadMoreCreator.setLoadMoreStatus(LoadMoreCreator.STATUS_END);
            mBaseAdapter.notifyItemChanged(getLoadMoreViewPosition());
        }
    }

    /**
     * 结束加载更多，可能还有数据
     */
    public void loadMoreComplete() {
        if (getLoadMoreViewCount() == 0) {
            return;
        }
        mLoading = false;
        mNextLoadEnable = true;
        mLoadMoreCreator.setLoadMoreStatus(LoadMoreCreator.STATUS_DEFAULT);
        mBaseAdapter.notifyItemChanged(getLoadMoreViewPosition());
    }

    /**
     * 加载失更多败
     */
    public void loadMoreFail() {
        if (getLoadMoreViewCount() == 0) {
            return;
        }
        mLoading = false;
        mLoadMoreCreator.setLoadMoreStatus(LoadMoreCreator.STATUS_FAIL);
        mBaseAdapter.notifyItemChanged(getLoadMoreViewPosition());
    }

    public interface RequestLoadMoreListener {
        void onLoadMoreRequested();
    }

    /**
     * 打开加载更多
     * @param requestLoadMoreListener 加载更多监听
     */
    public void openLoadMore(RequestLoadMoreListener requestLoadMoreListener) {
        this.mRequestLoadMoreListener = requestLoadMoreListener;
        mNextLoadEnable = true;
        mLoadMoreEnable = true;
        mLoading = false;
    }

    /**
     * 设置加载更多条目布局的状态
     * 主要为了配合在下拉刷新的时候，屏蔽上拉加载更多。
     * @param enable True if load more is enabled, false otherwise.
     */
    public void setEnableLoadMore(boolean enable) {
        //先获取之前有没有loading布局
        int oldLoadMoreCount = getLoadMoreViewCount();
        mLoadMoreEnable = enable;
        //根据传过来的状态，在获取一次loading布局
        int newLoadMoreCount = getLoadMoreViewCount();

        if (oldLoadMoreCount == 1) {
            if (newLoadMoreCount == 0) {
                //之前有loadMore条目，设置了移除
                mBaseAdapter.notifyItemRemoved(getLoadMoreViewPosition());
            }
        } else {
            if (newLoadMoreCount == 1) {
                //之前没有loadMore条目，添加
                mLoadMoreCreator.setLoadMoreStatus(LoadMoreCreator.STATUS_DEFAULT);
                mBaseAdapter.notifyItemInserted(getLoadMoreViewPosition());
            }
        }
    }

    private int getLoadMoreViewPosition() {
        return mBaseAdapter.getLoadMoreViewPosition();
    }
}
