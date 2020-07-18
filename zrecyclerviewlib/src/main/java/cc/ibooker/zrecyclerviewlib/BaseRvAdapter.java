package cc.ibooker.zrecyclerviewlib;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * RecyclerView 适配器类
 *
 * @author 邹峰立
 */
public abstract class BaseRvAdapter<T> extends RecyclerView.Adapter<BaseViewHolder>
        implements View.OnClickListener, View.OnLongClickListener {
    private List<T> mList = Collections.synchronizedList(new ArrayList<T>());
    private RvItemClickListener rvItemClickListener;
    private RvFooterViewClickListener rvFooterViewClickListener;
    private RvItemLongClickListener rvItemLongClickListener;
    private RvHeadViewClickListener rvHeadViewClickListener;
    private RvEmptyViewClickListener rvEmptyViewClickListener;
    public final int TYPE_FOOTER = Integer.MIN_VALUE;
    public final int TYPE_EMPTY = TYPE_FOOTER + 1;
    public final int TYPE_HEARD = TYPE_EMPTY + 1;
    private BaseRvFooterView rvFooterView;// 底部View
    private BaseRvEmptyView rvEmptyView;// 空页面
    private BaseRvHeadView rvHeadView;// 头部View
    private ZRecyclerView zRecyclerView;

    // 设置ZRecyclerView
    synchronized void attachRecyclerView(ZRecyclerView zRecyclerView) {
        this.zRecyclerView = zRecyclerView;
    }

    public ZRecyclerView getRecyclerView() {
        return zRecyclerView;
    }

    @Override
    public void onClick(View v) {
        if (zRecyclerView != null) {
            // 根据RecyclerView获得当前View的位置
            int position = zRecyclerView.getChildAdapterPosition(v);
            int itemCount = getItemCount();
            int viewType = getItemViewType(position);
            switch (viewType) {
                case TYPE_FOOTER:
                    if (rvFooterViewClickListener != null)
                        rvFooterViewClickListener.onRvFooterViewClick(v);
                    break;
                case TYPE_EMPTY:
                    if (rvEmptyViewClickListener != null)
                        rvEmptyViewClickListener.onRvEmptyViewClick(v);
                    break;
                case TYPE_HEARD:
                    if (rvHeadViewClickListener != null)
                        rvHeadViewClickListener.onRvHeadViewClick(v);
                    break;
                default:
                    if (position >= 0 && position < itemCount)
                        if (rvItemClickListener != null)
                            rvItemClickListener.onRvItemClick(v, position, getRealListPosition(position));
                    break;
            }

//            // 执行点击事件
//            if (position == 0 && rvHeadView != null) {
//                if (rvHeadViewClickListener != null)
//                    rvHeadViewClickListener.onRvHeadViewClick(v);
//            } else if (position == itemCount - 1 && rvFooterView != null) {
//                if (rvFooterViewClickListener != null)
//                    rvFooterViewClickListener.onRvFooterViewClick(v);
//            } else if (position >= 0 && position < itemCount) {
//                if (rvItemClickListener != null)
//                    rvItemClickListener.onRvItemClick(v, position, getRealListPosition(position));
//            }
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (zRecyclerView != null) {
            // 根据RecyclerView获得当前View的位置
            int position = zRecyclerView.getChildAdapterPosition(v);
            // 执行长按事件
            if (rvItemLongClickListener != null)
                rvItemLongClickListener.onRvItemLongClick(v, position, getRealListPosition(position));
        }
        return true;
    }

    // 设置单项点击监听
    public synchronized void setRvItemClickListener(RvItemClickListener rvItemClickListener) {
        this.rvItemClickListener = rvItemClickListener;
    }

    // 设置头部点击监听
    public synchronized void setRvHeadViewClickListener(RvHeadViewClickListener rvHeadViewClickListener) {
        this.rvHeadViewClickListener = rvHeadViewClickListener;
    }

    // 设置底部点击监听
    public synchronized void setRvFooterViewClickListener(RvFooterViewClickListener rvFooterViewClickListener) {
        this.rvFooterViewClickListener = rvFooterViewClickListener;
    }

    // 设置空页面点击监听
    public synchronized void setRvEmptyViewClickListener(RvEmptyViewClickListener rvEmptyViewClickListener) {
        this.rvEmptyViewClickListener = rvEmptyViewClickListener;
    }

    // 设置单项长按监听
    public synchronized void setRvItemLongClickListener(RvItemLongClickListener rvItemLongClickListener) {
        this.rvItemLongClickListener = rvItemLongClickListener;
    }

    public BaseRvAdapter() {
    }

    public BaseRvAdapter(List<T> list) {
        this.mList = list;
    }

    // 创建ViewHolder
    public abstract BaseViewHolder onCreateItemViewHolder(@NonNull ViewGroup viewGroup, int viewType);

    // 显示数据
    public abstract void onBindItemViewHolder(@NonNull BaseViewHolder baseViewHolder, int position);

    /**
     * 添加空界面
     *
     * @param rvEmptyView 待添加View
     */
    public synchronized BaseRvAdapter addRvEmptyView(BaseRvEmptyView rvEmptyView) {
        this.rvEmptyView = rvEmptyView;
        return this;
    }

    /**
     * 设置底部View
     *
     * @param rvFooterView 待添加View
     */
    public synchronized BaseRvAdapter addRvFooterView(BaseRvFooterView rvFooterView) {
        this.rvFooterView = rvFooterView;
        return this;
    }

    /**
     * 设置头部View
     *
     * @param rvHeadView 待添加View
     */
    public synchronized BaseRvAdapter addRvHeadView(BaseRvHeadView rvHeadView) {
        this.rvHeadView = rvHeadView;
        return this;
    }

    /**
     * 刷新空界面View
     */
    public synchronized BaseRvAdapter updateRvEmptyView() {
        if (rvEmptyView != null) {
            rvEmptyView.refreshEmptyView(rvEmptyView.getEmptyData());
            this.notifyDataSetChanged();
        }
        return this;
    }

    /**
     * 刷新底部View
     */
    public synchronized BaseRvAdapter updateRvFooterView() {
        if (rvFooterView != null) {
            rvFooterView.refreshFooterView(rvFooterView.getFooterData());
        }
        return this;
    }

    /**
     * 刷新头部View
     */
    public synchronized BaseRvAdapter updateRvHeadView() {
        if (rvHeadView != null) {
            rvHeadView.refreshHeadView(rvHeadView.getHeadData());
        }
        return this;
    }

    // 设置数据
    public synchronized BaseRvAdapter setData(List<T> list) {
        this.mList = list;
        return this;
    }

    // 获取数据
    public List<T> getData() {
        return mList;
    }

    /**
     * 刷新界面
     *
     * @param list 待刷新数据
     */
    public synchronized BaseRvAdapter refreshData(ArrayList<T> list) {
        this.mList = list;
        this.notifyDataSetChanged();
        return this;
    }

    /**
     * 局部刷新-多项
     *
     * @param positionStart 开始项
     * @param itemCount     刷新项数
     */
    public synchronized BaseRvAdapter updateItems(int positionStart, int itemCount) {
        if (itemCount > 0)
            this.notifyItemRangeChanged(positionStart, itemCount);
        return this;
    }

    /**
     * 局部刷新-单项
     *
     * @param position 待刷新的项
     */
    public synchronized BaseRvAdapter updateItem(int position) {
        this.notifyItemRangeChanged(position, 1);
        return this;
    }

    /**
     * 局部移除 - 多项
     *
     * @param positionStart 开始项
     * @param itemCount     移除项数
     */
    @Deprecated
    public synchronized BaseRvAdapter removeItems(int positionStart, int itemCount) {
        for (int i = 0; i < itemCount; i++) {
            positionStart++;
            removeItem(positionStart);
        }
        return this;
    }

    /**
     * 局部移除 - 多项
     *
     * @param positionStart 开始项
     * @param itemCount     移除项数
     */
    public synchronized BaseRvAdapter removeItems2(int positionStart, int itemCount) {
        for (int i = 0; i < itemCount; i++) {
            positionStart++;
            removeItem2(positionStart);
        }
        return this;
    }

    /**
     * 局部移除 - 单项
     *
     * @param position 待移除的项
     */
    @Deprecated
    public synchronized BaseRvAdapter removeItem(int position) {
        try {
            int itemCount = getItemCount();
            if (position >= 0 && position < itemCount) {
                int realPosition = getRealListPosition(position);
                if (mList != null && mList.size() > 0
                        && realPosition >= 0 && realPosition < mList.size()) {
                    mList.remove(realPosition);
                }
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, itemCount - position);
            }
        } catch (Exception e) {
            if (mList != null)
                refreshData((ArrayList<T>) mList);
        }
        return this;
    }

    /**
     * 局部移除 - 单项
     *
     * @param position 待移除的项
     */
    public synchronized BaseRvAdapter removeItem2(int position) {
        int itemCount = getItemCount();
        if (position >= 0 && position < itemCount) {
            int realPosition = getRealListPosition(position);
            if (mList != null && mList.size() > 0
                    && realPosition >= 0 && realPosition < mList.size()) {
                mList.remove(realPosition);
            }
            notifyDataSetChanged();
        }
        return this;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == TYPE_FOOTER) {
            if (rvFooterView.getFooterView() != null)
                rvFooterView.getFooterView().setOnClickListener(this);
            return new BaseViewHolder<>(rvFooterView.getFooterView());
        } else if (viewType == TYPE_EMPTY) {
            if (rvEmptyView.getEmptyView() != null)
                rvEmptyView.getEmptyView().setOnClickListener(this);
            return new BaseViewHolder(rvEmptyView.getEmptyView());
        } else if (viewType == TYPE_HEARD) {
            if (rvHeadView.getHeadView() != null)
                rvHeadView.getHeadView().setOnClickListener(this);
            return new BaseViewHolder(rvHeadView.getHeadView());
        } else {
            BaseViewHolder baseViewHolder = onCreateItemViewHolder(viewGroup, viewType);
            if (baseViewHolder.getItemView() != null) {
                baseViewHolder.getItemView().setOnClickListener(this);
                baseViewHolder.getItemView().setOnLongClickListener(this);
            }
            return baseViewHolder;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final BaseViewHolder viewHolder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == TYPE_FOOTER) {
            rvFooterView.refreshFooterView(rvFooterView.getFooterData());
        } else if (viewType == TYPE_EMPTY) {
            rvEmptyView.refreshEmptyView(rvEmptyView.getEmptyData());
        } else if (viewType == TYPE_HEARD) {
            rvHeadView.refreshHeadView(rvHeadView.getHeadData());
        } else
            onBindItemViewHolder(viewHolder, getRealListPosition(position));
    }

    @Override
    public int getItemCount() {
        if ((mList == null || mList.size() <= 0) && rvEmptyView != null)// 空页面
            return 1;
        int total = 0;
        if (mList != null)
            total = total + mList.size();
        if (rvHeadView != null)
            total = total + 1;
        if (mList != null && mList.size() > 0 && rvFooterView != null)// 添加底部
            total = total + 1;
        return total;
    }

    @Override
    public int getItemViewType(int position) {
        if ((mList == null || mList.size() <= 0) && rvEmptyView != null)// 显示空页面
            return TYPE_EMPTY;
        if (position == 0 && rvHeadView != null)// 显示头部
            return TYPE_HEARD;
        if (mList != null && position >= getItemCount() - 1 && rvFooterView != null)// 显示底部
            return TYPE_FOOTER;
        return getViewType(getRealListPosition(position));
    }

    /**
     * 获取ItemType
     *
     * @param position 列表项
     * @return item类型
     */
    public int getViewType(int position) {
        return super.getItemViewType(position);
    }

    /**
     * 获取mList真实位置
     *
     * @param position 原位置
     */
    public int getRealListPosition(int position) {
        int realPosition;
        if (mList == null || mList.size() <= 0)
            realPosition = -1;
        else {
            realPosition = rvHeadView != null ? position - 1 : position;
            if (realPosition >= mList.size())
                realPosition = -1;
        }
        return realPosition;
    }

    /**
     * 获取数据集总数
     */
    public int getDataSize() {
        if (getData() == null)
            return 0;
        return getData().size();
    }

    @Override
    public void onViewAttachedToWindow(@NonNull BaseViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        int viewType = holder.getItemViewType();
        if (viewType == TYPE_FOOTER
                || viewType == TYPE_EMPTY
                || viewType == TYPE_HEARD) {
            ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
            if (lp instanceof StaggeredGridLayoutManager.LayoutParams) {
                StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
                p.setFullSpan(true);
            }
        }
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridLM = (GridLayoutManager) manager;
            gridLM.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int viewType = getItemViewType(position);
                    return (viewType == TYPE_FOOTER
                            || viewType == TYPE_EMPTY
                            || viewType == TYPE_HEARD) ? 1 : gridLM.getSpanCount();
                }
            });
        }
    }
}
