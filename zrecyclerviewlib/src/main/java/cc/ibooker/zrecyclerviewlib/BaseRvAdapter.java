package cc.ibooker.zrecyclerviewlib;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView 适配器类
 *
 * @author 邹峰立
 */
public abstract class BaseRvAdapter<T> extends RecyclerView.Adapter<BaseViewHolder>
        implements View.OnClickListener,
        View.OnLongClickListener {
    private List<T> mList = new ArrayList<>();
    private RvItemClickListener rvItemClickListener;
    private RvFooterViewClickListener rvFooterViewClickListener;
    private RvItemLongClickListener rvItemLongClickListener;
    private final int TYPE_FOOTER = Integer.MIN_VALUE;
    private final int TYPE_EMPTY = TYPE_FOOTER + 1;
    private BaseRvFooterView rvFooterView;// 底部View
    private BaseRvEmptyView rvEmptyView;// 空页面
    private ZRecyclerView zRecyclerView;

    // 设置ZRecyclerView
    void attachRecyclerView(ZRecyclerView zRecyclerView) {
        this.zRecyclerView = zRecyclerView;
    }

    @Override
    public void onClick(View v) {
        if (zRecyclerView != null) {
            // 根据RecyclerView获得当前View的位置
            int position = zRecyclerView.getChildAdapterPosition(v);
            // 执行点击事件
            if (position < mList.size()) {
                if (rvItemClickListener != null)
                    rvItemClickListener.onRvItemClick(v, position);
            } else if (rvFooterViewClickListener != null)
                rvFooterViewClickListener.onRvFooterViewClick(v);
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (zRecyclerView != null) {
            // 根据RecyclerView获得当前View的位置
            int position = zRecyclerView.getChildAdapterPosition(v);
            // 执行长按事件
            if (rvItemLongClickListener != null)
                rvItemLongClickListener.onRvItemLongClick(v, position);
        }
        return true;
    }

    // 设置单项点击监听
    public void setRvItemClickListener(RvItemClickListener rvItemClickListener) {
        this.rvItemClickListener = rvItemClickListener;
    }

    // 设置底部点击监听
    public void setRvFooterViewClickListener(RvFooterViewClickListener rvFooterViewClickListener) {
        this.rvFooterViewClickListener = rvFooterViewClickListener;
    }

    // 设置单项长按监听
    public void setRvItemLongClickListener(RvItemLongClickListener rvItemLongClickListener) {
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
    public BaseRvAdapter addRvEmptyView(BaseRvEmptyView rvEmptyView) {
        this.rvEmptyView = rvEmptyView;
        return this;
    }

    /**
     * 设置底部View
     *
     * @param rvFooterView 待添加View
     */
    public BaseRvAdapter addRvFooterView(BaseRvFooterView rvFooterView) {
        this.rvFooterView = rvFooterView;
        return this;
    }

    /**
     * 刷新空界面View
     */
    public BaseRvAdapter updateRvEmptyView() {
        if (rvEmptyView != null) {
            rvEmptyView.refreshEmptyView(rvEmptyView.getEmptyData());
            this.notifyDataSetChanged();
        }
        return this;
    }

    /**
     * 刷新底部View
     */
    public BaseRvAdapter updateRvFooterView() {
        if (rvFooterView != null) {
            rvFooterView.refreshFooterView(rvFooterView.getFooterData());
        }
        return this;
    }

    // 设置数据
    public BaseRvAdapter setData(List<T> list) {
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
    public BaseRvAdapter refreshData(ArrayList<T> list) {
        this.mList = list;
        this.notifyDataSetChanged();
        return this;
    }

    /**
     * 局部刷新
     *
     * @param positionStart 开始项
     * @param itemCount     刷新项数
     */
    public BaseRvAdapter updateItems(int positionStart, int itemCount) {
        if (itemCount > 0)
            this.notifyItemRangeChanged(positionStart, itemCount);
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
            return new BaseViewHolder(rvEmptyView.getEmptyView());
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
        } else
            onBindItemViewHolder(viewHolder, position);
    }

    @Override
    public int getItemCount() {
        if (mList.size() <= 0 && rvEmptyView != null)// 空页面
            return 1;
        if (rvFooterView != null)// 添加底部
            return mList.size() + 1;
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mList.size() <= 0 && rvEmptyView != null)// 显示空页面
            return TYPE_EMPTY;
        if (position >= mList.size() && rvFooterView != null)// 显示底部
            return TYPE_FOOTER;
        return super.getItemViewType(position);
    }
}
