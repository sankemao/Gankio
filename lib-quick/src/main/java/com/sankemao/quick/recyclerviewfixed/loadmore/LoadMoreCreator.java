package com.sankemao.quick.recyclerviewfixed.loadmore;

import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.view.View;

import com.sankemao.quick.recyclerviewfixed.adapters.BaseViewHolder;

/**
 * Description:TODO
 * Create Time: 2018/2/13.10:55
 * Author:jin
 * Email:210980059@qq.com
 */
public abstract class LoadMoreCreator {

    public static final int STATUS_DEFAULT = 1;
    public static final int STATUS_LOADING = 2;
    public static final int STATUS_FAIL = 3;
    public static final int STATUS_END = 4;

    private int mLoadMoreStatus = STATUS_DEFAULT;
    private boolean mLoadMoreEndGone = false;

    public void setLoadMoreStatus(int loadMoreStatus) {
        this.mLoadMoreStatus = loadMoreStatus;
    }

    public int getLoadMoreStatus() {
        return mLoadMoreStatus;
    }

    public void convert(BaseViewHolder holder) {
        switch (mLoadMoreStatus) {
            case STATUS_LOADING:
                visibleLoading(holder, true);
                visibleLoadFail(holder, false);
                visibleLoadEnd(holder, false);
                break;
            case STATUS_FAIL:
                visibleLoading(holder, false);
                visibleLoadFail(holder, true);
                visibleLoadEnd(holder, false);
                break;
            case STATUS_END:
                visibleLoading(holder, false);
                visibleLoadFail(holder, false);
                visibleLoadEnd(holder, true);
                break;
            case STATUS_DEFAULT:
                visibleLoading(holder, false);
                visibleLoadFail(holder, false);
                visibleLoadEnd(holder, false);
                break;
        }
    }

    private void visibleLoading(BaseViewHolder holder, boolean visible) {
        holder.setViewVisibility(getLoadingViewId(), visible ? View.VISIBLE : View.GONE);
    }

    private void visibleLoadFail(BaseViewHolder holder, boolean visible) {
        holder.setViewVisibility(getLoadFailViewId(), visible ? View.VISIBLE : View.GONE);
    }

    private void visibleLoadEnd(BaseViewHolder holder, boolean visible) {
        final int loadEndViewId = getLoadEndViewId();
        if (loadEndViewId != 0) {
            holder.setViewVisibility(loadEndViewId, visible ? View.VISIBLE : View.GONE);
        }
    }

    public final void setLoadMoreEndGone(boolean loadMoreEndGone) {
        this.mLoadMoreEndGone = loadMoreEndGone;
    }

    public final boolean isLoadEndMoreGone() {
        if (getLoadEndViewId() == 0) {
            return true;
        }
        return mLoadMoreEndGone;
    }

    /**
     * load more layout
     */
    public abstract @LayoutRes int getLayoutId();

    /**
     * loading view
     */
    protected abstract @IdRes int getLoadingViewId();

    /**
     * load fail view
     */
    protected abstract @IdRes int getLoadFailViewId();

    /**
     * load end view, you can return 0
     */
    protected abstract @IdRes int getLoadEndViewId();

}
