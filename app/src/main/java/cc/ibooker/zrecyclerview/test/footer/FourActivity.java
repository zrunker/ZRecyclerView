package cc.ibooker.zrecyclerview.test.footer;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import cc.ibooker.zrecyclerview.R;
import cc.ibooker.zrecyclerview.test.ThreeBean;
import cc.ibooker.zrecyclerview.test.ThreeRvAdapter;
import cc.ibooker.zrecyclerviewlib.RvFooterViewClickListener;
import cc.ibooker.zrecyclerviewlib.RvHeadViewClickListener;
import cc.ibooker.zrecyclerviewlib.RvItemCViewClickListener;
import cc.ibooker.zrecyclerviewlib.RvItemClickListener;
import cc.ibooker.zrecyclerviewlib.RvItemLongClickListener;
import cc.ibooker.zrecyclerviewlib.RvScrollListener;
import cc.ibooker.zrecyclerviewlib.ZRecyclerView;
import cc.ibooker.zrecyclerviewlib.ZSwipeRefreshLayout;
import cc.ibooker.zrecyclerviewlib.example.footer.FooterData;
import cc.ibooker.zrecyclerviewlib.example.footer.RvFooterView;
import cc.ibooker.zrecyclerviewlib.example.footer.RvFooterViewStatue;
import cc.ibooker.zrecyclerviewlib.example.head.HeadData;
import cc.ibooker.zrecyclerviewlib.example.head.RvHeadView;

public class FourActivity extends Activity implements SwipeRefreshLayout.OnRefreshListener, RvScrollListener.OnLoadListener {
    private ZRecyclerView zRecyclerView;
    private FooterData footerData;
    private ZSwipeRefreshLayout swipeContainer;
    private ThreeRvAdapter threeRvAdapter;
    private ArrayList<ThreeBean> list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three);

        swipeContainer = findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(this);
        zRecyclerView = findViewById(R.id.zrv);
        zRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        // 添加加载更多监听
        zRecyclerView.setRvLoadListener(this);

        // 添加适配器
        ThreeRvAdapter threeRvAdapter = new ThreeRvAdapter();
        list = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            list.add(new ThreeBean("test" + i));
        }
        threeRvAdapter.setData(list);
        zRecyclerView.setRvAdapter(threeRvAdapter);

        // 添加底部
        footerData = new FooterData(RvFooterViewStatue.STATUE_HIDDEN);
        zRecyclerView.addFooterView(new RvFooterView(this, footerData));
        zRecyclerView.setRvFooterViewClickListener(new RvFooterViewClickListener() {
            @Override
            public void onRvFooterViewClick(@NonNull View view) {
                Toast.makeText(FourActivity.this, "点击底部", Toast.LENGTH_SHORT).show();
            }
        });

        zRecyclerView.setRvItemClickListener(new RvItemClickListener() {
            @Override
            public void onRvItemClick(@NonNull View view, int position, int realPosition) {
                Toast.makeText(FourActivity.this, "点击position：" + position + " 点击数据列表position：" + realPosition, Toast.LENGTH_SHORT).show();
            }
        }).setRvItemLongClickListener(new RvItemLongClickListener() {
            @Override
            public void onRvItemLongClick(@NonNull View view, int position, int realPosition) {
                Toast.makeText(FourActivity.this, "长按position：" + position + " 点击数据列表position：" + realPosition, Toast.LENGTH_SHORT).show();
            }
        }).setRvItemCViewClickListener(new RvItemCViewClickListener() {
            @Override
            public void onRvItemCViewClick(@NonNull View view, int position, int realPosition) {
                Toast.makeText(FourActivity.this, "点击" + view.getId(), Toast.LENGTH_SHORT).show();
            }
        });

        HeadData headData = new HeadData("测试头部");
        zRecyclerView.addHeadView(new RvHeadView(this, headData));
        zRecyclerView.setRvHeadViewClickListener(new RvHeadViewClickListener() {
            @Override
            public void onRvHeadViewClick(@NonNull View view) {
                Toast.makeText(FourActivity.this, "点击头部", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onLoad() {
        // 更新
        updateFooterView(RvFooterViewStatue.STATUE_LOADING);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                zRecyclerView.setLoading(false);
                // 更新
                updateFooterView(RvFooterViewStatue.STATUE_DEFAULT);

                // 模仿加载数据
                for (int i = 0; i < 15; i++)
                    list.add(new ThreeBean("test-A" + i));
                setAdapter();

            }
        }, 5000);
    }

    // 刷新底部
    private void updateFooterView(RvFooterViewStatue footerViewStatue) {
        // 更新数据
        footerData.setRvFooterViewStatue(footerViewStatue);
        // 刷新界面
        zRecyclerView.refreshRvFooterView();
    }

    @Override
    public void onRefresh() {
        // 关闭下拉加载
        zRecyclerView.setLoading(false);
        // 模仿加载数据
        list.clear();
        for (int i = 0; i < 15; i++)
            list.add(new ThreeBean("test" + i));
        setAdapter();
        // 关闭下拉刷新
        swipeContainer.setRefreshing(false);
    }

    public void setAdapter() {
        // 添加适配器
        if (threeRvAdapter == null) {
            threeRvAdapter = new ThreeRvAdapter();
            threeRvAdapter.setData(list);
            zRecyclerView.setRvAdapter(threeRvAdapter);
        } else
            threeRvAdapter.refreshData(list);
    }
}
