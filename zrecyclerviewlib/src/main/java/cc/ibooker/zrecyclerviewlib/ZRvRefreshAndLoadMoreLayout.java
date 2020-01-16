package cc.ibooker.zrecyclerviewlib;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;

/**
 * 自带下拉刷新和加载更多的布局
 *
 * @author 邹峰立
 */
public class ZRvRefreshAndLoadMoreLayout extends ZSwipeRefreshLayout
        implements RvScrollListener.OnLoadListener, ZSwipeRefreshLayout.OnRefreshListener {
    public ZRecyclerView zRv;

    public ZRvRefreshAndLoadMoreLayout(@NonNull Context context) {
        this(context, null);
    }

    public ZRvRefreshAndLoadMoreLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOnRefreshListener(this);
        LayoutInflater.from(context).inflate(R.layout.zrecyclerview_layout_rvrefreshandloadmore, this, true);
        zRv = findViewById(R.id.zrv);
        zRv.setRvLoadListener(this);
    }

    @Override
    public void onRefresh() {
        if (onRvRefreshAndLoadMoreListener != null)
            onRvRefreshAndLoadMoreListener.onRefresh();
    }

    @Override
    public void onLoad() {
        if (onRvRefreshAndLoadMoreListener != null)
            onRvRefreshAndLoadMoreListener.onLoad();
    }

    // 下拉刷新与加载更多监听事件
    public interface OnRvRefreshAndLoadMoreListener {
        void onRefresh();

        void onLoad();
    }

    private OnRvRefreshAndLoadMoreListener onRvRefreshAndLoadMoreListener;

    public ZRvRefreshAndLoadMoreLayout setOnRvRefreshAndLoadMoreListener(OnRvRefreshAndLoadMoreListener onRvRefreshAndLoadMoreListener) {
        this.onRvRefreshAndLoadMoreListener = onRvRefreshAndLoadMoreListener;
        return this;
    }
}
