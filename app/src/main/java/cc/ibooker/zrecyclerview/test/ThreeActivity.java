package cc.ibooker.zrecyclerview.test;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;

import cc.ibooker.zrecyclerview.R;
import cc.ibooker.zrecyclerviewlib.ZRecyclerView;

public class ThreeActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three);

        final ZRecyclerView zRecyclerView = findViewById(R.id.zrv);
        zRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        // 添加空页面
        final EmptyData emptyData = new EmptyData("隐藏", EmptyEnum.STATUE_HIDDEN);
        zRecyclerView.addEmptyView(new EmptyView(this, emptyData));

        // 添加适配器
        ThreeRvAdapter threeRvAdapter = new ThreeRvAdapter();
        zRecyclerView.setRvAdapter(threeRvAdapter);

        // 测试更新
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // 更新数据
                emptyData.setEmptyEnum(EmptyEnum.STATUE_DEFAULT);
                // 刷新界面
                zRecyclerView.refreshRvEmptyView();
            }
        }, 5000);


    }


}
