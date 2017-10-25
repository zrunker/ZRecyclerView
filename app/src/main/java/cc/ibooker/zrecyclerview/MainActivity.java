package cc.ibooker.zrecyclerview;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import java.util.ArrayList;

import cc.ibooker.zrecyclerview.adapter.RvAdapter;
import cc.ibooker.zrecyclerview.bean.FooterData;
import cc.ibooker.zrecyclerview.bean.RvData;
import cc.ibooker.zrecyclerview.recyclerview.MyLinearLayoutManager;
import cc.ibooker.zrecyclerview.recyclerview.RecyclerViewScrollListener;
import cc.ibooker.zrecyclerview.utils.ConstantUtil;
import cc.ibooker.zrecyclerview.view.AutoSwipeRefreshLayout;
import cc.ibooker.zrecyclerview.view.LoadRecyclerView;

/**
 * reyclerView实现下拉刷新和加载更多案例
 * <p>
 * create by 邹峰立
 */
public class MainActivity extends AppCompatActivity implements RecyclerViewScrollListener.OnLoadListener, SwipeRefreshLayout.OnRefreshListener {
    private AutoSwipeRefreshLayout swipeLayout;
    private LoadRecyclerView recyclerView;
    private RvAdapter adapter;
    private ArrayList<RvData> mDatas = new ArrayList<>();
    private FooterData footerData;
    private boolean isAbleLoading = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        // 加载数据
        swipeLayout.autoRefresh();
        onRefresh();
    }

    // 初始化控件
    private void initView() {
        swipeLayout = (AutoSwipeRefreshLayout) findViewById(R.id.swipe_container);
        swipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swipeLayout.setOnRefreshListener(this);

        // 设置recyclerView的布局管理器
        recyclerView = (LoadRecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new MyLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setOnLoadListener(this);

        // 初始化底部数据
        footerData = new FooterData(false, false, getResources().getString(R.string.load_more_before));
    }

    // 自定义setAdapter
    private void setAdapter() {
        if (adapter == null) {
            adapter = new RvAdapter(this, mDatas, footerData);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.reflushList(mDatas);
        }

        // 判断是否已经加载完成
        if (mDatas == null || mDatas.size() <= 0) {
            reflashFooterView(ConstantUtil.LOAD_MORE_HIDDEN);
        } else if (mDatas.size() < ConstantUtil.PAGE_SIZE || !isAbleLoading) {
            reflashFooterView(ConstantUtil.LOAD_MORE_COMPLETE);
        } else {
            reflashFooterView(ConstantUtil.LOAD_MORE_BEFORE);
        }
    }

    // 刷新底部
    private void reflashFooterView(int index) {
        // 重构底部数据
        switch (index) {
            case ConstantUtil.LOAD_MORE_BEFORE:// 加载前/后
                recyclerView.setLoading(false);
                footerData.setShowProgressBar(false);
                footerData.setShowFooter(true);
                footerData.setTitle(getResources().getString(R.string.load_more_before));
                break;
            case ConstantUtil.LOAD_MORE:// 加载中
                recyclerView.setLoading(false);
                footerData.setShowProgressBar(true);
                footerData.setShowFooter(true);
                footerData.setTitle(getResources().getString(R.string.load_more));
                break;
            case ConstantUtil.LOAD_MORE_COMPLETE:// 不允许加载
                recyclerView.setLoading(false);
                footerData.setShowProgressBar(false);
                footerData.setShowFooter(true);
                footerData.setTitle(getResources().getString(R.string.load_more_complete));
                break;
            case ConstantUtil.LOAD_MORE_HIDDEN:// 隐藏
                recyclerView.setLoading(false);
                footerData.setShowProgressBar(false);
                footerData.setShowFooter(false);
                footerData.setTitle(getResources().getString(R.string.load_more_complete));
                break;
        }
        // 刷新底部
        if (adapter != null)
            adapter.reflushFooterData(footerData);
    }

    // 下拉刷新-一般为加载网络数据
    @Override
    public void onRefresh() {
        isAbleLoading = true;
        // 获取最新数据
        // 模拟网络加载
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mDatas == null)
                    mDatas = new ArrayList<>();
                mDatas.clear();
                for (int i = 0; i < ConstantUtil.PAGE_SIZE; i++) {
                    RvData rvData = new RvData();
                    rvData.setTitle("标题=" + i);
                    rvData.setDesc("描述=" + i);
                    rvData.setType(RvAdapter.TYPE_ONE);
                    mDatas.add(rvData);
                }
                setAdapter();
                // 取消加载
                swipeLayout.setRefreshing(false);
            }
        }, 3000);

        reflashFooterView(ConstantUtil.LOAD_MORE_BEFORE);
    }

    // 加载更多
    @Override
    public void onLoad() {
        if (mDatas.size() >= ConstantUtil.PAGE_SIZE && isAbleLoading) {
            // 获取更多数据
            // 模拟网络加载
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // 加载数据
                    if (mDatas == null)
                        mDatas = new ArrayList<>();
                    int num = mDatas.size();
                    for (int i = num; i < num + ConstantUtil.PAGE_SIZE; i++) {
                        RvData rvData = new RvData();
                        rvData.setTitle("标题=" + i);
                        rvData.setDesc("描述=" + i);
                        rvData.setType(RvAdapter.TYPE_ONE);
                        mDatas.add(rvData);
                    }
                    setAdapter();
                }
            }, 3000);

            reflashFooterView(ConstantUtil.LOAD_MORE);
            swipeLayout.setRefreshing(false);
        } else {
            reflashFooterView(ConstantUtil.LOAD_MORE_COMPLETE);
        }

    }
}
