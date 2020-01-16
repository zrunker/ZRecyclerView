package cc.ibooker.zrecyclerviewlib;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;

/**
 * 自带下拉刷新的布局
 *
 * @author 邹峰立
 */
public class ZRvRefreshLayout extends ZSwipeRefreshLayout
        implements ZSwipeRefreshLayout.OnRefreshListener {
    public ZRecyclerView zRv;

    public ZRvRefreshLayout(@NonNull Context context) {
        this(context, null);
    }

    public ZRvRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOnRefreshListener(this);
        LayoutInflater.from(context).inflate(R.layout.zrecyclerview_layout_rvrefreshandloadmore, this, true);
        zRv = findViewById(R.id.zrv);
    }

    @Override
    public void onRefresh() {
        if (onRvRefreshListener != null)
            onRvRefreshListener.onRefresh();
    }

    // 下拉刷新监听事件
    public interface OnRvRefreshListener {
        void onRefresh();
    }

    private OnRvRefreshListener onRvRefreshListener;

    public ZRvRefreshLayout setOnRvRefreshListener(OnRvRefreshListener onRvRefreshListener) {
        this.onRvRefreshListener = onRvRefreshListener;
        return this;
    }
}
