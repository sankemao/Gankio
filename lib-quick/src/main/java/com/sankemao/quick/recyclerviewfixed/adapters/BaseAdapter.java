package com.sankemao.quick.recyclerviewfixed.adapters;

import android.content.Context;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sankemao.quick.recyclerviewfixed.headfootview.LoadMoreCreator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jin on 2017/5/10.
 * 支持添加头部尾部的adapter
 */
public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseViewHolder>{

    private MultiTypeSupport<T> multiTypeSupport;
    private int mLayoutId;

    protected Context mContext;
    public List<T> mShowItems;
    private final LayoutInflater mInflater;

    //头尾布局
    private SparseArray<View> mHeaderViews;
    private SparseArray<View> mFooterViews;
    //Loading布局
    private LoadMoreCreator mLoadingView;

    private static int TYPE_HEADER = 10000000;
    private static int TYPE_FOOTER = 20000000;
    public static final int TYPE_LOADING = 9999999;

    public BaseAdapter(Context context, List<T> showItems, int layoutId) {
        this.mContext = context;
        this.mLayoutId = layoutId;
        mInflater = LayoutInflater.from(context);
        mShowItems = showItems;

        mHeaderViews = new SparseArray<>();
        mFooterViews = new SparseArray<>();
    }

    public BaseAdapter(Context context, List<T> showItems, MultiTypeSupport<T> multiTypeSupport) {
        this(context, showItems, -1);
        this.multiTypeSupport = multiTypeSupport;
    }


    /**
     * @param position
     * @return multitypeSupport的返回值, 即多条目的布局layoutId.
     */
    @Override
    public int getItemViewType(int position) {
        if (position < getHeadersCount()) {
            return mHeaderViews.keyAt(position);
        } else if (position < getHeadersCount() + getShowItemsCount()) {
            return super.getItemViewType(position);
        } else if (position < getHeadersCount() + getShowItemsCount() + getFootersCount()) {
            return mFooterViews.keyAt(position);
        } else {
            return TYPE_LOADING;
        }
    }

    /**
     * 是不是底部位置
     */
    private boolean isFooterPosition(int position) {
        return position >= getHeadersCount() + getShowItemsCount();
    }

    /**
     * 是不是头部位置
     */
    private boolean isHeaderPosition(int position) {
        return position < getHeadersCount();
    }

    /**
     * 包含头尾布局在内的所有条目数量
     */
    @Override
    public int getItemCount() {
        return getHeadersCount() + getShowItemsCount() + getFootersCount();
    }

    /**
     * 获取头布局的数目
     */
    public int getHeadersCount() {
        return mHeaderViews.size();
    }

    /**
     * 获取尾布局的数目
     */
    public int getFootersCount() {
        return mFooterViews.size();
    }

    /**
     * 获取实际条目数
     */
    private int getShowItemsCount() {
        return mShowItems == null ? 0 : mShowItems.size();
    }

    /**
     *
     * @param parent
     * @param viewType getItemViewType的返回值.
     * @return
     */
    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // viewType 可能就是 SparseArray 的key
        if (isHeaderViewType(viewType)) {
            return new BaseViewHolder(mHeaderViews.get(viewType));
        }

        if (isFooterViewType(viewType)) {
            return new BaseViewHolder(mFooterViews.get(viewType));
        }

        if (viewType == TYPE_LOADING) {
            return getLoadingHolder(parent);
        }

        BaseViewHolder baseViewHolder = new BaseViewHolder(mInflater.inflate(mLayoutId, parent, false));
        bindViewClickListener(baseViewHolder);
        return baseViewHolder;
    }

    private BaseViewHolder getLoadingHolder(ViewGroup parent) {

        return null;
    }

    /**
     * 绑定点击事件，避免生成多个clickListener
     */
    private void bindViewClickListener(final BaseViewHolder baseViewHolder) {
        if (baseViewHolder == null) {
            return;
        }
        View view = baseViewHolder.itemView;
        if (view == null) {
            return;
        }
        final int position = baseViewHolder.getLayoutPosition() - mHeaderViews.size();
        if (mItemClickListener != null) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemClickListener.onItemClick(view, mShowItems.get(position), position);
                }
            });
        }

        if (mLongClickListener != null) {
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    return mLongClickListener.onLongClick(view, mShowItems.get(position), position);
                }
            });
        }
    }

    /**
     * 是不是底部类型
     */
    private boolean isFooterViewType(int viewType) {
        int position = mFooterViews.indexOfKey(viewType);
        return position >= 0;
    }

    /**
     * 是不是头部类型
     */
    private boolean isHeaderViewType(int viewType) {
        int position = mHeaderViews.indexOfKey(viewType);
        return position >= 0;
    }

    private SpanSizeLookup mSpanSizeLookup;

    public void setSpanSizeLookup(SpanSizeLookup spanSizeLookup) {
        this.mSpanSizeLookup = spanSizeLookup;
    }

    public interface SpanSizeLookup {
        int getSpanSize(GridLayoutManager gridLayoutManager, int position);
    }

    /**
     * 解决gridLayout Span问题
     */
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = (GridLayoutManager) manager;
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    boolean isHeaderOrFooter = isHeaderPosition(position) || isFooterPosition(position);
                    if (mSpanSizeLookup == null) {
                        return isHeaderOrFooter ? gridManager.getSpanCount() : 1;
                    } else {
                        //如果有自定义跨度，非头尾布局按自定义的来
                        return isHeaderOrFooter ? gridManager.getSpanCount() : mSpanSizeLookup.getSpanSize(gridManager, position - mHeaderViews.size());
                    }
                }
            });
        }
    }

    @Override
    public void onViewAttachedToWindow(BaseViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        int position = holder.getLayoutPosition();
        if (isHeaderPosition(position) || isFooterPosition(position)) {
            setFullSpan(holder);
        } else {
            addAnimation(holder);
        }
    }

    private void setFullSpan(BaseViewHolder holder) {
        if (holder.itemView.getLayoutParams() instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) holder
                    .itemView.getLayoutParams();
            params.setFullSpan(true);
        }
    }

    //TODO:给holder添加动画
    private void addAnimation(BaseViewHolder holder) {

    }

    /**
     * RecyclerView的条目数据有偏移
     */
    @Override
    public void onBindViewHolder(final BaseViewHolder holder, final int position) {
        if (isHeaderPosition(position) || isFooterPosition(position)) {
            //头布局和尾布局都先与数据绑定完成了, 不需要再绑定, 直接return;
            return;
        }
        convert(holder, mShowItems.get(position - mHeaderViews.size()), position - mHeaderViews.size());
    }

    protected abstract void convert(BaseViewHolder holder, T itemData, int position);


    //************************************对外提供的调用接口***************************************//

    /**
     * 添加头布局
     */
    public void addHeaderView(View view) {
        //查询头部集合中没有, 则添加
        int position = mHeaderViews.indexOfValue(view);
        if (position < 0) {
            mHeaderViews.put(TYPE_HEADER++, view);
        }
        notifyDataSetChanged();
    }

    /**
     * 添加尾布局
     */
    public void addFooterView(View view) {
        int position = mFooterViews.indexOfValue(view);
        if (position < 0) {
            mFooterViews.put(TYPE_FOOTER++, view);
        }
        notifyDataSetChanged();
    }

    /**
     * 移除头部
     */
    public void removeHeaderView(View view) {
        int index = mHeaderViews.indexOfValue(view);
        if (index < 0) return;
        mHeaderViews.removeAt(index);
        notifyDataSetChanged();
    }

    /**
     * 移除底部
     */
    public void removeFooterView(View view) {
        int index = mFooterViews.indexOfValue(view);
        if (index < 0) return;
        mFooterViews.removeAt(index);
        notifyDataSetChanged();
    }

    public List<T> getShowItems() {
        return mShowItems;
    }

    /**
     * 设置数据集合
     */
    public void setNewDatas(List<T> showItems) {
        if (showItems == null) {
            showItems = new ArrayList<>();
        }
        this.mShowItems = showItems;
        this.notifyDataSetChanged();
    }

    /**
     * change data
     */
    public void changeData(@IntRange(from = 0) int index, @NonNull T showItem) {
        mShowItems.set(index, showItem);
        notifyItemChanged(index + mHeaderViews.size());
    }

    /**
     * 添加数据集合
     */
    public void addDatas(int position, List<T> newItems) {
        if (mShowItems == null) {
            mShowItems = new ArrayList<>();
        }
        mShowItems.addAll(position, newItems);
        notifyItemRangeInserted(position + mHeaderViews.size(), newItems.size());
        compatibilityDataSizeChanged(newItems.size());
    }

    /**
     * 添加数据集合
     */
    public void addDatas(List<T> newItems) {
        if (mShowItems == null) {
            mShowItems = new ArrayList<>();
        }
        mShowItems.addAll(newItems);
        notifyItemRangeInserted(getShowItemsCount() + mHeaderViews.size() - newItems.size(), newItems.size());
        compatibilityDataSizeChanged(newItems.size());
    }

    /**
     * 添加一个数据
     */
    public void addData(int position, T data) {
        mShowItems.add(position, data);
        notifyItemInserted(position + mHeaderViews.size());
        //如果添加了一个条目后mShowItems集合size为1，则需要再次刷新，为了改变loading状态。
        compatibilityDataSizeChanged(1);
    }

    public void addData(T data) {
        mShowItems.add(data);
        notifyItemInserted(mShowItems.size() + mHeaderViews.size());
        compatibilityDataSizeChanged(1);
    }

    /**
     * 删除指定条目
     */
    public void removeItem(int position) {
        mShowItems.remove(position);
        int internalPosition = position + mHeaderViews.size();
        //删除条目并带有动画效果
        this.notifyItemRemoved(internalPosition);
        compatibilityDataSizeChanged(0);
        //刷新列表的position.
        this.notifyItemRangeChanged(internalPosition, getShowItemsCount() - internalPosition);
    }

    /**
     * 清除所有条目.
     */
    public void clear() {
        synchronized (BaseAdapter.class) {
            if (mShowItems == null) {
                return;
            }
            int size = mShowItems.size();
            if (size > 0) {
                mShowItems.clear();
                notifyItemRangeRemoved(mHeaderViews.size(), size + mHeaderViews.size());
            }
        }
    }

    private void compatibilityDataSizeChanged(int size) {
        final int dataSize = mShowItems == null ? 0 : mShowItems.size();
        if (dataSize == size) {
            notifyDataSetChanged();
        }
    }


    /****************
     * 条目点击事件
     ***************/
    public OnItemClickListener<T> mItemClickListener;
    public OnLongClickListener<T> mLongClickListener;

    public void setOnItemClickListener(OnItemClickListener<T> itemClickListener) {
        this.mItemClickListener = itemClickListener;
    }

    public void setOnLongClickListener(OnLongClickListener<T> longClickListener) {
        this.mLongClickListener = longClickListener;
    }


    public interface OnItemClickListener<K>{
        void onItemClick(View v, K itemData, int position);
    }

    public interface OnLongClickListener<K> {
        boolean onLongClick(View v, K ItemData, int position);
    }
}
