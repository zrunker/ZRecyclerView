package cc.ibooker.zrecyclerviewlib;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 自定义自动下拉刷新SwipeRefreshLayout
 * Created by 邹峰立 on 2017/5/10.
 */
public class ZSwipeRefreshLayout extends SwipeRefreshLayout {
    private OnRefreshListener onRefreshListener;

    public ZSwipeRefreshLayout(@NonNull Context context) {
        this(context, null);
    }

    public ZSwipeRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    // 初始化
    private void init() {
        setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    @Override
    public void setOnRefreshListener(@Nullable OnRefreshListener listener) {
        if (listener != null)
            onRefreshListener = listener;
        super.setOnRefreshListener(listener);
    }

    /**
     * 取消下拉刷新监听
     */
    public void setCanRefresh(boolean isCanRefresh) {
        if (isCanRefresh) {
            setOnRefreshListener(onRefreshListener);
            setRefreshing(false);
        } else {
            setOnRefreshListener(null);
            setRefreshing(true);
        }
    }

    /**
     * 自动刷新 - 执行下拉刷新
     */
    public void executeRefresh() {
        // 修改界面
        try {
            Field mCircleView = SwipeRefreshLayout.class.getDeclaredField("mCircleView");
            mCircleView.setAccessible(true);
            View progress = (View) mCircleView.get(this);
            progress.setVisibility(VISIBLE);

            Method setRefreshing = SwipeRefreshLayout.class.getDeclaredMethod("setRefreshing", boolean.class, boolean.class);
            setRefreshing.setAccessible(true);
            setRefreshing.invoke(this, true, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 执行刷新事件
        if (onRefreshListener != null)
            onRefreshListener.onRefresh();
    }
}
