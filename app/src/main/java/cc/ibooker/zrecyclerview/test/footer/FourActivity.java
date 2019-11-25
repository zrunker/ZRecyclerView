package cc.ibooker.zrecyclerview.test.footer;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import cc.ibooker.zrecyclerview.R;
import cc.ibooker.zrecyclerview.test.ThreeBean;
import cc.ibooker.zrecyclerview.test.ThreeRvAdapter;
import cc.ibooker.zrecyclerviewlib.RvItemClickListener;
import cc.ibooker.zrecyclerviewlib.RvItemLongClickListener;
import cc.ibooker.zrecyclerviewlib.RvScrollListener;
import cc.ibooker.zrecyclerviewlib.ZRecyclerView;
import cc.ibooker.zrecyclerviewlib.example.FooterData;
import cc.ibooker.zrecyclerviewlib.example.RvFooterView;
import cc.ibooker.zrecyclerviewlib.example.RvFooterViewStatue;

public class FourActivity extends Activity implements RvScrollListener.OnLoadListener {
    private ZRecyclerView zRecyclerView;
    private FooterData footerData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three);

        zRecyclerView = findViewById(R.id.zrv);
        zRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        // 添加加载更多监听
        zRecyclerView.setRvLoadListener(this);

        // 添加适配器
        ThreeRvAdapter threeRvAdapter = new ThreeRvAdapter();
        ArrayList<ThreeBean> list = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            list.add(new ThreeBean("test" + i));
        }
        threeRvAdapter.setData(list);
        zRecyclerView.setRvAdapter(threeRvAdapter);

        // 添加底部
        footerData = new FooterData(RvFooterViewStatue.STATUE_HIDDEN);
        zRecyclerView.addFooterView(new RvFooterView(this, footerData));

        zRecyclerView.setRvItemClickListener(new RvItemClickListener() {
            @Override
            public void onRvItemClick(View view, int position) {
                Toast.makeText(FourActivity.this, "点击position：" + position, Toast.LENGTH_SHORT).show();
            }
        }).setRvItemLongClickListener(new RvItemLongClickListener() {
            @Override
            public void onRvItemLongClick(View view, int position) {
                Toast.makeText(FourActivity.this, "长按position：" + position, Toast.LENGTH_SHORT).show();
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
}
