package cc.ibooker.zrecyclerviewlib;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * 自定义RecyclerView
 * 1- 加载更多监听
 * 2- 显示空页面
 * 3- 显示底部View
 *
 * @author 邹峰立
 */
public class ZRecyclerView extends RecyclerView {
    private BaseRvAdapter rvAdapter;
    private BaseRvEmptyView emptyView;
    private BaseRvFooterView footerView;
    private RvScrollListener rvScrollListener;
    private RvScrollListener.OnLoadListener rvLoadListener;
    private RvItemClickListener rvItemClickListener;
    private RvItemLongClickListener rvItemLongClickListener;
    private RvFooterViewClickListener rvFooterViewClickListener;

    public ZRecyclerView(@NonNull Context context) {
        super(context);
    }

    public ZRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ZRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    // 设置是否加载更多
    public void setLoading(boolean bool) {
        if (rvScrollListener != null)
            rvScrollListener.setLoadingMore(bool);
    }

    // 加载更多监听
    public ZRecyclerView setRvLoadListener(RvScrollListener.OnLoadListener rvLoadListener) {
        this.rvLoadListener = rvLoadListener;
        setOpenLoadMoreListener(rvLoadListener != null);
        return this;
    }

    // 设置单项点击监听
    public ZRecyclerView setRvItemClickListener(RvItemClickListener rvItemClickListener) {
        this.rvItemClickListener = rvItemClickListener;
        if (rvAdapter != null)
            rvAdapter.setRvItemClickListener(rvItemClickListener);
        return this;
    }

    // 设置单项长按监听
    public ZRecyclerView setRvItemLongClickListener(RvItemLongClickListener rvItemLongClickListener) {
        this.rvItemLongClickListener = rvItemLongClickListener;
        if (rvAdapter != null)
            rvAdapter.setRvItemLongClickListener(rvItemLongClickListener);
        return this;
    }

    // 设置底部点击监听
    public ZRecyclerView setRvFooterViewClickListener(RvFooterViewClickListener rvFooterViewClickListener) {
        this.rvFooterViewClickListener = rvFooterViewClickListener;
        if (rvAdapter != null)
            rvAdapter.setRvFooterViewClickListener(rvFooterViewClickListener);
        return this;
    }

    /**
     * 添加空页面
     *
     * @param emptyView 待添加空页面View
     */
    public ZRecyclerView addEmptyView(@NonNull BaseRvEmptyView emptyView) {
        this.emptyView = emptyView;
        if (rvAdapter != null) {
            rvAdapter.addRvEmptyView(emptyView);
            rvAdapter.updateRvEmptyView();
        }
        return this;
    }

    /**
     * 添加底部View
     *
     * @param footerView 待添加底部View
     */
    public ZRecyclerView addFooterView(@NonNull BaseRvFooterView footerView) {
        this.footerView = footerView;
        if (rvAdapter != null) {
            rvAdapter.addRvFooterView(footerView);
            rvAdapter.updateRvFooterView();
        }
        return this;
    }

    /**
     * 刷新空界面View
     */
    public ZRecyclerView refreshRvEmptyView() {
        if (rvAdapter != null)
            rvAdapter.updateRvEmptyView();
        return this;
    }

    /**
     * 刷新底部View
     */
    public ZRecyclerView refreshRvFooterView() {
        if (rvAdapter != null)
            rvAdapter.updateRvFooterView();
        return this;
    }

    /**
     * 复写setAdapter
     */
    public void setAdapter(@NonNull BaseRvAdapter adapter) {
        setRvAdapter(adapter);
    }

    /**
     * 设置适配器
     *
     * @param adapter 待设置的适配器
     */
    public ZRecyclerView setRvAdapter(@NonNull BaseRvAdapter adapter) {
        this.rvAdapter = adapter;
        if (footerView != null)
            rvAdapter.addRvFooterView(footerView);
        if (emptyView != null)
            rvAdapter.addRvEmptyView(emptyView);
        if (rvItemClickListener != null)
            rvAdapter.setRvItemClickListener(rvItemClickListener);
        if (rvFooterViewClickListener != null)
            rvAdapter.setRvFooterViewClickListener(rvFooterViewClickListener);
        if (rvItemLongClickListener != null)
            rvAdapter.setRvItemLongClickListener(rvItemLongClickListener);
        rvAdapter.attachRecyclerView(this);
        super.setAdapter(adapter);
        return this;
    }

    /**
     * 设置是否开启下拉更多监听
     *
     * @param isOpenLoadMoreListener true开发，false关闭
     */
    private void setOpenLoadMoreListener(boolean isOpenLoadMoreListener) {
        if (isOpenLoadMoreListener) {
            if (rvScrollListener == null)
                rvScrollListener = new RvScrollListener();
            rvScrollListener.setOnLoadListener(rvLoadListener);
            addOnScrollListener(rvScrollListener);
        } else {
            if (rvScrollListener != null)
                removeOnScrollListener(rvScrollListener);
        }
    }

}
