package cc.ibooker.zrecyclerviewlib;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * 自定义RecyclerView
 * 1- 加载更多监听
 * 2- 显示空页面
 * 3- 显示底部View
 * 4- 显示头部View
 *
 * @author 邹峰立
 */
public class ZRecyclerView extends RecyclerView {
    private BaseRvAdapter rvAdapter;
    private BaseRvEmptyView emptyView;
    private BaseRvFooterView footerView;
    private BaseRvHeadView headView;
    private RvScrollListener rvScrollListener;
    private RvScrollListener.OnLoadListener rvLoadListener;
    private RvItemClickListener rvItemClickListener;
    private RvItemLongClickListener rvItemLongClickListener;
    private RvFooterViewClickListener rvFooterViewClickListener;
    private RvHeadViewClickListener rvHeadViewClickListener;
    private RvEmptyViewClickListener rvEmptyViewClickListener;
    private RvItemCViewClickListener rvItemCViewClickListener;
    private RvItemLongCViewClickListener rvItemLongCViewClickListener;
    private RvItemDiyCViewClickListener rvItemDiyCViewClickListener;
    private RvItemDiyLongCViewClickListener rvItemDiyLongCViewClickListener;
    private LayoutManager layoutManager;

    public ZRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public ZRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    // 初始化
    private void init(Context context) {
        this.setLayoutManager(new ZLinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void setLayoutManager(@Nullable LayoutManager layout) {
        if (layout instanceof LinearLayoutManager && !(layout instanceof GridLayoutManager)) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layout;
            layout = new LinearLayoutManager(getContext(), linearLayoutManager.getOrientation(), linearLayoutManager.getReverseLayout());
        }
        layoutManager = layout;
        super.setLayoutManager(layoutManager);
    }

    @Nullable
    @Override
    public LayoutManager getLayoutManager() {
        return layoutManager;
    }

    public boolean isMoreLoading() {
        boolean isMoreLoading = false;
        if (rvScrollListener != null)
            isMoreLoading = rvScrollListener.isLoadingMore();
        return isMoreLoading;
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

    // 设置头部点击监听
    public ZRecyclerView setRvHeadViewClickListener(RvHeadViewClickListener rvHeadViewClickListener) {
        this.rvHeadViewClickListener = rvHeadViewClickListener;
        if (rvAdapter != null)
            rvAdapter.setRvHeadViewClickListener(rvHeadViewClickListener);
        return this;
    }

    // 设置空页面点击监听
    public ZRecyclerView setRvEmptyViewClickListener(RvEmptyViewClickListener rvEmptyViewClickListener) {
        this.rvEmptyViewClickListener = rvEmptyViewClickListener;
        if (rvAdapter != null)
            rvAdapter.setRvEmptyViewClickListener(rvEmptyViewClickListener);
        return this;
    }

    // 监听单项点击事件
    public ZRecyclerView setRvItemCViewClickListener(RvItemCViewClickListener rvItemCViewClickListener) {
        this.rvItemCViewClickListener = rvItemCViewClickListener;
        if (rvAdapter != null)
            rvAdapter.setRvItemCViewClickListener(rvItemCViewClickListener);
        return this;
    }

    // 监听单项长按事件
    public ZRecyclerView setRvItemLongCViewClickListener(RvItemLongCViewClickListener rvItemLongCViewClickListener) {
        this.rvItemLongCViewClickListener = rvItemLongCViewClickListener;
        if (rvAdapter != null)
            rvAdapter.setRvItemLongCViewClickListener(rvItemLongCViewClickListener);
        return this;
    }

    // 自定义监听单项点击事件
    public ZRecyclerView regRvItemDiyCViewClickListener(RvItemDiyCViewClickListener rvItemDiyCViewClickListener) {
        this.rvItemDiyCViewClickListener = rvItemDiyCViewClickListener;
        if (rvAdapter != null)
            rvAdapter.regRvItemDiyCViewClickListener(rvItemDiyCViewClickListener);
        return this;
    }

    // 自定义监听单项长按事件
    public ZRecyclerView regRvItemLongDiyCViewClickListener(RvItemDiyLongCViewClickListener rvItemDiyLongCViewClickListener) {
        this.rvItemDiyLongCViewClickListener = rvItemDiyLongCViewClickListener;
        if (rvAdapter != null)
            rvAdapter.regRvItemLongDiyCViewClickListener(rvItemDiyLongCViewClickListener);
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
     * 添加头部View
     *
     * @param headView 待添加头部View
     */
    public ZRecyclerView addHeadView(@NonNull BaseRvHeadView headView) {
        this.headView = headView;
        if (rvAdapter != null) {
            rvAdapter.addRvHeadView(headView);
            rvAdapter.updateRvHeadView();
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
     * 刷新顶部View
     */
    public ZRecyclerView refreshRvHeadView() {
        if (rvAdapter != null)
            rvAdapter.updateRvHeadView();
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
        if (headView != null)
            rvAdapter.addRvHeadView(headView);
        if (footerView != null)
            rvAdapter.addRvFooterView(footerView);
        if (emptyView != null)
            rvAdapter.addRvEmptyView(emptyView);
        if (rvItemClickListener != null)
            rvAdapter.setRvItemClickListener(rvItemClickListener);
        if (rvFooterViewClickListener != null)
            rvAdapter.setRvFooterViewClickListener(rvFooterViewClickListener);
        if (rvHeadViewClickListener != null)
            rvAdapter.setRvHeadViewClickListener(rvHeadViewClickListener);
        if (rvItemLongClickListener != null)
            rvAdapter.setRvItemLongClickListener(rvItemLongClickListener);
        if (rvEmptyViewClickListener != null)
            rvAdapter.setRvEmptyViewClickListener(rvEmptyViewClickListener);
        if (rvItemCViewClickListener != null)
            rvAdapter.setRvItemCViewClickListener(rvItemCViewClickListener);
        if (rvItemLongCViewClickListener != null)
            rvAdapter.setRvItemLongCViewClickListener(rvItemLongCViewClickListener);
        if (rvItemDiyCViewClickListener != null)
            rvAdapter.regRvItemDiyCViewClickListener(rvItemDiyCViewClickListener);
        if (rvItemDiyLongCViewClickListener != null)
            rvAdapter.regRvItemLongDiyCViewClickListener(rvItemDiyLongCViewClickListener);
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

    /**
     * RecyclerView 移动到指定位置，
     *
     * @param position 要跳转的位置
     */
    public void moveToPosition(int position) {
        if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            int firstItem = linearLayoutManager.findFirstVisibleItemPosition();
            int lastItem = linearLayoutManager.findLastVisibleItemPosition();
            if (position <= firstItem) {
                this.scrollToPosition(position);
            } else if (position <= lastItem) {
                int top = this.getChildAt(position - firstItem).getTop();
                this.scrollBy(0, top);
            } else {
                this.scrollToPosition(position);
            }
        } else
            this.scrollToPosition(position);
    }

    /**
     * RecyclerView 缓慢移动到指定位置，
     *
     * @param position 要跳转的位置
     */
    public void smoothMoveToPosition(int position) {
        if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            int firstItem = linearLayoutManager.findFirstVisibleItemPosition();
            int lastItem = linearLayoutManager.findLastVisibleItemPosition();
            if (position <= firstItem) {
                this.scrollToPosition(position);
            } else if (position <= lastItem) {
                int top = this.getChildAt(position - firstItem).getTop();
                this.smoothScrollBy(0, top);
            } else {
                this.smoothScrollToPosition(position);
            }
        } else
            this.smoothScrollToPosition(position);
    }

    /**
     * 获取列表真实位置
     *
     * @param position 原位置
     */
    public int getRealListPosition(int position) {
        if (rvAdapter != null)
            return rvAdapter.getRealListPosition(position);
        return 0;
    }
}
