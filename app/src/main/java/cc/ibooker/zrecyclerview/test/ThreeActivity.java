package cc.ibooker.zrecyclerview.test;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;

import cc.ibooker.zrecyclerview.R;
import cc.ibooker.zrecyclerviewlib.ZRecyclerView;

public class ThreeActivity extends Activity {
    private ZRecyclerView zRecyclerView;
    private EmptyData emptyData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three);

        zRecyclerView = findViewById(R.id.zrv);
        zRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        // 添加空页面
        emptyData = new EmptyData("隐藏", EmptyEnum.STATUE_HIDDEN);
        zRecyclerView.addEmptyView(new EmptyView(this, emptyData));

        // 添加适配器
        ThreeRvAdapter threeRvAdapter = new ThreeRvAdapter();
        zRecyclerView.setRvAdapter(threeRvAdapter);

        // 测试更新
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // 更新
                refreshEmptyView(EmptyEnum.STATUE_DEFAULT);
            }
        }, 5000);

    }

    /**
     * 刷新空页面
     *
     * @param emptyEnum 空页面状态
     */
    private void refreshEmptyView(EmptyEnum emptyEnum) {
        if (emptyData != null) {
            // 更新数据
            emptyData.setEmptyEnum(emptyEnum);
            // 刷新界面
            zRecyclerView.refreshRvEmptyView();
        }
    }

}
