package cc.ibooker.zrecyclerview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import cc.ibooker.zrecyclerview.recyclerview.RecyclerViewScrollListener;

/**
 * RecyclerViewScrollListener的综合使用
 * <p>
 * Created by 邹峰立 on 2018/3/6.
 */
public class SecondActivity extends AppCompatActivity implements
        RecyclerViewScrollListener.OnPositionTopDistanceListener,
        RecyclerViewScrollListener.OnScrollDistanceListener,
        RecyclerViewScrollListener.OnScrollStateChangedListener,
        RecyclerViewScrollListener.OnLoadListener {
    private RecyclerView recyclerView;
    private RecyclerViewScrollListener recyclerViewScrollListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        initView();
    }

    private void initView() {
        recyclerViewScrollListener = new RecyclerViewScrollListener();
        recyclerViewScrollListener.setOnLoadListener(this);
        recyclerViewScrollListener.setOnPositionTopDistanceListener(this, 5);
        recyclerViewScrollListener.setOnScrollDistanceListener(this);
        recyclerViewScrollListener.setOnScrollStateChangedListener(this);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.addOnScrollListener(recyclerViewScrollListener);
    }

    // 监听滚动距离
    @Override
    public void onScrollDistance(int dy) {

    }

    // 滚动状态改变事件
    @Override
    public void onScrollStateChanged(int newState) {

    }

    // 指定位置position到顶部的距离
    @Override
    public void onPositionTopDistance(int dy) {

    }

    // 加载更多
    @Override
    public void onLoad() {

    }
}
